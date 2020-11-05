package com.example.queyrdsl.staff.repository;


import com.example.queyrdsl.staff.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Long> {
}
