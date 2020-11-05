package com.example.queyrdsl.store.repository;

import com.example.queyrdsl.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Store findByName(String name);
}
