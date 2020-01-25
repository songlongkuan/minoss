package io.javac.minoss.minosscommon.vertx;

import io.javac.minoss.minosscommon.model.jwt.JwtAuthEntity;
import io.vertx.core.http.Cookie;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;

import java.util.Optional;

/**
 * @author pencilso
 * @date 2020/1/25 12:20 下午
 */
public class VertxRequest {
    private final RoutingContext routingContext;

    private VertxRequest(RoutingContext routingContext) {
        this.routingContext = routingContext;
    }

    public static VertxRequest build(RoutingContext routingContext) {
        return new VertxRequest(routingContext);
    }

    public String getParam(String key) {
        HttpServerRequest request = routingContext.request();
        return request.getParam(key);
    }

    /**
     * 获取token解析后的用户id
     *
     * @return
     */
    public JwtAuthEntity getAuthEntitu() {
        return routingContext.get("jwt_auth");
    }

    /**
     * 构建 VertxRespone
     *
     * @return VertxRespone
     */
    public VertxRespone buildVertxRespone() {
        return VertxRespone.build(routingContext);
    }

    /**
     * 获取AccessToken 令牌
     *
     * @return
     */
    public Optional<String> getAccessToken() {
        HttpServerRequest request = routingContext.request();
        String accesstoken = null;
        if ((accesstoken = request.getHeader("accesstoken")) != null) {
        } else if ((accesstoken = request.getParam("accesstoken")) != null) {
        } else {
            Cookie cookie = request.getCookie("accesstoken");
            if (cookie != null) accesstoken = cookie.getValue();
        }
        return Optional.ofNullable(accesstoken);
    }
}
