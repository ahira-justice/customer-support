package com.ahirajustice.customersupport.modules.conversation.queries;

import com.ahirajustice.customersupport.common.entities.BaseEntity;
import com.ahirajustice.customersupport.common.entities.Conversation;
import com.ahirajustice.customersupport.common.entities.QConversation;
import com.ahirajustice.customersupport.common.queries.BaseQuery;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchConversationsQuery extends BaseQuery {

    private String loggedInUserUsername;

    @Override
    protected Class<? extends BaseEntity> getSortEntityClass() {
        return Conversation.class;
    }

    @Override
    protected Predicate getPredicate(BooleanExpression expression) {
        expression = expression.and(
                QConversation.conversation.user.username.eq(loggedInUserUsername).or(
                        QConversation.conversation.agent.user.username.eq(loggedInUserUsername)
                )
        );

        return expression;
    }

}
