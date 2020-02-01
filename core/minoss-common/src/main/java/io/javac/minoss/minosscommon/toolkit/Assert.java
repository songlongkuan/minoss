package io.javac.minoss.minosscommon.toolkit;

import io.javac.minoss.minosscommon.exception.MinOssAssertException;

/**
 * @author pencilso
 * @date 2020/2/1 8:41 下午
 */
public class Assert {


    public static void notNull(Object value) {
        notNull(value, "value can not be null");
    }

    /**
     * assert object not null
     *
     * @param value
     * @param message
     */
    public static void notNull(Object value, String message) {
        if (value == null)
            throw new MinOssAssertException(message);
    }


    public static void notBlank(String value) {
        notBlank(value, "value can not be blank");
    }

    /**
     * assert String not blank
     *
     * @param value
     * @param message
     */
    public static void notBlank(String value, String message) {
        if (value == null || value.length() == 0)
            throw new MinOssAssertException(message);
    }

}
