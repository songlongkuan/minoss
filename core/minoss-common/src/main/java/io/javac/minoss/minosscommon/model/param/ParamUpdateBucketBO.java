package io.javac.minoss.minosscommon.model.param;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * update bucket param model
 *
 * @author pencilso
 * @date 2020/1/27 5:54 下午
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ParamUpdateBucketBO extends ParamInsertBucketBO {
    /**
     * Bucket 业务唯一ID
     */
    private Long bucketMid;
}
