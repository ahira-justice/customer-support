package com.ahirajustice.customersupport.conversation.services;

import com.ahirajustice.customersupport.common.entities.Agent;
import com.ahirajustice.customersupport.common.entities.Conversation;
import com.ahirajustice.customersupport.conversation.requests.InitiateConversationRequest;
import com.ahirajustice.customersupport.conversation.viewmodels.ConversationViewModel;

public interface ConversationService {

    ConversationViewModel initiateConversation(InitiateConversationRequest request);

    Conversation getConversation(long conversationId);

    void assignAgentToConversation(Conversation conversation, Agent agent);
}
