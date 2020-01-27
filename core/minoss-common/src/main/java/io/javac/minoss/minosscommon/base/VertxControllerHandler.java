package io.javac.minoss.minosscommon.base;

import io.javac.minoss.minosscommon.vertx.VertxRequest;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * Controller 返回的handle
 *
 * @author pencilso
 * @date 2020/1/23 11:06 下午
 */
public interface VertxControllerHandler extends Handler<RoutingContext> {

    @Override
    default void handle(RoutingContext event) {
        handle(VertxRequest.build(event));
    }

    void handle(VertxRequest vertxRequest) ;
}
