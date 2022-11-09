package com.logtrace.logtrace.logtrace;

import com.logtrace.logtrace.trace.TraceId;
import com.logtrace.logtrace.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class FieldLogTrace implements LogTrace {
    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    private TraceId traceIdHolder;

    @Override
    public TraceStatus begin(String message) {
        syncTraceId();
        long startMs = System.currentTimeMillis();
        log.info("[{}] {} {}",
                traceIdHolder.getId(),
                addSpace(START_PREFIX, traceIdHolder.getLevel()),
                message
        );
        return new TraceStatus(traceIdHolder, startMs, message);
    }

    @Override
    public void end(TraceStatus traceStatus) {
        complete(traceStatus, null);
    }

    @Override
    public void exception(TraceStatus traceStatus, Exception e) {
        complete(traceStatus, e);
    }

    private void complete(TraceStatus traceStatus, Exception e) {
        TraceId traceId = traceStatus.getTraceId();
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - traceStatus.getStartTimeMs();
        if (Objects.isNull(e)) {
            log.info("[{}] {} {} time = {}ms",
                    traceId.getId(),
                    addSpace(COMPLETE_PREFIX, traceId.getLevel()),
                    traceStatus.getMessage(),
                    elapsedTime
            );
        } else {
            log.info("[{}] {} {} time = {}ms ex={}",
                    traceId.getId(),
                    addSpace(EX_PREFIX, traceId.getLevel()),
                    traceStatus.getMessage(),
                    elapsedTime,
                    e.toString()
            );
        }
        releaseTraceId();
    }

    private void releaseTraceId() {
        if (traceIdHolder.isFirstLevel()) {
            traceIdHolder = null; // destory
        } else {
            traceIdHolder = traceIdHolder.createPreviousId();
        }
    }

    private void syncTraceId() {
        if (Objects.isNull(traceIdHolder)) {
            traceIdHolder = new TraceId();
        } else {
            traceIdHolder = traceIdHolder.createNextId();
        }
    }

    private String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append((i == level - 1) ? "|" + prefix : "|   ");
        }
        return sb.toString();
    }
}
