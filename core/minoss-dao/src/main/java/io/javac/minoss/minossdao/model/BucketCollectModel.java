package io.javac.minoss.minossdao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.javac.minoss.minossdao.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * Bucket 汇总表 （各数据的统计）
 * </p>
 *
 * @author pencilso
 * @since 2020-01-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("minoss_bucket_collect")
public class BucketCollectModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * bucket的业务ID
     */
    private Long bucketMid;

    /**
     * 存储空间已使用大小
     */
    private Long storeUsedSize;

    /**
     * 文件存储数量
     */
    private Long storeFileSize;


}
