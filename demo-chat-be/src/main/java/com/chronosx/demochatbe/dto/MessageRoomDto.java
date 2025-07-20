package com.chronosx.demochatbe.dto;

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
public class MessageRoomDto {
    UUID id;
    String roomName;
    Boolean isGroup;
    LocalDateTime createdAt;
    String createdBy;
}
