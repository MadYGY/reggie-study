package com.ygy.controller;

import com.ygy.pojo.Orders;
import com.ygy.service.OrdersService;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@ResponseBody
@RequestMapping("/order")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;
    @PostMapping("/submit")
    public Result submit(@RequestBody Orders orders, HttpSession session){
        Long customerid = (Long) session.getAttribute("CUSTOMERID");
        orders.setUserId(customerid);
        return ordersService.submit(orders);
    }

    @GetMapping("/userPage")
    public Result page(int page, int pageSize, HttpSession session){
        Long customerid = (Long) session.getAttribute("CUSTOMERID");
        return ordersService.page(page, pageSize, session);
    }

    /**
     * 后端管理请求所有订单的页面
     * @param page
     * @param pageSize
     * @param session
     * @return
     */
    @GetMapping("/page")
    public Result getAllOrder(int page, int pageSize, Long number, String beginTime, String endTime, HttpSession session){
        Long customerid = (Long) session.getAttribute("CUSTOMERID");
        String beginTimeDecode = null;
        String endTimeDecode = null;
        if(beginTime!=null && endTime!=null){
            beginTimeDecode = URLDecoder.decode(beginTime, StandardCharsets.UTF_8);
            endTimeDecode = URLDecoder.decode(endTime, StandardCharsets.UTF_8);
        }

        return ordersService.getAllOrder(page,pageSize,number,beginTimeDecode,endTimeDecode,session);
    }
}
