package org.example.ratelimiter.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Validated
@Configuration
@ConfigurationProperties("application")
public class RateLimiterHazelcastProperties {
    @NotNull
    private List<RateLimitersProperties> rateLimiters;
    @NotNull
    private HazelcastProperties hazelcast;
    @Getter
    @Setter
    public static class RateLimitersProperties {
        @NotBlank
        private String rateLimiterName;
        @NotBlank
        private String timeValue;
        @NotNull
        private Long limitForPeriod;
    }
    @Getter
    @Setter
    public static class HazelcastProperties {
        @NotBlank
        private String rateLimiterBucketName;
        @NotNull
        private String[] hazelcastClusterAddresses;
    }
}
