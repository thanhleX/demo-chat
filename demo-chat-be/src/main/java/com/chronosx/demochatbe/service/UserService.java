package com.chronosx.demochatbe.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.chronosx.demochatbe.utils.FileUtils;
import org.springframework.stereotype.Service;

import com.chronosx.demochatbe.dto.UserDto;
import com.chronosx.demochatbe.entity.User;
import com.chronosx.demochatbe.enums.UserStatus;
import com.chronosx.demochatbe.mapper.UserMapper;
import com.chronosx.demochatbe.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

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

    public UserDto connect(UserDto request) {
        Optional<User> user = userRepository.findById(request.getUsername());

        user.ifPresent(u -> {
            u.setStatus(UserStatus.ONLINE);
            userRepository.save(u);
        });

        return user.map(userMapper::toDto).orElse(null);
    }

    public List<UserDto> getOnlineUsers() {
        return userRepository.findAllByStatus(UserStatus.ONLINE).stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDto logout(UserDto request) {
        Optional<User> user = userRepository.findById(request.getUsername());

        user.ifPresent(u -> {
            u.setStatus(UserStatus.OFFLINE);
            u.setLastLogin(LocalDateTime.now());
            userRepository.save(u);
        });

        return user.map(userMapper::toDto).orElse(null);
    }

    public List<UserDto> searchUsers(String username) {
        return userRepository.findAllByUsernameContainingIgnoreCase(username).stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDto uploadAvatar(MultipartFile file, String username) throws IOException {
        Optional<User> user = userRepository.findById(username);

        if (user.isPresent()) {
            if (user.get().getAvatarUrl() != null) {
                // delete
                FileUtils.deleteFile("/" + FileUtils.FOLDER_AVATARS + "/" + user.get().getAvatarShortUrl());

                // upload
                String newFileName = FileUtils.storeFile(file, FileUtils.FOLDER_AVATARS);
                user.get().setAvatarUrl(newFileName);
                userRepository.save(user.get());
            }
        }
        return userMapper.toDto(user.orElse(null));
    }
}
