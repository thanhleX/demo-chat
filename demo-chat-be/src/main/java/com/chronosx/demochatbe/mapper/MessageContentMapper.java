package com.chronosx.demochatbe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.chronosx.demochatbe.dto.MessageContentDto;
import com.chronosx.demochatbe.entity.MessageContent;

@Mapper(componentModel = "spring")
public interface MessageContentMapper {

    @Mapping(source = "messageRoom.id", target = "messageRoomId")
    @Mapping(source = "user.username", target = "sender")
    MessageContentDto toDto(MessageContent entity);

    @Mapping(target = "dateSent", ignore = true)
    @Mapping(target = "messageRoom", ignore = true)
    @Mapping(target = "user", ignore = true)
    MessageContent toEntity(MessageContentDto dto);
}
