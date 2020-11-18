package com.example.flyway.mapper;

import com.example.flyway.vo.TestVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ObjectMapperTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void snake_JSON에서_camelVO로_Convert_test() throws JSONException {
        //given
        String name = "임용태";
        Integer age = 32;
        String address = "강남 아파트에 살고싶다";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_name", name);
        jsonObject.put("user_age", age);
        jsonObject.put("user_address", address);

        //when
        TestVo testVo = objectMapper.convertValue(jsonObject, TestVo.class);

        //then
        assertThat(testVo.getUserName()).isEqualTo(jsonObject.get("user_name"));
        assertThat(testVo.getUserAge()).isEqualTo(jsonObject.get("user_age"));
        assertThat(testVo.getUserAddress()).isEqualTo(jsonObject.get("user_address"));
    }

    @Test
    void snake_Map에서_camelVO로_Convert_test(){
        //given
        String name = "임용태";
        Integer age = 32;
        String address = "강남 아파트에 살고싶다";

        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("user_name", name);
        parameterMap.put("user_age", age);
        parameterMap.put("user_address", address);

        //when
        TestVo testVo = objectMapper.convertValue(parameterMap, TestVo.class);

        //then
        assertThat(testVo.getUserName()).isEqualTo(parameterMap.get("user_name"));
        assertThat(testVo.getUserAge()).isEqualTo(parameterMap.get("user_age"));
        assertThat(testVo.getUserAddress()).isEqualTo(parameterMap.get("user_address"));
    }
}
