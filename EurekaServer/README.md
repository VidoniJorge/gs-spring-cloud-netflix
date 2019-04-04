# Eureka Server

## Guides

### Crear un Server Eureka básico

#### Dependencias necesarias
Group: org.springframework.cloud; artifact:spring-cloud-starter-netflix-eureka-server

#### Procedimiento
1. Lo primero que tenemos que hacer es configurar la dependencia en nuestro proyecto. Esto lo realizamos agregando en nuestro.

   Gradle

   > dependencies {
   >
   > 	implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-server'
   >
   > 	testImplementation 'org.springframework.boot:spring-boot-starter-test' 
   >
   > }

   Maven

2. Una ves configuradas las dependencias, tenemos que crear nuestra clase que iniciara el Server Eureka. La forma más simple de hacer esto es agregando la anotación @EnableEurekaServer en la clase applicacion.

>package com.curso.spring.eurekaserver;
> 
>import org.springframework.boot.SpringApplication;
>
>import org.springframework.boot.autoconfigure.SpringBootApplication;
>
>import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
>
>@EnableEurekaServer
>
>@SpringBootApplication
>
>public class EurekaServerApplication {
>
>  public static void main(String[] args) {
>
>    SpringApplication.run(EurekaServerApplication.class, args);
>
>	}
>
>}

3. La ultima parte es configurar las propiedades de nuestro server en nuestro archivo application.properties o application.yml
eureka:  
  client:
    serviceUrl:
      defaultZone: http://localhost:8760/eureka/,http://localhost:8761/eureka/

server:
  port: 8761
spring:
  profiles: peer2
eureka:
  instance:
    hostname: localhost
  client:
    registerWithEureka: false
    fetchRegistry: false
    serviceUrl:
      defaultZone: http://localhost:8760/eureka/


