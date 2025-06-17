package com.example.cinepick_be.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
      String basePathMbti;

      if (System.getProperty("os.name").toLowerCase().contains("win")) {
         // 윈도우 환경일 때
         basePathMbti = "file:/C:/Users/82104/IdeaProjects/cinepick-be/uploads/mbti/";
      } else {
         // 리눅스 환경일 때
         basePathMbti = "file:/home/ubuntu/cinepick-be/uploads/mbti/";
      }

      registry.addResourceHandler("/uploads/mbti/**")
            .addResourceLocations(basePathMbti);
   }
}
