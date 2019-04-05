# Ribbon

Ribbon es un equilibrador de carga del lado del cliente que le da mucho control sobre el comportamiento de los clientes HTTP y TCP.

## Guides

### Configuración básica sin utilizar un Server Eureka

#### Pre requisitos
Tener algún servicio funcionando, en nuestro caso se utilizara el que expone en el ejemplo EurekaClient. El fuente se puede bajar del siguiente
[link](https://github.com/VidoniJorge/spring-cloud-netflix/tree/master/EurekaClient)

El cual es un método get el cual se accede mediante la siguiente url http://localhost:8090/getProducto. El método getProducto no recibe paramentos de entrada y siempre retorna el siguiente json:

>     {
>          "nombre": "Marca",
>          "desc": "Chocolate",
>          "codigo": "00192"
>     }

#### Dependencias necesarias
     Group: 'org.springframework.cloud' ; artifact: 'cloud-starter-netflix-ribbon'

#### Procedimiento

* Configurar dependencias de librerías
* Configuramos nuestro service client
* Crear service client que consume el servicio expuesto, ver pre requisitos
* Configuramos nuestro servicio para que utilice Ribbon

##### Agregar Dependencias
Para configurar las dependencia en nuestro proyecto solo tenemos que agregar en nuestro archivo gradle o maven, las siguientes las dependencias especificadas anteriormente.

   Gradle

   >     dependencies {
   >          implementation 'org.springframework.boot:spring-boot-starter'
   >          compile 'org.springframework.cloud:spring-cloud-starter-netflix-ribbon'
   >          compile 'org.springframework.boot:spring-boot-starter-web'
   >     }

   Maven
   
   >     <dependencies>
   >          <dependency>
   >               <groupId>org.springframework.boot</groupId>
   >               <artifactId>spring-boot-starter</artifactId>
   >          </dependency>
   >          <dependency>
   >               <groupId>org.springframework.cloud</groupId>
   >               <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
   >          </dependency>
   >          <dependency>
   >               <groupId>org.springframework.boot</groupId>
   >               <artifactId>spring-boot-starter-web</artifactId>
   >          </dependency>
   >     </dependencies>
 

##### Configurar Cliente
Esta configuración se tiene que realizar en nuestro archivo application.properties o application.yml. Por el momento no agregaremos nada sobre la configuración de ribbon

Las propiedades básicas que son conveniente configurar son:

_Configuramos el puerto del Cliente de eureka_
> server.port = 8092

_Configurar el nombre de nuestro cliente_
> spring.application.name = <Nombre de nuestro cliente>
Este nombre es con el que se registrara el cliente en el server Eureka

##### Crear service client
Creamos un cliente el cual consuma nuestro servicio expuesto en http://localhost:8090/getProducto.

Ejemplo:

>     import org.springframework.beans.factory.annotation.Autowired;
>     import org.springframework.stereotype.Controller;
>     import org.springframework.web.bind.annotation.GetMapping;
>     import org.springframework.web.bind.annotation.RequestMapping;
>     import org.springframework.web.bind.annotation.ResponseBody;
>     import org.springframework.web.client.RestTemplate;
>
>     @Controller
>     public class RibbonController {
>          @Autowired
>          RestTemplate  restTemplate;
>
>          @GetMapping("/test")
>          @ResponseBody
>          public String test() {
>               return this.restTemplate.getForObject("http://localhost:8090/getProducto", String.class);
>          }
>     }

##### Configurar las propiedades de nuestro Ribbon

_Indicamos que no vamos a trabajar con un server Eureka_
>     <nombre a nuestra elección, se utilizara mas adelante como host>.ribbon.eureka.enabled: false

para nuestro ejemplo se utilizara el siguiente nombre **ribbon-test**, el cual se utilizara en las siguientes propiedades para abreviar. quedando la la anterior de la siguiente forma:
>     ribbon-test.ribbon.eureka.enabled: false

_Creamos la lista de url, que Ribbon utilizara para hacer el load balance_

>     ribbon-test.ribbon.listOfServers = <lista de url separadas por ",">

_ServerListRefreshInterval_
Es el intervalo, en milisegundos, entre las actualizaciones de la lista de servicios de Ribbon

>     ribbon-test.ribbon.ServerListRefreshInterval: 15000

#### Configuramos nuestro servicio para que utilice Ribbon

Lo primero que vamos a realizar es indicar a nuestro controller que vamos a utilizar Ribbon, esto lo realizamos con la anotación @RibbonClient (es obligatorio agregarle el name).

También anotamos el RestTemplate con @LoadBalanced, lo que sugiere que queremos que la carga sea equilibrada y, en este caso, con Ribbon.

Por ultimo modificamos la url http://localhost:8090/getProducto por http://ribbon-test/getProducto. Como se puede ver en lugar de utilizar el localhost:8090 utilizamos el nombre del host que configuramos en nuestro archivoapplication.properties o application.yml

Ejemplo:

>     import org.springframework.beans.factory.annotation.Autowired;
>     import org.springframework.stereotype.Controller;
>     import org.springframework.web.bind.annotation.GetMapping;
>     import org.springframework.web.bind.annotation.ResponseBody;
>     import org.springframework.web.client.RestTemplate;
>     import org.springframework.cloud.client.loadbalancer.LoadBalanced;
>     import org.springframework.cloud.netflix.ribbon.RibbonClient;
>     import org.springframework.context.annotation.Bean;
>
>     @Controller
>     @RibbonClient(name = "ribbon-a-test")
>     public class RibbonController {
>
>          @Autowired
>          RestTemplate  restTemplate;
>
>          @GetMapping("/test")
>          @ResponseBody
>          public String test() {
>               return this.restTemplate.getForObject("http://ribbon-test/getProducto", String.class);
>          }
>	
>          @LoadBalanced
>          @Bean
>          RestTemplate restTemplate(){
>               return new RestTemplate();
>          }
>     }
