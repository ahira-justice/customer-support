package com.ahirajustice.customersupport.message.services.impl;

import com.ahirajustice.customersupport.agent.services.AgentService;
import com.ahirajustice.customersupport.common.entities.Agent;
import com.ahirajustice.customersupport.common.entities.Conversation;
import com.ahirajustice.customersupport.common.entities.Message;
import com.ahirajustice.customersupport.common.entities.User;
import com.ahirajustice.customersupport.common.enums.ConversationStatus;
import com.ahirajustice.customersupport.common.exceptions.BadRequestException;
import com.ahirajustice.customersupport.common.exceptions.NotFoundException;
import com.ahirajustice.customersupport.common.repositories.ConversationRepository;
import com.ahirajustice.customersupport.common.repositories.MessageRepository;
import com.ahirajustice.customersupport.message.queries.SearchMessagesByConversationQuery;
import com.ahirajustice.customersupport.message.queries.SearchMessagesQuery;
import com.ahirajustice.customersupport.message.requests.SendMessageRequest;
import com.ahirajustice.customersupport.message.services.MessageService;
import com.ahirajustice.customersupport.message.viewmodels.MessageViewModel;
import com.ahirajustice.customersupport.user.services.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final CurrentUserService currentUserService;
    private final AgentService agentService;
    private final SimpMessagingTemplate simpMessagingTemplate;

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

        pushMessageToOtherUserInConversation(conversation, loggedInUser, message);

        return MessageViewModel.from(message);
    }

    @Override
    public Page<MessageViewModel> searchMessages(SearchMessagesQuery query) {
        User loggedInUser = currentUserService.getCurrentUser();

        Page<Message> messages = messageRepository.findAll(query.getPredicate(), query.getPageable());

        return new PageImpl<>(
                messages.stream()
                        .filter(message -> filterMessagesByLoggedInUser(message, loggedInUser))
                        .collect(Collectors.toList()),
                messages.getPageable(),
                messages.getTotalElements()
        ).map(MessageViewModel::from);
    }

    @Override
    public Page<MessageViewModel> searchMessagesByConversation(SearchMessagesByConversationQuery query) {
        User loggedInUser = currentUserService.getCurrentUser();

        Page<Message> messages = messageRepository.findAll(query.getPredicate(), query.getPageable());

        return new PageImpl<>(
                messages.stream()
                        .filter(message -> filterMessagesByLoggedInUser(message, loggedInUser))
                        .collect(Collectors.toList()),
                messages.getPageable(),
                messages.getTotalElements()
        ).map(MessageViewModel::from);
    }

    private boolean filterMessagesByLoggedInUser(Message message, User loggedInUser) {
        boolean loggedInUserIsUserInConversation = loggedInUser.equals(message.getConversation().getUser());

        boolean loggedInUserIsAgentInConversation = false;

        if (message.getConversation().getAgent() != null){
            loggedInUserIsAgentInConversation = message.getConversation().getAgent().getUser().equals(loggedInUser);
        }

        return loggedInUserIsUserInConversation || loggedInUserIsAgentInConversation;
    }

    private void pushMessageToOtherUserInConversation(Conversation conversation, User sender, Message message) {
        User receiver = getReceiver(conversation, sender);
        simpMessagingTemplate.convertAndSend("/topic/messages/" + receiver.getUsername(), message.toString().getBytes());
    }

    private User getReceiver(Conversation conversation, User sender) {
        return conversation.getUser().equals(sender) ? conversation.getAgent().getUser() : conversation.getUser();
    }

    private void verifyLoggedInUserIsPartOfConversation(Conversation conversation, User loggedInUser, boolean loggedInUserIsAgent) {
        if (conversation.getAgent() == null) {
            if (loggedInUser.getId() != conversation.getUser().getId() && !loggedInUserIsAgent){
                throw new BadRequestException("User is not part of this conversation");
            }
        }

        if (conversation.getAgent() != null) {
            if (loggedInUser.getId() != conversation.getUser().getId() &&
                    loggedInUser.getId() !=  conversation.getAgent().getUser().getId()){
                throw new BadRequestException("User is not part of this conversation");
            }
        }
    }

    private Conversation getConversation(long conversationId) {
        return conversationRepository.findById(conversationId)
                .orElseThrow(() -> new NotFoundException(String.format("Conversation with id: '%s' not found", conversationId)));
    }

    private void setStatusActiveAndAssignAgentToConversation(Conversation conversation, Agent agent) {
        conversationRepository.updateAgentIdAndStatus(
                conversation.getId(), agent.getId(), ConversationStatus.ACTIVE
        );
    }

}
