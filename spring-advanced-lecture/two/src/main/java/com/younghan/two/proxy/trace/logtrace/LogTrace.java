package com.younghan.two.proxy.trace.logtrace;


import com.younghan.two.proxy.trace.TraceStatus;

public interface LogTrace {
    TraceStatus begin(String message);
    void end(TraceStatus traceStatus);
    void exception(TraceStatus traceStatus, Exception e);
}
