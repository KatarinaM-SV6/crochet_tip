package com.ftn.sbnz.service.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.data.domain.PageRequest;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    // Za svrhe razvoja konfigurisemo dozvolu za CORS kako ne bismo morali @CrossOrigin anotaciju da koristimo nad svakim kontrolerom
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:3000").allowedHeaders("*").allowCredentials(true);
    }
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setOneIndexedParameters(true);
        resolver.setFallbackPageable(PageRequest.of(0, 99));
        argumentResolvers.add(resolver);
    }
}
