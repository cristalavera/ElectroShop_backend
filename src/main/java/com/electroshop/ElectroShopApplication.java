package com.electroshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class ElectroShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElectroShopApplication.class, args);
    }
}