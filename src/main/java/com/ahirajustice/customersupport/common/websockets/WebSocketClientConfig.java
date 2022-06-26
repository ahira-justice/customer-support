package com.ahirajustice.customersupport.common.websockets;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketClientConfig {

    private final ObjectMapper objectMapper;

    @Bean
    public WebSocketStompClient webSocketStompClient() {
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        stompClient.connect("ws://localhost:9091/ws", stompSessionHandler());

        return stompClient;
    }

    @Bean
    public WebSocketClient webSocketClient() {
        return new StandardWebSocketClient();
    }

    @Bean
    public StompSessionHandler stompSessionHandler() {
        List<String> subscribeUrls = Arrays.asList(
                "/topic/messages/ahira",
                "/topic/conversations"
        );

        return new AppStompSessionHandler(subscribeUrls, objectMapper);
    }

}