# Simple Spring boot app

## Docker

### Build Docker Image
```
docker build --tag=simple-spring-boot-app:latest .
```

### Run Simple Toxic Proxy Test
1. Start Toxiproxy Server
```
C:\mytools\toxiproxy_2.8.0_windows_amd64\.\toxiproxy-server
```
2. Run _SimpleSpringBootAppApplication_
3. Run _HelloWorldApiNetworkTest_, it will produce the following logs for 5 seconds HTTP delay
```
10:31:04.079 [main] INFO com.example.integrationtest.HelloWorldApiNetworkTest -- Invoking web service
10:31:14.370 [awaitility-thread] INFO com.example.integrationtest.HelloWorldApiNetworkTest -- Response Body: OK
10:31:14.379 [main] INFO com.example.integrationtest.HelloWorldApiNetworkTest -- Deleted proxy
```