package com.ahirajustice.customersupport.user.queries;

import com.ahirajustice.customersupport.common.entities.BaseEntity;
import com.ahirajustice.customersupport.common.entities.QUser;
import com.ahirajustice.customersupport.common.entities.User;
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
public class SearchUsersQuery extends BaseQuery {

    private String username;
    private String email;
    private Boolean isEmailVerified;
    private String firstName;
    private String lastName;
    private long roleId;

    @Override
    protected Class<? extends BaseEntity> getSortEntityClass() {
        return User.class;
    }

    @Override
    protected Predicate getPredicate(BooleanExpression expression) {
        if (StringUtils.isNotBlank(username)) {
            expression = expression.and(QUser.user.username.contains(username));
        }

        if (StringUtils.isNotBlank(email)) {
            expression = expression.and(QUser.user.email.contains(email));
        }

        if (isEmailVerified != null) {
            expression = expression.and(QUser.user.isEmailVerified.eq(isEmailVerified));
        }

        if (StringUtils.isNotBlank(firstName)) {
            expression = expression.and(QUser.user.firstName.contains(firstName));
        }

        if (StringUtils.isNotBlank(lastName)) {
            expression = expression.and(QUser.user.lastName.contains(lastName));
        }

        if (roleId > 0) {
            expression = expression.and(QUser.user.role.id.eq(roleId));
        }

        return expression;
    }

}
