package com.ygy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ygy.pojo.Dish;
import com.ygy.pojo.DishFlavor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DishDto extends Dish {

    List<DishFlavor> flavors;

    private String categoryName;

    //在Setmeal中要用的份数
    private Integer copies;
}
