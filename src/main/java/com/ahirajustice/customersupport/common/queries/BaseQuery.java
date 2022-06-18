package com.ahirajustice.customersupport.common.queries;

import com.ahirajustice.customersupport.common.entities.BaseEntity;
import com.ahirajustice.customersupport.common.exceptions.ValidationException;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseQuery {

    @Min(0)
    private int pageIndex = 0;
    @Min(1)
    private int pageSize = 10;
    private List<@NotBlank String> sort;

    protected abstract Class<? extends BaseEntity> getSortEntityClass();

    protected abstract Predicate getPredicate(BooleanExpression expression);

    public Predicate getPredicate() {
        BooleanExpression expression = Expressions.asBoolean(true).isTrue();
        return getPredicate(expression);
    }

    public Pageable getPageable() {
        List<Sort.Order> sortOrders = validateSortFields();
        return PageRequest.of(pageIndex, pageSize, Sort.by(sortOrders));
    }

    public List<Sort.Order> validateSortFields() {
        Class<?> cls = getSortEntityClass();

        if (sort == null || sort.isEmpty() || cls == null) {
            return Collections.singletonList(Sort.Order.desc("createdOn"));
        }

        if (sort.size() == 2 &&
                Stream.of("asc", "desc", "ascending", "descending")
                        .anyMatch(val -> val.equalsIgnoreCase(sort.get(1).trim()))
        ) {
            sort = Collections.singletonList(sort.get(0) + "," + sort.get(1));
        }

        List<Sort.Order> sortOrders = sort.stream()
                .map(BaseQuery::toSortOrder)
                .collect(Collectors.toList());

        List<String> validSortFields = getAllFields(cls);
        Collections.sort(validSortFields);

        for (Sort.Order sortOrder : sortOrders) {
            boolean fieldNotExist = validSortFields.stream()
                    .noneMatch(field -> field.equals(sortOrder.getProperty()));

            if (fieldNotExist) {
                throw new ValidationException(String.format(
                        "%s is not a valid sort field. Valid fields: %s",
                        sortOrder.getProperty(),
                        String.join(", ", validSortFields)
                ));
            }
        }

        return sortOrders;
    }

    private static Sort.Order toSortOrder(String sortValues) {
        String[] sortArgs = sortValues.split(",");

        String fieldName = sortArgs[0].trim();
        boolean isAscending = true;

        if (sortArgs.length >= 2) {
            isAscending = Stream.of("desc", "descending")
                    .noneMatch(val -> val.equalsIgnoreCase(sortArgs[1].trim()));
        }

        return isAscending ? Sort.Order.asc(fieldName) : Sort.Order.desc(fieldName);
    }

    private List<String> getAllFields(Class<?> cls) {
        Set<String> validSortFields = new HashSet<>();

        while (cls != null && cls != Object.class) {
            Set<String> baseFields = getSimpleFields(cls);
            validSortFields.addAll(baseFields);
            cls = cls.getSuperclass();
        }

        return new ArrayList<>(validSortFields);
    }

    private static Set<String> getSimpleFields(Class<?> cls) {
        return Arrays.stream(cls.getDeclaredFields())
                .filter(field -> BeanUtils.isSimpleValueType(field.getType()))
                .map(Field::getName)
                .collect(Collectors.toSet());
    }
    
}
