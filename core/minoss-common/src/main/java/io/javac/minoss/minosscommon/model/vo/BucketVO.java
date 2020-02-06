package io.javac.minoss.minosscommon.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * bucket data to view model
 *
 * @author pencilso
 * @date 2020/1/25 10:20 下午
 */
@Data
public class BucketVO {
    /**
     * bucket的业务ID
     */
    private Long bucketMid;
    /**
     * bucket 存储到本地的路径
     */
    private String bucketStorePath;
    /**
     * bucket 名称
     */
    private String bucketName;

    /**
     * bucket权限 1私有  2公共读
     */
    private Byte bucketRight;
    /**
     * 存储空间已使用大小
     */
    private Long storeUsedSize;

    /**
     * 文件存储数量
     */
    private Long storeFileSize;

    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;
}