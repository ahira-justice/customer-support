package com.ahirajustice.customersupport.modules.conversation.services.impl;

import com.ahirajustice.customersupport.common.constants.ChannelConstants;
import com.ahirajustice.customersupport.common.entities.Conversation;
import com.ahirajustice.customersupport.common.entities.Message;
import com.ahirajustice.customersupport.common.entities.User;
import com.ahirajustice.customersupport.common.enums.ConversationStatus;
import com.ahirajustice.customersupport.common.enums.EventType;
import com.ahirajustice.customersupport.common.exceptions.NotFoundException;
import com.ahirajustice.customersupport.common.exceptions.ValidationException;
import com.ahirajustice.customersupport.common.models.Event;
import com.ahirajustice.customersupport.common.repositories.ConversationRepository;
import com.ahirajustice.customersupport.common.utils.ObjectMapperUtil;
import com.ahirajustice.customersupport.modules.conversation.queries.SearchConversationsQuery;
import com.ahirajustice.customersupport.modules.conversation.queries.SearchInitiatedConversationsQuery;
import com.ahirajustice.customersupport.modules.conversation.requests.CloseConversationRequest;
import com.ahirajustice.customersupport.modules.conversation.requests.InitiateConversationRequest;
import com.ahirajustice.customersupport.modules.conversation.services.ConversationService;
import com.ahirajustice.customersupport.modules.conversation.viewmodels.ConversationViewModel;
import com.ahirajustice.customersupport.modules.message.services.MessageService;
import com.ahirajustice.customersupport.modules.user.services.CurrentUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ably.lib.rest.AblyRest;
import io.ably.lib.rest.Channel;
import io.ably.lib.types.AblyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConversationServiceImpl implements ConversationService {

    private final ObjectMapper objectMapper;
    private final ConversationRepository conversationRepository;
    private final CurrentUserService currentUserService;
    private final MessageService messageService;
    private final AblyRest ablyRest;

    @Override
    @Transactional
    public ConversationViewModel initiateConversation(InitiateConversationRequest request) {
        User loggedInUser = currentUserService.getCurrentUser();

        Conversation conversation = createConversation(loggedInUser);
        Message message = messageService.createMessage(conversation, loggedInUser, request.getMessageBody());

        ConversationViewModel response = ConversationViewModel.from(conversation, message);

        pushInitiatedConversationEventToAgents(response);

        return response;
    }

    private Conversation createConversation(User user) {
        Conversation conversation = Conversation.builder()
                .user(user)
                .status(ConversationStatus.INITIATED)
                .build();

        return conversationRepository.save(conversation);
    }

    private void pushInitiatedConversationEventToAgents(ConversationViewModel conversation) {
        Event event = Event.builder()
                .eventId(conversation.getId())
                .eventType(EventType.INITIATED_CONVERSATION)
                .payload(conversation)
                .build();

        String eventPayload = ObjectMapperUtil.serialize(objectMapper, event);

        try {
            Channel channel = ablyRest.channels.get(ChannelConstants.CONVERSATIONS);
            channel.publish(event.getEventType().name(), eventPayload);
        } catch (AblyException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    @Override
    public Page<ConversationViewModel> searchConversations(SearchConversationsQuery query) {
        User loggedInUser = currentUserService.getCurrentUser();
        query.setLoggedInUserUsername(loggedInUser.getUsername());

        return conversationRepository.findAll(query.getPredicate(), query.getPageable())
                .map(
                        conversation -> ConversationViewModel.from(
                                conversation, messageService.getMostRecentMessage(conversation)
                        )
                );
    }

    @Override
    public Page<ConversationViewModel> searchInitiatedConversations(SearchInitiatedConversationsQuery query) {
        return conversationRepository
                .findAll(query.getPredicate(), query.getPageable())
                .map(conversation -> ConversationViewModel.from(
                        conversation, messageService.getMostRecentMessage(conversation)
                ));
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

        return ConversationViewModel.from(
                closeConversation(conversation), messageService.getMostRecentMessage(conversation)
        );
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
