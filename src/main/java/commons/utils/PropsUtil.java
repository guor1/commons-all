package commons.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * 全局系统参数，优先级：jvm参数 >> 配置文件
 * 配置文件默认加载server.properties，如果配置了server.config.location，则从配置路径加载配置
 *
 * @author guorui1
 */
@Slf4j
public class PropsUtil {

    /**
     * 从文件中加载配置,可以是系统文件或者classpath
     *
     * @param fileName the name of the properties file to load.
     */
    @SneakyThrows
    public static Properties loadProps(String fileName) {
        Properties props = new Properties();
        fillProperties(props, fileName);
        return props;
    }

    private static void fillProperties(Properties props, String fileName) throws IOException {
        try (InputStream is = findInputStream(fileName)) {
            props.load(is);
        }
    }

    private static InputStream findInputStream(String fileName) throws IOException {
        Objects.requireNonNull(fileName);
        File f = new File(fileName);
        if (f.exists()) {
            log.info(fileName + " found in file system");
            return new FileInputStream(f);
        } else {
            InputStream in = findInClassPath(fileName);
            if (in != null) {
                log.info(fileName + " found in classpath");
            }
            return in;
        }
    }

    private static InputStream findInClassPath(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
    }

    private static final String DEFAULT_CONF = "server.properties";

    private static final Properties LOCAL_PROPS;

    static {
        String userConfig = System.getProperty("server.config.location");
        if (!StringUtils.isEmpty(userConfig)) {
            LOCAL_PROPS = PropsUtil.loadProps(userConfig);
        } else {
            LOCAL_PROPS = PropsUtil.loadProps(DEFAULT_CONF);
        }
    }

    public static String getProperty(String key, String def) {
        String v = System.getProperty(key);
        if (!StringUtils.isEmpty(v)) {
            return v;
        }
        if (LOCAL_PROPS != null) {
            v = LOCAL_PROPS.getProperty(key);
        }
        return StringUtils.isEmpty(v) ? def : v;
    }

    public static String getProperty(String key) {
        return getProperty(key, null);
    }
}
