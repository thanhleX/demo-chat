package com.chronosx.demochatbe.mapper;

import com.chronosx.demochatbe.dto.UserDto;
import com.chronosx.demochatbe.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto dto);

    UserDto toDto(User user);
}
