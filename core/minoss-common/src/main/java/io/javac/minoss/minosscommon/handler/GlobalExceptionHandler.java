package io.javac.minoss.minosscommon.handler;

import io.javac.minoss.minosscommon.constant.ResponeWrapperConst;
import io.javac.minoss.minosscommon.exception.MinOssTokenExpireException;
import io.javac.minoss.minosscommon.exception.MinOssTokenInvalidException;
import io.javac.minoss.minosscommon.exception.MinOssMessageException;
import io.javac.minoss.minosscommon.model.respone.ResponeWrapper;
import io.javac.minoss.minosscommon.vertx.VertxRespone;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理
 *
 * @author pencilso
 * @date 2020/1/24 4:16 下午
 */
@Component
public class GlobalExceptionHandler implements Handler<RoutingContext> {

    private final Map<Class<?>, Integer> exMap = new HashMap<>();

    @PostConstruct
    public void init() {
        //参数校验失败
        exMap.put(ConstraintViolationException.class, ResponeWrapperConst.VALIDATION_PARAM_FAIL);
        //登录失效
        exMap.put(MinOssTokenInvalidException.class, ResponeWrapperConst.LOGIN_INVALID);
        exMap.put(MinOssTokenExpireException.class, ResponeWrapperConst.LOGIN_INVALID);
        //自定义错误异常信息
        exMap.put(MinOssMessageException.class, ResponeWrapperConst.OPERATE_FAIL);
    }

    @Override
    public void handle(RoutingContext event) {
        ResponeWrapper<String> responeWrapper = new ResponeWrapper<>();
        Throwable failure = event.failure();
        failure.printStackTrace();
        Class<? extends Throwable> aClass = failure.getClass();
        Integer code = exMap.get(aClass);
        if (code == null) {
            failure.printStackTrace();
            responeWrapper.setCode(ResponeWrapperConst.SERVER_ERROR).setMessage("服务器出现异常，我们将会尽快处理！");
        } else {
            responeWrapper.setCode(code).setMessage(failure.getMessage());
        }
        //respone json
        VertxRespone.build(event).respone(responeWrapper);
    }
}
