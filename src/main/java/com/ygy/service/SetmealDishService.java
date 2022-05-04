package com.ygy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ygy.pojo.Setmeal;
import com.ygy.pojo.SetmealDish;

import java.sql.Timestamp;
import java.util.List;

public interface SetmealDishService {

    boolean save(SetmealDish setmealDish, String setmealId, Long userId, Timestamp ts);

    boolean changeSetmealDishBySetmealId(List<SetmealDish> setmealDishes, Long setmealId, Long userId, Timestamp ts);


}
