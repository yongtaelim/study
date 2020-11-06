package net.newploy.payroll.batch.repository;

import net.newploy.payroll.batch.entity.People;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeopleRepository extends JpaRepository<People, Long> {
}
