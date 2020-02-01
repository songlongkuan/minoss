package io.javac.minoss.minosscommon.vertx;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.javac.minoss.minosscommon.constant.ResponeWrapperConst;
import io.javac.minoss.minosscommon.model.respone.ResponeWrapper;
import io.javac.minoss.minosscommon.toolkit.JsonUtils;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author pencilso
 * @date 2020/1/24 4:28 下午
 */
@Slf4j
public class VertxRespone {

    private final RoutingContext routingContext;

    private VertxRespone(RoutingContext routingContext) {
        this.routingContext = routingContext;
    }

    public static VertxRespone build(RoutingContext routingContext) {
        return new VertxRespone(routingContext);
    }


    public void respone(ResponeWrapper responeWrapper) {
        HttpServerResponse httpServerResponse = routingContext.response();
        httpServerResponse.putHeader("Content-Type", "text/json;charset=utf-8");
        try {
            httpServerResponse.end(JsonUtils.objectToJson(responeWrapper));
        } catch (JsonProcessingException e) {
            log.error("serialize object to json fail wrapper: [{}]", responeWrapper);
            e.printStackTrace();
        }
    }

    public void responeSuccess(Object data) {
        respone(new ResponeWrapper(ResponeWrapperConst.SUCCESS, data, "操作成功"));
    }


    public void responePage(IPage<?> iPage) {
        ResponeWrapper.ResponePageWrapper<List> responePageWrapper = new ResponeWrapper.ResponePageWrapper<List>();
        responePageWrapper
                .setTotalPages((int) iPage.getPages())
                .setTotalData((int) iPage.getTotal())
                .setCurrentPage((int) iPage.getCurrent())
                .setCode(ResponeWrapperConst.SUCCESS)
                .setMessage("操作成功")
                .setData(iPage.getRecords());
        respone(responePageWrapper);
    }

    public void responeState(boolean state) {
        if (state) {
            respone(ResponeWrapper.RESPONE_SUCCESS);
        } else {
            respone(ResponeWrapper.RESPONE_FAIL);
        }
    }


}
