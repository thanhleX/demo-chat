package com.chronosx.demochatbe.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.chronosx.demochatbe.entity.MessageContent;

@Repository
public interface MessageContentRepository extends JpaRepository<MessageContent, UUID> {
    Optional<MessageContent> findTopByMessageRoomIdOrderByDateSentDesc(UUID messageRoomId);

    List<MessageContent> findByMessageRoomIdOrderByDateSent(UUID messageRoomId);

    @Query(
            """
					select count(*)
					from MessageContent c
					join MessageRoomMember rm
						on c.messageRoom = rm.messageRoom
					where rm.user.username = :username
						and c.messageRoom.id = :roomId
						and c.user.username <> :username
						and c.dateSent > rm.lastSeen
			""")
    Long countUnseenMessages(UUID roomId, String username);
}
