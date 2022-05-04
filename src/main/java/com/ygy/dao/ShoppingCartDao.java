package com.ygy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ygy.pojo.ShoppingCart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShoppingCartDao extends BaseMapper<ShoppingCart> {
    @Select("select * from shopping_cart where user_id = #{userId}")
    List<ShoppingCart> selectByUserId(Long userId);

    @Update("update shopping_cart set number=number+1 where dish_id = #{dishId} and user_id = #{userId}")
    Integer updateByDishID(@Param("userId")Long userId, @Param("dishId")Long dishId);

    @Update("update shopping_cart set number=number+1 where setmeal_id = #{setmealId} and user_id = #{userId}")
    Integer updateBySetmealId(@Param("userId")Long userId, @Param("setmealId")Long setmealId);

    ShoppingCart selectByDishIdOrSetmealId(@Param("userId")Long userId, @Param("dishId")Long dishId, @Param("flavor")String flavor, @Param("setmealId")Long setmealId);

    @Select("select * from shopping_cart where setmeal_id = #{setmealId} and user_id = #{userId}")
    ShoppingCart selectBySetmealId(@Param("userId")Long userId, @Param("setmealId")Long setmealId);

    Integer subNumberByDishIdOrSetmealId(@Param("cart")ShoppingCart cart);

    ShoppingCart selectByDishIdOrSetmealId2(@Param("cart")ShoppingCart cart);

    Integer deleteByByDishIdOrSetmealId(@Param("cart")ShoppingCart cart);
    @Delete("delete from shopping_cart where user_id = #{userId}")
    Integer deleteByUserId(@Param("userId") Long userId);
}
