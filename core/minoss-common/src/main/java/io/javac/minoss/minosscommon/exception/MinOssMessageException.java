package io.javac.minoss.minosscommon.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author pencilso
 * @date 2020/1/23 8:55 下午
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class MinOssMessageException extends RuntimeException {

    private Exception exception;

    public MinOssMessageException(String message) {
        this(message, null);
    }

    public MinOssMessageException(Exception exception) {
        this.exception = exception;
    }

    public MinOssMessageException(String message, Exception exception) {
        super(message);
        this.exception = exception;
    }

    public MinOssMessageException(String message, Throwable cause, Exception exception) {
        super(message, cause);
        this.exception = exception;
    }

    public MinOssMessageException(Throwable cause, Exception exception) {
        super(cause);
        this.exception = exception;
    }

    public MinOssMessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Exception exception) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.exception = exception;
    }
}
