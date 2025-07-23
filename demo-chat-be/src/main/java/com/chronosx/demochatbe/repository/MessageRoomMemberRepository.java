package com.chronosx.demochatbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chronosx.demochatbe.entity.MessageRoomMember;
import com.chronosx.demochatbe.entity.MessageRoomMemberId;

@Repository
public interface MessageRoomMemberRepository extends JpaRepository<MessageRoomMember, MessageRoomMemberId> {}
