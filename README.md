# Simple Spring boot app


### Run Simple Toxiproxy Test
1. Start Toxiproxy Server. Note that this is required for any Toxiproxy test.
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

## Docker

### Build Docker Image
```
docker build --tag=simple-spring-boot-app:latest .
```

### Run the web service application in a Docker container
```
docker run -p 8080:8080 --name simple-spring-boot-app-container simple-spring-boot-app
```

The following logs indicates it starts up
```
PS C:\Users\kenwu> docker run -p 8080:8080 --name simple-spring-boot-app-container simple-spring-boot-app

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.1.1)

2024-04-13T10:07:10.380Z  INFO 1 --- [           main] c.e.SimpleSpringBootAppApplication       : Starting SimpleSpringBootAppApplication v0.0.1-SNAPSHOT using Java 17.0.2 with PID 1 (/app/app.jar started by root in /app)
2024-04-13T10:07:10.384Z  INFO 1 --- [           main] c.e.SimpleSpringBootAppApplication       : No active profile set, falling back to 1 default profile: "default"
2024-04-13T10:07:11.250Z  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2024-04-13T10:07:11.268Z  INFO 1 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2024-04-13T10:07:11.268Z  INFO 1 --- [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.10]
2024-04-13T10:07:11.352Z  INFO 1 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2024-04-13T10:07:11.355Z  INFO 1 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 874 ms
2024-04-13T10:07:11.668Z  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2024-04-13T10:07:11.687Z  INFO 1 --- [           main] c.e.SimpleSpringBootAppApplication       : Started SimpleSpringBootAppApplication in 1.746 seconds (process running for 2.162)
```

### Run Toxiproxy Test using Docker

#### Run Toxiproxy Server in Docker

1. Create a Toxiproxy server image (https://github.com/Shopify/toxiproxy/pkgs/container/toxiproxy)
```
docker pull ghcr.io/shopify/toxiproxy:2.9.0
```
2. Run Toxiproxy server in Docker. Note that this is required for any Toxiproxy test.
```
docker run -d --name toxiproxy -p 8474:8474 ghcr.io/shopify/toxiproxy
```
3. Run _HelloWorldApiNetworkTest_