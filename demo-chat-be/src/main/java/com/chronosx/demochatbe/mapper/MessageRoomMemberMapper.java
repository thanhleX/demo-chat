package com.chronosx.demochatbe.mapper;

import com.chronosx.demochatbe.dto.MessageRoomMemberDto;
import com.chronosx.demochatbe.entity.MessageRoomMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageRoomMemberMapper {
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "messageRoom.messageRoomId", target = "messageRoomId")
    @Mapping(source = "user.lastLogin", target = "lastLogin")
    MessageRoomMemberDto toDto(MessageRoomMember entity);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "messageRoom", ignore = true)
    @Mapping(target = "id", ignore = true)
    MessageRoomMember toEntity(MessageRoomMemberDto dto);
}
