package com.ahirajustice.customersupport.agent.services;

import com.ahirajustice.customersupport.agent.requests.CreateAgentRequest;
import com.ahirajustice.customersupport.agent.viewmodels.AgentViewModel;
import com.ahirajustice.customersupport.common.entities.Agent;
import com.ahirajustice.customersupport.common.entities.User;

public interface AgentService {

    AgentViewModel createAgent(CreateAgentRequest request);

    Agent getAgent(User user);

}
