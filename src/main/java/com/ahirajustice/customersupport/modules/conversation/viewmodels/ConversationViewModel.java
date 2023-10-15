package com.ahirajustice.customersupport.modules.conversation.viewmodels;

import com.ahirajustice.customersupport.modules.agent.viewmodels.AgentViewModel;
import com.ahirajustice.customersupport.common.entities.Conversation;
import com.ahirajustice.customersupport.common.entities.Message;
import com.ahirajustice.customersupport.common.enums.ConversationStatus;
import com.ahirajustice.customersupport.common.viewmodels.BaseViewModel;
import com.ahirajustice.customersupport.modules.message.viewmodels.MessageViewModel;
import com.ahirajustice.customersupport.modules.user.viewmodels.UserViewModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversationViewModel extends BaseViewModel {

    private AgentViewModel agent;
    private UserViewModel user;
    private MessageViewModel mostRecentMessage;
    private ConversationStatus status;
    private LocalDateTime closedOn;

    public static ConversationViewModel from(Conversation conversation, Message message) {
        ConversationViewModel response = new ConversationViewModel();

        BeanUtils.copyProperties(conversation, response);

        if (conversation.getAgent() != null)
            response.setAgent(AgentViewModel.from(conversation.getAgent()));

        response.setUser(UserViewModel.from(conversation.getUser()));
        response.setMostRecentMessage(MessageViewModel.from(message));

        return response;
    }

}
