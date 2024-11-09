package com.intellect.serverstatuschecker;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply CORS settings to all endpoints
            .allowedOrigins("http://localhost:4200") // Allow requests from Angular's local dev server
            .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow these HTTP methods
            .allowedHeaders("*") // Allow all headers
            .allowCredentials(true); // Allow credentials (cookies, authorization headers, etc.)
    }
}

