http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#getting-started

The second class-level annotation is @EnableAutoConfiguration. This annotation tells Spring Boot to “guess” how you will want to configure Spring, based on the jar dependencies that you have added. Since spring-boot-starter-web added Tomcat and Spring MVC, the auto-configuration will assume that you are developing a web application and setup Spring accordingly.


Run
mvn spring-boot:run. Or you can build the JAR file with mvn clean package and run the JAR by typing: java -jar target/gs-actuator-service-0.1.0.jar

Configure
	
	
	
Convert an existing application to Spring Boot 
http://docs.spring.io/spring-boot/docs/current/reference/html/howto-traditional-deployment.html	