package com.ygy.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class SetmealDish {
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long id;
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private String setmealId;
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private String dishId;
  private String name;
  private Double price;
  private Integer copies;
  private Integer sort;
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private java.sql.Timestamp createTime;
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private java.sql.Timestamp updateTime;
  private Long createUser;
  private Long updateUser;
  private Integer isDeleted;


}
