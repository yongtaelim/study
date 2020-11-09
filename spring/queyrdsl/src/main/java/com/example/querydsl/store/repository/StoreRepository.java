package com.example.querydsl.store.repository;

import com.example.querydsl.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Store findByName(String name);
}
