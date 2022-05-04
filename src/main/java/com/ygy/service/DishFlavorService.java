package com.ygy.service;

import com.ygy.pojo.DishFlavor;

import java.util.List;

public interface DishFlavorService {
    boolean save(List<DishFlavor> flavors, Long userid, Long dishId);

    List<DishFlavor> selectByDishId(Long id);

    boolean update(List<DishFlavor> flavors, Long userid, Long dishId);
}
