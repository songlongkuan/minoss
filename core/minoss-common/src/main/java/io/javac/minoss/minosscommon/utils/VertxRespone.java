package io.javac.minoss.minosscommon.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.javac.minoss.minosscommon.constant.ResponeWrapperConst;
import io.javac.minoss.minosscommon.model.respone.ResponeWrapper;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author pencilso
 * @date 2020/1/24 4:28 下午
 */
@Slf4j
public class VertxRespone {


    public static void respone(RoutingContext routingContext, ResponeWrapper responeWrapper) {
        respone(routingContext.response(), responeWrapper);
    }

    public static void respone(HttpServerResponse httpServerResponse, ResponeWrapper responeWrapper) {
        httpServerResponse.putHeader("Content-Type", "text/json;charset=utf-8");
        try {
            httpServerResponse.end(JsonUtils.objectToJson(responeWrapper));
        } catch (JsonProcessingException e) {
            log.error("serialize object to json fail wrapper: [{}]", responeWrapper);
            e.printStackTrace();
        }
    }

    public static void responeSuccess(RoutingContext routingContext, Object data) {
        responeSuccess(routingContext.response(), data);
    }

    public static void responeSuccess(HttpServerResponse httpServerResponse, Object data) {
        respone(httpServerResponse, new ResponeWrapper(ResponeWrapperConst.SUCCESS, data, "操作成功"));
    }
}
