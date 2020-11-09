package com.example.queyrdsl.staff.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StaffVo {
    private Long id;
    private String name;
    private Integer age;
    private Long storeId;
}
