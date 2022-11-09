package com.logtrace.logtrace.logtrace;

import com.logtrace.logtrace.trace.TraceStatus;

public interface LogTrace {
    TraceStatus begin(String message);
    void end(TraceStatus traceStatus);
    void exception(TraceStatus traceStatus, Exception e);
}
