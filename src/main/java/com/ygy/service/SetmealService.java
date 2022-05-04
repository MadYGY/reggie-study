package com.ygy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ygy.pojo.Setmeal;

import java.sql.Timestamp;
import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    boolean save(Setmeal setmeal, Long userId, Timestamp ts);
    boolean changeSetmeal(Setmeal setmeal, Long userId, Timestamp ts);
    boolean batchStatus(List ids, Integer status);
}
