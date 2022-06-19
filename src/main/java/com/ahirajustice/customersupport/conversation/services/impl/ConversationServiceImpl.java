package com.ahirajustice.customersupport.conversation.services.impl;

import com.ahirajustice.customersupport.common.entities.Conversation;
import com.ahirajustice.customersupport.common.entities.User;
import com.ahirajustice.customersupport.common.repositories.ConversationRepository;
import com.ahirajustice.customersupport.conversation.requests.InitiateConversationRequest;
import com.ahirajustice.customersupport.conversation.services.ConversationService;
import com.ahirajustice.customersupport.conversation.viewmodels.ConversationViewModel;
import com.ahirajustice.customersupport.message.services.MessageService;
import com.ahirajustice.customersupport.user.services.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    private Conversation buildConversation(User user) {
        return Conversation.builder()
                .user(user)
                .build();
    }

}
