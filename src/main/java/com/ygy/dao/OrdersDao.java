package com.ygy.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ygy.pojo.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrdersDao extends BaseMapper<Orders> {
    @Select("select count(*) from orders where user_id = #{userId}")
    Integer count(Long userId);
    @Select("select * from orders where user_id = #{userId} order by order_time DESC LIMIT #{pageSize} OFFSET #{page} ")
    List<Orders> selectByUserId(@Param("userId") Long userId, @Param("page") Integer page, @Param("pageSize") Integer pageSize);

    List<Orders> selectAll(Integer page, Integer pageSize, String beginTime, String endTime, Long id);

    @Select("select count(*) from orders")
    Integer allCount();
}
