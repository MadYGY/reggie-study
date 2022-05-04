package com.ygy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ygy.dto.DishDto;
import com.ygy.pojo.Category;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryDao extends BaseMapper<Category> {
    public List<Category> selectByPage(@Param("pageSize") Integer pageSize, @Param("page")Integer page);

    public Integer count();

    public List<Category> getAllNameByType(@Param("type") Integer type);


}
