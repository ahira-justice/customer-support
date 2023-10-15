package com.ahirajustice.customersupport.modules.agent.services;

import com.ahirajustice.customersupport.modules.agent.requests.CreateAgentRequest;
import com.ahirajustice.customersupport.modules.agent.viewmodels.AgentViewModel;
import com.ahirajustice.customersupport.common.entities.Agent;
import com.ahirajustice.customersupport.common.entities.User;

public interface AgentService {

    AgentViewModel createAgent(CreateAgentRequest request);

    Agent getAgent(User user);

}
