package com.younghan.two.proxy.cglib;

import com.younghan.two.proxy.cglib.code.TimeMethodInterceptor;
import com.younghan.two.proxy.common.service.ConcreteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class CglibTest {

    @Test
    void cglib() {
        ConcreteService target = new ConcreteService();
        TimeMethodInterceptor interceptor = new TimeMethodInterceptor(target);

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ConcreteService.class);
        enhancer.setCallback(interceptor);

        ConcreteService proxy = (ConcreteService) enhancer.create();

        log.info("target Class = {}", target.getClass());
        log.info("proxy Class = {}", proxy.getClass());

        proxy.call();
    }
}
