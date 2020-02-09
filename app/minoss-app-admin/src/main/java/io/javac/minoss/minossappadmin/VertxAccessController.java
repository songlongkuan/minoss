package io.javac.minoss.minossappadmin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.javac.minoss.minoss.minossappservice.VertxAccessService;
import io.javac.minoss.minoss.minossappservice.VertxBucketService;
import io.javac.minoss.minosscommon.annotation.RequestBody;
import io.javac.minoss.minosscommon.annotation.RequestMapping;
import io.javac.minoss.minosscommon.base.VertxControllerHandler;
import io.javac.minoss.minosscommon.enums.RequestMethod;
import io.javac.minoss.minosscommon.exception.MinOssMessageException;
import io.javac.minoss.minosscommon.model.param.ParamInsertAccessBO;
import io.javac.minoss.minosscommon.model.param.ParamUpdateAccessBO;
import io.javac.minoss.minosscommon.model.param.ParamUpdateAccessBucketBO;
import io.javac.minoss.minosscommon.model.vo.AccessVO;
import io.javac.minoss.minosscommon.toolkit.Kv;
import io.javac.minoss.minossdao.model.AccessBucketModel;
import io.javac.minoss.minossdao.model.AccessModel;
import io.javac.minoss.minossdao.model.BucketModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author pencilso
 * @date 2020/2/6 8:20 下午
 */
@Slf4j
@Component
@RequestMapping("/api/admin/access")
public class VertxAccessController {

    @Autowired
    private VertxAccessService vertxAccessService;
    @Autowired
    private VertxBucketService vertxBucketService;

    /**
     * query access list for page
     *
     * @return
     */
    @RequestMapping("queryaccesslist")
    public VertxControllerHandler queryAccessList() {
        return vertxRequest -> {
            Page<AccessVO> accessVOPage = vertxAccessService.listPageVO(vertxRequest.buildPage());
            vertxRequest.buildVertxRespone().responePage(accessVOPage);
        };
    }

    /**
     * insert new access
     *
     * @return
     */
    @RequestBody
    @RequestMapping(value = "insertaccess", method = RequestMethod.POST)
    public VertxControllerHandler insertAccess() {
        return vertxRequest -> {
            ParamInsertAccessBO paramInsertAccessBO = vertxRequest.getBodyJsonToBean(ParamInsertAccessBO.class);
            boolean success = vertxAccessService.insert(paramInsertAccessBO);
            vertxRequest.buildVertxRespone().responeState(success);
        };
    }

    /**
     * update db access
     *
     * @return
     */
    @RequestBody
    @RequestMapping(value = "updateaccess", method = RequestMethod.POST)
    public VertxControllerHandler updateAccess() {
        return vertxRequest -> {
            ParamUpdateAccessBO paramUpdateAccessBO = vertxRequest.getBodyJsonToBean(ParamUpdateAccessBO.class);
            AccessModel accessModel = vertxAccessService.getAccessModel(paramUpdateAccessBO.getAccessMid()).orElseThrow(() -> new MinOssMessageException("该 Access 不存在"));
            boolean success = vertxAccessService.update(accessModel.getVersion(), paramUpdateAccessBO);
            vertxRequest.buildVertxRespone().responeState(success);
        };
    }

    /**
     * query access detail
     *
     * @return
     */
    @RequestMapping("queryaccessdetail")
    public VertxControllerHandler queryAccessDetail() {
        return vertxRequest -> {
            String accessMid = vertxRequest.getParamNotBlank("accessMid");
            AccessVO accessVO = vertxAccessService.getAccessVOModel(Long.valueOf(accessMid)).orElseThrow(() -> new MinOssMessageException("该 Access 不存在"));
            vertxRequest.buildVertxRespone().responeSuccess(accessVO);
        };
    }

    /**
     * delete access
     *
     * @return
     */
    @RequestBody
    @RequestMapping(value = "deleteaccess", method = RequestMethod.POST)
    public VertxControllerHandler deleteAccess() {
        return vertxRequest -> {
            Long accessMid = Long.valueOf(vertxRequest.getParamNotBlank("accessMid"));
            AccessModel accessModel = vertxAccessService.getAccessModel(accessMid).orElseThrow(() -> new MinOssMessageException("该Access 不存在，无需删除！"));
            boolean success = vertxAccessService.deleteAccess(accessMid);
            vertxRequest.buildVertxRespone().responeState(success);
        };
    }

    /**
     * query acess bucket
     *
     * @return
     */
    @RequestMapping("queryaccessbucketlist")
    public VertxControllerHandler queryAccessBucketList() {
        return vertxRequest -> {
            Long accessMid = Long.valueOf(vertxRequest.getParamNotBlank("accessMid"));
            List<AccessBucketModel> alreadyAccessBucketList = vertxAccessService.getAccessBucketList(accessMid);
            //query all bucket model
            Map<Long, BucketModel> bucketModelMap = vertxBucketService.getBucketModelAll().stream().collect(Collectors.toMap(BucketModel::getMid, model -> model));

            List<Kv<String, Object>> responeAlreadyAccess = new LinkedList<>();
            List<Kv<String, Object>> responeAllAccess = new LinkedList<>();

            // assembly all no access bucket data
            bucketModelMap.values().forEach(bucketModel -> {
                responeAllAccess.add(Kv.stringObjectKv().set("value", bucketModel.getMid()).set("title", bucketModel.getBucketName()));
            });

            // assembly already access bucket data
            alreadyAccessBucketList.forEach(accessBucketModel -> {
                BucketModel bucketModel = bucketModelMap.get(accessBucketModel.getBucketMid());
                responeAlreadyAccess.add(Kv.stringObjectKv().set("value", bucketModel.getMid()).set("title", bucketModel.getBucketName()));
            });
            vertxRequest.buildVertxRespone().responeSuccess(Kv.stringObjectKv().set("allaccess", responeAllAccess).set("alreadyaccess", responeAlreadyAccess));
        };
    }

    @RequestBody
    @RequestMapping(value = "updateaccessbucket", method = RequestMethod.POST)
    public VertxControllerHandler updateAccessBucket() {
        return vertxRequest -> {
            ParamUpdateAccessBucketBO paramUpdateAccessBucketBO = vertxRequest.getBodyJsonToBean(ParamUpdateAccessBucketBO.class);
            boolean success = vertxAccessService.updateAccessBucket(paramUpdateAccessBucketBO);
            vertxRequest.buildVertxRespone().responeState(success);
        };
    }
}