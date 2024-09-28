package com.joaovictor.picpaysimplificado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PicpaySimplificadoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PicpaySimplificadoApplication.class, args);
    }

}
