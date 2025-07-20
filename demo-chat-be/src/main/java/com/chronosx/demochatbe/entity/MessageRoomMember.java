package com.chronosx.demochatbe.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "message_room_members")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageRoomMember {
    @EmbeddedId
    MessageRoomMemberId id;

    @MapsId("username")
    @ManyToOne
    @JoinColumn(name = "username")
    User user;

    @MapsId("messageRoomId")
    @ManyToOne
    @JoinColumn(name = "message_room_id")
    MessageRoom messageRoom;

    @Column(name = "is_admin")
    Boolean isAdmin;

    @Column(name = "last_seen")
    LocalDateTime lastSeen;
}
