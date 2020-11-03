package com.example.queyrdsl.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "store_id")
    private Collection<Staff> staff;

    @Builder
    public Store(Long id, String name, String address, Collection<Staff> staff) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.staff = staff;
    }
}
