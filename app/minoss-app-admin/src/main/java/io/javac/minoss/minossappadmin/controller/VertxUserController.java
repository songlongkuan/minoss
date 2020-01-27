package io.javac.minoss.minossappadmin.controller;

import io.javac.minoss.minossappadmin.service.VertxUserService;
import io.javac.minoss.minosscommon.annotation.RequestInterceptClear;
import io.javac.minoss.minosscommon.annotation.RequestMapping;
import io.javac.minoss.minosscommon.base.VertxControllerHandler;
import io.javac.minoss.minosscommon.enums.RequestMethod;
import io.javac.minoss.minosscommon.model.jwt.JwtAuthModel;
import io.javac.minoss.minossdao.model.UserModel;
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
    public VertxControllerHandler login() {
        return vertxRequest -> {
            String loginName = vertxRequest.getParam("loginName").get();
            String loginPassword = vertxRequest.getParam("loginPassword").get();
            vertxUserService.userLogin(vertxRequest, loginName, loginPassword);
        };
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @RequestMapping(value = "userdetails", method = RequestMethod.GET)
    public VertxControllerHandler userDetails() {
        return vertxRequest -> {
            JwtAuthModel authEntitu = vertxRequest.getAuthEntitu();
            UserModel userModel = vertxUserService.getByuMid(authEntitu.getUMid());
            vertxRequest.buildVertxRespone().responeSuccess(userModel);
        };
    }

}
