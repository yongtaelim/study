package com.example.queyrdsl.staff.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer age;

    @Column(name = "store_id")
    private Long storeId;

//    @JoinColumn(name = "store_id", foreignKey = @ForeignKey(name = "fk_staff_store_id"))
//    private Store store;

    @Builder
//    public Staff(Long id, String name, Integer age, Store store) {
    public Staff(Long id, String name, Integer age, Long storeId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.storeId = storeId;
//        this.store = store;
    }
}
