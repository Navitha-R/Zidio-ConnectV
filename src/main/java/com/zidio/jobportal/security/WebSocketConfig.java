package com.zidio.jobportal.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

@Override
public void registerStompEndpoints(StompEndpointRegistry registry) {

    registry.addEndpoint("/ws-chat")     // main websocket endpoint
            .setAllowedOriginPatterns("*")
            .withSockJS();               // fallback for browsers that don't support websocket
}

@Override
public void configureMessageBroker(MessageBrokerRegistry registry) {

    // Client → Server (send)
    registry.setApplicationDestinationPrefixes("/app");

    // Server → Client (broadcast)
    registry.enableSimpleBroker("/topic", "/queue");

    // For private chat messages
    registry.setUserDestinationPrefix("/user");
}
}



