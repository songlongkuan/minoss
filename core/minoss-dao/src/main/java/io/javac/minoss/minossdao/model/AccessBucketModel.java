package io.javac.minoss.minossdao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.javac.minoss.minossdao.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author pencilso
 * @since 2020-02-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("minoss_access_bucket")
public class AccessBucketModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * bucket 业务唯一ID
     */
    private Long bucketMid;

    /**
     * access 业务唯一ID
     */
    private Long accessMid;

    /**
     * 排序
     */
    private Integer orderSort;


}
