package com.skillio.api_v1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOriginPatterns(
                        "https://skillio.netlify.app",
                        "https://test-skillio.netlify.app",
                        "https://localhost:4200"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders(
                        "Authorization", 
                        "Content-Type",
                        "Accept",
                        "Origin",
                        "X-Requested-With"
                )
                .exposedHeaders("Authorization")  // Permite que el cliente lea el header Authorization
                .allowCredentials(true)
                .maxAge(3600);
    }

}
