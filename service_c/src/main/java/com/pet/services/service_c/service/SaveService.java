package com.pet.services.service_c.service;

import com.pet.services.service_c.dto.SaveRequestDto;
import com.pet.services.service_c.entity.Message;
import com.pet.services.service_c.mapper.MessageMapper;
import com.pet.services.service_c.repository.SaveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SaveService {
    private final SaveRepository repository;
    private final MessageMapper mapper;

    public Message saveMessage(SaveRequestDto saveRequest, String traceId) {
        log.info("Saving message. Message='{}', traceId={}", saveRequest, traceId);
        Message messageEntity = mapper.toEntity(saveRequest);
        return repository.save(messageEntity);
    }
}
