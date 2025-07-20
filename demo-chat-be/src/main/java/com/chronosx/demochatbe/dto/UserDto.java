package com.chronosx.demochatbe.dto;

import com.chronosx.demochatbe.enums.UserStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    String username;
    String password;
    UserStatus status;
    String avatarUrl;
}
