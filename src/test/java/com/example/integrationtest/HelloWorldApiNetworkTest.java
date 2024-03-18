package com.example.integrationtest;

import eu.rekawek.toxiproxy.Proxy;
import eu.rekawek.toxiproxy.ToxiproxyClient;
import eu.rekawek.toxiproxy.model.ToxicDirection;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class HelloWorldApiNetworkTest {
    private ToxiproxyClient client;

    private Proxy httpProxy;

    private RestTemplate restTemplate;

    private void setUp() throws Exception {
        client = new ToxiproxyClient("localhost", 8474);
        httpProxy = client.createProxy("http-tproxy", "localhost:8081", "localhost:8080");
    }

    public void invokeHelloWorldEndPoint() throws IOException {
        if (httpProxy != null) {
            httpProxy.toxics().latency("latency-toxic", ToxicDirection.DOWNSTREAM, 1000);
        }

        restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:8081/api/health";
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
