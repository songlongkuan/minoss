package io.javac.minoss.minosscommon.handler;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * Cors 跨域
 *
 * @author pencilso
 * @date 2020/1/30 10:47 下午
 */
@Slf4j
public class CorsHandler implements Handler<RoutingContext> {

    private String allowOrigin = "https://minoss.pencilso.cn";
    private String allowMethods = "GET,POST,PUT,DELETE,OPTIONS";
    private String allowCredentials = "true";
    private String allowHeaders = "Origin, X-Requested-With, Content-Type, Accept , accesstoken";

    @Override
    public void handle(RoutingContext event) {
        HttpServerResponse response = event.response();
        String requestOrigin = event.request().getHeader("Origin");

        if (!StringUtils.isEmpty(allowOrigin) && !StringUtils.isEmpty(requestOrigin)) {
            String[] split = allowOrigin.split(",");
            for (String origin : split) {
                if (requestOrigin.startsWith(origin)) {
                    response.putHeader("Access-Control-Allow-Origin", requestOrigin);
                    log.info("put header origin [{}]", requestOrigin);
                    break;
                }
            }
        }

        response.putHeader("Access-Control-Allow-Methods", allowMethods)
                .putHeader("Access-Control-Allow-Credentials", allowCredentials)
                .putHeader("Access-Control-Allow-Headers", allowHeaders);
        event.next();
    }
}
