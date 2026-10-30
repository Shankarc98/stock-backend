package com.example.stock_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/**")
                        .allowedOriginPatterns(
                            "http://localhost",
                            "http://localhost:*",
                            "http://127.0.0.1",
                            "http://127.0.0.1:*",
                            "https://stock-exchange-app-zeta.vercel.app",
                            "https://stock-exchange-app-git-main-shankarnarayan1555-3920s-projects.vercel.app"                            
                        )
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowCredentials(true);

            }
        };
    }
}
