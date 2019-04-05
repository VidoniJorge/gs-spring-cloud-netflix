# Hystrix

Es la implementaci�n del patr�n de Circuit Breaker creada por Netfix.

La idea principal que tenemos que tener en cuenta cuando estamos implementando un Circuit Breaker, es 
como vamos a tratar en nuestro cliente alguna falla durante la comunicaci�n con alg�n servicio o con 
el procesamiento del response o request.

## Guides

### Configuraci�n b�sica

#### Dependencias necesarias
     Group: 'org.springframework.cloud' ; artifact: 'spring-cloud-starter-netflix-hystrix'
     Group: 'org.springframework.cloud' ; artifact: 'spring-cloud-starter-hystrix'

Tambi�n tenemos que crear las librer�as necesarias para la creaci�n del cliente. Esto varia 
dependiendo de la implementaci�n del cliente. En nuestro caso, utilizamos las siguientes.
	
     Group: org.springframework.cloud ; artifact: spring-cloud-starter-netflix-eureka-client
     Group: org.springframework.boot  : artifact: spring-boot-starter-web

Las siguientes dependencias son opcionales y solo se tienen que agregar si se quiere habilitar las m�tricas stream y el Dashboard
    
     Group: 'org.springframework.boot'  ; artifact: 'spring-boot-starter-actuator'
     Group: 'org.springframework.cloud' ; artifact: 'spring-cloud-starter-netflix-hystrix-dashboard'

#### Procedimiento
Para crear un Cliente Euraka b�sico solo necesitamos completar 5 pasos:
* Configurar dependencias de librer�as
* Configurar las propiedades del Cliente
* Habilitar circuit breaker
* Creamos nuestro servicio
* Configurar nuestro Circuit Breaker


##### Agregar Dependencias
Para configurar las dependencia en nuestro proyecto solo tenemos que agregar en nuestro archivo gradle o maven, las siguientes las dependencias especificadas anteriormente.

   Gradle

   >     dependencies {
   >        implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-client'
   >        compile 'org.springframework.boot:spring-boot-starter-web'
   >        compile group: 'org.springframework.cloud', name: 'spring-cloud-starter-netflix-hystrix'
   >        compile group: 'org.springframework.cloud', name: 'spring-cloud-starter-hystrix' 
   >        compile group: 'org.springframework.boot', name: 'spring-boot-starter-actuator'
   >        compile group: 'org.springframework.cloud', name: 'spring-cloud-starter-netflix-hystrix-dashboard'
   >     }

   Maven
   
   >     <dependencies>
   >          <dependency>
   >               <groupId>org.springframework.cloud</groupId>
   >               <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
   >          </dependency>
   >          <dependency>
   >               <groupId>org.springframework.boot</groupId>
   >               <artifactId>spring-boot-starter-web</artifactId>
   >          </dependency>
   >          <dependency>
   >               <groupId>org.springframework.cloud</groupId>
   >               <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
   >          </dependency>
   >          <dependency>
   >               <groupId>org.springframework.cloud</groupId>
   >               <artifactId>spring-cloud-starter-hystrix</artifactId>
   >          </dependency>
   >          <dependency>
   >               <groupId>org.springframework.cloud</groupId>
   >               <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
   >          </dependency>
   >          <dependency>
   >               <groupId>org.springframework.boot</groupId>
   >               <artifactId>spring-boot-starter-actuator</artifactId>
   >          </dependency>
   >     </dependencies>
 

##### Configurar Cliente
Esta configuraci�n se tiene que realizar en nuestro archivo application.properties o application.yml.

Las propiedades b�sicas que son conveniente configurar son:

_Configuramos el puerto del Cliente de eureka_
> server.port = 8091

_Configurar el nombre de nuestro cliente_
> spring.application.name = <Nombre de nuestro cliente>
Este nombre es con el que se registrara el cliente en el server Eureka

_Server Eureka_
> eureka.client.serviceUrl.defaultZone = http://localhost:8761/eureka/

indicamos la url de nuestro server Eureka

_Habilitamos las m�tricas stream_

> management.endpoints.web.exposure.include = hystrix.stream

Esto se expone en /actuator/hystrix.stream

##### Habilitar circuit breaker
Agregamos en nuestra clase SpringBootApplication la anotaci�n @EnableCircuitBreaker, con esto le decimos a Spring Cloud que la aplicaci�n usa interruptores autom�ticos y para habilitar su monitoreo, apertura y cierre.

Si queremos habilitar nuestro Dashboard agregamos la anotaci�n @EnableHystrixDashboard

Ejemplo:

>     import org.springframework.boot.SpringApplication;
>     import org.springframework.boot.autoconfigure.SpringBootApplication;
>     import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
>     import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
>
>     @SpringBootApplication
>     @EnableCircuitBreaker
>     @EnableHystrixDashboard
>     public class HystrixApplication {
>
>          public static void main(String[] args) {
>               SpringApplication.run(HystrixApplication.class, args);
>          }
>
>     }

#### Creamos nuestro servicio
Para ver como se configura un servicio b�sico ver el siguiente [link](https://github.com/VidoniJorge/spring-cloud-netflix/tree/master/EurekaClient)

Ejemplo de controller antes de agregar el circuit breaker

>     @Controller
>     public class ClientController {
>
>          @GetMapping("/getProducto")
>          @ResponseBody
>          public String getProducto() {
>               return new RestTemplate().getForObject("http://eze1-lhp-b01902.synapse.com:8090/getProducto",String.class);
>          }
>
>     }

#### Configurar nuestro Circuit Breaker

Lo primero que tenemos que hacer es agregar el m�todo que se invocara ante alguna falla en nuestra operaci�n principal. Para nuestro ejemplo se agrego el m�todo defaultMgs() el cual solo devuelve un mensaje duro.

Lo siguiente es vincular el metodo recien creado con la ejecucion de nuestro operacion principal. Esto lo haremos con la anotaci�n @HystrixCommand(fallbackMethod  = "defaultMgs").

Ejemplo:

>     import org.springframework.stereotype.Controller;
>     import org.springframework.web.bind.annotation.GetMapping;
>     import org.springframework.web.bind.annotation.ResponseBody;
>     import org.springframework.web.client.RestTemplate;
>     import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
>     import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
>
>     @Controller
>     public class ClientController {
>
>          @HystrixCommand(fallbackMethod  = "defaultMgs",
>               commandProperties =
>                    {
>                         @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="5"),
>                         @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="5000"),
>                         @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value="10")
>                    })
>          @GetMapping("/getProducto")
>          @ResponseBody
>          public String getProducto() {
>               return new RestTemplate().getForObject("http://eze1-lhp-  b01902.synapse.com:8090/getProducto",String.class);
>          }
>     	
>          private String defaultMgs() {
>               return "mensaje standar";
>          }
>     }

Se puede observar que en nuestro ejemplo tambi�n se agrego dentro del HystrixCommand una serie de propiedades. Este paso es opcional, pero nos sera �til para personalizar el comportamiento del circuit breaker.

Para ver m�s opciones de configuracion ver el siguiente [link](https://github.com/Netflix/Hystrix/wiki/Configuration#CommandCircuitBreaker)
