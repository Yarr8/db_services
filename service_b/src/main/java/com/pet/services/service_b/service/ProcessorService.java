package com.pet.services.service_b.service;

import com.pet.services.service_b.dto.ProcessRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class ProcessorService {

    private final RestClient restClient;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public void processMessage(ProcessRequestDto message, String traceId) {
        if (shouldSave(message))
            save(message);

        restClient.post()
                .uri("/api/save")
                .body(message)
                .retrieve()
                .toBodilessEntity();
    }

    private boolean shouldSave(ProcessRequestDto message) {
        return true;
    }

    private void save(ProcessRequestDto message) {
        String json = objectMapper.writeValueAsString(message);
        redisTemplate.opsForValue().set("message:" + message.key(), json);
    }
}
