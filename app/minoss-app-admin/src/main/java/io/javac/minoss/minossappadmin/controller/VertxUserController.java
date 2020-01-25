package io.javac.minoss.minossappadmin.controller;

import io.javac.minoss.minossappadmin.service.VertxUserService;
import io.javac.minoss.minosscommon.annotation.RequestInterceptClear;
import io.javac.minoss.minosscommon.annotation.RequestMapping;
import io.javac.minoss.minosscommon.enums.RequestMethod;
import io.javac.minoss.minosscommon.model.jwt.JwtAuthEntity;
import io.javac.minoss.minosscommon.vertx.VertxRequest;
import io.javac.minoss.minossdao.model.UserModel;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author pencilso
 * @date 2020/1/24 2:55 下午
 */
@Component
@RequestMapping("/api/admin/user")
public class VertxUserController {
    @Autowired
    private VertxUserService vertxUserService;

    /**
     * 用户登录
     *
     * @return
     */
    @RequestInterceptClear
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Handler<RoutingContext> login() {
        return ctx -> {
            VertxRequest vertxRequest = VertxRequest.build(ctx);
            String loginName = vertxRequest.getParam("loginName");
            String loginPassword = vertxRequest.getParam("loginPassword");
            vertxUserService.userLogin(vertxRequest, loginName, loginPassword);
        };
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @RequestMapping(value = "userdetails", method = RequestMethod.GET)
    public Handler<RoutingContext> userDetails() {
        return ctx -> {
            VertxRequest vertxRequest = VertxRequest.build(ctx);
            JwtAuthEntity authEntitu = vertxRequest.getAuthEntitu();
            UserModel userModel = vertxUserService.getByuMid(authEntitu.getUMid());
            vertxRequest.buildVertxRespone().responeSuccess(userModel);
        };
    }

}
