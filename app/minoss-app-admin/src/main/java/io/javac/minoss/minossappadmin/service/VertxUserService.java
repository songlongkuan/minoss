package io.javac.minoss.minossappadmin.service;

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


    void userLogin(@NotNull RoutingContext routingContext, @NotEmpty String loginName, @NotEmpty String loginPassword);
}
