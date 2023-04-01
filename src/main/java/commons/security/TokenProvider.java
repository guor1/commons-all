package commons.security;

import commons.utils.DateUtils;
import commons.utils.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JwtToken生成的工具类
 * JWT token的格式：header.payload.signature
 * header的格式（算法、token的类型）：
 * {"alg": "HS512","typ": "JWT"}
 * payload的格式（用户名、创建时间、生成时间）：
 * {"sub":"wang","created":1489079981393,"exp":1489684781}
 * signature的生成算法：
 * HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
 *
 * @author guorui1
 */
@Slf4j
@Data
public class TokenProvider {
    public static final String TOKEN_HEAD = "x-token";
    private static final String CLAIM_KEY_AUTH = "auth";
    private final String secret;
    private final int expiration;
    private Key key;

    public TokenProvider(String secret, int expiration) {
        this.secret = secret;
        this.expiration = expiration;
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        final Date createdDate = DateUtils.now();
        final Date expireDate = expirationDate(createdDate);
        return Jwts.builder().setSubject(authentication.getName()).claim(CLAIM_KEY_AUTH, authorities).setIssuedAt(createdDate).setExpiration(expireDate).signWith(key, SignatureAlgorithm.HS256).compact();
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaimsFromToken(token);
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(claims.get(CLAIM_KEY_AUTH).toString());
        User principal = new User(claims.getSubject(), StringUtils.EMPTY, authorities);

        return new UsernamePasswordAuthenticationToken(principal, StringUtils.EMPTY, authorities);
    }

    /**
     * 校验token，解析并验证过期时间
     */
    public boolean validateToken(String token) {
        try {
            boolean tokenExpired = isTokenExpired(token);
            if (tokenExpired) {
                log.debug("JWT token expired");
            }
            return !tokenExpired;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("无效token.");
        }
        return false;
    }

    /**
     * 判断token是否已经失效
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(DateUtils.now());
    }

    /**
     * 从token中获取过期时间
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    private Date expirationDate(Date createdDate) {
        return DateUtils.addSeconds(createdDate, expiration);
    }
}
