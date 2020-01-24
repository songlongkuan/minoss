package io.javac.minoss.minosscommon.annotation;

import io.javac.minoss.minosscommon.enums.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author pencilso
 * @date 2020/1/23 10:27 下午
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    /**
     * 路径
     *
     * @return
     */
    String[] value() default {};

    /**
     * 请求方法
     *
     * @return
     */
    RequestMethod method() default RequestMethod.GET;

}