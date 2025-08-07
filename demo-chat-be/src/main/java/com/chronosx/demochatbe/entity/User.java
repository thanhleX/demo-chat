package com.chronosx.demochatbe.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.chronosx.demochatbe.utils.FileUtils;
import jakarta.persistence.*;

import com.chronosx.demochatbe.enums.UserStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    String username;

    String password;

    @Column(name = "last_login")
    LocalDateTime lastLogin = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    UserStatus status;

    @Column(name = "avatar_url")
    String avatarUrl;

    @OneToMany(mappedBy = "createdBy")
    List<MessageRoom> messageRooms;

    @OneToMany(mappedBy = "user")
    List<MessageRoomMember> messageRoomMembers;

    public String getAvatarUrl() {
        if (avatarUrl == null) return null;
        return FileUtils.getAvatarUrl(avatarUrl);
    }

    public String getAvatarShortUrl() {
        return avatarUrl;
    }
}
