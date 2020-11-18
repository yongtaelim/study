package com.example.flyway.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
//@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TestVo {
    private String userName;
    private Integer userAge;
    private String userAddress;
}
