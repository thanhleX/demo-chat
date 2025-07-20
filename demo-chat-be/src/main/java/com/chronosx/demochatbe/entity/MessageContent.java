package com.chronosx.demochatbe.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;

import org.springframework.data.annotation.CreatedDate;

import com.chronosx.demochatbe.enums.MessageType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "message_contents")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageContent {
    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    UUID id;

    String content;

    @CreatedDate
    @Column(name = "date_sent")
    LocalDateTime dateSent;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_type")
    MessageType messageType;

    @ManyToOne
    @JoinColumn(name = "message_room_id")
    MessageRoom messageRoom;

    @ManyToOne
    @JoinColumn(name = "username")
    User user;
}
