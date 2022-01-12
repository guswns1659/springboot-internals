package com.titanic.webmvc.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@Configuration
@RequiredArgsConstructor
@Slf4j
public class JackInterceptorAdapter2 extends WebMvcConfigurerAdapter {

    private final JackHandlerInterceptor2 jackHandlerInterceptor2;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("JackInterceptorAdapter2");
        registry.addInterceptor(jackHandlerInterceptor2);
    }
}
