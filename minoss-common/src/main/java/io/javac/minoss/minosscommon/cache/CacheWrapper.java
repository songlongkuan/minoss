package io.javac.minoss.minosscommon.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author pencilso
 * @date 2020/1/23 8:16 下午
 */
@Accessors(chain = true)
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CacheWrapper<V> {
    /**
     * 缓存的数据
     */
    private V data;

    /**
     * 过期时间
     */
    private long expireAt;
    /**
     * 创建时间
     */
    private long createAt;
}
