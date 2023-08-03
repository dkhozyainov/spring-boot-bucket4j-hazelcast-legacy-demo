package org.example.ratelimiter.config;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class HazelcastClientConfig {
    private final RateLimiterHazelcastProperties rateLimiterHazelcastProperties;

    @Bean("getHazelcastInstance")
    protected HazelcastInstance getHazelcastInstance() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.getNetworkConfig().addAddress(rateLimiterHazelcastProperties
                .getHazelcast().getHazelcastClusterAddresses());
        return HazelcastClient.newHazelcastClient(clientConfig);
    }
}
