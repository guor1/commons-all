package commons.security.filter;

import commons.security.TokenProvider;
import commons.utils.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Nonnull;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT登录授权过滤器
 *
 * @author guorui1
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    private final TokenProvider tokenProvider;

    public JwtAuthenticationTokenFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain chain) throws ServletException, IOException {
        String authToken = resolveToken(request);
        if (StringUtils.isNotEmpty(authToken) && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (tokenProvider.validateToken(authToken)) {
                Authentication authentication = this.tokenProvider.getAuthentication(authToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }

    /**
     * 从request中获取请求token
     * 1.请求url中携带x-token
     * 2.请求header中携带x-token
     * 3.请求header中携带Bearer token
     */
    private String resolveToken(HttpServletRequest request) {
        String authToken = request.getParameter(TokenProvider.TOKEN_HEAD);
        if (StringUtils.isNotBlank(authToken)) {
            return authToken;
        }
        authToken = request.getHeader(TokenProvider.TOKEN_HEAD);
        if (StringUtils.isNotBlank(authToken)) {
            return authToken;
        }
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return bearerToken;
    }
}
