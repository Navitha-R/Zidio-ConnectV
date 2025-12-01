package com.zidio.jobportal.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zidio.jobportal.Enum.MessageStatus;
import com.zidio.jobportal.entity.ChatSupport;
import com.zidio.jobportal.service.ChatSupportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatSupportController {
	 private final ChatSupportService chatSupportService;
	    private final SimpMessagingTemplate messagingTemplate;

	    // 🚀 SEND MESSAGE (REST + WebSocket)
	    @PostMapping("/send")
	    public ChatSupport sendMessage(@RequestBody ChatSupport message) {

	        message.setTimeStamp(LocalDateTime.now());
	        message.setStatus(MessageStatus.SENT);

	        ChatSupport savedMessage = chatSupportService.saveMessage(message);

	        // 🔥 WebSocket broadcast to receiver
	        messagingTemplate.convertAndSend(
	                "/topic/chat/" + message.getReceiverId(),    // Dynamic receiver channel
	                savedMessage
	        );

	        return savedMessage;
	    }

	    // 📥 GET CHAT HISTORY BETWEEN TWO USERS
	    @GetMapping("/history")
	    public List<ChatSupport> getChatHistory(
	            @RequestParam String user1,
	            @RequestParam String user2
	    ) {
	        return chatSupportService.getChatHistory(user1, user2);
	    }

	    // 👁️ MARK A MESSAGE AS READ
	    @PutMapping("/mark-read/{id}")
	    public ChatSupport markAsRead(@PathVariable Long id) {
	        return chatSupportService.updateStatus(id, MessageStatus.READ);
	    }

	    // 🧹 DELETE MESSAGE
	    @DeleteMapping("/{id}")
	    public String deleteMessage(@PathVariable Long id) {
	        chatSupportService.deleteMessage(id);
	        return "Message deleted successfully";
	    }

}
