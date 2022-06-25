package com.ahirajustice.customersupport.message.queries;

import com.ahirajustice.customersupport.common.entities.BaseEntity;
import com.ahirajustice.customersupport.common.entities.Message;
import com.ahirajustice.customersupport.common.entities.QMessage;
import com.ahirajustice.customersupport.common.queries.BaseQuery;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.Min;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchMessagesByConversationQuery extends BaseQuery {

    @Min(value = 1, message = "conversationId is required; must be greater than or equal to 1")
    private long conversationId;
    private String body;

    @Override
    protected Class<? extends BaseEntity> getSortEntityClass() {
        return Message.class;
    }

    @Override
    protected Predicate getPredicate(BooleanExpression expression) {
        expression = expression.and(QMessage.message.conversation.id.eq(conversationId));

        if (StringUtils.isNotBlank(body)) {
            expression = expression.and(QMessage.message.body.contains(body));
        }

        return expression;
    }
}
