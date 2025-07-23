package com.chronosx.demochatbe.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.chronosx.demochatbe.enums.MessageType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageContentDto {
    UUID id;
    String content;
    LocalDateTime dateSent;
    MessageType messageType;
    UUID messageRoomId;
    String sender;
}
