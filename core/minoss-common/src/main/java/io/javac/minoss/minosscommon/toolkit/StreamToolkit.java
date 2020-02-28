package io.javac.minoss.minosscommon.toolkit;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author pencilso
 * @date 2020/2/27 6:11 下午
 */
public class StreamToolkit {

    /**
     * close stream
     *
     * @param closeables
     */
    public static void closeStream(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
