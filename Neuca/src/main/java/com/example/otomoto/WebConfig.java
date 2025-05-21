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

    @Autowired
    private LekarzInterceptor lekarzInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
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

        registry.addInterceptor(lekarzInterceptor)
                .addPathPatterns("/Neuca/strefaLekarza/**")
                .excludePathPatterns(
                        "/Neuca/strefaLekarza/UtworzGabinet",
                        "/Neuca/strefaLekarza/potwierdzLekarza",
                        "/Neuca/strefaLekarza/utworzonoGabinet"
                );
    }
}
