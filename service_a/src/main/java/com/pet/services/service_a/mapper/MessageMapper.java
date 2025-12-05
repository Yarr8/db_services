package com.pet.services.service_a.mapper;

import com.pet.services.service_a.dto.MessageResponseDto;
import com.pet.services.service_a.entity.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageResponseDto toResponseDto(Message entity);
}
