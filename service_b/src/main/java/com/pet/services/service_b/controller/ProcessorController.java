package com.pet.services.service_b.controller;

import com.pet.services.service_b.dto.ProcessRequestDto;
import com.pet.services.service_b.service.ProcessorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProcessorController {
    private final ProcessorService service;

    @PostMapping("/api/process")
    public void process(@RequestBody ProcessRequestDto message, @RequestHeader(value = "X-Request-Id", required = false) String requestId) {
        String traceId = requestId != null ? requestId : java.util.UUID.randomUUID().toString();
        MDC.put("traceId", traceId);
        try {
            log.info("Received message to process={}, traceId={}", message, traceId);
            service.processMessage(message, traceId);
        } finally {
            MDC.clear();
        }
    }
}
