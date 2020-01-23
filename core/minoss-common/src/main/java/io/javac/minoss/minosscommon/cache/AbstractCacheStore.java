package io.javac.minoss.minosscommon.cache;

import io.javac.minoss.minosscommon.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author pencilso
 * @date 2020/1/23 8:16 下午
 */
@Slf4j
public abstract class AbstractCacheStore<K, V> implements CacheStore<K, V> {

    /**
     * 获取wrapper缓存
     *
     * @param key 键值
     * @return 返回缓存数据
     */
    @NonNull
    public abstract Optional<CacheWrapper<V>> getInternal(@NonNull K key);

    /**
     * 放入一个wrapper缓存
     *
     * @param key          键值
     * @param cacheWrapper 缓存数据
     */
    public abstract void putInternal(@NonNull K key, @NonNull CacheWrapper<V> cacheWrapper);


    @Override
    public @NotNull Optional<V> get(@NotNull K key) {

        return getInternal(key).map(cacheWrapper -> {
            // 检查是否过期
            if (cacheWrapper.getExpireAt() != -1 && cacheWrapper.getExpireAt() > DateUtils.nowMS()) {
                // 缓存过期  删除
                log.warn("cache key: [{}] has been expired", key);
                // 删除key
                delete(key);

                return null;
            }
            return cacheWrapper.getData();
        });
    }

    @Override
    public void put(@NotNull K key, @NotNull V value, Long timeout, TimeUnit timeUnit) {
        CacheWrapper<V> objectCacheWrapper = new CacheWrapper<V>()
                .setData(value)
                .setCreateAt(DateUtils.nowMS());
        if (timeout != null && timeUnit != null) {
            Long expireAt = DateUtils.add(objectCacheWrapper.getCreateAt(), timeout, timeUnit);
            objectCacheWrapper.setExpireAt(expireAt);
        }
        putInternal(key, objectCacheWrapper);
    }

    @Override
    public void put(@NotNull K key, @NotNull V value) {
        put(key, value, null, null);
    }

}
