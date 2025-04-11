package com.example.otomoto;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // Dzięki temu możesz np. wejść na: http://localhost:8080/foty/nazwa.jpg
    registry.addResourceHandler("/foty/**")
            .addResourceLocations("file:///C:/Users/User/Desktop/foty/");
}

}
