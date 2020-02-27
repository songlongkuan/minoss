package io.javac.minoss.minossappclient;

import io.javac.minoss.minosscommon.annotation.RequestBody;
import io.javac.minoss.minosscommon.annotation.RequestInterceptClear;
import io.javac.minoss.minosscommon.annotation.RequestMapping;
import io.javac.minoss.minossservice.base.VertxControllerHandler;
import io.javac.minoss.minosscommon.enums.request.RequestMethod;
import io.javac.minoss.minossservice.controller.VertxAccessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author pencilso
 * @date 2020/2/9 9:02 下午
 */
@Slf4j
@Component("client-VertxAccessController")
@RequestMapping("/api/client/access")
public class VertxAccessController {

    @Autowired
    private VertxAccessService vertxAccessService;

    /**
     * access login
     *
     * @return
     */
    @RequestInterceptClear
    @RequestBody
    @RequestMapping(value = "accesslogin", method = RequestMethod.POST)
    public VertxControllerHandler accesslogin() {
        return vertxRequest -> {
            String accessKey = vertxRequest.getParamNotBlank("accessKey");
            String accessSecret = vertxRequest.getParamNotBlank("accessSecret");
            String accesstoken = vertxAccessService.accessLogin(accessKey, accessSecret);
            vertxRequest.buildVertxRespone().responeSuccess(accesstoken);
        };
    }
}