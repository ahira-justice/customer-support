package com.ahirajustice.customersupport.conversation.services.impl;

import com.ahirajustice.customersupport.common.entities.Conversation;
import com.ahirajustice.customersupport.common.entities.User;
import com.ahirajustice.customersupport.common.enums.ConversationStatus;
import com.ahirajustice.customersupport.common.exceptions.NotFoundException;
import com.ahirajustice.customersupport.common.exceptions.ValidationException;
import com.ahirajustice.customersupport.common.repositories.ConversationRepository;
import com.ahirajustice.customersupport.conversation.requests.CloseConversationRequest;
import com.ahirajustice.customersupport.conversation.requests.InitiateConversationRequest;
import com.ahirajustice.customersupport.conversation.services.ConversationService;
import com.ahirajustice.customersupport.conversation.viewmodels.ConversationViewModel;
import com.ahirajustice.customersupport.message.services.MessageService;
import com.ahirajustice.customersupport.user.services.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;
    private final CurrentUserService currentUserService;
    private final MessageService messageService;

    @Override
    @Transactional
    public ConversationViewModel initiateConversation(InitiateConversationRequest request) {
        User loggedInUser = currentUserService.getCurrentUser();

        Conversation conversation = buildConversation(loggedInUser);
        conversationRepository.save(conversation);
        messageService.createMessage(conversation, loggedInUser, request.getMessageBody());

        return ConversationViewModel.from(conversation);
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

    @Override
    public Conversation getConversation(long conversationId) {
        return conversationRepository.findById(conversationId)
                .orElseThrow(() -> new NotFoundException(String.format("Conversation with id: '%s' not found", conversationId)));
    }

    private Conversation buildConversation(User user) {
        return Conversation.builder()
                .user(user)
                .status(ConversationStatus.INITIATED)
                .build();
    }

    private Conversation closeConversation(Conversation conversation) {
        conversation.setStatus(ConversationStatus.CLOSED);
        conversation.setClosedOn(LocalDateTime.now());

        return conversationRepository.save(conversation);
    }

}
