package com.ahirajustice.customersupport.agent.services;

import com.ahirajustice.customersupport.agent.requests.CreateAgentRequest;
import com.ahirajustice.customersupport.agent.viewmodels.AgentViewModel;

public interface AgentService {

    AgentViewModel createAgent(CreateAgentRequest request);

}
