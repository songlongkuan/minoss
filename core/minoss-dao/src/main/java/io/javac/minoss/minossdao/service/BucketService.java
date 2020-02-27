package io.javac.minoss.minossdao.service;

import io.javac.minoss.minossdao.model.BucketModel;
import io.javac.minoss.minossdao.base.BaseIService;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * bucket 存储空间 服务类
 * </p>
 *
 * @author pencilso
 * @since 2020-01-25
 */
public interface BucketService extends BaseIService<BucketModel> {
    /**
     * query bucket model by bucket name
     *
     * @param bucketName where bucket name
     * @return
     */
    BucketModel getByBucketName(@NotBlank String bucketName);
}
