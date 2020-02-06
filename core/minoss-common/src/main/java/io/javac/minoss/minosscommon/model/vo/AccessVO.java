package io.javac.minoss.minosscommon.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * access data to view
 *
 * @author pencilso
 * @date 2020/2/6 8:30 下午
 */
@Accessors(chain = true)
@Data
public class AccessVO {
    /**
     * access mid
     */
    private Long mid;

    /**
     * 授权key
     */
    private String accessKey;
    /**
     * 授权secret
     */
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


    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;

}
