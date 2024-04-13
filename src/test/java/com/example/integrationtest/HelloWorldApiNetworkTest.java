package com.example.integrationtest;

import eu.rekawek.toxiproxy.Proxy;
import eu.rekawek.toxiproxy.ToxiproxyClient;
import eu.rekawek.toxiproxy.model.ToxicDirection;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public class HelloWorldApiNetworkTest {
    private static final Logger logger = LoggerFactory.getLogger(HelloWorldApiNetworkTest.class);
    private ToxiproxyClient client;

    private Proxy httpProxy;

    private RestTemplate restTemplate;

    // start up toxiproxy server in docker
    // docker run -d --name toxiproxy -p 8474:8474 ghcr.io/shopify/toxiproxy
    private void setUp() throws Exception {
        client = new ToxiproxyClient("127.0.0.1", 8474);
        httpProxy = client.createProxy("http-tproxy", "localhost:8082", "localhost:8080");
    }

    public void invokeHelloWorldEndPoint() throws IOException {
        httpProxy.toxics().latency("latency-toxic", ToxicDirection.DOWNSTREAM, 5000);
        logger.info("Invoking web service");

        await().atMost(20, SECONDS).pollDelay(5, SECONDS).pollInterval(2, SECONDS).until(() -> {
            restTemplate = new RestTemplate();
            String apiUrl = "http://localhost:8082/api/health";
            String responseBody = restTemplate.getForObject(apiUrl, String.class);
            logger.info("Response Body: " + responseBody);
            return StringUtils.equals("OK", responseBody);
        });

        httpProxy.delete();
        logger.info("Deleted proxy");
    }

    public static void main(String... args) throws Exception {
        HelloWorldApiNetworkTest test = new HelloWorldApiNetworkTest();
        test.setUp();
        test.invokeHelloWorldEndPoint();
    }
}
