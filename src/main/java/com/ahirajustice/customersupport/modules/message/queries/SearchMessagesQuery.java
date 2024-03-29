package com.ahirajustice.customersupport.modules.message.queries;

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

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchMessagesQuery extends BaseQuery {

    private String loggedInUserUsername;
    private String body;

    @Override
    protected Class<? extends BaseEntity> getSortEntityClass() {
        return Message.class;
    }

    @Override
    protected Predicate getPredicate(BooleanExpression expression) {
        expression = expression.and(
                QMessage.message.conversation.user.username.eq(loggedInUserUsername).or(
                        QMessage.message.conversation.agent.user.username.eq(loggedInUserUsername)
                )
        );

        if (StringUtils.isNotBlank(body)) {
            expression = expression.and(QMessage.message.body.contains(body));
        }

        return expression;
    }

}
