# spring-cloud-netflix

Todos todos los proyectos se est�n desarrollando con la versi�n 2.1.3.RELEASE de org.springframework.boot.

Cada proyecto tiene una breve descripci�n de como realizar una configuraci�n b�sica.

## Eureka Server

Eureka es un servicio basado en REST (Representational State Transfer) que se utiliza principalmente 
en la nube de AWS para localizar servicios con el fin de equilibrar la carga y la conmutaci�n por 
error de los servidores de nivel medio.

## Service Discovery: Eureka Clients

El descubrimiento de servicios es uno de los principios clave de una arquitectura basada en 
micro servicios. Tratar de configurar manualmente cada cliente o alg�n tipo de convenci�n puede ser 
dif�cil de hacer y puede ser fr�gil. Eureka es el servidor y cliente de descubrimiento de servicios 
de Netflix. 	El servidor se puede configurar y desplegar para que est� altamente disponible, 
con cada servidor replicando el estado de los servicios registrados a los otros.

## Hystrix

Es la implementaci�n del patr�n de Circuit Breaker creada por Netfix.
## Ribbon

Ribbon es un equilibrador de carga del lado del cliente que le da mucho control sobre el comportamiento de los clientes HTTP y TCP.

## Zuul

Zuul es un enrutador basado en JVM y un equilibrador de carga del lado del servidor de Netflix.
