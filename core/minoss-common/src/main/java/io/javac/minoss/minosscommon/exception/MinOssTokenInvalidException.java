package io.javac.minoss.minosscommon.exception;

/**
 * 登录失效 异常
 *
 * @author pencilso
 * @date 2020/1/25 11:09 上午
 */
public class MinOssTokenInvalidException extends MinOssMessageException {
    public MinOssTokenInvalidException() {
        super("登录已失效，请尝试重新登录！");
    }

}
