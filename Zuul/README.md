# ZUUL

Zuul es un enrutador basado en JVM y un equilibrador de carga del lado del servidor de Netflix.

## Guides

### Configuración básica

#### Pre requisitos
Tener algún servicio funcionando, en nuestro caso se utilizara el que expone en el ejemplo Ribbon. El fuente se puede bajar del siguiente
[link](https://github.com/VidoniJorge/spring-cloud-netflix/tree/master/Ribbon)

El cual es un método get el cual se accede mediante la siguiente url http://localhost:8092/test. El método test no recibe paramentos de entrada y siempre retorna el siguiente json:

>     {
>          "nombre": "Marca",
>          "desc": "Chocolate",
>          "codigo": "00192"
>     }

#### Dependencias necesarias
     Group: 'org.springframework.cloud' ; artifact: 'spring-cloud-starter-netflix-zuul'

#### Procedimiento

* Configurar dependencias de librerías
* Configurar Zuul
* Habilitar Zuul

##### Agregar Dependencias
Para configurar las dependencia en nuestro proyecto solo tenemos que agregar en nuestro archivo gradle o maven, las siguientes las dependencias especificadas anteriormente.

   Gradle

   >     dependencies {
   >          implementation 'org.springframework.cloud:spring-cloud-starter-netflix-zuul'
   >     }

   Maven
   
   >     <dependencies>
   >          <dependency>
   >               <groupId>org.springframework.cloud</groupId>
   >               <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
   >          </dependency>
   >     </dependencies>
 

##### Configurar Cliente
Esta configuración se tiene que realizar en nuestro archivo application.properties o application.yml. Por el momento no agregaremos nada sobre la configuración de ribbon

Las propiedades básicas que son conveniente configurar son:

_Configuramos el puerto de zuul_
> server.port = 8080

_Configurar el router_
> zuul.routes.<path a utiliza>.url = <utl a invocar>

para nuestro ejemplo se configuro de la siguiente manera

> zuul.routes.producto-back-zuul.url = http://localhost:8092

##### Habilitar Zuul
Esto se hace en nuestra clase SpringBootApplication, agregando la anotación @EnableZuulProxy

Ejemplo:

>     import org.springframework.boot.SpringApplication;
>     import org.springframework.boot.autoconfigure.SpringBootApplication;
>     import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
>
>     @EnableZuulProxy
>     @SpringBootApplication
>     public class ZuulApplication {
>
>          public static void main(String[] args) {
>                    SpringApplication.run(ZuulApplication.class, args);
>          }
>
>     }

Para probar nuestro router, solo tenemos que invocar la siguiente url http://localhost:8080/producto-back-zuul/test. 