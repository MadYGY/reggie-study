package com.ygy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ygy.pojo.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorDao extends BaseMapper<DishFlavor> {
    Integer save(DishFlavor dishFlavor);

    List<DishFlavor> selectByDishId(Long id);

    Integer update(DishFlavor dishFlavor);

}
