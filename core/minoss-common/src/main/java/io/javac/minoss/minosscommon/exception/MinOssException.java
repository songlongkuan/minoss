package io.javac.minoss.minosscommon.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author pencilso
 * @date 2020/1/23 8:55 下午
 */
@Data
@AllArgsConstructor
public class MinOssException extends RuntimeException {

    private String message;
    private Exception exception;
}
