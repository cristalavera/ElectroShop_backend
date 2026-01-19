package com.electroshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class ElectroShopApplication {

    public static void main(String[] args) {
        // Carga .env solo si existe
        try {
            Dotenv dotenv = Dotenv.load();
            System.setProperty("DB_URL", dotenv.get("DB_URL"));
            System.setProperty("DB_USER", dotenv.get("DB_USER"));
            System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        } catch (Exception e) {
            // Si no existe .env, asumimos que estamos en producción
            System.out.println("No se encontró .env, usando variables de entorno del sistema.");
        }

        SpringApplication.run(ElectroShopApplication.class, args);
    }
}