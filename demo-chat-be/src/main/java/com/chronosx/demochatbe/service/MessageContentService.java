package com.chronosx.demochatbe.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.chronosx.demochatbe.dto.MessageContentDto;
import com.chronosx.demochatbe.entity.MessageContent;
import com.chronosx.demochatbe.entity.MessageRoom;
import com.chronosx.demochatbe.entity.User;
import com.chronosx.demochatbe.mapper.MessageContentMapper;
import com.chronosx.demochatbe.repository.MessageContentRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageContentService {
    MessageContentRepository messageContentRepository;

    MessageContentMapper messageContentMapper;

    public MessageContentDto getLastMessage(UUID messageRoomId) {
        return messageContentRepository
                .findTopByMessageRoomIdOrderByDateSentDesc(messageRoomId)
                .map(messageContentMapper::toDto)
                .orElse(null);
    }

    public List<MessageContentDto> getMessagesByRoomId(UUID roomId) {
        return messageContentRepository.findByMessageRoomIdOrderByDateSent(roomId).stream()
                .map(messageContentMapper::toDto)
                .toList();
    }

    public MessageContentDto save(MessageContentDto request) {
        MessageContent messageContent = messageContentRepository.save(toEntity(request));
        return messageContentMapper.toDto(messageContent);
    }

    public Long countUnseenMessages(UUID roomId, String username) {
        return messageContentRepository.countUnseenMessages(roomId, username);
    }

    private MessageContent toEntity(MessageContentDto request) {
        MessageContent entity = messageContentMapper.toEntity(request);

        // Gán các field bị ignore trong mapper
        if (entity.getDateSent() == null) {
            entity.setDateSent(java.time.LocalDateTime.now());
        }

        if (request.getMessageRoomId() != null) {
            MessageRoom room = new MessageRoom();
            room.setId(request.getMessageRoomId());
            entity.setMessageRoom(room);
        }

        if (request.getSender() != null) {
            User user = new User();
            user.setUsername(request.getSender());
            entity.setUser(user);
        }
        return entity;
    }
}
