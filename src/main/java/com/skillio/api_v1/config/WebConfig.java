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
                ) // Se pasan todos los orígenes en una sola llamada
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permitir estos métodos HTTP
                .allowedHeaders("Content-Type") // Permitir estos encabezados en las solicitudes
                .allowCredentials(true) // Permitir el envío de credenciales (cookies, tokens de autenticación, etc.)
                .maxAge(3600); // Tiempo máximo que los resultados preflight serán cacheados
    }

}
