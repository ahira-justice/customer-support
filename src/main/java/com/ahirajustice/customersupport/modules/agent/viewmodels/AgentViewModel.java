package com.ahirajustice.customersupport.modules.agent.viewmodels;

import com.ahirajustice.customersupport.common.entities.Agent;
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
public class AgentViewModel extends BaseViewModel {

    private UserViewModel user;

    public static AgentViewModel from(Agent agent) {
        AgentViewModel response = new AgentViewModel();

        BeanUtils.copyProperties(agent, response);
        response.setUser(UserViewModel.from(agent.getUser()));

        return response;
    }
}
