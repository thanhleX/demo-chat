package com.chronosx.demochatbe.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chronosx.demochatbe.dto.UserDto;
import com.chronosx.demochatbe.entity.User;
import com.chronosx.demochatbe.enums.UserStatus;
import com.chronosx.demochatbe.mapper.UserMapper;
import com.chronosx.demochatbe.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;

    UserMapper userMapper;

    public UserDto login(UserDto request) {
        User user = userRepository.findById(request.getUsername()).orElseGet(() -> createUser(request));

        validatePassword(request, user.getPassword());

        return userMapper.toDto(user);
    }

    public UserDto connect(UserDto request) {
        Optional<User> user = userRepository.findById(request.getUsername());

        user.ifPresent(u -> {
            u.setStatus(UserStatus.ONLINE);
            userRepository.save(u);
        });

        return user.map(userMapper::toDto).orElse(null);
    }

    void validatePassword(UserDto request, String password) {
        if (!request.getPassword().equals(password)) throw new IllegalArgumentException("Invalid password");
    }

    User createUser(UserDto request) {
        User user = userMapper.toEntity(request);

        user.setLastLogin(LocalDateTime.now());
        user.setStatus(UserStatus.ONLINE);

        userRepository.save(user);

        return user;
    }
}
