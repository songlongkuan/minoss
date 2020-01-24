package io.javac.minoss.minosscommon.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 指定某个类 或者 具体某个方法 是同步阻塞调用
 *
 * @author pencilso
 * @date 2020/1/24 9:59 上午
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestBlockingHandler {
}
