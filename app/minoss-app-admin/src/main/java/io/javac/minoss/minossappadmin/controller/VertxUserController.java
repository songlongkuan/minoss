package io.javac.minoss.minossappadmin.controller;

import io.javac.minoss.minossappadmin.service.VertxUserService;
import io.javac.minoss.minosscommon.annotation.RequestMapping;
import io.javac.minoss.minosscommon.enums.RequestMethod;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
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

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Handler<RoutingContext> login() {
        return ctx -> {
            HttpServerRequest request = ctx.request();
            String loginName = request.getParam("loginName");
            String loginPassword = request.getParam("loginPassword");
            vertxUserService.userLogin(ctx, loginName, loginPassword);
        };
    }

}
