package com.ahirajustice.customersupport.conversation.services;

import com.ahirajustice.customersupport.common.entities.Conversation;
import com.ahirajustice.customersupport.conversation.requests.CloseConversationRequest;
import com.ahirajustice.customersupport.conversation.requests.InitiateConversationRequest;
import com.ahirajustice.customersupport.conversation.viewmodels.ConversationViewModel;

public interface ConversationService {

    ConversationViewModel initiateConversation(InitiateConversationRequest request);

    ConversationViewModel closeConversation(CloseConversationRequest request);

    Conversation getConversation(long conversationId);

}
