package com.ygy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ygy.pojo.AddressBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AddressBookDao extends BaseMapper<AddressBook> {

    AddressBook selectByCityCodeAndIsDefault(@Param("addressBook") AddressBook addressBook);

    List<AddressBook> selectAllByUserId(@Param("addressBook") AddressBook addressBook);

    Integer updateIsDefault(@Param("addressBook") AddressBook addressBook);

    Integer updateAddress(@Param("addressBook") AddressBook addressBook);
}
