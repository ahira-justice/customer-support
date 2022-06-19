package com.ahirajustice.customersupport.message.services;

import com.ahirajustice.customersupport.common.entities.Conversation;
import com.ahirajustice.customersupport.common.entities.Message;
import com.ahirajustice.customersupport.common.entities.User;

public interface MessageService {

    Message createMessage(Conversation conversation, User user, String body);

}
