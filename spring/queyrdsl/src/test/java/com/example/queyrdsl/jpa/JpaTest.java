package com.example.queyrdsl.jpa;

import com.example.queyrdsl.staff.entity.Staff;
import com.example.queyrdsl.staff.repository.StaffRepository;
import com.example.queyrdsl.store.entity.Store;
import com.example.queyrdsl.store.repository.StoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

@ActiveProfiles("local")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JpaTest {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Test
    void entity저장후조회() {
        //given
        final Long id = 1L;
        final String storeName = "스토어1";
        final String storeAddress = "주소1";

        Store store = Store.builder()
                .id(id)
                .name(storeName)
                .address(storeAddress)
                .build();
        storeRepository.save(store);

        //when
        Store resultStore = storeRepository.findByName(storeName);

        //then
        Assertions.assertEquals(resultStore.getName(), storeName);
    }

    @Test
    @DisplayName("초기 테스트 시 entity저장후조회 테스트 먼저 진행 후 테스트.")
    void entity수정() {
        //given
        final Long id = 1L;
        final String storeName = "스토어2";
        final String storeAddress = "주소2";
        Store store = Store.builder()
                .id(id)     // id 가 같으면 수정처리함
                .name(storeName)
                .address(storeAddress)
                .build();

        //when
        Store udpateStore = storeRepository.save(store);

        //then
        Assertions.assertEquals(udpateStore.getName(), storeName);
    }

//    @Test
//    @DisplayName("Store, Staff entity 저장")
//    void entity저장_관계매핑되어있을경우() {
//        //given
//        final Long storeId = 1L;
//        final String storeName = "store1234";
//        final String storeAddress = "storeAddress";
//
//        final Long staffId = 1L;
//        final String staffName = "staff1234";
//        final Integer staffAge = 30;
//
//        Staff staff = Staff.builder()
//                .id(staffId)
//                .name(staffName)
//                .age(staffAge)
//                .build();
//
//        Store store = Store.builder()
//                .id(storeId)
//                .name(storeName)
//                .address(storeAddress)
//                .staff(Arrays.asList(staff))
//                .build();
//
//        //when
//        Store saveStore = storeRepository.save(store);
//
//        //then
//        Assertions.assertEquals(saveStore.getName(), storeName);
//
//        Collection<Staff> staff1 = saveStore.getStaff();
//        Iterator<Staff> iterator = staff1.iterator();
//        Staff next = iterator.next();
//        Assertions.assertEquals(next.getName(), staffName);
//    }

    @Test
    @DisplayName("Store, Staff entity 저장")
    void entity저장_관계매핑되어있지않을경우() {
        //given
        final Long storeId = 2L;
        final String storeName = "store12345";
        final String storeAddress = "storeAddress";

        final Long staffId1 = 2L;
        final String staffName1 = "staff12345";
        final Integer staffAge1 = 30;

        final Long staffId2 = 3L;
        final String staffName2 = "staff123456";
        final Integer staffAge2 = 36;

        Staff staff1 = Staff.builder()
                .id(staffId1)
                .name(staffName1)
                .age(staffAge1)
                .build();

        Staff staff2 = Staff.builder()
                .id(staffId2)
                .name(staffName2)
                .age(staffAge2)
                .build();

        staffRepository.saveAll( Arrays.asList(staff1, staff2));

        Store store = Store.builder()
                .id(storeId)
                .name(storeName)
                .address(storeAddress)
                .build();

        //when
        Store saveStore = storeRepository.save(store);

        //then
        Assertions.assertEquals(saveStore.getName(), storeName);
    }
}
