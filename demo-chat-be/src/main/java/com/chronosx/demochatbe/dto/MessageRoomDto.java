package com.chronosx.demochatbe.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageRoomDto {
    UUID id;
    String roomName;
    Boolean isGroup;
    LocalDateTime createdAt;
    String createdBy;
    MessageContentDto lastMessage;
    List<MessageRoomMemberDto> members;
    Long unseenCount;
}
