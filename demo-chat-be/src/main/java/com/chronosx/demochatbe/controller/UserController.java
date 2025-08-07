package com.chronosx.demochatbe.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import com.chronosx.demochatbe.dto.UserDto;
import com.chronosx.demochatbe.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping
    ResponseEntity<UserDto> login(@RequestBody UserDto request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @MessageMapping("/user/connect") // Receives messages from clients sending to /app/user/connect
    @SendTo("/topic/active") // Send the response to all clients subscribe to /topic/active
    public UserDto connect(@RequestBody UserDto request) {
        return userService.connect(request);
    }

    @MessageMapping("/user/disconnect") // Receives messages from clients sending to /app/user/disconnect
    @SendTo("/topic/active") // Send the response to all clients subscribe to /topic/active
    public UserDto disconnect(@RequestBody UserDto request) {
        return userService.logout(request);
    }

    @GetMapping("/online")
    public ResponseEntity<List<UserDto>> getOnlineUsers() {
        return ResponseEntity.ok(userService.getOnlineUsers());
    }

    @GetMapping("/search/{username}")
    public ResponseEntity<List<UserDto>> searchUsers(@PathVariable String username) {
        return ResponseEntity.ok(userService.searchUsers(username));
    }

    @PostMapping("/avatar")
    public ResponseEntity<UserDto> uploadAvatar(@RequestParam MultipartFile file, @RequestParam String username) throws IOException {
        return ResponseEntity.ok(userService.uploadAvatar(file, username));
    }
}
