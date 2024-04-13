package com.example.integrationtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloWorldHealthCheck {
    private static final Logger logger = LoggerFactory.getLogger(HelloWorldHealthCheck.class);

    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void invokeHelloWorldEndPoint() throws IOException {
        logger.info("Invoking web service");
        String apiUrl = "http://localhost:8080/api/health";
        String responseBody = restTemplate.getForObject(apiUrl, String.class);
        logger.info("Response Body: " + responseBody);
        assertEquals("OK", responseBody);
    }
}
