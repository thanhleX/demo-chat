package com.chronosx.demochatbe.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.chronosx.demochatbe.entity.MessageRoom;

@Repository
public interface MessageRoomRepository extends JpaRepository<MessageRoom, UUID> {
    @Query(
            """
				select r
				from MessageRoom r
				join MessageRoomMember rm
					on rm.messageRoom = r
				group by r.id
				having count(case when rm.user.username in :members then 1 end) = :size
					and count(*) = :size
			""")
    Optional<MessageRoom> findMessageRoomByMembers(List<String> members, int size);

    @Query(
            """
				select r
				from MessageRoom r
				join MessageRoomMember rm
					on rm.messageRoom = r
				join MessageContent c
					on c.messageRoom = r
				where rm.user.username = :username
				group by r.id
				having count(c) > 0
				order by max(c.dateSent) desc
			""")
    List<MessageRoom> findChatRoomAtLeastOneContent(String username);
}
