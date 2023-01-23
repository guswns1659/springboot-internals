package com.logtrace.logtrace.proxy.trace;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TraceStatus {
    private TraceId traceId;
    private long startTimeMs;
    private String message;
}
