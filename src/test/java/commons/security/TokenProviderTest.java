package commons.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

public class TokenProviderTest {

    @Test
    public void test() {
        String secret = "SecretKey012345678901234567890123456789012345678901234567890123456789";
        int expiration = 30;
        TokenProvider tokenProvider = new TokenProvider(secret, expiration);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken("admin", "admin", AuthorityUtils.createAuthorityList("admin"));
        String token = tokenProvider.createToken(usernamePasswordAuthenticationToken);
        Assertions.assertTrue(tokenProvider.validateToken(token));

        Authentication authentication = tokenProvider.getAuthentication(token);
        Assertions.assertEquals("admin", authentication.getName());
    }
}
