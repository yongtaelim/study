package com.example.queyrdsl.querydsl;

import com.example.queyrdsl.entity.Staff;
import com.example.queyrdsl.entity.Store;
import com.example.queyrdsl.repository.StoreRepository;
import com.example.queyrdsl.repository.support.StoreRepositorySupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StoreRepositorySupportTest {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreRepositorySupport storeRepositorySupport;

    @Test
    void findOneByNameTest() {
        //given
        final Long id = 3L;
        final String address = "주소3";
        final String name = "스토어3";

        Store store = Store.builder()
                .id(id)
                .address(address)
                .name(name)
                .build();

        storeRepository.save(store);

        //when
        Store resultByStore = storeRepositorySupport.findOneByName(name);


        //then
        Assertions.assertEquals(name, resultByStore.getName());
    }

    @Test
    void findStaffsByNameTest() {
        //given
        final Long staffId1 = 2L;
        final String staffName1 = "staffName1";
        final Integer age1 = 31;


        final Long staffId2 = 3L;
        final String staffName2 = "staffName2";
        final Integer age2 = 41;

        final Long id = 4L;
        final String address = "주소4";
        final String name = "스토어4";

        Staff staff1 = Staff.builder()
                .id(staffId1)
                .name(staffName1)
                .age(age1)
                .build();

        Staff staff2 = Staff.builder()
                .id(staffId2)
                .name(staffName2)
                .age(age2)
                .build();

        Store store = Store.builder()
                .id(id)
                .address(address)
                .name(name)
                .staff(Arrays.asList(staff1, staff2))
                .build();

        storeRepository.save(store);

        //when
        List<Staff> staffs = storeRepositorySupport.findStaffsByName(name);

        //then
        assertThat(staffs.size()).isGreaterThan(0);
        assertThat(staffs.get(0).getName()).isEqualTo(staffName1);
        assertThat(staffs.get(1).getName()).isEqualTo(staffName2);
    }
}
