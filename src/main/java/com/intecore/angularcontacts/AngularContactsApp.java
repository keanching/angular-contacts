package com.intecore.angularcontacts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class AngularContactsApp extends SpringBootServletInitializer{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AngularContactsApp.class);
    }
    
    public static void main(String[] args) {
        SpringApplication.run(AngularContactsApp.class, args);
    }
}
