package com.example.btpservice.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class corsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // Match your actual endpoint path
                        .allowedOrigins("*")   // You can restrict this to your frontend domain
                        .allowedMethods("GET", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
}
