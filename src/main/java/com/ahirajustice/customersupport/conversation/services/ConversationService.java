package com.ahirajustice.customersupport.conversation.services;

import com.ahirajustice.customersupport.common.entities.Conversation;
import com.ahirajustice.customersupport.conversation.queries.SearchConversationsQuery;
import com.ahirajustice.customersupport.conversation.requests.CloseConversationRequest;
import com.ahirajustice.customersupport.conversation.requests.InitiateConversationRequest;
import com.ahirajustice.customersupport.conversation.viewmodels.ConversationViewModel;
import org.springframework.data.domain.Page;

public interface ConversationService {

    ConversationViewModel initiateConversation(InitiateConversationRequest request);

    Page<ConversationViewModel> searchConversations(SearchConversationsQuery query);

    ConversationViewModel closeConversation(CloseConversationRequest request);

    Conversation getConversation(long conversationId);

}
