package io.javac.minoss.minosscommon.base;

import io.javac.minoss.minosscommon.exception.MinOssTokenInvalidException;
import io.javac.minoss.minosscommon.vertx.VertxRequest;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * 拦截器父类
 *
 * @author pencilso
 * @date 2020/1/25 11:05 上午
 */
public abstract class BaseInterceptHandler implements Handler<RoutingContext> {


    @Override
    public void handle(RoutingContext event) {
        String accesstoken = VertxRequest.build(event).getAccessToken().orElseThrow(MinOssTokenInvalidException::new);
        //next
        handle(accesstoken, event);
    }

    /**
     * 处理token鉴权
     *
     * @param accesstoken    token
     * @param routingContext 上下文对象
     */
    public abstract void handle(String accesstoken, RoutingContext routingContext);
}
