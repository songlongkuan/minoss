package io.javac.minoss.minosscommon.cache;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @param <K> 键值对  key
 * @param <V> 键值对  value
 * @author pencilso
 * @date 2020/1/23 8:05 下午
 */

public interface CacheStore<K, V> {

    /**
     * 通过key 获取value对象
     *
     * @param key
     * @return 返回缓存 value
     */
    Optional<V> get(@NotNull K key);

    /**
     * 放入一条缓存
     *
     * @param key      键值
     * @param value    数据
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     */
    void put(@NotNull K key, @NotNull V value, Long timeout,  TimeUnit timeUnit);

    /**
     * 放入一条缓存  并且设置默认的过期时间
     *
     * @param key   键值
     * @param value 数据
     */
    void put(@NotNull K key, @NotNull V value);

    /**
     * 删除一条缓存
     *
     * @param key
     */
    void delete(@NotNull K key);
}
