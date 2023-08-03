package org.example.ratelimiter.utils;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.ConfigurationBuilder;
import org.example.ratelimiter.config.RateLimiterHazelcastProperties;

import java.time.Duration;

public final class RateLimiterUtils {
    public static BucketConfiguration getBucketConfiguration(RateLimiterHazelcastProperties.RateLimitersProperties
                                                                     rateLimiter) {
        ConfigurationBuilder bucketConfiguration = BucketConfiguration.builder();
        bucketConfiguration.addLimit(buildBandwidth(rateLimiter));
        return bucketConfiguration.build();
    }

    private static Bandwidth buildBandwidth(RateLimiterHazelcastProperties.RateLimitersProperties rateLimiter) {
        char timeUnit = rateLimiter.getTimeValue().charAt(rateLimiter.getTimeValue().length() - 1);
        long timeValue = Long.parseLong(rateLimiter.getTimeValue().substring(0, rateLimiter.getTimeValue().length() - 1));
        long limitForPeriod = rateLimiter.getLimitForPeriod();

        switch (timeUnit) {
            case 's':
                return Bandwidth.simple(limitForPeriod, Duration.ofSeconds(timeValue));
            case 'm':
                return Bandwidth.simple(limitForPeriod, Duration.ofMinutes(timeValue));
            case 'h':
                return Bandwidth.simple(limitForPeriod, Duration.ofHours(timeValue));
            case 'd':
                return Bandwidth.simple(limitForPeriod, Duration.ofDays(timeValue));
            default:
                return Bandwidth.simple(5000, Duration.ofHours(1));
        }
    }
}
