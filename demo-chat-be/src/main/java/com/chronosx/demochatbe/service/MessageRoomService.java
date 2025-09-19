package com.chronosx.demochatbe.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chronosx.demochatbe.dto.MessageContentDto;
import com.chronosx.demochatbe.dto.MessageRoomDto;
import com.chronosx.demochatbe.dto.MessageRoomMemberDto;
import com.chronosx.demochatbe.entity.*;
import com.chronosx.demochatbe.mapper.MessageRoomMapper;
import com.chronosx.demochatbe.repository.MessageRoomRepository;
import com.chronosx.demochatbe.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageRoomService {
    MessageRoomRepository messageRoomRepository;
    UserRepository userRepository;

    MessageRoomMapper messageRoomMapper;

    MessageContentService messageContentService;
    MessageRoomMemberService messageRoomMemberService;

    public MessageRoomDto findChatRoom(List<String> members) {
        return messageRoomRepository
                .findMessageRoomByMembers(members, members.size())
                .map(m -> {
                    MessageRoomDto roomDto = messageRoomMapper.toDto(m);
                    List<MessageRoomMemberDto> membersDto =
                            messageRoomMemberService.findByMessageRoomId(roomDto.getId());
                    roomDto.setMembers(membersDto);
                    return roomDto;
                })
                .orElse(null);
    }

    @Transactional
    public MessageRoomDto createChatRoom(List<String> members, String username) {
        User user = userRepository.findById(username).orElseThrow();

        MessageRoom messageRoom = MessageRoom.builder()
                .isGroup(members.size() > 2)
                .createdBy(user)
                .createdAt(LocalDateTime.now())
                .members(new ArrayList<>())
                .build();

        List<User> users = userRepository.findAllByUsernameIn(members);

        users.forEach(u -> {
            MessageRoomMember messageRoomMember = MessageRoomMember.builder()
                    .id(new MessageRoomMemberId(u.getUsername(), messageRoom.getId()))
                    .user(u)
                    .messageRoom(messageRoom)
                    .isAdmin(u.getUsername().equals(username))
                    .lastSeen(LocalDateTime.now())
                    .build();
            messageRoom.getMembers().add(messageRoomMember);
        });

        //        // temp
        //        MessageContent messageContent = MessageContent.builder()
        //                .content("Hello")
        //                .dateSent(LocalDateTime.now())
        //                .messageRoom(messageRoom)
        //                .user(user)
        //                .build();
        //
        //        if (messageRoom.getMessageContents() == null) messageRoom.setMessageContents(new ArrayList<>());
        //
        //        messageRoom.getMessageContents().add(messageContent);

        MessageRoomDto roomDto = messageRoomMapper.toDto(messageRoomRepository.save(messageRoom));
        List<MessageRoomMemberDto> membersDto = messageRoomMemberService.findByMessageRoomId(roomDto.getId());
        roomDto.setMembers(membersDto);

        return roomDto;
    }

    public List<MessageRoomDto> findChatRoomAtLeastOneContent(String username) {
        return messageRoomRepository.findChatRoomAtLeastOneContent(username).stream()
                .map(m -> {
                    MessageRoomDto roomDto = messageRoomMapper.toDto(m);
                    roomDto.setUnseenCount(messageContentService.countUnseenMessages(roomDto.getId(), username));

                    MessageContentDto lastMessage = messageContentService.getLastMessage(roomDto.getId());
                    roomDto.setLastMessage(lastMessage);

                    List<MessageRoomMemberDto> members = messageRoomMemberService.findByMessageRoomId(roomDto.getId());
                    roomDto.setMembers(members);

                    return roomDto;
                })
                .toList();
    }

    public MessageRoomDto findById(UUID roomId) {
        return messageRoomRepository
                .findById(roomId)
                .map(room -> {
                    MessageRoomDto roomDto = messageRoomMapper.toDto(room);
                    List<MessageRoomMemberDto> membersDto =
                            messageRoomMemberService.findByMessageRoomId(roomDto.getId());
                    roomDto.setMembers(membersDto);
                    return roomDto;
                })
                .orElseThrow(null);
    }
}
