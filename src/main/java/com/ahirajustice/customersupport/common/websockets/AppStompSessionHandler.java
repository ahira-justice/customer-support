package com.ahirajustice.customersupport.common.websockets;

import com.ahirajustice.customersupport.common.models.WebSocketEvent;
import com.ahirajustice.customersupport.common.utils.ObjectMapperUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppStompSessionHandler extends StompSessionHandlerAdapter {

    private final List<String> subscribeUrls;
    private final ObjectMapper objectMapper;

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        for (String subscribeUrl: subscribeUrls) {
            session.subscribe(subscribeUrl, this);
        }

        log.info("New session: {}", session.getSessionId());
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        log.error("Error occurred while handling frame: ", exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return WebSocketEvent.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        String response = ObjectMapperUtil.serialize(objectMapper, payload);
        log.info("Received: {}", response);
    }

}
