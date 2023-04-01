package commons.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "secure.props")
public class SecureProperties {
    private Jwt jwt = new Jwt();
    private List<String> ignoredUrls = new ArrayList<>();

    @Data
    public static class Jwt {
        private String secret = "SecretKey012345678901234567890123456789012345678901234567890123456789";
        private int expiration = 3600;
    }
}
