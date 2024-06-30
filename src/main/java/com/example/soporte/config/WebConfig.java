package com.example.soporte.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
    /**
     * Configures Cross-Origin Resource Sharing (CORS) mappings.
     *
     * @param registry an instance of CorsRegistry used to register CORS configuration
     * This method:
     * - Applies CORS configuration to all paths.
     * - Allows requests from any origin.
     * - Permits HTTP methods GET, POST, PUT, and DELETE for cross-origin requests.
     * - Allows all headers in cross-origin requests.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
            .allowedHeaders("*");
    }
}
