package com.ahirajustice.customersupport.modules.message.viewmodels;

import com.ahirajustice.customersupport.common.entities.Message;
import com.ahirajustice.customersupport.common.viewmodels.BaseViewModel;
import com.ahirajustice.customersupport.modules.user.viewmodels.UserViewModel;
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

    private long conversationId;
    private UserViewModel user;
    private String body;

    public static MessageViewModel from(Message message) {
        MessageViewModel response = new MessageViewModel();

        BeanUtils.copyProperties(message, response);
        response.setConversationId(message.getConversation().getId());
        response.setUser(UserViewModel.from(message.getUser()));

        return response;
    }
}
