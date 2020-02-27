package io.javac.minoss.minossappadmin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.javac.minoss.minoss.minossappservice.VertxBucketService;
import io.javac.minoss.minosscommon.annotation.RequestBody;
import io.javac.minoss.minosscommon.annotation.RequestMapping;
import io.javac.minoss.minossservice.base.VertxControllerHandler;
import io.javac.minoss.minosscommon.enums.request.RequestMethod;
import io.javac.minoss.minosscommon.exception.MinOssMessageException;
import io.javac.minoss.minosscommon.model.param.ParamInsertBucketBO;
import io.javac.minoss.minosscommon.model.param.ParamUpdateBucketBO;
import io.javac.minoss.minosscommon.model.vo.BucketVO;
import io.javac.minoss.minossdao.model.BucketModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * bucket controller
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
     * query bucket list for page
     *
     * @return
     */
    @RequestMapping("querybucketlist")
    public VertxControllerHandler queryBucketList() {
        return vertxRequest -> {
            Page<BucketVO> bucketVOPage = vertxBucketService.listPageVO(vertxRequest.buildPage());
            vertxRequest.buildVertxRespone().responePage(bucketVOPage);
        };
    }

    /**
     * insert new bucket
     *
     * @return
     */
    @RequestBody
    @RequestMapping(value = "insertbucket", method = RequestMethod.POST)
    public VertxControllerHandler insertBucket() {
        return vertxRequest -> {
            ParamInsertBucketBO paramInsertBucketBO = vertxRequest.getBodyJsonToBean(ParamInsertBucketBO.class);
            boolean success = vertxBucketService.insert(paramInsertBucketBO);
            vertxRequest.buildVertxRespone().responeState(success);
        };
    }

    /**
     * update old bucket
     *
     * @return
     */
    @RequestBody
    @RequestMapping(value = "updatebucket", method = RequestMethod.POST)
    public VertxControllerHandler updateBucket() {
        return vertxRequest -> {
            ParamUpdateBucketBO paramUpdateBucketBO = vertxRequest.getBodyJsonToBean(ParamUpdateBucketBO.class);
            BucketModel bucketModel = vertxBucketService.getBucketModel(paramUpdateBucketBO.getBucketMid());
            //check bucket is exists
            if (bucketModel == null) throw new MinOssMessageException("该bucket 不存在 更新失败！");
            //update bucket model
            boolean success = vertxBucketService.update(bucketModel.getVersion(), paramUpdateBucketBO);
            vertxRequest.buildVertxRespone().responeState(success);
        };
    }

    /**
     * query bucket detail
     *
     * @return
     */
    @RequestMapping("querybucketdetail")
    public VertxControllerHandler queryBucketDetail() {
        return vertxRequest -> {
            String bucketMid = vertxRequest.getParamNotBlank("bucetMid");
            BucketVO bucketVO = vertxBucketService.getBucketVOModel(Long.valueOf(bucketMid));
            vertxRequest.buildVertxRespone().responeSuccess(bucketVO);
        };
    }
}
