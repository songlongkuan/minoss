package io.javac.minoss.minossdao.service;

import io.javac.minoss.minossdao.model.AccessBucketModel;
import io.javac.minoss.minossdao.base.BaseIService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author pencilso
 * @since 2020-02-06
 */
public interface AccessBucketService extends BaseIService<AccessBucketModel> {

    /**
     * query accessbucket model from accessmid and bucket mid
     *
     * @param accessMid access mid
     * @param bucketMid bucket mid
     * @return
     */
    AccessBucketModel getByAccessMidAndBucketMid(Long accessMid, Long bucketMid);
}
