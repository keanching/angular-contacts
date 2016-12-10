package com.intecore.angularcontacts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class AngularContactsApp extends SpringBootServletInitializer{
    public static void main(String[] args) {
        SpringApplication.run(AngularContactsApp.class, args);
    }
}
