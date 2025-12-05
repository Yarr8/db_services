package com.pet.services.service_a.service;

import com.pet.services.service_a.dto.MessageResponseDto;
import com.pet.services.service_a.entity.Message;
import com.pet.services.service_a.mapper.MessageMapper;
import com.pet.services.service_a.metrics.MessageMetrics;
import com.pet.services.service_a.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsumerService {

    private final MessageRepository repository;
    private final RestClient restClient;
    private final MessageMapper messageMapper;
    private final MessageMetrics messageMetrics;

    @KafkaListener(id = "service-a", topics = "in")
    public void listen(String message, @Header(name = KafkaHeaders.RECEIVED_KEY, required = false) String key) {
        String traceId = randomUUID().toString();
        MDC.put("traceId", traceId);

        log.info("Received message. Message='{}', traceId={}", message, traceId);

        try {
            Message messageEntity = new Message();
            messageEntity.setMessage(message);
            messageEntity.setKey(key);
            Message savedMessage = repository.save(messageEntity);

            MessageResponseDto messageDto = messageMapper.toResponseDto(savedMessage);

            restClient.post()
                    .uri("/api/process")
                    .body(messageDto)
                    .retrieve()
                    .toBodilessEntity();

            messageMetrics.increment();
        } catch (Exception e) {
            System.err.println("Failed to send to next service: " + e.getMessage());
        } finally {
            MDC.clear();
        }
    }

}
