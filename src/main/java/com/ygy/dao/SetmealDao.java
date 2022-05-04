package com.ygy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ygy.pojo.Dish;
import com.ygy.pojo.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDao extends BaseMapper<Setmeal> {
    @Select("select count(*) from setmeal;")
    Integer count();

    List<Setmeal> selectPageLikeName(@Param("pageSize")Integer pageSize, @Param("page") Integer page, @Param("name")String name);

    public Integer batchStatus(@Param("list") List ids, @Param("status") Integer status);

    @Select("select * from setmeal where category_id = #{categoryId} and status = #{status};")
    public List<Dish> selectByCategoryIdAndStatus(@Param("categoryId")Long categoryId, @Param("status")Integer status);
}
