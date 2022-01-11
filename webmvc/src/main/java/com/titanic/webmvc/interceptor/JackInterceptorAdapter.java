package com.titanic.webmvc.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@Configuration
@RequiredArgsConstructor
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JackInterceptorAdapter extends WebMvcConfigurerAdapter {

    private final JackHandlerInterceptor jackHandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("JackInterceptorAdapter");
        registry.addInterceptor(jackHandlerInterceptor);
    }
}
