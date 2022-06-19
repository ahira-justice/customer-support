package com.ahirajustice.customersupport.message.services;

import com.ahirajustice.customersupport.common.entities.Conversation;
import com.ahirajustice.customersupport.common.entities.Message;
import com.ahirajustice.customersupport.common.entities.User;
import com.ahirajustice.customersupport.message.requests.SendMessageRequest;
import com.ahirajustice.customersupport.message.viewmodels.MessageViewModel;

public interface MessageService {

    Message createMessage(Conversation conversation, User user, String body);

    MessageViewModel sendMessage(SendMessageRequest request);

}
