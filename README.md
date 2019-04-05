# spring-cloud-netflix

Todos todos los proyectos se están desarrollando con la versión 2.1.3.RELEASE de org.springframework.boot.

Cada proyecto tiene una breve descripción de como realizar una configuración básica.

## Eureka Server

Eureka es un servicio basado en REST (Representational State Transfer) que se utiliza principalmente 
en la nube de AWS para localizar servicios con el fin de equilibrar la carga y la conmutación por 
error de los servidores de nivel medio.

## Service Discovery: Eureka Clients

El descubrimiento de servicios es uno de los principios clave de una arquitectura basada en 
micro servicios. Tratar de configurar manualmente cada cliente o algún tipo de convención puede ser 
difícil de hacer y puede ser frágil. Eureka es el servidor y cliente de descubrimiento de servicios 
de Netflix. 	El servidor se puede configurar y desplegar para que esté altamente disponible, 
con cada servidor replicando el estado de los servicios registrados a los otros.

## Hystrix

Es la implementación del patrón de Circuit Breaker creada por Netfix.
## Ribbon

Ribbon es un equilibrador de carga del lado del cliente que le da mucho control sobre el comportamiento de los clientes HTTP y TCP.

## Zuul

Zuul es un enrutador basado en JVM y un equilibrador de carga del lado del servidor de Netflix.
