package commons.security.config;

import com.alibaba.fastjson2.JSONObject;
import commons.security.TokenProvider;
import commons.security.filter.JwtAuthenticationTokenFilter;
import commons.utils.Ret;
import io.jsonwebtoken.Jwts;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 权限相关自动配置，通过@Import注解引入
 *
 * @author guor
 */
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SecureProperties.class)
public class SecureAutoConfig {

    @Bean
    @ConditionalOnClass(BCryptPasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnClass(Jwts.class)
    public TokenProvider tokenProvider(SecureProperties secureProperties) {
        return new TokenProvider(secureProperties.getJwt().getSecret(), secureProperties.getJwt().getExpiration());
    }

    @Bean
    @ConditionalOnBean(TokenProvider.class)
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    public JwtAuthenticationTokenFilter authenticationTokenFilter(TokenProvider tokenProvider) {
        return new JwtAuthenticationTokenFilter(tokenProvider);
    }

    /**
     * 自定义SecurityFilterChain，内置的（SpringBootWebSecurityConfiguration）不可扩展，@Order注解一定要加上
     *
     * @see org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
     */
    @Bean
    @Order(0)
    @ConditionalOnClass({SecurityFilterChain.class})
    public SecurityFilterChain filterChain(HttpSecurity http, SecureProperties secureProperties, JwtAuthenticationTokenFilter authenticationTokenFilter) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        // 不需要保护的资源路径允许访问
        for (String url : secureProperties.getIgnoredUrls()) {
            registry.antMatchers(url).permitAll();
        }
        // 允许跨域请求的OPTIONS请求
        registry.antMatchers(HttpMethod.OPTIONS).permitAll();
        // 允许访问静态资源
        registry.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll();
        // 任何请求需要身份认证
        return registry.and().authorizeRequests().anyRequest().authenticated()
                // 关闭跨站请求防护及不使用session
                .and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 自定义权限拒绝处理类
                .and().exceptionHandling().accessDeniedHandler(new MyAccessDeniedHandler()).authenticationEntryPoint(new MyAuthenticationEntryPoint())
                // 自定义权限拦截器JWT过滤器
                .and().addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

    static class MyAccessDeniedHandler implements AccessDeniedHandler {
        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(new JSONObject(Ret.forbidden()).toJSONString());
            response.getWriter().flush();
        }
    }

    static class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(new JSONObject(Ret.unauthorized()).toJSONString());
            response.getWriter().flush();
        }
    }
}
