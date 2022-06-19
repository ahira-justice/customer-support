package com.ahirajustice.customersupport.conversation.viewmodels;

import com.ahirajustice.customersupport.common.entities.Conversation;
import com.ahirajustice.customersupport.common.viewmodels.BaseViewModel;
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
public class ConversationViewModel extends BaseViewModel {

    private UserViewModel agent;
    private UserViewModel customer;

    public static ConversationViewModel from(Conversation conversation) {
        ConversationViewModel response = new ConversationViewModel();

        BeanUtils.copyProperties(conversation, response);

        if (conversation.getAgent() != null)
            response.setAgent(UserViewModel.from(conversation.getAgent()));

        response.setCustomer(UserViewModel.from(conversation.getCustomer()));

        return response;
    }

}
