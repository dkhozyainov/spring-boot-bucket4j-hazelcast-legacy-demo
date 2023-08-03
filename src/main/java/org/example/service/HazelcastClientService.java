package org.example.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import lombok.RequiredArgsConstructor;
import org.example.ratelimiter.config.RateLimiterHazelcastProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class HazelcastClientService {

    private final RateLimiterHazelcastProperties rateLimiterHazelcastProperties;
    protected HazelcastInstance hazelcastInstance;
    protected IMap<String, String> map;

    @Autowired
    public HazelcastClientService(RateLimiterHazelcastProperties rateLimiterHazelcastProperties, @Qualifier("getHazelcastInstance") HazelcastInstance hazelcastInstance) {
        this.rateLimiterHazelcastProperties = rateLimiterHazelcastProperties;
        map = hazelcastInstance.getMap(rateLimiterHazelcastProperties.getHazelcast().getRateLimiterBucketName());
        this.hazelcastInstance = hazelcastInstance;
    }

    void writeInCache() {
        map.put("1", "John");
        map.put("2", "Mary");
        map.put("3", "Jane");
    }

    void readFromCache() {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    protected String readFromCacheByKey(String key) {
        return map.get(key);
    }

}
