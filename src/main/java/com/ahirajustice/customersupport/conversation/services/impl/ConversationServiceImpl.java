package com.ahirajustice.customersupport.conversation.services.impl;

import com.ahirajustice.customersupport.common.entities.Conversation;
import com.ahirajustice.customersupport.common.entities.QConversation;
import com.ahirajustice.customersupport.common.entities.User;
import com.ahirajustice.customersupport.common.enums.ConversationStatus;
import com.ahirajustice.customersupport.common.enums.WebSocketEventType;
import com.ahirajustice.customersupport.common.exceptions.NotFoundException;
import com.ahirajustice.customersupport.common.exceptions.ValidationException;
import com.ahirajustice.customersupport.common.models.WebSocketEvent;
import com.ahirajustice.customersupport.common.repositories.ConversationRepository;
import com.ahirajustice.customersupport.common.utils.ObjectMapperUtil;
import com.ahirajustice.customersupport.conversation.queries.SearchConversationsQuery;
import com.ahirajustice.customersupport.conversation.queries.SearchInitiatedConversationsQuery;
import com.ahirajustice.customersupport.conversation.requests.CloseConversationRequest;
import com.ahirajustice.customersupport.conversation.requests.InitiateConversationRequest;
import com.ahirajustice.customersupport.conversation.services.ConversationService;
import com.ahirajustice.customersupport.conversation.viewmodels.ConversationViewModel;
import com.ahirajustice.customersupport.message.services.MessageService;
import com.ahirajustice.customersupport.user.services.CurrentUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {

    private final ObjectMapper objectMapper;
    private final ConversationRepository conversationRepository;
    private final CurrentUserService currentUserService;
    private final MessageService messageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    @Transactional
    public ConversationViewModel initiateConversation(InitiateConversationRequest request) {
        User loggedInUser = currentUserService.getCurrentUser();

        Conversation conversation = createConversation(loggedInUser);
        messageService.createMessage(conversation, loggedInUser, request.getMessageBody());

        pushInitiatedConversationEventToAgents(conversation);

        return ConversationViewModel.from(conversation);
    }

    private Conversation createConversation(User user) {
        Conversation conversation = Conversation.builder()
                .user(user)
                .status(ConversationStatus.INITIATED)
                .build();

        return conversationRepository.save(conversation);
    }

    private void pushInitiatedConversationEventToAgents(Conversation conversation) {
        WebSocketEvent event = WebSocketEvent.builder()
                .eventId(conversation.getId())
                .eventType(WebSocketEventType.INITIATED_CONVERSATION)
                .build();
        String payload = ObjectMapperUtil.serialize(objectMapper, event);

        simpMessagingTemplate.convertAndSend("/topic/conversations", payload.getBytes());
    }

    @Override
    public Page<ConversationViewModel> searchConversations(SearchConversationsQuery query) {
        User loggedInUser = currentUserService.getCurrentUser();

        BooleanExpression expression = (BooleanExpression) query.getPredicate();
        expression = expression.and(
                QConversation.conversation.agent.user.id.eq(loggedInUser.getId())
                        .or(QConversation.conversation.user.id.eq(loggedInUser.getId()))
        );

        return conversationRepository.findAll(expression, query.getPageable()).map(ConversationViewModel::from);
    }

    @Override
    public Page<ConversationViewModel> searchInitiatedConversations(SearchInitiatedConversationsQuery query) {
        return conversationRepository.findAll(query.getPredicate(), query.getPageable()).map(ConversationViewModel::from);
    }

    @Override
    public ConversationViewModel closeConversation(CloseConversationRequest request) {
        Conversation conversation = getConversation(request.getConversationId());

        if (ConversationStatus.CLOSED.equals(conversation.getStatus())) {
            throw new ValidationException("Conversation already closed");
        }

        if (ConversationStatus.INITIATED.equals(conversation.getStatus())) {
            throw new ValidationException("Cannot close un-attended conversation");
        }

        return ConversationViewModel.from(closeConversation(conversation));
    }

    private Conversation closeConversation(Conversation conversation) {
        conversation.setStatus(ConversationStatus.CLOSED);
        conversation.setClosedOn(LocalDateTime.now());

        return conversationRepository.save(conversation);
    }

    @Override
    public Conversation getConversation(long conversationId) {
        return conversationRepository.findById(conversationId)
                .orElseThrow(() -> new NotFoundException(String.format("Conversation with id: '%s' not found", conversationId)));
    }

}
