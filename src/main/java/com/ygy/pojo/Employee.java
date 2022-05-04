package com.ygy.pojo;


import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class Employee {
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long id;
  private String name;
  private String username;
  @TableField(select = false)
  private String password;
  private String phone;
  private String sex;
  private String idNumber;
  private Integer status;
  private java.sql.Timestamp createTime;
  private java.sql.Timestamp updateTime;
  private Long createUser;
  private Long updateUser;


}
