package com.zidio.jobportal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zidio.jobportal.Enum.MessageStatus;
import com.zidio.jobportal.entity.ChatSupport;
import com.zidio.jobportal.repository.ChatSupportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatSupportService {

	private final ChatSupportRepository chatSupportRepository;

    public ChatSupport saveMessage(ChatSupport chat) {
        return chatSupportRepository.save(chat);
    }

    public List<ChatSupport> getChatHistory(String user1, String user2) {
        return chatSupportRepository.findChatHistory(user1, user2);
    }

    public ChatSupport updateStatus(Long id, MessageStatus status) {
        ChatSupport chat = chatSupportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        chat.setStatus(status);
        return chatSupportRepository.save(chat);
    }

    public void deleteMessage(Long id) {
        chatSupportRepository.deleteById(id);
    }
}
