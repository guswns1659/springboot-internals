package com.logtrace.logtrace.trace.app.v4;

import com.logtrace.logtrace.logtrace.LogTrace;
import com.logtrace.logtrace.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV4 {
    private final OrderRepositoryV4 orderRepository;
    private final LogTrace trace;

    public void orderItem(String itemId) {
        AbstractTemplate<Void> abstractTemplate = new AbstractTemplate<>(trace) {

            @Override
            protected Void call() {
                orderRepository.save(itemId);
                return null;
            }
        };
        abstractTemplate.execute("OrderService.orderItem()");
    }
}
