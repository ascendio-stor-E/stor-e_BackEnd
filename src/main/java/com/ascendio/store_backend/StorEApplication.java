package com.ascendio.store_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class StorEApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorEApplication.class, args);
    }

}
