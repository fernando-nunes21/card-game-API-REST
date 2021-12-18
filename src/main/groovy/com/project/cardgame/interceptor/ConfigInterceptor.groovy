package com.project.cardgame.interceptor

import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@RequiredArgsConstructor
@Component
class ConfigInterceptor implements WebMvcConfigurer{

    @Autowired
    private Interceptor interceptor

    @Override
    void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(interceptor)
    }

}
