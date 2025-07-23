package com.chronosx.demochatbe.mapper;

import org.mapstruct.Mapper;

import com.chronosx.demochatbe.dto.UserDto;
import com.chronosx.demochatbe.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto dto);

    UserDto toDto(User user);
}
