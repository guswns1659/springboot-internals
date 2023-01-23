package com.younghan.two.proxy.trace;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TraceStatus {
    private TraceId traceId;
    private long startTimeMs;
    private String message;
}
