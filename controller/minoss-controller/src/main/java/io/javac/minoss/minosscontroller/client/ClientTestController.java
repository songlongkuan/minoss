package io.javac.minoss.minosscontroller.client;

import io.javac.minoss.minosscommon.annotation.RequestMapping;
import io.javac.minoss.minosscommon.enums.RequestMethod;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

/**
 * @author pencilso
 * @date 2020/1/23 9:51 下午
 */
@Component
@RequestMapping("/api/test")
public class ClientTestController {


    /**
     * 演示服务调用
     *
     * @return
     */
    @RequestMapping(value = "/listUsers", method = RequestMethod.GET)
    public Handler<RoutingContext> listUsers() {
        return ctx -> {
            System.out.println("runing... listUsers");
            ctx.response().end("success...");
        };
    }
}
