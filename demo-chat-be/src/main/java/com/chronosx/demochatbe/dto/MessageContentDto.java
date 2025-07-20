package com.chronosx.demochatbe.dto;

import com.chronosx.demochatbe.enums.MessageType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

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
