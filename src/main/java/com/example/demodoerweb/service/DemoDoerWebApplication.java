package com.example.demodoerweb.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:context.xml")
public class DemoDoerWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoDoerWebApplication.class, args);
    }

}