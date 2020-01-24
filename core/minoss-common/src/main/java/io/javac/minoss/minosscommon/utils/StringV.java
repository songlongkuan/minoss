package io.javac.minoss.minosscommon.utils;

import java.util.HashMap;

/**
 * @author pencilso
 * @date 2020/1/24 4:47 下午
 */
public class StringV extends HashMap<String, Object> {

    public static StringV by(String key, Object value) {
        return new StringV().set(key, value);
    }

    public StringV set(String key, Object value) {
        put(key, value);
        return this;
    }
}
