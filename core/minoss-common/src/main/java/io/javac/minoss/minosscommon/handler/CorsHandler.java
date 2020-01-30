package io.javac.minoss.minosscommon.handler;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

/**
 * Cors 跨域
 *
 * @author pencilso
 * @date 2020/1/30 10:47 下午
 */
public class CorsHandler implements Handler<RoutingContext> {

    private String allowOrigin = "https://minoss.pencilso.cn";
    private String allowMethods = "GET,POST,PUT,DELETE,OPTIONS";
    private String allowCredentials = "true";
    private String allowHeaders = "Content-Type,accesstoken";

    @Override
    public void handle(RoutingContext event) {
        HttpServerResponse response = event.response();
        String currentOrigin = event.request().getHeader("Origin");
        if (!allowOrigin.equals("*")) {
            String[] allowOriginArray = allowOrigin.split(",");
            for (String origin : allowOriginArray) {
                if (origin.equals(currentOrigin)) {
                    currentOrigin = origin;
                    break;
                } else {
                    currentOrigin = null;
                }
            }
        }
        response.putHeader("Access-Control-Allow-Origin", currentOrigin)
                .putHeader("Access-Control-Allow-Methods", allowMethods)
                .putHeader("Access-Control-Allow-Credentials", allowCredentials)
                .putHeader("Access-Control-Allow-Headers", allowHeaders);
        event.next();
    }
}
