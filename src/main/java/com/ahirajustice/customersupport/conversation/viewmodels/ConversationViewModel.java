package com.ahirajustice.customersupport.conversation.viewmodels;

import com.ahirajustice.customersupport.agent.viewmodels.AgentViewModel;
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

    private AgentViewModel agent;
    private UserViewModel user;

    public static ConversationViewModel from(Conversation conversation) {
        ConversationViewModel response = new ConversationViewModel();

        BeanUtils.copyProperties(conversation, response);

        if (conversation.getAgent() != null)
            response.setAgent(AgentViewModel.from(conversation.getAgent()));

        response.setUser(UserViewModel.from(conversation.getUser()));

        return response;
    }

}
