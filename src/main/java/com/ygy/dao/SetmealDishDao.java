package com.ygy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ygy.pojo.Setmeal;
import com.ygy.pojo.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishDao extends BaseMapper<SetmealDish> {
    List<SetmealDish> selectAllBySetmealId(Long id);

    int deleteBySetmealId(Long id);

    @Select("select * from setmeal where category_id = #{categoryId} and status = #{status};")
    List<SetmealDish> selectAllByCategoryIdAndStatus(@Param("categoryId")Long categoryId, @Param("status")Integer status);

}
