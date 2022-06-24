package com.ahirajustice.customersupport.common.repositories;

import com.ahirajustice.customersupport.common.entities.Agent;
import com.ahirajustice.customersupport.common.entities.Conversation;
import com.ahirajustice.customersupport.common.entities.User;
import com.ahirajustice.customersupport.common.enums.ConversationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long>, QuerydslPredicateExecutor<Conversation> {

    List<Conversation> findAllByAgent(Agent agent);

    List<Conversation> findAllByUser(User user);

    @Transactional
    @Modifying
    @Query("update #{#entityName} conversation " +
            "set conversation.agent.id = :agentId, conversation.status = :status " +
            "where conversation.id = :conversationId")
    void updateAgentIdAndStatus(long conversationId, long agentId, ConversationStatus status);

}