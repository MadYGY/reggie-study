package com.ygy.dto;

import com.ygy.pojo.Dish;
import com.ygy.pojo.Setmeal;
import com.ygy.pojo.SetmealDish;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SetmealDto extends Setmeal {
    //套餐分类名称
    private String categoryName;

    private List<SetmealDish> setmealDishes;
}
