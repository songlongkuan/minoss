package io.javac.minoss.minosscommon.model.param;

import lombok.Data;

/**
 * 更新Bucket 参数
 *
 * @author pencilso
 * @date 2020/1/27 5:54 下午
 */
@Data
public class ParamUpdateBucketBO extends ParamInsertBucketBO {
    /**
     * Bucket 业务唯一ID
     */
    private Long mid;
}
