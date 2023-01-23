package com.logtrace.logtrace.advanced.trace.logtrace;

import com.logtrace.logtrace.advanced.trace.TraceStatus;

public interface LogTrace {
    TraceStatus begin(String message);
    void end(TraceStatus traceStatus);
    void exception(TraceStatus traceStatus, Exception e);
}
