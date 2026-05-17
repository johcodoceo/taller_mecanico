package com.taller_mecanico.orden_trabajo_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrdenTrabajoServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(
                OrdenTrabajoServiceApplication.class,
                args
        );
    }
}
