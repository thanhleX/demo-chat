package com.chronosx.demochatbe.entity;

import java.io.Serializable;
import java.util.UUID;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageRoomMemberId implements Serializable {
    String username;
    UUID messageRoomId;
}
