package com.chronosx.demochatbe.mapper;

import com.chronosx.demochatbe.dto.MessageRoomDto;
import com.chronosx.demochatbe.entity.MessageRoom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageRoomMapper {
    @Mapping(source = "createdBy.username", target = "createdBy")
    MessageRoomDto toDto(MessageRoom entity);

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "members", ignore = true)
    @Mapping(target = "messageContents", ignore = true)
    MessageRoom toEntity(MessageRoomDto dto);
}
