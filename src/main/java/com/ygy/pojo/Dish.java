package com.ygy.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class Dish {
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long id;
  private String name;
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long categoryId;
  private Double price;
  private String code;
  private String image;
  private String description;
  private Integer status;
  private Integer sort;
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private java.sql.Timestamp createTime;
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private java.sql.Timestamp updateTime;
  private Long createUser;
  private Long updateUser;
  private Integer isDeleted;

}
