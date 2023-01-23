package com.logtrace.logtrace.proxy.trace.callback;

import com.logtrace.logtrace.advanced.trace.logtrace.LogTrace;
import com.logtrace.logtrace.advanced.trace.TraceStatus;

public class TraceTemplate {
    private final LogTrace trace;

    public TraceTemplate(LogTrace trace) {
        this.trace = trace;
    }

    public <T> T execute(String message, TraceCallback<T> callback) {
        TraceStatus status = null;
        try {
            status = trace.begin(message);

            // 로직 호출. 변하는 부분
            T result = callback.call();

            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
