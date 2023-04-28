package commons.ratelimitj;

import commons.exception.BizException;
import commons.utils.RateLimiters;
import es.moki.ratelimitj.core.limiter.request.RequestRateLimiter;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimitMethodInterceptor implements MethodInterceptor {
    private final Map<String, RequestRateLimiter> limiterMap = new ConcurrentHashMap<>();

    @Nullable
    @Override
    public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        RateLimit rateLimit = AnnotationUtils.findAnnotation(method, RateLimit.class);
        if (rateLimit != null) {
            RequestRateLimiter requestRateLimiter = limiterMap.putIfAbsent(rateLimit.path(), RateLimiters.createRateLimiter(rateLimit.seconds(), rateLimit.limit()));
            assert requestRateLimiter != null;
            if (requestRateLimiter.overLimitWhenIncremented(rateLimit.path())) {
                throw new BizException("访问太快");
            }
        }
        return invocation.proceed();
    }
}
