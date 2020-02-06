package io.javac.minoss.minossbridge.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.javac.minoss.minosscommon.cache.CacheWrapper;
import io.javac.minoss.minosscommon.cache.StringCacheStore;
import io.javac.minoss.minosscommon.config.MinOssProperties;
import io.javac.minoss.minosscommon.toolkit.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.iq80.leveldb.*;
import org.iq80.leveldb.impl.Iq80DBFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 利用Leveldb 实现缓存
 *
 * @author pencilso
 * @date 2020/1/23 8:50 下午
 */
@Validated
@Slf4j
public class LevelCacheStore extends StringCacheStore {
    /**
     * Cleaner schedule period. (ms)
     */
    private final static long PERIOD = 60 * 1000;

    private static DB leveldb;


    private Timer timer;

    @Autowired
    private MinOssProperties minOssProperties;

    @PostConstruct
    public void init() {
        if (leveldb != null) return;
        try {
            //work path
            String leveldbPath = minOssProperties.getWorkDir() + ".leveldb";
            File folder = new File(leveldbPath);
            log.info("leveldb store path: [{}]", leveldbPath);
            folder.mkdirs();
            DBFactory factory = new Iq80DBFactory();
            Options options = new Options();
            options.createIfMissing(true);
            //open leveldb store folder
            leveldb = factory.open(folder, options);
            timer = new Timer();
            timer.scheduleAtFixedRate(new CacheExpiryCleaner(), 0, PERIOD);
        } catch (Exception ex) {
            log.error("init leveldb error ", ex);
        }
    }

    /**
     * 销毁
     */
    @PreDestroy
    public void preDestroy() {
        try {
            leveldb.close();
            timer.cancel();
        } catch (IOException e) {
            log.error("close leveldb error ", e);
        }
    }


    @Override
    public Optional<CacheWrapper<String>> getInternal(@NotNull String key) {
        Assert.hasText(key, "Cache key must not be blank");
        byte[] bytes = leveldb.get(stringToBytes(key));
        if (bytes != null) {
            String valueJson = bytesToString(bytes);
            return StringUtils.isEmpty(valueJson) ? Optional.empty() : jsonToCacheWrapper(valueJson);
        }
        return Optional.empty();
    }

    @Override
    public void putInternal(@NotNull String key, @NotNull CacheWrapper<String> cacheWrapper) {
        try {
            leveldb.put(
                    stringToBytes(key),
                    stringToBytes(JsonUtils.objectToJson(cacheWrapper))
            );
        } catch (JsonProcessingException e) {
            log.warn("Put cache fail json2object key: [{}] value:[{}]", key, cacheWrapper);
        }
        log.debug("Cache key: [{}], original cache wrapper: [{}]", key, cacheWrapper);
    }


    @Override
    public void delete(String key) {
        leveldb.delete(stringToBytes(key));
        log.debug("cache remove key: [{}]", key);
    }


    private byte[] stringToBytes(@NotBlank String str) {
        return str.getBytes(Charset.defaultCharset());
    }

    private String bytesToString(@NotNull byte[] bytes) {
        return new String(bytes, Charset.defaultCharset());
    }

    private Optional<CacheWrapper<String>> jsonToCacheWrapper(@NotBlank String json) {
        CacheWrapper<String> cacheWrapper = null;
        try {
            cacheWrapper = JsonUtils.jsonToObject(json, CacheWrapper.class);
        } catch (IOException e) {
            e.printStackTrace();
            log.debug("erro json to wrapper value bytes: [{}]", json, e);
        }
        return Optional.ofNullable(cacheWrapper);
    }

    private class CacheExpiryCleaner extends TimerTask {

        @Override
        public void run() {
            //batch
            WriteBatch writeBatch = leveldb.createWriteBatch();

            DBIterator iterator = leveldb.iterator();
            long currentTimeMillis = System.currentTimeMillis();
            while (iterator.hasNext()) {
                Map.Entry<byte[], byte[]> next = iterator.next();
                if (next.getKey() == null || next.getValue() == null) {
                    continue;
                }

                String valueJson = bytesToString(next.getValue());
                Optional<CacheWrapper<String>> stringCacheWrapper = StringUtils.isEmpty(valueJson) ? Optional.empty() : jsonToCacheWrapper(valueJson);
                if (stringCacheWrapper.isPresent()) {
                    //get expireat time
                    long expireAtTime = stringCacheWrapper.map(CacheWrapper::getExpireAt)
                            .orElse(0L);
                    //if expire
                    if (expireAtTime > 0 && currentTimeMillis > expireAtTime) {
                        writeBatch.delete(next.getKey());
                        log.debug("deleted the cache: [{}] for expiration", bytesToString(next.getKey()));
                    }
                }
            }
            leveldb.write(writeBatch);
        }
    }
}

