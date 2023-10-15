package com.ahirajustice.customersupport.modules.message.services.impl;

import com.ahirajustice.customersupport.modules.agent.services.AgentService;
import com.ahirajustice.customersupport.common.entities.Agent;
import com.ahirajustice.customersupport.common.entities.Conversation;
import com.ahirajustice.customersupport.common.entities.Message;
import com.ahirajustice.customersupport.common.entities.User;
import com.ahirajustice.customersupport.common.enums.ConversationStatus;
import com.ahirajustice.customersupport.common.enums.Roles;
import com.ahirajustice.customersupport.common.enums.EventType;
import com.ahirajustice.customersupport.common.exceptions.BadRequestException;
import com.ahirajustice.customersupport.common.exceptions.NotFoundException;
import com.ahirajustice.customersupport.common.models.Event;
import com.ahirajustice.customersupport.common.repositories.ConversationRepository;
import com.ahirajustice.customersupport.common.repositories.MessageRepository;
import com.ahirajustice.customersupport.common.utils.ObjectMapperUtil;
import com.ahirajustice.customersupport.modules.message.queries.SearchMessagesByConversationQuery;
import com.ahirajustice.customersupport.modules.message.queries.SearchMessagesQuery;
import com.ahirajustice.customersupport.modules.message.requests.SendMessageRequest;
import com.ahirajustice.customersupport.modules.message.services.MessageService;
import com.ahirajustice.customersupport.modules.message.viewmodels.MessageViewModel;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final ObjectMapper objectMapper;
    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final CurrentUserService currentUserService;
    private final AgentService agentService;
    private final AblyRest ablyRest;

    @Override
    public Message createMessage(Conversation conversation, User user, String body) {
        Message message = Message.builder()
                .conversation(conversation)
                .user(user)
                .body(body)
                .build();

        return messageRepository.save(message);
    }

    @Transactional
    @Override
    public MessageViewModel sendMessage(SendMessageRequest request) {
        Conversation conversation = getConversation(request.getConversationId());

        verifyConversationNotClosed(conversation);

        User loggedInUser = currentUserService.getCurrentUser();

        Agent agent = agentService.getAgent(loggedInUser);
        boolean loggedInUserIsAgent = agent != null;

        verifyLoggedInUserIsPartOfConversation(conversation, loggedInUser, loggedInUserIsAgent);

        if (ConversationStatus.INITIATED.equals(conversation.getStatus())) {
            if (loggedInUserIsAgent) {
                setStatusActiveAndAssignAgentToConversation(conversation, agent);
            }
        }

        Message message = createMessage(conversation, loggedInUser, request.getMessageBody());

        User receiver = getReceiver(conversation, loggedInUser);

        MessageViewModel response = MessageViewModel.from(message);

        pushMessageEventToOtherUserInConversation(receiver, response);

        return response;
    }

    private Conversation getConversation(long conversationId) {
        return conversationRepository.findById(conversationId)
                .orElseThrow(() -> new NotFoundException(String.format("Conversation with id: '%s' not found", conversationId)));
    }

    private void verifyConversationNotClosed(Conversation conversation) {
        if (ConversationStatus.CLOSED.equals(conversation.getStatus()))
            throw new BadRequestException("Cannot send message to closed conversation");
    }

    private void verifyLoggedInUserIsPartOfConversation(Conversation conversation, User loggedInUser, boolean loggedInUserIsAgent) {
        if (conversation.getAgent() == null) {
            if (loggedInUser.getId() != conversation.getUser().getId() && !loggedInUserIsAgent) {
                throw new BadRequestException("User is not part of this conversation");
            }
        }

        if (conversation.getAgent() != null) {
            if (loggedInUser.getId() != conversation.getUser().getId() &&
                    loggedInUser.getId() !=  conversation.getAgent().getUser().getId()) {
                throw new BadRequestException("User is not part of this conversation");
            }
        }
    }

    private void setStatusActiveAndAssignAgentToConversation(Conversation conversation, Agent agent) {
        conversationRepository.updateAgentIdAndStatus(
                conversation.getId(), agent.getId(), ConversationStatus.ACTIVE
        );
    }

    private void pushMessageEventToOtherUserInConversation(User receiver, MessageViewModel message) {
        if (receiver == null) {
            return;
        }

        Event event = Event.builder()
                .eventId(message.getId())
                .eventType(EventType.NEW_MESSAGE)
                .payload(message)
                .build();

        String eventPayload = ObjectMapperUtil.serialize(objectMapper, event);

        try {
            Channel channel = ablyRest.channels.get(receiver.getUsername());
            channel.publish(event.getEventType().name(), eventPayload);
        } catch (AblyException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    private User getReceiver(Conversation conversation, User sender) {
        if (!conversation.getUser().equals(sender)) {
            return conversation.getUser();
        }
        else if (conversation.getUser().equals(sender) && conversation.getAgent() != null) {
            return conversation.getAgent().getUser();
        }
        else{
            return null;
        }
    }

    @Override
    public Page<MessageViewModel> searchMessages(SearchMessagesQuery query) {
        User loggedInUser = currentUserService.getCurrentUser();
        query.setLoggedInUserUsername(loggedInUser.getUsername());

        return messageRepository.findAll(query.getPredicate(), query.getPageable())
                .map(MessageViewModel::from);
    }

    @Override
    public Page<MessageViewModel> searchMessagesByConversation(SearchMessagesByConversationQuery query) {
        User loggedInUser = currentUserService.getCurrentUser();

        if (!currentUserService.currentUserHasRole(Roles.AGENT))
            query.setLoggedInUserUsername(loggedInUser.getUsername());

        return messageRepository.findAll(query.getPredicate(), query.getPageable())
                .map(MessageViewModel::from);
    }

    @Override
    public Message getMostRecentMessage(Conversation conversation) {
        return messageRepository.findFirstByConversationOrderByCreatedOnDesc(conversation);
    }

}
