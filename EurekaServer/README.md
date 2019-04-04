# Eureka Server

## Guides

### Crear un Server Eureka básico

#### Dependencias necesarias
Group: org.springframework.cloud; artifact:spring-cloud-starter-netflix-eureka-server

#### Procedimiento
Para crear un Server Euraka basico solo necestiamos completar 3 pasos:
* Configurar dependencias de librerías
* Configurar las propiedades del server (Se puede omitir este paso y dejar que el server tome los valores por defecto)
* Configurar la clase que inicializará el Server

##### Agregar Dependencias
Para configurar las dependencia en nuestro proyecto solo tenemos que agregar en nuestro archivo gradle o maven, las siguientes las dependencias especificadas anteriormente.

   Gradle

   >     dependencies {
   >
   >          implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-server'
   >
   >     }

   Maven
   
   >     <dependencies><dependency>
   >
   >          <groupId>org.springframework.cloud</groupId>
   >
   >          <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
   >
   >     </dependency></dependencies>
 

##### Configurar Server
Esta configuración se tiene que realizar en nuestro archivo application.properties o application.yml.

Las propiedades básicas que son conveniente configurar son

_Configuramos el puerto del server de eureka_
> server.port = 8761

_Configurar nuestro host name_
> eureka.instance.hostname = localhost

No es necesario configurar el hostname si se está ejecutando el server en una máquina que conoce su propio nombre de host (de forma predeterminada, se busca mediante java.net.InetAddress).

_Configuramos el cliente del server_

El Server de Eureka también es un cliente (Esto es para configurar el server en cluster), por tal motivo cuando se inicia lo lo primero que hace es intentar conectarse un server. Al no existir ninguno nos llenara nuestro log de errores. Para desavilitar solo tenemos que poner en false las siguientes propiedades

> eureka.client.registerWithEureka = false

Si hacemos que esta propiedad sea verdadera, entonces mientras el servidor inicia, el cliente incorporado intentará registrarse con el servidor Eureka.

> eureka.client.fetchRegistry = false

El cliente incorporado intentará obtener el registro de Eureka si configuramos esta propiedad como verdadera.

##### Inicializar Server
Una ves configuradas las dependencias y configurado las propiedades de nuestro server, solo resta crear nuestra clase que iniciara el Server Eureka. La forma más simple de hacer esto es agregando la anotación @EnableEurekaServer en la clase SpringBootApplication.

Ejemplo:

>     package com.curso.spring.eurekaserver;
> 
>     import org.springframework.boot.SpringApplication;
>
>     import org.springframework.boot.autoconfigure.SpringBootApplication;
>
>     import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
>
>     @EnableEurekaServer
>
>     @SpringBootApplication
>
>     public class EurekaServerApplication {
>
>          public static void main(String[] args) {
>
>               SpringApplication.run(EurekaServerApplication.class, args);
>
>          }
>
>     }