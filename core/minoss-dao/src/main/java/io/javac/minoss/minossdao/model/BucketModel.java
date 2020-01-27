package io.javac.minoss.minossdao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.javac.minoss.minossdao.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * bucket 存储空间
 * </p>
 *
 * @author pencilso
 * @since 2020-01-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("minoss_bucket")
public class BucketModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * bucket 名称
     */
    private String bucketName;

    /**
     * bucket权限 1私有  2公共读
     */
    private Byte bucketRight;

    /**
     * bucket 存储到本地的路径
     */
    private String bucketStorePath;
}
