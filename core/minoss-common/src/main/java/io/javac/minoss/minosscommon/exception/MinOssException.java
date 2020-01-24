package io.javac.minoss.minosscommon.exception;

import lombok.Data;

/**
 * @author pencilso
 * @date 2020/1/23 8:55 下午
 */
@Data
public class MinOssException extends RuntimeException {

    private Exception exception;

    public MinOssException(String message) {
        this(message, null);
    }

    public MinOssException(Exception exception) {
        this.exception = exception;
    }

    public MinOssException(String message, Exception exception) {
        super(message);
        this.exception = exception;
    }

    public MinOssException(String message, Throwable cause, Exception exception) {
        super(message, cause);
        this.exception = exception;
    }

    public MinOssException(Throwable cause, Exception exception) {
        super(cause);
        this.exception = exception;
    }

    public MinOssException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Exception exception) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.exception = exception;
    }
}
