package io.javac.minoss.minossservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.javac.minoss.minosscommon.model.param.ParamInsertAccessBO;
import io.javac.minoss.minosscommon.model.param.ParamUpdateAccessBO;
import io.javac.minoss.minosscommon.model.param.ParamUpdateAccessBucketBO;
import io.javac.minoss.minosscommon.model.vo.AccessVO;
import io.javac.minoss.minossdao.model.AccessBucketModel;
import io.javac.minoss.minossdao.model.AccessModel;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * @author pencilso
 * @date 2020/2/6 8:23 下午
 */
public interface VertxAccessService {


    /**
     * query access list for page
     *
     * @param page
     * @return access vo model
     */
    Page<AccessVO> listPageVO(Page page);

    /**
     * db data model Transformation view model
     *
     * @param accessModel
     * @return
     */
    @NotNull
    AccessVO dto2vo(@NotNull AccessModel accessModel);

    /**
     * insert new access for ParamInsertAccessBO
     * valid param
     *
     * @param paramInsertAccessBO param
     * @return
     */
    boolean insert(@Valid ParamInsertAccessBO paramInsertAccessBO);

    /**
     * query access model to view model
     *
     * @param accessMid
     * @return
     */
    Optional<AccessVO> getAccessVOModel(@NotNull Long accessMid);

    /**
     * query access model
     *
     * @param accessMid
     * @return
     */
    Optional<AccessModel> getAccessModel(@NotNull Long accessMid);

    /**
     * update access db
     * valid param
     *
     * @param version             lock version
     * @param paramUpdateAccessBO param model
     * @return
     */
    boolean update(@NotNull Integer version, @Valid ParamUpdateAccessBO paramUpdateAccessBO);

    /**
     * delete access db
     *
     * @param accessMid access mid
     * @return
     */
    boolean deleteAccess(@NotNull Long accessMid);

    /**
     * query access bucket and transformation  view model
     *
     * @param accessMid
     * @return
     */
    List<AccessBucketModel> getAccessBucketList(@NotNull Long accessMid);

    /**
     * update access bucket
     *
     * @param paramUpdateAccessBucketBO update param model
     * @return
     */
    boolean updateAccessBucket(@Valid ParamUpdateAccessBucketBO paramUpdateAccessBucketBO);

    /**
     * access key secret login
     *
     * @param accessKey    accessKey
     * @param accessSecret accessSecret
     * @return
     */
    String accessLogin(@NotNull String accessKey, @NotNull String accessSecret);
}