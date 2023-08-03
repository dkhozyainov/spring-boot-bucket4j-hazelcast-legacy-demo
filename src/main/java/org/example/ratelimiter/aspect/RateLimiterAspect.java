package org.example.ratelimiter.aspect;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.grid.hazelcast.HazelcastProxyManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.ratelimiter.annotation.RateLimiter;
import org.example.ratelimiter.config.RateLimiterHazelcastProperties;
import org.example.ratelimiter.model.RateLimiterKey;
import org.example.ratelimiter.utils.RateLimiterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
public class RateLimiterAspect {
    private final ProxyManager<RateLimiterKey> proxyManager;
    private final RateLimiterHazelcastProperties rateLimiterHazelcastProperties;

    @Autowired
    public RateLimiterAspect(@Qualifier("getHazelcastInstance") HazelcastInstance hazelcastInstance,
                             RateLimiterHazelcastProperties rateLimiterHazelcastProperties) {
        this.rateLimiterHazelcastProperties = rateLimiterHazelcastProperties;
        IMap<RateLimiterKey, byte[]> bucketsMap = hazelcastInstance.getMap(this.rateLimiterHazelcastProperties
                .getHazelcast().getRateLimiterBucketName());
        proxyManager = new HazelcastProxyManager<>(bucketsMap);
    }

    @Around("@annotation(rateLimiter)")
    public Object rateLimitersExecution(ProceedingJoinPoint joinPoint, RateLimiter rateLimiter) throws Throwable {
        RateLimiterKey key = new RateLimiterKey(rateLimiter.name());
        RateLimiterHazelcastProperties.RateLimitersProperties limitersProperties =
                getRateLimiterByName(rateLimiter.name()).orElse(null);
        if (limitersProperties != null) {
            Bucket bucket = proxyManager.builder().build(key, ()
                    -> RateLimiterUtils.getBucketConfiguration(limitersProperties));

            if (!bucket.tryConsume(1)) {
                throw new RuntimeException("Too many requests");
            }
        }
        return joinPoint.proceed();
    }

    private Optional<RateLimiterHazelcastProperties.RateLimitersProperties> getRateLimiterByName(String rateLimiterName) {
        return rateLimiterHazelcastProperties.getRateLimiters()
                .stream()
                .filter(rateLimitersProperties -> rateLimitersProperties.getRateLimiterName().equals(rateLimiterName))
                .findFirst();
    }
}
