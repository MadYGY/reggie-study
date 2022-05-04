package com.ygy.service.impl;

import com.ygy.controller.Result;
import com.ygy.dao.AddressBookDao;
import com.ygy.dao.OrderDetailDao;
import com.ygy.dao.OrdersDao;
import com.ygy.dao.ShoppingCartDao;
import com.ygy.dto.OrdersDto;
import com.ygy.pojo.AddressBook;
import com.ygy.pojo.OrderDetail;
import com.ygy.pojo.Orders;
import com.ygy.pojo.ShoppingCart;
import com.ygy.service.OrdersService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private ShoppingCartDao shoppingCartDao;
    @Autowired
    private AddressBookDao addressBookDao;
    @Autowired
    private OrdersDao ordersDao;
    @Autowired
    private OrderDetailDao orderDetailDao;
    @Override
    public Result submit(Orders orders) {
        int insertNumber=0; // 插入mysql的条数, 用来判断是否insert成功
        Long userId =orders.getUserId();
        List<ShoppingCart> shoppingCarts = shoppingCartDao.selectByUserId(userId);
        Double amounts=0D;
        for (ShoppingCart cart:shoppingCarts){
            Integer number = cart.getNumber();
            Double amount = cart.getAmount();
            Double money = amount * number;
            amounts = amounts + money;
        }
        orders.setAmount(amounts*100);
        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = addressBookDao.selectById(addressBookId);
        //获取下单的手机号
        String phone = addressBook.getPhone();
        orders.setPhone(phone);
        //获取下单的详细地址
        String detail = addressBook.getDetail();
        orders.setAddress(detail);
        //获取用户姓名
        String name = addressBook.getConsignee();
        orders.setConsignee(name);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        orders.setOrderTime(ts);
        orders.setCheckoutTime(ts);
        insertNumber += ordersDao.insert(orders);
        //获取订单ID
        Long ordersId = orders.getId();

        for (ShoppingCart cart:shoppingCarts){
            OrderDetail orderDetail = new OrderDetail();
            //获取订单ID
            orderDetail.setOrderId(ordersId);
            //获取菜品名称
            name = cart.getName();
            orderDetail.setName(name);
            //获取菜品图片
            String image = cart.getImage();
            orderDetail.setImage(image);
            //获取菜品单价
            Double amount = cart.getAmount();
            orderDetail.setAmount(amount);
            //获取菜品下单数量
            Integer number = cart.getNumber();
            orderDetail.setNumber(number);
            //获取菜品口味
            String dishFlavor = cart.getDishFlavor();
            orderDetail.setDishFlavor(dishFlavor);
            //获取setmeal或dish的id
            Long setmealId = cart.getSetmealId();
            if(setmealId!=null){
                orderDetail.setSetmealId(setmealId);
            } else{
                Long dishId = cart.getDishId();
                orderDetail.setDishId(dishId);
            }
            insertNumber += orderDetailDao.insert(orderDetail);

        }

        if(insertNumber>=2){
            //删除购物车里的内容
            shoppingCartDao.deleteByUserId(userId);
            return new Result(1, true);
        }
        return new Result(0, false);
    }

    @Override
    public Result page(int page, int pageSize, HttpSession session) {
        Long userId = (Long) session.getAttribute("CUSTOMERID");

        Integer count = ordersDao.count(userId);
        List<Orders> orders = ordersDao.selectByUserId(userId, pageSize*(page - 1), pageSize);

        Map<String, List> map = new HashMap<>();
        List<OrdersDto> ordersDtoList = new ArrayList<>();
        //循环获取ordersID, 再在循环里一起获取orderDetai
        for(Orders order: orders){

            OrdersDto ordersDto = new OrdersDto();
            BeanUtils.copyProperties(order, ordersDto);
            Long orderId = order.getId();
            //构造一个OrderDetail的集合
            List<OrderDetail> orderDetails = orderDetailDao.selectByorderId(orderId);
            ordersDto.setOrderDetails(orderDetails);
            ordersDtoList.add(ordersDto);

        }
        map.put("records", ordersDtoList);
        //将OrderDetail集合赋值给ordersDto传给前端
        if(orders!=null && count!=null){
            return new Result(1, map, count);
        }
        return new Result(0, false, "查询失败");
    }

    @Override
    public Result getAllOrder(int page, int pageSize, Long number, String beginTime, String endTime, HttpSession session) {
        List<Orders> orders = ordersDao.selectAll(pageSize * (page - 1), pageSize, beginTime, endTime, number);
        Map<String,List> map = new HashMap<>();
        map.put("records", orders);
        Integer count = ordersDao.allCount();
        if(orders!=null){
            return new Result(1, map, count);
        }
        return new Result(0, false, "获取失败");
    }
}
