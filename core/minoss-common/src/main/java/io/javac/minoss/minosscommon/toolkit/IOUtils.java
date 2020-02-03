package io.javac.minoss.minosscommon.toolkit;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author pencilso
 * @date 2020/2/2 4:18 下午
 */
public class IOUtils {

    private static final int BUFFER_SIZE = 1024 * 4;


    public static void copy(InputStream inputStream, OutputStream outputStream) {
        int len = -1;
        byte[] buffer = new byte[BUFFER_SIZE];
        try {
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void closeQuietly(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            if (closeable!=null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
