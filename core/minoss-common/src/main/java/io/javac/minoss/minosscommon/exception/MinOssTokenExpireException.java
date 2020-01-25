package io.javac.minoss.minosscommon.exception;

/**
 * 登录失效 异常
 *
 * @author pencilso
 * @date 2020/1/25 11:09 上午
 */
public class MinOssTokenExpireException extends MinOssMessageException {
    public MinOssTokenExpireException() {
        super("登录已过期，请尝试重新登录！");
    }

}
