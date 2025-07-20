package com.chronosx.demochatbe.repository;

import com.chronosx.demochatbe.entity.MessageRoomMember;
import com.chronosx.demochatbe.entity.MessageRoomMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRoomMemberRepository extends JpaRepository<MessageRoomMember, MessageRoomMemberId> {}
