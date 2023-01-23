package com.younghan.one.trace.logtrace;


import com.younghan.one.trace.TraceStatus;

public interface LogTrace {
    TraceStatus begin(String message);
    void end(TraceStatus traceStatus);
    void exception(TraceStatus traceStatus, Exception e);
}
