package io.javac.minoss.minossappadmin.service.impl;

import io.javac.minoss.minossappadmin.service.VertxUserService;
import io.javac.minoss.minosscommon.bcrypt.BCryptPasswordEncoder;
import io.javac.minoss.minosscommon.exception.MinOssException;
import io.javac.minoss.minosscommon.plugin.JwtPlugin;
import io.javac.minoss.minosscommon.utils.StringV;
import io.javac.minoss.minosscommon.utils.VertxRespone;
import io.javac.minoss.minosscommon.utils.id.IdGeneratorCore;
import io.javac.minoss.minossdao.model.UserModel;
import io.javac.minoss.minossdao.service.UserService;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * @author pencilso
 * @date 2020/1/24 3:21 下午
 */
@Slf4j
@Validated
@Service
public class VertxUserServiceImpl implements VertxUserService {
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JwtPlugin jwtPlugin;

    @Override
    public void userLogin(@NotNull RoutingContext routingContext, @NotEmpty String loginName, @NotEmpty String loginPassword) {
        Optional<UserModel> optionalUserModel = userService.getByLoginName(loginName);
        optionalUserModel.orElseThrow(() -> new MinOssException("该用户不存在，请尝试注册或与管理员联系！"));
        UserModel userModel = optionalUserModel.get();
        //check login password
        boolean matches = bCryptPasswordEncoder.matches(loginPassword, userModel.getLoginPassword());
        if (!matches) {
            throw new MinOssException("账户或者密码错误，请尝试稍后重试！");
        }
        //generator new salt
        String jwtSalt = IdGeneratorCore.generatorUUID();
        //generator new accesstoken
        Optional<String> generateToken = jwtPlugin.generateToken(userModel.getMid(), jwtSalt);
        String accesstoken = generateToken.orElseThrow(() -> new MinOssException("生成token令牌出错，请尝试稍后重试！"));
        //更新用户token 和 hash salt
        userService.updateModelByMid(
                new UserModel().setJwtSalt(jwtSalt).setJwtToken(accesstoken).setVersion(userModel.getVersion()),
                userModel.getMid());
        VertxRespone.responeSuccess(routingContext, StringV.by("accesstoken", userModel.getJwtToken()));

        log.debug("user login success by  [{}]  new accesstoken [{}]", loginName, userModel.getJwtToken());
    }
}
