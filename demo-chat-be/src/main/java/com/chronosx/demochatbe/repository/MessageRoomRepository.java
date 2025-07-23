package com.chronosx.demochatbe.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chronosx.demochatbe.entity.MessageRoom;

@Repository
public interface MessageRoomRepository extends JpaRepository<MessageRoom, UUID> {}
