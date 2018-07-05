package com.jermaine.democatchingbatchservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoCatchingbatchserviceApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(DemoCatchingbatchserviceApplication.class, args);
	}
}
//@SpringBootApplication is a convenience annotation that adds all of the following:
//
//@Configuration tags the class as a source of bean definitions for the application context.
//
//@EnableAutoConfiguration tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
//
//Normally you would add @EnableWebMvc for a Spring MVC app, but Spring Boot adds it automatically when it sees spring-webmvc on the classpath. This flags the application as a web application and activates key behaviors such as setting up a DispatcherServlet.
//
//@ComponentScan tells Spring to look for other components, configurations, and services in the hello package, allowing it to find the controllers.
//
//main() method uses Spring Boot’s SpringApplication.run() method to launch an application

//When an error occurs within a method, the method creates an object and hands it off to the runtime system. The object, called an exception object, contains information about the error, including its type and the state of the program when the error occurred. Creating an exception object and handing it to the runtime system is called throwing an exception.