package com.ahirajustice.customersupport.common.repositories;

import com.ahirajustice.customersupport.common.entities.Conversation;
import com.ahirajustice.customersupport.common.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long>, QuerydslPredicateExecutor<Conversation> {

    List<Conversation> findAllByAgent(User agent);

    List<Conversation> findAllByCustomer(User customer);

}