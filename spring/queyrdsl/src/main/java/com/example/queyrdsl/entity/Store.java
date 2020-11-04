package com.example.queyrdsl.entity;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;

    @OneToMany(mappedBy = "store")
    private List<Staff> staff = new ArrayList<>();

    @Builder
    public Store(Long id, String name, String address, List<Staff> staff) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.staff = staff;
    }
}
