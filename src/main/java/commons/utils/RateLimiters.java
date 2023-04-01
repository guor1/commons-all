package commons.utils;

import es.moki.ratelimitj.core.limiter.request.RequestLimitRule;
import es.moki.ratelimitj.core.limiter.request.RequestRateLimiter;
import es.moki.ratelimitj.inmemory.request.InMemorySlidingWindowRequestRateLimiter;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 限流工具
 *
 * @author guorui1
 */
public class RateLimiters {

    public static final RequestRateLimiter RRL_LOGIN = createRateLimiter(
            new int[]{30, 60, 3600},
            new int[]{5, 10, 100});

    public static RequestRateLimiter createRateLimiter(int seconds, int limit) {
        Set<RequestLimitRule> rules = Collections.singleton(
                RequestLimitRule.of(Duration.ofSeconds(seconds), limit));
        return new InMemorySlidingWindowRequestRateLimiter(rules);
    }

    public static RequestRateLimiter createRateLimiter(int[] seconds, int[] limits) {
        Assert.isTrue(seconds.length == limits.length, "Rule pair not matches");

        Set<RequestLimitRule> rules = new HashSet<>();
        for (int i = 0; i < seconds.length; i++) {
            rules.add(RequestLimitRule.of(Duration.ofSeconds(seconds[i]), limits[i]));
        }
        return new InMemorySlidingWindowRequestRateLimiter(rules);
    }
}
