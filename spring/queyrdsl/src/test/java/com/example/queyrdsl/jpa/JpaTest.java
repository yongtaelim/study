package com.example.queyrdsl.jpa;

import com.example.queyrdsl.entity.Staff;
import com.example.queyrdsl.entity.Store;
import com.example.queyrdsl.repository.StoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

@ActiveProfiles("local")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JpaTest {

    @Autowired
    private StoreRepository storeRepository;

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

    @Test
    @DisplayName("Store, Staff entity 저장")
    void entity저장() {
        //given
        final Long storeId = 2L;
        final String storeName = "store1234";
        final String storeAddress = "storeAddress";

        final Long staffId = 1L;
        final String staffName = "staff1234";
        final Integer staffAge = 30;

        Staff staff = Staff.builder()
                .id(staffId)
                .name(staffName)
                .age(staffAge)
                .build();

        Store store = Store.builder()
                .id(storeId)
                .name(storeName)
                .address(storeAddress)
                .staff(Arrays.asList(staff))
                .build();

        //when
        storeRepository.save(store);
        Store resultStore = storeRepository.findByName(storeName);

        //then
        Assertions.assertEquals(resultStore.getName(), storeName);

        Collection<Staff> staff1 = resultStore.getStaff();
        Iterator<Staff> iterator = staff1.iterator();
        Staff next = iterator.next();
        Assertions.assertEquals(next.getName(), staffName);
    }
}
