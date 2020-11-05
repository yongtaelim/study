package com.example.queyrdsl.store.support;


import com.example.queyrdsl.staff.entity.QStaff;
import com.example.queyrdsl.staff.entity.Staff;
import com.example.queyrdsl.store.entity.QStore;
import com.example.queyrdsl.store.entity.Store;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.queyrdsl.staff.entity.QStaff.*;
import static com.example.queyrdsl.store.entity.QStore.*;

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

    /**
     * Entity 관계 매핑 되어 있는 경우
     * @param name
     * @return
     */
//    public List<Staff> findStaffsByName(String name) {
//        return jpaQueryFactory
//                .select(Projections.fields(Staff.class,
//                        staff.id
//                        , staff.age
//                        , staff.name
//                ))
//                .from(store)
//                .join(store.staff, staff)
//                .where(store.name.eq(name))
//                .fetch();
//    }

    /**
     * Entity 관계 매핑 되어 있지 않을 경우
     * @param name
     * @return
     */
    public List<Staff> findStaffsByName(String name) {
        return jpaQueryFactory
                .select(Projections.constructor(Staff.class,
                        staff.id
                        , staff.age
                        , staff.name
                ))
                .from(store)
                .join(staff)
                    .on(store.id.eq(staff.storeId))
                .where(store.name.eq(name))
                .fetch();
    }
}
