package io.javac.minoss.minoss.minossappservice;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.javac.minoss.minosscommon.model.param.ParamInsertBucketBO;
import io.javac.minoss.minosscommon.model.param.ParamUpdateBucketBO;
import io.javac.minoss.minosscommon.model.vo.BucketVO;
import io.javac.minoss.minossdao.model.BucketModel;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * vertx 接口 Bucket 接口
 *
 * @author pencilso
 * @date 2020/1/25 10:16 下午
 */
public interface VertxBucketService {
    /**
     * 查询列表 并且分页
     *
     * @param page
     * @return
     */
    Page<BucketVO> listPage(Page page);

    /**
     * 查询新的bucket
     *
     * @param paramInsertBucketBO 新增bucket的参数
     * @return
     */
    boolean insert(@Valid ParamInsertBucketBO paramInsertBucketBO);

    /**
     * 更新bucket
     *
     * @param version             乐观锁版本
     * @param paramUpdateBucketBO 更新bucket的参数
     * @return
     */
    boolean update(Integer version, @Valid ParamUpdateBucketBO paramUpdateBucketBO);

    /**
     * 获取bucket model
     *
     * @param mid bucket唯一ID
     * @return
     */
    BucketModel getBucketModel(@NotNull Long mid);

}
