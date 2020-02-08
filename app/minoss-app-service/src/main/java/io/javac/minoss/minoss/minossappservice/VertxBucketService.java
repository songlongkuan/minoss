package io.javac.minoss.minoss.minossappservice;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.javac.minoss.minosscommon.model.param.ParamInsertBucketBO;
import io.javac.minoss.minosscommon.model.param.ParamUpdateBucketBO;
import io.javac.minoss.minosscommon.model.vo.BucketVO;
import io.javac.minoss.minossdao.model.BucketCollectModel;
import io.javac.minoss.minossdao.model.BucketModel;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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


}
