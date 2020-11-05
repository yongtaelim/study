package com.example.queyrdsl.staff.support;

import com.example.queyrdsl.staff.entity.Staff;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class StaffRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     * @param domainClass must not be {@literal null}.
     */
    public StaffRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Staff.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }
}
