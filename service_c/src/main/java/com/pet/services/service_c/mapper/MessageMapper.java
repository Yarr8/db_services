package com.pet.services.service_c.mapper;

import com.pet.services.service_c.dto.SaveRequestDto;
import com.pet.services.service_c.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(target = "id", ignore = true)
    Message toEntity(SaveRequestDto request);
}
