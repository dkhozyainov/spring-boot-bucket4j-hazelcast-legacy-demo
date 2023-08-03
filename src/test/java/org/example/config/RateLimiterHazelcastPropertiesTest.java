package org.example.config;

import org.example.ratelimiter.config.RateLimiterHazelcastProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes = RateLimiterHazelcastProperties.class)
@EnableConfigurationProperties
@ActiveProfiles("test")
@TestPropertySource(locations = {"classpath:application-test.yml"})
public class RateLimiterHazelcastPropertiesTest {

    @Autowired
    private RateLimiterHazelcastProperties properties;

    @Test
    void testRateLimitersProperties() {
        assertEquals("ratelimiter1", properties.getRateLimiters().get(0).getRateLimiterName());
        assertEquals("1s", properties.getRateLimiters().get(0).getTimeValue());
        assertEquals(2L, properties.getRateLimiters().get(0).getLimitForPeriod());
    }

    @Test
    void testHazelcastProperties() {
        assertEquals("ratelimiter-bucketmap", properties.getHazelcast().getRateLimiterBucketName());
        assertArrayEquals(new String[]{"192.168.1.227:5701", "127.0.0.1:5701"},
                properties.getHazelcast().getHazelcastClusterAddresses());
    }
}