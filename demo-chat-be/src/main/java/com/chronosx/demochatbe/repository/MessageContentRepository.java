package com.chronosx.demochatbe.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chronosx.demochatbe.entity.MessageContent;

@Repository
public interface MessageContentRepository extends JpaRepository<MessageContent, UUID> {
    Optional<MessageContent> findTopByMessageRoomIdOrderByDateSentDesc(UUID messageRoomId);

    List<MessageContent> findByMessageRoomIdOrderByDateSent(UUID messageRoomId);
}
