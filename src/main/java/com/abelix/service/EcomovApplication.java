package com.abelix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ComponentScan(basePackages = "com.abelix")
@EnableJpaRepositories(basePackages = "com.abelix.repository")
@EntityScan(basePackages = "com.abelix.model")

public class EcomovApplication implements CommandLineRunner {

    @Autowired
    private CSVLeitorService csvLeitorService;

    public static void main(String[] args) {
        SpringApplication.run(EcomovApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // Para popular apenas quando necessário
        csvLeitorService.carregarBairros();
        csvLeitorService.carregarConexoes();
        csvLeitorService.carregarPontosColeta();
    }
    

   
    
    
}
