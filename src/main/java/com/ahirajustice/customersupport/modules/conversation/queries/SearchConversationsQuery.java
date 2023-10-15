package com.ahirajustice.customersupport.modules.conversation.queries;

import com.ahirajustice.customersupport.common.entities.BaseEntity;
import com.ahirajustice.customersupport.common.entities.Conversation;
import com.ahirajustice.customersupport.common.queries.BaseQuery;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SearchConversationsQuery extends BaseQuery {

    @Override
    protected Class<? extends BaseEntity> getSortEntityClass() {
        return Conversation.class;
    }

    @Override
    protected Predicate getPredicate(BooleanExpression expression) {
        return expression;
    }

}
