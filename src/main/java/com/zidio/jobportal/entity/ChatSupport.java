package com.zidio.jobportal.entity;

import java.time.LocalDateTime;

import com.zidio.jobportal.Enum.MessageStatus;

import jakarta.persistence.*;
import lombok.*;

	@Entity
	@Table(name="chat_support")
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public class ChatSupport {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    // The user who is sending the message (User ID or Admin ID)
	    private String senderId;

	    // The user who receives the message
	    private String receiverId;

	    // The actual message text
	    @Column(columnDefinition = "TEXT")
	    private String message;

	    // Optional attachment file path
	    private String attachmentUrl;

	    // For grouping messages inside same chat session
	    private String sessionId;

	    // Message status → SENT | DELIVERED | READ
	    @Enumerated(EnumType.STRING)
	    private MessageStatus status;

	    private LocalDateTime timeStamp;
	}



