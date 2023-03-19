package com.younghan.two.proxy.postprocessor;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanPostProcessorTest {

    @Test
    void beanPostProcessor() {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);

        Assertions.assertThatThrownBy(() -> context.getBean("a", A.class))
                .isInstanceOf(NoSuchBeanDefinitionException.class);
    }

    @Configuration(proxyBeanMethods = false)
    static class BeanConfig {

        @Bean
        public A a() {
            return new A();
        }

        @Bean
        public AToBBeanPostProcessor aToBBeanPostProcessor() {
            return new AToBBeanPostProcessor();
        }
    }

    static class A {

    }

    static class B {

    }

    static class AToBBeanPostProcessor implements BeanPostProcessor {
        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            if (bean instanceof A) {
                return new B();
            }
            return bean;
        }
    }
}


