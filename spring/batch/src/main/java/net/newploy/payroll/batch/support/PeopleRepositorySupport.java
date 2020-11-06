package net.newploy.payroll.batch.support;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.newploy.payroll.batch.entity.People;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static net.newploy.payroll.batch.entity.QPeople.people;


@Repository
public class PeopleRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public PeopleRepositorySupport(JPAQueryFactory queryFactory) {
        super(People.class);
        this.queryFactory = queryFactory;
    }

    public JPAQuery<People> findAll() {
        return queryFactory
                .selectFrom(people);
    }
}
