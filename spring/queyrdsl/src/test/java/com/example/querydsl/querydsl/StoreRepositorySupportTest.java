package com.example.querydsl.querydsl;

import com.example.querydsl.staff.entity.Staff;
import com.example.querydsl.staff.repository.StaffRepository;
import com.example.querydsl.staff.vo.StaffVo;
import com.example.querydsl.store.entity.Store;
import com.example.querydsl.store.repository.StoreRepository;
import com.example.querydsl.store.support.StoreRepositorySupport;
import com.example.querydsl.store.vo.StoreVo;
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
    private StaffRepository staffRepository;

    @Autowired
    private StoreRepositorySupport storeRepositorySupport;

    @Test
    void findOneByNameTest() {
        //given
        final Long id = 5L;
        final String address = "주소5";
        final String name = "스토어5";

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

//    @Test
//    void findStaffsByNameTest_Entity관계매핑되어있을경우() {
//        //given
//        final Long staffId1 = 2L;
//        final String staffName1 = "staffName1";
//        final Integer age1 = 31;
//
//
//        final Long staffId2 = 3L;
//        final String staffName2 = "staffName2";
//        final Integer age2 = 41;
//
//        final Long id = 4L;
//        final String address = "주소4";
//        final String name = "스토어4";
//
//        Staff staff1 = Staff.builder()
//                .id(staffId1)
//                .name(staffName1)
//                .age(age1)
//                .build();
//
//        Staff staff2 = Staff.builder()
//                .id(staffId2)
//                .name(staffName2)
//                .age(age2)
//                .build();
//
//        Store store = Store.builder()
//                .id(id)
//                .address(address)
//                .name(name)
//                .staff(Arrays.asList(staff1, staff2))
//                .build();
//
//        storeRepository.save(store);
//
//        //when
//        List<StaffVo> staffs = storeRepositorySupport.findStaffsByName(name);
//
//        //then
//        assertThat(staffs.size()).isGreaterThan(0);
//        assertThat(staffs.get(0).getName()).isEqualTo(staffName1);
//        assertThat(staffs.get(1).getName()).isEqualTo(staffName2);
//    }

    @Test
    void findStaffsByNameTest_Entity관계매핑안되어있는경우() {
        //given
        final Long staffId1 = 10L;
        final String staffName1 = "staffName4";
        final Integer age1 = 34;


        final Long staffId2 = 11L;
        final String staffName2 = "staffName5";
        final Integer age2 = 21;

        final Long id = 8L;
        final String address = "주소6";
        final String name = "스토어6";

        Staff staff1 = Staff.builder()
                .id(staffId1)
                .name(staffName1)
                .age(age1)
                .storeId(id)
                .build();

        Staff staff2 = Staff.builder()
                .id(staffId2)
                .name(staffName2)
                .age(age2)
                .storeId(id)
                .build();

        staffRepository.saveAll(Arrays.asList(staff1, staff2));

        Store store = Store.builder()
                .id(id)
                .address(address)
                .name(name)
                .build();

        storeRepository.save(store);

        //when
        List<StaffVo> staffs = storeRepositorySupport.findStaffsByName(name);

        //then
        assertThat(staffs.size()).isGreaterThan(0);
        assertThat(staffs.get(0).getName()).isEqualTo(staffName1);
        assertThat(staffs.get(1).getName()).isEqualTo(staffName2);
    }

    @Test
    void querydsl_log비교() {
        //given
        final String name = "스토어6";

        //when
        List<StaffVo> staffs = storeRepositorySupport.findStaffsByName(name);

        //then
        assertThat(staffs.size()).isGreaterThan(0);
    }

@Test
void querydsl_Mysql내부함수_call() {
    //given
    final String name = "스토어6";

    //when
    StoreVo store = storeRepositorySupport.findByName(name);

    //then
    assertThat(store.getName()).isEqualTo("TEST_" + name);
}
}
