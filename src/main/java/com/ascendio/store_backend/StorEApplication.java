package com.ascendio.store_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class StorEApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorEApplication.class, args);
    }

}
