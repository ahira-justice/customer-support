package com.ahirajustice.customersupport.modules.conversation.services;

import com.ahirajustice.customersupport.common.entities.Conversation;
import com.ahirajustice.customersupport.modules.conversation.queries.SearchConversationsQuery;
import com.ahirajustice.customersupport.modules.conversation.queries.SearchInitiatedConversationsQuery;
import com.ahirajustice.customersupport.modules.conversation.requests.CloseConversationRequest;
import com.ahirajustice.customersupport.modules.conversation.requests.InitiateConversationRequest;
import com.ahirajustice.customersupport.modules.conversation.viewmodels.ConversationViewModel;
import org.springframework.data.domain.Page;

public interface ConversationService {

    ConversationViewModel initiateConversation(InitiateConversationRequest request);

    Page<ConversationViewModel> searchConversations(SearchConversationsQuery query);

    Page<ConversationViewModel> searchInitiatedConversations(SearchInitiatedConversationsQuery query);

    ConversationViewModel closeConversation(CloseConversationRequest request);

    Conversation getConversation(long conversationId);

}
