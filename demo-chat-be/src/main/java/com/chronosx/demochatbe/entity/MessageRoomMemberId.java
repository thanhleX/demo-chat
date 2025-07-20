package com.chronosx.demochatbe.entity;

import java.io.Serializable;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageRoomMemberId implements Serializable {
    String username;
    UUID messageRoomId;
}
