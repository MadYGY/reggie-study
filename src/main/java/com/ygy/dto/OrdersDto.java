package com.ygy.dto;

import com.ygy.pojo.OrderDetail;
import com.ygy.pojo.Orders;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OrdersDto extends Orders {
    List<OrderDetail> orderDetails;
}
