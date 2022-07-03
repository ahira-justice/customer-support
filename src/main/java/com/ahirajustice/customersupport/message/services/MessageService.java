package com.ahirajustice.customersupport.message.services;

import com.ahirajustice.customersupport.common.entities.Conversation;
import com.ahirajustice.customersupport.common.entities.Message;
import com.ahirajustice.customersupport.common.entities.User;
import com.ahirajustice.customersupport.message.queries.SearchMessagesByConversationQuery;
import com.ahirajustice.customersupport.message.queries.SearchMessagesQuery;
import com.ahirajustice.customersupport.message.requests.SendMessageRequest;
import com.ahirajustice.customersupport.message.viewmodels.MessageViewModel;
import org.springframework.data.domain.Page;

public interface MessageService {

    Message createMessage(Conversation conversation, User user, String body);

    MessageViewModel sendMessage(SendMessageRequest request);

    Page<MessageViewModel> searchMessages(SearchMessagesQuery query);

    Page<MessageViewModel> searchMessagesByConversation(SearchMessagesByConversationQuery query);

    Message getMostRecentMessage(Conversation conversation);
}
