package com.pet.services.service_c.controller;

import com.pet.services.service_c.dto.SaveRequestDto;
import com.pet.services.service_c.entity.Message;
import com.pet.services.service_c.service.SaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SaveController {
    private final SaveService service;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "out";

    @PostMapping("/api/save")
    public void saveMessage(@RequestBody SaveRequestDto saveRequest, @RequestHeader(value = "X-Request-Id", required = false) String requestId) {
        String traceId = requestId != null ? requestId : java.util.UUID.randomUUID().toString();
        MDC.put("traceId", traceId);
        try {
            log.info("Received message to save={}, traceId={}", saveRequest, traceId);
            Message saved = service.saveMessage(saveRequest, traceId);
            String key = saved.getKey();
            String message = saved.getMessage();
            kafkaTemplate.send(TOPIC, key, message);
        } finally {
            MDC.clear();
        }
    }
}
