package com.chronosx.demochatbe.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.chronosx.demochatbe.dto.MessageContentDto;
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
}
