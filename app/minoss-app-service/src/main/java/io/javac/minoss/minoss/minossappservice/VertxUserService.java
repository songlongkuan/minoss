package io.javac.minoss.minoss.minossappservice;

import io.javac.minoss.minosscommon.vertx.VertxRequest;
import io.javac.minoss.minossdao.model.UserModel;
import io.vertx.ext.web.RoutingContext;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * controller  User service
 *
 * @author pencilso
 * @date 2020/1/24 3:14 下午
 */
public interface VertxUserService {

    /**
     * user login
     *
     * @param vertxRequest  vertxRequest
     * @param loginName     loginName
     * @param loginPassword loginPassword
     */
    void userLogin(@NotNull VertxRequest vertxRequest, @NotEmpty String loginName, @NotEmpty String loginPassword);

    /**
     * query user model
     *
     * @param uMid user mid
     * @return user model
     */
    Optional<UserModel> getByuMid(@NotNull Long uMid);
}
