package commons.utils;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;

import java.util.Arrays;
import java.util.List;

/**
 * apollo客户端使用指南：https://www.apolloconfig.com/#/zh/usage/java-sdk-user-guide
 * <p>
 * apollo配置中心工具类，使用说明
 * 1.依赖AppId，Apollo Meta Server
 * 2.AppId配置三种位置：jvm参数配置-Dapp.id=YOUR-APP-ID，SpringBoot的application.properties文件配置，classpath:/META-INF/app.properties文件配置
 * 3.Apollo Meta Server配置
 * </p>
 *
 * @author guorui1
 */
public class ConfigUtil {
    private static final String DEFAULT_DELIMITER = ",";

    private final Config config;

    private ConfigUtil(Config config) {
        this.config = config;
    }

    public static ConfigUtil of(String namespace) {
        return new ConfigUtil(ConfigService.getConfig(namespace));
    }

    public String getProperty(String key, String defaultValue) {
        return config.getProperty(key, defaultValue);
    }

    public String getProperty(String key) {
        return getProperty(key, StringUtils.EMPTY);
    }

    public String[] getArrayProperty(String key) {
        return config.getArrayProperty(key, DEFAULT_DELIMITER, new String[]{});
    }

    public String[] getArrayProperty(String key, String[] defaultValue) {
        return config.getArrayProperty(key, DEFAULT_DELIMITER, defaultValue);
    }

    public String[] getArrayProperty(String key, String delimiter, String[] defaultValue) {
        return config.getArrayProperty(key, delimiter, defaultValue);
    }

    public int getIntProperty(String key, int defaultValue) {
        return config.getIntProperty(key, defaultValue);
    }

    public List<String> getListProperty(String key) {
        return Arrays.asList(this.getArrayProperty(key));
    }

    public long getLongProperty(String key, long defaultValue) {
        return config.getLongProperty(key, defaultValue);
    }
}
