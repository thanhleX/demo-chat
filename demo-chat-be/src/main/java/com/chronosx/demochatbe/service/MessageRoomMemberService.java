package com.chronosx.demochatbe.service;

import java.util.List;
import java.util.UUID;

import com.chronosx.demochatbe.entity.MessageRoomMember;
import org.springframework.stereotype.Service;

import com.chronosx.demochatbe.dto.MessageRoomMemberDto;
import com.chronosx.demochatbe.mapper.MessageRoomMemberMapper;
import com.chronosx.demochatbe.repository.MessageRoomMemberRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageRoomMemberService {
    MessageRoomMemberRepository messageRoomMemberRepository;

    MessageRoomMemberMapper messageRoomMemberMapper;

    public List<MessageRoomMemberDto> findByMessageRoomId(UUID messageRoomId) {
        return messageRoomMemberRepository.findByMessageRoomId(messageRoomId).stream()
                .map(messageRoomMemberMapper::toDto)
                .toList();
    }

    public MessageRoomMemberDto updateLastSeen(UUID roomId, String username) {
        MessageRoomMember member = messageRoomMemberRepository.findByMessageRoomIdAndUserUsername(roomId, username);

        member.setLastSeen(java.time.LocalDateTime.now());

        return messageRoomMemberMapper.toDto(messageRoomMemberRepository.save(member));
    }

}
