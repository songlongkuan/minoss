package io.javac.minoss.minossappadmin;

import io.javac.minoss.minoss.minossappservice.VertxUserService;
import io.javac.minoss.minosscommon.annotation.RequestBody;
import io.javac.minoss.minosscommon.annotation.RequestInterceptClear;
import io.javac.minoss.minosscommon.annotation.RequestMapping;
import io.javac.minoss.minossservice.base.VertxControllerHandler;
import io.javac.minoss.minosscommon.enums.RequestMethod;
import io.javac.minoss.minosscommon.exception.MinOssMessageException;
import io.javac.minoss.minosscommon.model.jwt.JwtAuthModel;
import io.javac.minoss.minossdao.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * user controller
 *
 * @author pencilso
 * @date 2020/1/24 2:55 下午
 */
@Slf4j
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
    @RequestBody
    @RequestInterceptClear
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public VertxControllerHandler login() {
        return vertxRequest -> {
            Optional<String> loginName1 = vertxRequest.getParam("loginName");
            log.info("loginName :[{}]", loginName1);
            String loginName = vertxRequest.getParam("loginName").orElse(null);
            String loginPassword = vertxRequest.getParam("loginPassword").orElse(null);
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
            UserModel userModel = vertxUserService.getByuMid(authEntitu.getId()).orElseThrow(() -> new MinOssMessageException("该用户不存在!"));
            vertxRequest.buildVertxRespone().responeSuccess(userModel);
        };
    }

}
