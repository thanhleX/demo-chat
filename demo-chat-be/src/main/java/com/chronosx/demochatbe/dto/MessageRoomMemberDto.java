package com.chronosx.demochatbe.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageRoomMemberDto {
    UUID messageRoomId;
    String username;
    Boolean isAdmin;
    LocalDateTime lastSeen;
    LocalDateTime lastLogin;
}
