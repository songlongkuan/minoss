package io.javac.minoss.minossdao.service;

import io.javac.minoss.minossdao.model.BucketCollectModel;
import io.javac.minoss.minossdao.base.BaseIService;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * Bucket 汇总表 （各数据的统计） 服务类
 * </p>
 *
 * @author pencilso
 * @since 2020-01-25
 */
public interface BucketCollectService extends BaseIService<BucketCollectModel> {

    /**
     * 批量获取汇总
     * @param bucketMids
     * @return
     */
    List<BucketCollectModel> getByBucketMids(Collection<Long> bucketMids);

    /**
     * 获取单个汇总
     * @param mid
     * @return
     */
    BucketCollectModel getByBucketMid(Long bucketMid);

}
