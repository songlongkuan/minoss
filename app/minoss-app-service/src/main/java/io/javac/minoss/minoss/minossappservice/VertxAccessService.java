package io.javac.minoss.minoss.minossappservice;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.javac.minoss.minosscommon.model.param.ParamInsertAccessBO;
import io.javac.minoss.minosscommon.model.vo.AccessVO;
import io.javac.minoss.minossdao.model.AccessModel;

import javax.validation.Valid;

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
    AccessVO dto2vo(AccessModel accessModel);

    /**
     * insert new access for ParamInsertAccessBO
     * valid param
     *
     * @param paramInsertAccessBO param
     * @return
     */
    boolean insert(@Valid ParamInsertAccessBO paramInsertAccessBO);
}