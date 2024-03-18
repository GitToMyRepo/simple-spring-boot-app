package com.example.integrationtest;

import eu.rekawek.toxiproxy.Proxy;
import eu.rekawek.toxiproxy.ToxiproxyClient;
import eu.rekawek.toxiproxy.model.ToxicDirection;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public class HelloWorldApiNetworkTest {
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
        /*if (httpProxy != null) {
            httpProxy.toxics().latency("latency-toxic", ToxicDirection.DOWNSTREAM, 1000);
        }

        await().atMost(20, SECONDS).pollDelay(5, SECONDS).pollInterval(5, SECONDS).until(() -> true);*/

        restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:8082/api/health";
        String responseBody = restTemplate.getForObject(apiUrl, String.class);

        // Print the response body
        System.out.println("Response Body:");
        System.out.println(responseBody);

        if (httpProxy != null) httpProxy.delete();
    }

    public static void main(String... args) throws Exception {
        HelloWorldApiNetworkTest test = new HelloWorldApiNetworkTest();
        test.setUp();
        test.invokeHelloWorldEndPoint();
    }
}
