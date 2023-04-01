package commons.utils;

import org.apache.groovy.io.StringBuilderWriter;

import java.io.*;

public class IoUtil {
    /**
     * 流拷贝
     */
    public static void copy(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024 * 4];
        int n;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
    }

    /**
     * 流拷贝
     */
    public static void copy(Reader input, Writer output) throws IOException {
        char[] buffer = new char[1024 * 4];
        int n;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
    }

    public static String toString(Reader input) throws IOException {
        StringBuilderWriter sw = new StringBuilderWriter();
        copy(input, sw);
        return sw.toString();
    }
}
