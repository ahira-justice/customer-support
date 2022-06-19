package com.ahirajustice.customersupport.message.viewmodels;

import com.ahirajustice.customersupport.common.entities.Message;
import com.ahirajustice.customersupport.common.viewmodels.BaseViewModel;
import com.ahirajustice.customersupport.conversation.viewmodels.ConversationViewModel;
import com.ahirajustice.customersupport.user.viewmodels.UserViewModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageViewModel extends BaseViewModel {

    private ConversationViewModel conversation;
    private UserViewModel user;
    private String body;

    public static MessageViewModel from(Message message) {
        MessageViewModel response = new MessageViewModel();

        BeanUtils.copyProperties(message, response);
        response.setConversation(ConversationViewModel.from(message.getConversation()));
        response.setUser(UserViewModel.from(message.getUser()));

        return response;
    }
}
