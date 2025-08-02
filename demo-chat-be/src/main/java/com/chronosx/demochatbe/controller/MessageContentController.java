package com.chronosx.demochatbe.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import com.chronosx.demochatbe.dto.MessageContentDto;
import com.chronosx.demochatbe.dto.MessageRoomMemberDto;
import com.chronosx.demochatbe.service.MessageContentService;
import com.chronosx.demochatbe.service.MessageRoomMemberService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/message-contents")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageContentController {
    MessageContentService messageContentService;
    MessageRoomMemberService messageRoomMemberService;

    SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/{roomId}")
    public ResponseEntity<List<MessageContentDto>> getMessagesByRoomId(@PathVariable UUID roomId) {
        return ResponseEntity.ok(messageContentService.getMessagesByRoomId(roomId));
    }

    @MessageMapping("/send-message")
    public void sendMessages(MessageContentDto request) {
        MessageContentDto saved = messageContentService.save(request);
        List<MessageRoomMemberDto> members = messageRoomMemberService.findByMessageRoomId(request.getMessageRoomId());
        members.forEach(member -> {
            simpMessagingTemplate.convertAndSendToUser(member.getUsername(), "/queue/messages", saved);
        });
    }
}
