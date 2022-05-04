package com.ygy.service;

import com.ygy.controller.Result;
import com.ygy.pojo.Orders;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;

public interface OrdersService {
    Result submit(@RequestBody Orders orders);

    Result page(int page, int pageSize, HttpSession session);

    Result getAllOrder(int page, int pageSize, Long number, String beginTime, String endTime, HttpSession session);
}
