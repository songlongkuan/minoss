package io.javac.minoss.minossappclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.javac.minoss.minosscommon.annotation.RequestMapping;
import io.javac.minoss.minosscommon.enums.RequestMethod;
import io.javac.minoss.minosscommon.toolkit.JsonUtils;
import io.javac.minoss.minossdao.model.UserModel;
import io.javac.minoss.minossdao.service.UserService;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author pencilso
 * @date 2020/1/23 9:51 下午
 */
@Component
@RequestMapping("/api/test")
public class ClientTestController {
    @Autowired
    private UserService userService;

    /**
     * 演示服务调用
     *
     * @return
     */
    @RequestMapping(value = "/listUsers", method = RequestMethod.GET)
    public Handler<RoutingContext> listUsers() {
        return ctx -> {
            List<UserModel> userModelList = userService.list();
            try {
                HttpServerResponse response = ctx.response();
                response.putHeader("Content-Type", "text/json;charset=utf-8");
                @NotNull String objectToJson = JsonUtils.objectToJson(userModelList);
                response.end(objectToJson);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        };
    }
}
