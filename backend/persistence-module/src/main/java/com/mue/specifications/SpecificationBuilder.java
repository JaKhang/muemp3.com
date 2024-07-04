package com.mue.specifications;

import com.mue.core.domain.ApiQuery;
import com.mue.core.domain.QueryOperation;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SpecificationBuilder {

    public static <T> Specification<T> build(@Nonnull List<ApiQuery> apiQuerys) {
        AtomicReference<Specification<T>> specification = new AtomicReference<>(null);
        apiQuerys.forEach(apiQuery -> {
            if (specification.get() == null){
                specification.set(build(apiQuery));
                return;
            }


            if (apiQuery.isOr()) {
                specification.get().or(build(apiQuery));
            } else {
                specification.get().and(build(apiQuery));
            }
        });
        return specification.get();
    }

    public static <T> Specification<T> build(ApiQuery apiQuery) {
        return (root, query1, builder) -> {

            if (apiQuery.getOperation() == QueryOperation.GREATER) {
                return builder.greaterThanOrEqualTo(
                        root.<String>get(apiQuery.getKey()), apiQuery.getValue().toString());
            } else if (apiQuery.getOperation() == QueryOperation.LESS) {
                return builder.lessThanOrEqualTo(
                        root.<String>get(apiQuery.getKey()), apiQuery.getValue().toString());
            } else if (apiQuery.getOperation() == QueryOperation.EQUAL) {
                if (root.get(apiQuery.getKey()).getJavaType() == String.class) {
                    return builder.like(
                            root.<String>get(apiQuery.getKey()), "%" + apiQuery.getValue() + "%");
                } else {
                    return builder.equal(root.get(apiQuery.getKey()), apiQuery.getValue());
                }
            }
            return builder.conjunction();
        };
    }
}
