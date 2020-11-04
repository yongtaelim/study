package com.example.queyrdsl.repository.support;


import com.example.queyrdsl.entity.Staff;
import com.example.queyrdsl.entity.Store;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.queyrdsl.entity.QStaff.staff;
import static com.example.queyrdsl.entity.QStore.store;

@Repository
public class StoreRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     * @param domainClass must not be {@literal null}.
     */
    public StoreRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Store.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Store> findByName(String name) {
        return jpaQueryFactory
                .selectFrom(store)
                .where(store.name.eq(name))
                .fetch();
    }

    public Store findOneByName(String name) {
        return jpaQueryFactory
                .selectFrom(store)
                .where(store.name.eq(name))
                .fetchOne();
    }

    public List<Staff> findStaffsByName(String name) {
        return jpaQueryFactory
                .select(Projections.fields(Staff.class,
                        staff.id
                        , staff.age
                        , staff.name
                ))
                .from(store)
                .join(store.staff, staff)
                .where(store.name.eq(name))
                .fetch();
    }
}
