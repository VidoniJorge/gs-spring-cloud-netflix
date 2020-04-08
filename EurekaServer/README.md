
# Service Registry

A continuación, se ejemplificará como realizar una implementación de un Service Registry con el stack de librerías que nos provee [Spring.io](www.spring.io).

El service registry que nos provee Spring se llama Eureka y está basado en la arquitectura que propuso Netflix.

Para más información [ver](https://spring.io/guides/gs/service-registration-and-discovery/)

## Dependencias

    Group: org.springframework.cloud; artifact:spring-cloud-starter-netflix-eureka-server

#### Procedimiento
Para crear un Server Euraka básico solo necesitamos completar 3 pasos:
* Configurar dependencias de librerías
* Configurar las propiedades del server (Se puede omitir este paso y dejar que el server tome los valores por defecto)
* Configurar la clase que inicializará el Server

##### Agregar Dependencias
Para configurar las dependencias en nuestro proyecto solo tenemos que agregar en nuestro archivo gradle o maven, las dependencias especificadas anteriormente.

   Gradle

   >     dependencies {
   >          implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-server'
   >     }

   Maven
   
   >     <dependencies>
   >          <dependency>
   >               <groupId>org.springframework.cloud</groupId>
   >               <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
   >          </dependency>
   >     </dependencies>

##### Configurar Server
Esta configuración se tiene que realizar en nuestro archivo application.properties o application.yml.

Las propiedades básicas que son conveniente configurar son

_Configuramos el puerto del server de eureka_
> server.port = 8761

_Configurar nuestro host name_
> eureka.instance.hostname = localhost

No es necesario configurar el hostname si se está ejecutando el server en una máquina que conoce su propio nombre de host (de forma predeterminada, se busca mediante java.net.InetAddress).

_Configuramos el cliente del server_

El Server de Eureka además de fungir como server, tambián desempeña el rol de cliente (Esto es para configurar el server en cluster). Si deseamos deshabilitar la opción de que nuestro server se conecte con otro, lo que tenemos que hacer es poner en false las siguientes propiedades

> eureka.client.registerWithEureka = false

Si hacemos que esta propiedad sea verdadera, entonces mientras el servidor inicia, el cliente incorporado intentará registrarse con el servidor Eureka.

> eureka.client.fetchRegistry = false

El cliente incorporado intentará obtener el registro de Eureka si configuramos esta propiedad como verdadera.

_Preservación de Eureka_

Durante el inicio, los clientes desencadenan una llamada **REST con el servidor Eureka para registrarse automáticamente en el registro de instancias del servidor**. Cuando se produce un apagado correcto después del uso, los clientes activan otra llamada REST para que el servidor pueda borrar todos los datos relacionados con la persona que llama.

Para manejar los apagados del cliente sin gracia, el servidor espera latidos del cliente a intervalos específicos. Esto se llama _renewal_. Si el servidor deja de recibir los latidos durante un período específico, comenzará a expulsar las instancias obsoletas.

El mecanismo que **deja de desalojar las instancias en que los latidos del corazón están por debajo del umbral esperado** se llama _self-preservation_. Esto puede suceder en el caso de una partición de red deficiente, donde las instancias todavía están activas, pero no se puede alcanzar por un momento o en el caso de un cierre abrupto del cliente.

Y cuando el servidor activa el modo de self-preservation, retiene el desalojo de la instancia hasta que la tasa de renovación vuelva a estar por encima del umbral esperado.

> eureka.server.enableSelfPreservation = false

para más información [ver](https://www.baeldung.com/eureka-self-preservation-renewal)

> eureka.server.maxThreadsForPeerReplication = false


##### Inicializar Server
Una vez configuradas las dependencias y configurado las propiedades de nuestro server, solo resta crear nuestra clase que iniciara el Server Eureka. La forma más simple es agregando la anotación @EnableEurekaServer en la clase SpringBootApplication.

Ejemplo:

>     package com.curso.spring.eurekaserver;
> 
>     import org.springframework.boot.SpringApplication;
>     import org.springframework.boot.autoconfigure.SpringBootApplication;
>     import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
>     
>     @EnableEurekaServer
>     @SpringBootApplication
>     public class EurekaServerApplication {
>
>          public static void main(String[] args) {
>               SpringApplication.run(EurekaServerApplication.class, args);
>          }
>
>     }
