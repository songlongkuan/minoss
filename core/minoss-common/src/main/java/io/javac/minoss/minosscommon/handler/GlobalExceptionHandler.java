package io.javac.minoss.minosscommon.handler;

import io.javac.minoss.minosscommon.constant.ResponeWrapperConst;
import io.javac.minoss.minosscommon.exception.MinOssException;
import io.javac.minoss.minosscommon.model.respone.ResponeWrapper;
import io.javac.minoss.minosscommon.utils.VertxRespone;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;

/**
 * 全局异常处理
 *
 * @author pencilso
 * @date 2020/1/24 4:16 下午
 */
@Component
public class GlobalExceptionHandler implements Handler<RoutingContext> {


    @Override
    public void handle(RoutingContext event) {
        ResponeWrapper<String> responeWrapper = new ResponeWrapper<>();
        Throwable failure = event.failure();
        //参数校验失败
        if (failure instanceof ConstraintViolationException) {
            responeWrapper.setCode(ResponeWrapperConst.VALIDATION_PARAM_FAIL)
                    .setMessage(failure.getMessage());
        }
        //自定义异常
        if (failure instanceof MinOssException) {
            responeWrapper.setCode(ResponeWrapperConst.OPERATE_FAIL)
                    .setMessage(failure.getMessage());
        }
        if (responeWrapper.getMessage() == null) {
            failure.printStackTrace();
        }
        //respone json
        VertxRespone.respone(event, responeWrapper);
    }
}
