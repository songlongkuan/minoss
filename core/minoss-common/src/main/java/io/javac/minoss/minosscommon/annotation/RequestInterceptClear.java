package io.javac.minoss.minosscommon.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 是否添加拦截器的注解  可用于取消拦截器
 *
 * @author pencilso
 * @date 2020/1/25 10:45 上午
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestInterceptClear {
    /**
     * 是否校验权限
     *
     * @return
     */
    boolean check() default true;
}