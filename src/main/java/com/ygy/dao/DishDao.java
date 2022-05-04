package com.ygy.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ygy.dto.DishDto;
import com.ygy.pojo.Category;
import com.ygy.pojo.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishDao extends BaseMapper<Dish> {
    public List<Dish> selectByPage(@Param("pageSize") Integer pageSize, @Param("page")Integer page, @Param("name") String name);

    public Integer count();

    public String getTypeById(Long id);

    public Integer batchStatus(@Param("list") List ids, @Param("status") Integer status);

    public Integer save(DishDto dishDto);

    public Integer update(DishDto dishDto);

    public List<Dish> selectByCategoryId(Long categoryId);

    @Select("select * from dish where category_id = #{categoryId} and status = #{status};")
    public List<Dish> selectByCategoryIdAndStatus(@Param("categoryId")Long categoryId, @Param("status")Integer status);
}
