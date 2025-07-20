package com.chronosx.demochatbe.repository;

import com.chronosx.demochatbe.entity.MessageRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MessageRoomRepository extends JpaRepository<MessageRoom, UUID> {}
