package com.ahirajustice.customersupport.common.repositories;

import com.ahirajustice.customersupport.common.entities.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long>, QuerydslPredicateExecutor<Agent> {

}
