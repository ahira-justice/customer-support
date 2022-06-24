package com.ahirajustice.customersupport.agent.services.impl;

import com.ahirajustice.customersupport.agent.requests.CreateAgentRequest;
import com.ahirajustice.customersupport.agent.services.AgentService;
import com.ahirajustice.customersupport.agent.viewmodels.AgentViewModel;
import com.ahirajustice.customersupport.common.entities.Agent;
import com.ahirajustice.customersupport.common.entities.User;
import com.ahirajustice.customersupport.common.enums.Roles;
import com.ahirajustice.customersupport.common.repositories.AgentRepository;
import com.ahirajustice.customersupport.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;
    private final UserService userService;

    @Override
    @Transactional
    public AgentViewModel createAgent(CreateAgentRequest request) {
        User user = userService.createUser(request, Roles.AGENT);
        Agent agent = Agent.builder()
                .user(user)
                .build();

        return AgentViewModel.from(agentRepository.save(agent));
    }

    @Override
    public Agent getAgent(User user) {
        return agentRepository.findByUser(user).orElse(null);
    }

}
