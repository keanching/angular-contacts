package com.keanching.angularcontacts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class AngularContactsApp {
    public static void main(String[] args) {
        SpringApplication.run(AngularContactsApp.class, args);
    }
}
