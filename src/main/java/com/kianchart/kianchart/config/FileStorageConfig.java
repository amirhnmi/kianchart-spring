package com.kianchart.kianchart.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileStorageConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/image/**")
                .addResourceLocations("file:uploads/images/");

        registry.addResourceHandler("/uploads/video/**")
                .addResourceLocations("file:uploads/videos/");

        registry.addResourceHandler("/uploads/document/**")
                .addResourceLocations("file:uploads/documents/");
    }
}
