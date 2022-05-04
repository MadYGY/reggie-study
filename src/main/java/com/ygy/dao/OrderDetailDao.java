package com.ygy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ygy.pojo.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderDetailDao extends BaseMapper<OrderDetail> {
    @Select("select * from order_detail where order_id = #{orderId}")
    List<OrderDetail> selectByorderId(Long orderId);
}
