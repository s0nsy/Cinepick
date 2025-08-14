package com.example.cinepick_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class CinepickBeApplication {

   public static void main(String[] args) {

      SpringApplication.run(CinepickBeApplication.class, args);

   }

}
