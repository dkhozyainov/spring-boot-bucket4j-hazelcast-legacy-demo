package org.example.controller;

import containers.AbstractHazelcastTestContainer;
import org.example.ratelimiter.config.RateLimiterHazelcastProperties;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = {"classpath:application-test.yml"})
class ExampleControllerTest extends AbstractHazelcastTestContainer {

    @Autowired
    private ExampleController exampleController;
    @Autowired
    private RateLimiterHazelcastProperties properties;

    @BeforeEach
    void init() {
        containerStart();
    }

    @AfterEach
    void afterEach() {
        containerStop();
    }

    @AfterAll
    static void afterAll() {
        containerClose();
    }

    @Test
    void exampleController_rateLimiter_success_test() {
        var p = properties.getRateLimiters().get(0);
        int responseCount = 0;
        for (int i = 0; i < 2; i++) {
            String exampleControllerResponse = exampleController.exampleMethod();
            if (exampleControllerResponse.equals("ok")) {
                responseCount++;
            }
        }
        assertEquals(2, responseCount);
    }

    @Test
    void exampleController_rateLimiter_unSuccess_test() {
        assertThrows(RuntimeException.class, () -> {
            for (int i = 0; i < 4; i++) {
                exampleController.exampleMethod();
            }
        });
    }

}