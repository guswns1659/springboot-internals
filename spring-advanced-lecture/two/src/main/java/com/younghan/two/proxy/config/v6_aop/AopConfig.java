package com.younghan.two.proxy.config.v6_aop;

import com.younghan.two.proxy.config.AppV1Config;
import com.younghan.two.proxy.config.AppV2Config;
import com.younghan.two.proxy.config.v6_aop.aspect.LogTraceAspect;
import com.younghan.two.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AopConfig {

    @Bean
    public LogTraceAspect aspect(LogTrace logTrace) {
        return new LogTraceAspect(logTrace);
    }
}
