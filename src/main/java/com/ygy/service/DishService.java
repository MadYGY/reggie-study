package com.ygy.service;

import com.ygy.dto.DishDto;
import com.ygy.pojo.Dish;

import java.util.List;

public interface DishService {

    public boolean batchStatus(List ids, Integer status);

    public boolean batchDelete(List ids);

    public boolean save(DishDto dishDto, Long userid);

    public boolean update(DishDto dishDto, Long userid);
}
