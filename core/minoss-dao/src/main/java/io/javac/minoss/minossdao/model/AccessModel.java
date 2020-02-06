package io.javac.minoss.minossdao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.javac.minoss.minossdao.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * bucket 授权
 * </p>
 *
 * @author pencilso
 * @since 2020-02-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("minoss_access")
public class AccessModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    private String accessKey;

    private String accessSecret;

    /**
     * 是否可以上传 0：不可以  1：可以
     */
    private Boolean ifBucketPut;

    /**
     * 是否可以删除  0：不可以  1：可以
     */
    private Boolean ifBucketDel;

    /**
     * 是否可以获取 0：不可以  1：可以
     */
    private Boolean ifBucketGet;

    /**
     * 是否可以编辑  0：不可以 1：可以
     */
    private Boolean ifBucketEdit;


}
