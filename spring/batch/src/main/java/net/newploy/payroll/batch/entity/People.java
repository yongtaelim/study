package net.newploy.payroll.batch.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@ToString
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class People {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personId;
    private String firstName;
    private String lastName;
    private Integer enabled;

    @Builder
    public People(String firstName, String lastName, Integer enabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = enabled;
    }
}