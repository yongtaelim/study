package net.newploy.payroll.batch.mapper;

import net.newploy.payroll.batch.entity.People;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PeopleMapper {
    List<People> selectById(Long storeId);
}
