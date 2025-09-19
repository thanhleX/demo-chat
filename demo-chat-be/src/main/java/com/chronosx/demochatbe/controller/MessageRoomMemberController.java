package com.chronosx.demochatbe.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chronosx.demochatbe.dto.MessageRoomMemberDto;
import com.chronosx.demochatbe.service.MessageRoomMemberService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/message-room-members")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageRoomMemberController {
    MessageRoomMemberService messageRoomMemberService;

    @PostMapping("/update-last-seen/{roomId}/{username}")
    public ResponseEntity<MessageRoomMemberDto> updateLastSeen(
            @PathVariable UUID roomId, @PathVariable String username) {
        return ResponseEntity.ok(messageRoomMemberService.updateLastSeen(roomId, username));
    }
}
