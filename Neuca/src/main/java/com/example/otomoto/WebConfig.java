package com.example.otomoto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private AptekaInterceptor aptekaInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Dzięki temu możesz np. wejść na: http://localhost:8080/foty/nazwa.jpg
        registry.addResourceHandler("/foty/**")
                .addResourceLocations("file:///C:/Users/User/Desktop/foty/");
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(aptekaInterceptor)
                .addPathPatterns("/Neuca/strefaAptekarza/**")
                .excludePathPatterns(
                        "/Neuca/strefaAptekarza/utworzApteke",
                        "/Neuca/strefaAptekarza/potwierdzApteka",
                        "/Neuca/strefaAptekarza/utworzonoApteke"
                );
    }

}
