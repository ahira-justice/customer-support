package com.ahirajustice.customersupport.conversation.services.impl;

import com.ahirajustice.customersupport.common.entities.Conversation;
import com.ahirajustice.customersupport.common.entities.Message;
import com.ahirajustice.customersupport.common.entities.User;
import com.ahirajustice.customersupport.common.enums.ConversationStatus;
import com.ahirajustice.customersupport.common.exceptions.NotFoundException;
import com.ahirajustice.customersupport.common.exceptions.ValidationException;
import com.ahirajustice.customersupport.common.repositories.ConversationRepository;
import com.ahirajustice.customersupport.conversation.queries.SearchConversationsQuery;
import com.ahirajustice.customersupport.conversation.queries.SearchInitiatedConversationsQuery;
import com.ahirajustice.customersupport.conversation.requests.CloseConversationRequest;
import com.ahirajustice.customersupport.conversation.requests.InitiateConversationRequest;
import com.ahirajustice.customersupport.conversation.services.ConversationService;
import com.ahirajustice.customersupport.conversation.viewmodels.ConversationViewModel;
import com.ahirajustice.customersupport.message.services.MessageService;
import com.ahirajustice.customersupport.user.services.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

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

        Conversation conversation = createConversation(loggedInUser);
        Message message = messageService.createMessage(conversation, loggedInUser, request.getMessageBody());

        pushInitiatedConversationEventToAgents(conversation);

        return ConversationViewModel.from(conversation, message);
    }

    private Conversation createConversation(User user) {
        Conversation conversation = Conversation.builder()
                .user(user)
                .status(ConversationStatus.INITIATED)
                .build();

        return conversationRepository.save(conversation);
    }

    private void pushInitiatedConversationEventToAgents(Conversation conversation) {
        
    }

    @Override
    public Page<ConversationViewModel> searchConversations(SearchConversationsQuery query) {
        User loggedInUser = currentUserService.getCurrentUser();

        Page<Conversation> conversations = conversationRepository.findAll(query.getPredicate(), query.getPageable());

        return new PageImpl<>(
                conversations.stream()
                        .filter(conversation -> filterConversationsByLoggedInUser(conversation, loggedInUser))
                        .collect(Collectors.toList()),
                conversations.getPageable(),
                conversations.getTotalElements()
        ).map(conversation -> ConversationViewModel.from(
                conversation, messageService.getMostRecentMessage(conversation)
        ));
    }

    private boolean filterConversationsByLoggedInUser(Conversation conversation, User loggedInUser) {
        boolean loggedInUserIsUserInConversation = conversation.getUser().equals(loggedInUser);

        boolean loggedInUserIsAgentInConversation = false;

        if (conversation.getAgent() != null){
            loggedInUserIsAgentInConversation = conversation.getAgent().getUser().equals(loggedInUser);
        }

        return loggedInUserIsUserInConversation || loggedInUserIsAgentInConversation;
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
