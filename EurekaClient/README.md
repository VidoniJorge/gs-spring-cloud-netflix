# Discovery Client

## Guides

### Crear un Cliente de básico

#### Dependencias necesarias
     Group: org.springframework.cloud ; artifact: spring-cloud-starter-netflix-eureka-client
     Group: org.springframework.boot ; artifact: spring-boot-starter-web


#### Procedimiento
Para crear un Cliente Euraka básico solo necesitamos completar 4 pasos:
* Configurar dependencias de librerías
* Configurar las propiedades del Cliente
* Configurar la clase que inicializará el Cliente
* Creamos nuestro servicio (Esto es opcional)


##### Agregar Dependencias
Para configurar las dependencia en nuestro proyecto solo tenemos que agregar en nuestro archivo gradle o maven, las siguientes las dependencias especificadas anteriormente.

   Gradle

   >     dependencies {
   >          implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-client'
   >          compile 'org.springframework.boot:spring-boot-starter-web'
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
   >     </dependencies>
 

##### Configurar Cliente
Esta configuración se tiene que realizar en nuestro archivo application.properties o application.yml.

Las propiedades básicas que son conveniente configurar son

_Configuramos el puerto del Cliente de eureka_
> server.port = 8090

_Configurar el nombre de nuestro cliente_
spring.application.name = <Nombre de nuestro cliente>
Ests nombre es con el que se registrara el cliente en el server Eureka

_indica_
eureka.client.serviceUrl.defaultZone = http://localhost:8761/eureka/

indicamos la url de nuestro server Eureka

##### Inicializar Cliente Eureka
Una ves configuradas las dependencias y configurado las propiedades de nuestro cliente, solo resta crear nuestra clase que iniciara el Cliente Eureka. La forma más simple de hacer esto es agregando la anotación @EnableDiscoveryClient en la clase SpringBootApplication.

Ejemplo:

>     import org.springframework.boot.SpringApplication;
>     import org.springframework.boot.autoconfigure.SpringBootApplication;
>     import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
>
>     @EnableDiscoveryClient
>     @SpringBootApplication
>     public class EurekaClientBackApplication {
>
>          public static void main(String[] args) {
>               SpringApplication.run(EurekaClientBackApplication.class, args);
>          }
>
>     }

#### Creamos nuestro servicio
Nuestro servicio lo creamos agregado la anotación @Controller a la clase que expondrá nuestros métodos. En el ejemplo siguiente ejemplo se expone un método get el cual responde un json de productos

Ejemplo
>     import java.util.List;
>     import org.springframework.beans.factory.annotation.Autowired;
>     import org.springframework.cloud.client.ServiceInstance;
>     import org.springframework.cloud.client.discovery.DiscoveryClient;
>     import org.springframework.stereotype.Controller;
>     import org.springframework.web.bind.annotation.GetMapping;
>     import org.springframework.web.bind.annotation.RequestMapping;
>     import org.springframework.web.bind.annotation.RequestParam;
>     import org.springframework.web.bind.annotation.ResponseBody;
>
>     @Controller
>     public class ClientBackController {
>
>          @Autowired
>          private DiscoveryClient discoveryClient;
>	
>          @GetMapping("/getProducto")
>          @ResponseBody
>          public Producto getProducto() {
>               return new Producto("Milka", "Chocolate", "00192");
>          }
>     }