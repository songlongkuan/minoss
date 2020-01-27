package io.javac.minoss.minossappadmin.controller;

import io.javac.minoss.minossappadmin.service.VertxBucketService;
import io.javac.minoss.minosscommon.annotation.RequestMapping;
import io.javac.minoss.minosscommon.base.VertxControllerHandler;
import io.javac.minoss.minosscommon.enums.RequestMethod;
import io.javac.minoss.minosscommon.model.param.ParamInsertBucketBO;
import io.javac.minoss.minosscommon.model.param.ParamUpdateBucketBO;
import io.javac.minoss.minosscommon.model.vo.BucketVO;
import io.javac.minoss.minossdao.model.BucketModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 存储空间 控制器
 *
 * @author pencilso
 * @date 2020/1/25 10:14 下午
 */
@Component
@RequestMapping("/api/admin/bucket")
public class VertxBucketController {
    @Autowired
    private VertxBucketService vertxBucketService;

    /**
     * 查询存储空间列表
     *
     * @return
     */
    @RequestMapping("querybucketlist")
    public VertxControllerHandler queryBucketList() {
        return vertxRequest -> {
            List<BucketVO> bucketVOS = vertxBucketService.listPage(vertxRequest.buildPage());
            vertxRequest.buildVertxRespone().responeSuccess(bucketVOS);
        };
    }

    /**
     * 添加新的bucket
     *
     * @return
     */
    @RequestMapping(value = "insertbucket", method = RequestMethod.POST)
    public VertxControllerHandler insertBucket() {
        return vertxRequest -> {
            ParamInsertBucketBO paramInsertBucketBO = vertxRequest.getParamBean(ParamInsertBucketBO.class);
            boolean success = vertxBucketService.insert(paramInsertBucketBO);
            vertxRequest.buildVertxRespone().responeState(success);
        };
    }

    /**
     * 更新bucket
     *
     * @return
     */
    @RequestMapping(value = "updatebucket", method = RequestMethod.POST)
    public VertxControllerHandler updateBucket() {
        return vertxRequest -> {
            ParamUpdateBucketBO paramUpdateBucketBO = vertxRequest.getParamBean(ParamUpdateBucketBO.class);
            BucketModel bucketModel = vertxBucketService.getBucketModel(paramUpdateBucketBO.getMid());
            //更新bucket model
            boolean success = vertxBucketService.update(bucketModel.getVersion(), paramUpdateBucketBO);
            vertxRequest.buildVertxRespone().responeState(success);
        };
    }

}
