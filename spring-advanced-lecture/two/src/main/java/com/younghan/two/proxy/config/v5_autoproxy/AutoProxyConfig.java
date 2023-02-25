package com.younghan.two.proxy.config.v5_autoproxy;

import com.younghan.two.proxy.config.AppV1Config;
import com.younghan.two.proxy.config.AppV2Config;
import com.younghan.two.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import com.younghan.two.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {

    @Bean
    public Advisor advisor(LogTrace logTrace) {
        // 정확하지 않은 pointcut
//        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
//        pointcut.setMappedNames("request*", "order*", "save*");

        // 정확한 pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.younghan.two.proxy.app..*(..)) && !execution(* com.younghan.two.proxy.app..noLog(..))");

        // advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
