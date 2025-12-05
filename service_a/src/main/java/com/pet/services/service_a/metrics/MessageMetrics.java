package com.pet.services.service_a.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class MessageMetrics {

    private final Counter messagesReceivedCounter;

    public MessageMetrics(MeterRegistry registry) {
        this.messagesReceivedCounter = Counter.builder("messages_received_total")
                .description("Count of received messages")
                .register(registry);
    }

    public void increment() {
        messagesReceivedCounter.increment();
    }
}
