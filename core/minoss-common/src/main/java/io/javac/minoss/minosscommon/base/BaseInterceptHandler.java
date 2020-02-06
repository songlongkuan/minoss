package io.javac.minoss.minosscommon.base;

import io.javac.minoss.minosscommon.exception.MinOssTokenInvalidException;
import io.javac.minoss.minosscommon.vertx.VertxRequest;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * intercept handler supper
 *
 * @author pencilso
 * @date 2020/1/25 11:05 上午
 */
public abstract class BaseInterceptHandler implements Handler<RoutingContext> {


    @Override
    public void handle(RoutingContext event) {
        VertxRequest vertxRequest = VertxRequest.build(event);
        String accesstoken = vertxRequest.getAccessToken().orElseThrow(MinOssTokenInvalidException::new);
        //next
        handle(accesstoken, event, vertxRequest);
    }

    /**
     * check accesstoken
     *
     * @param accesstoken  token
     * @param vertxRequest vertxRequest
     */
    public abstract void handle(String accesstoken, RoutingContext routingContext, VertxRequest vertxRequest);
}
