package org.example.service;

import containers.AbstractHazelcastTestContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = {"classpath:application-test.yml"})
class HazelcastClientServiceTest extends AbstractHazelcastTestContainer {

    @Autowired
    private HazelcastClientService hazelcastClientService;

    @BeforeAll
    static void init() {
        containerStart();
    }

    @AfterAll
    static void after() {
        containerClose();
    }

    @ParameterizedTest
    @CsvSource({"1,John", "2,Mary", "3,Jane"})
    public void test_service(String key, String expectedValue) {
        hazelcastClientService.writeInCache();
        String actualValue = hazelcastClientService.readFromCacheByKey(key);
        assertEquals(expectedValue, actualValue);
    }

}