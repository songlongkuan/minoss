package io.javac.minoss.minosscommon.toolkit;

import java.util.HashMap;

/**
 * @author pencilso
 * @date 2020/2/3 12:05 下午
 */
public class Kv<K, V> extends HashMap<K, V> {

    /**
     * key -> String
     * value -> String
     *
     * @return
     */
    public static Kv<String, String> stringStringKv() {
        return getInstance(String.class, String.class);
    }


    /**
     * key -> string
     * value -> object
     *
     * @return
     */
    public static Kv<String, Object> stringObjectKv() {
        return getInstance(String.class, Object.class);
    }

    /**
     * key -> object
     * value -> object
     *
     * @return
     */
    public static Kv<Object, Object> objectObjectKv() {
        return getInstance(Object.class, Object.class);
    }


    public static <K, V> Kv<K, V> getInstance(Class<? extends K> classKey, Class<? extends V> classValue) {
        return new Kv<K, V>();
    }


    public Kv<K, V> set(K key, V value) {
        put(key, value);
        return this;
    }
}
