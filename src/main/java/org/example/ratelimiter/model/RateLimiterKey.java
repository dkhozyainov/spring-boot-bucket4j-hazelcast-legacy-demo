package org.example.ratelimiter.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class RateLimiterKey implements Serializable {
    private String userId;
}
