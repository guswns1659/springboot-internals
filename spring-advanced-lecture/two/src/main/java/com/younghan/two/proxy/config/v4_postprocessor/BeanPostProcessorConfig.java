package com.younghan.two.proxy.config.v4_postprocessor;

import com.younghan.two.proxy.config.AppV1Config;
import com.younghan.two.proxy.config.AppV2Config;
import com.younghan.two.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import com.younghan.two.proxy.config.v4_postprocessor.postprocessor.PackageLogTraceProxyPostProcessor;
import com.younghan.two.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class BeanPostProcessorConfig {

    @Bean
    public PackageLogTraceProxyPostProcessor packageLogTraceProxyPostProcessor(LogTrace logTrace) {
        String basePackage = "com.younghan.two.proxy.app";

        // pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        // advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, advice);

        return new PackageLogTraceProxyPostProcessor(basePackage, advisor);
    }
}
