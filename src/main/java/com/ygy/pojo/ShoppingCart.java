package com.ygy.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class ShoppingCart {

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long id;
  private String name;
  private String image;
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long userId;
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long dishId;
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long setmealId;
  private String dishFlavor;
  private Integer number;
  private Double amount;
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private java.sql.Timestamp createTime;

}
