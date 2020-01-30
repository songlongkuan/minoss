package io.javac.minoss.minoss.minossappservice;

import io.javac.minoss.minosscommon.vertx.VertxRequest;
import io.javac.minoss.minossdao.model.UserModel;
import io.vertx.ext.web.RoutingContext;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * vertx 接口 User 服务
 *
 * @author pencilso
 * @date 2020/1/24 3:14 下午
 */
public interface VertxUserService {

    /**
     * 用户登录
     *
     * @param vertxRequest 上下文对象
     * @param loginName      登录账号
     * @param loginPassword  登录密码
     */
    void userLogin(@NotNull VertxRequest vertxRequest, @NotEmpty String loginName, @NotEmpty String loginPassword);

    /**
     * 获取用户模型
     *
     * @param uMid 用户id
     * @return  用户模型
     */
    UserModel getByuMid(@NotNull Long uMid);
}
