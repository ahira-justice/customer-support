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
import com.ahirajustice.customersupport.message.requests.SendMessageRequest;
import com.ahirajustice.customersupport.message.services.MessageService;
import com.ahirajustice.customersupport.message.viewmodels.MessageViewModel;
import com.ahirajustice.customersupport.user.services.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final CurrentUserService currentUserService;
    private final AgentService agentService;

    @Override
    public Message createMessage(Conversation conversation, User user, String body) {
        Message message = Message.builder()
                .conversation(conversation)
                .user(user)
                .body(body)
                .build();

        return messageRepository.save(message);
    }

    @Override
    public MessageViewModel sendMessage(SendMessageRequest request) {
        Conversation conversation = getConversation(request.getConversationId());
        User loggedInUser = currentUserService.getCurrentUser();

        verifyLoggedInUserIsPartOfConversation(conversation, loggedInUser);

        if (ConversationStatus.INITIATED.equals(conversation.getStatus())) {
            Agent agent = agentService.getAgent(loggedInUser);

            if (agent != null) {
                assignAgentToConversation(conversation, agent);
            }
        }

        return MessageViewModel.from(
                createMessage(conversation, loggedInUser, request.getMessageBody())
        );
    }

    private void verifyLoggedInUserIsPartOfConversation(Conversation conversation, User loggedInUser) {
        if (
            (loggedInUser.equals(conversation.getUser()) && conversation.getAgent() == null) ||
            (conversation.getAgent() != null && loggedInUser.equals(conversation.getAgent().getUser()))
        ) {
            throw new BadRequestException("User is not part of this conversation");
        }
    }

    private Conversation getConversation(long conversationId) {
        return conversationRepository.findById(conversationId)
                .orElseThrow(() -> new NotFoundException(String.format("Conversation with id: '%s' not found", conversationId)));
    }

    private void assignAgentToConversation(Conversation conversation, Agent agent) {
        conversation.setAgent(agent);
        conversation.setStatus(ConversationStatus.ACTIVE);

        conversationRepository.save(conversation);
    }

}
