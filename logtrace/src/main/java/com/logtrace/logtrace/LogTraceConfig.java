package com.logtrace.logtrace;

import com.logtrace.logtrace.logtrace.FieldLogTrace;
import com.logtrace.logtrace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {
    @Bean
    public LogTrace logTrace() {
        return new FieldLogTrace();
    }
}
