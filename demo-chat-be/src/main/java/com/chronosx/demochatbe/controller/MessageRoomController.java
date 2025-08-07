package com.chronosx.demochatbe.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chronosx.demochatbe.dto.MessageRoomDto;
import com.chronosx.demochatbe.service.MessageRoomService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/message-rooms")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageRoomController {
    MessageRoomService messageRoomService;

    @GetMapping("/find-chat-room")
    public ResponseEntity<MessageRoomDto> findChatRoom(@RequestParam List<String> members) {
        return ResponseEntity.ok(messageRoomService.findChatRoom(members));
    }

    @PostMapping("/create-chat-room")
    public ResponseEntity<MessageRoomDto> createChatRoom(
            @RequestParam List<String> members, @RequestParam String username) {
        return ResponseEntity.ok(messageRoomService.createChatRoom(members, username));
    }

    @GetMapping("/find-chat-room-at-least-one-content/{username}")
    public ResponseEntity<List<MessageRoomDto>> findChatRoomAtLeastOneContent(@PathVariable String username) {
        return ResponseEntity.ok(messageRoomService.findChatRoomAtLeastOneContent(username));
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<MessageRoomDto> findById(@PathVariable UUID roomId) {
        return ResponseEntity.ok(messageRoomService.findById(roomId));
    }
}
