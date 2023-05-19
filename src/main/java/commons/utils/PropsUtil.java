package commons.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 自动加载当前目录或者classpath下的application.properties文件
 *
 * @author guorui1
 */
@Slf4j
public class PropsUtil {
    private static final String PROPS_LOCATION = "application.properties";
    private static final Properties PROPS = new Properties();

    static {
        try {
            ClassLoader classLoader = PropsUtil.class.getClassLoader();
            try (InputStream is = classLoader.getResourceAsStream(PROPS_LOCATION)) {
                if (is != null) {
                    PROPS.load(is);
                }
            }
        } catch (IOException ex) {
            System.err.println("Could not load 'spring.properties' file from local classpath: " + ex);
        }
    }

    public static String getProperty(String key, String def) {
        String v = System.getProperty(key);
        if (!StringUtils.isEmpty(v)) {
            return v;
        }
        v = PROPS.getProperty(key);
        return StringUtils.isEmpty(v) ? def : v;
    }

    public static String getProperty(String key) {
        return getProperty(key, null);
    }
}
