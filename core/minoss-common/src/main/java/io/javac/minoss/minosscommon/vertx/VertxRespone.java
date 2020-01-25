package io.javac.minoss.minosscommon.vertx;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.javac.minoss.minosscommon.constant.ResponeWrapperConst;
import io.javac.minoss.minosscommon.model.respone.ResponeWrapper;
import io.javac.minoss.minosscommon.utils.JsonUtils;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

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
}
