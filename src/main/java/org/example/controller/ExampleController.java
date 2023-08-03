package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.ratelimiter.annotation.RateLimiter;
import org.example.ratelimiter.config.RateLimiterHazelcastProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class ExampleController {

    private final RateLimiterHazelcastProperties properties;

    @RateLimiter(name = "ratelimiter1")
    @GetMapping("/example")
    public String exampleMethod() {
        return "ok";
    }
}
