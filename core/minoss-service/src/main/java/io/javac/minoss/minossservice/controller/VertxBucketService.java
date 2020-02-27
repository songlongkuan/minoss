package io.javac.minoss.minossservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.javac.minoss.minosscommon.model.param.ParamInsertBucketBO;
import io.javac.minoss.minosscommon.model.param.ParamUpdateBucketBO;
import io.javac.minoss.minosscommon.model.vo.BucketVO;
import io.javac.minoss.minossdao.model.BucketCollectModel;
import io.javac.minoss.minossdao.model.BucketModel;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

/**
 * controller bucket service
 *
 * @author pencilso
 * @date 2020/1/25 10:16 下午
 */
public interface VertxBucketService {
    /**
     * query bucket list for page
     *
     * @param page
     * @return bucket vo model
     */
    Page<BucketVO> listPageVO(Page page);

    /**
     * add new bucket
     *
     * @param paramInsertBucketBO param
     * @return
     */
    boolean insert(@Valid ParamInsertBucketBO paramInsertBucketBO);

    /**
     * update bucket
     *
     * @param version             lock version
     * @param paramUpdateBucketBO update param
     * @return
     */
    boolean update(Integer version, @Valid ParamUpdateBucketBO paramUpdateBucketBO);

    /**
     * query bucket model
     *
     * @param bucketMid bucket唯一ID
     * @return
     */
    BucketModel getBucketModel(@NotNull Long bucketMid);

    /**
     * query bucket model list
     *
     * @param bucketMid
     * @return
     */
    List<BucketModel> getBucketModel(@NotNull Collection<Long> bucketMid);


    /**
     * get bucket collect model by bucketMid
     *
     * @param bucketMid
     * @return
     */
    BucketCollectModel getBucketCollect(@NotNull Long bucketMid);

    /**
     * get bucket vo model by bucketMid
     *
     * @param bucketMid
     * @return
     */
    BucketVO getBucketVOModel(@NotNull Long bucketMid);

    /**
     * query all bucket model
     *
     * @return
     */
    List<BucketModel> getBucketModelAll();

    /**
     * query bucket model and put caffine cache
     *
     * @param bucketName where bucket name
     * @return
     */
    BucketModel getBucketModelByCache(@NotBlank String bucketName);

    /**
     * incr bucket file size
     *
     * @param bucketMid where bucket mid
     * @param incr      incr file size
     * @return
     */
    boolean incrBucketCollectFileSize(Long bucketMid, int incr);

    /**
     * incr bucket used size
     *
     * @param bucketMid where bucket mid
     * @param length    incr length
     */
    boolean incrBucketCollectUsedSize(Long bucketMid, long length);
}
