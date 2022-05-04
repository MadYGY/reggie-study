package com.ygy.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class User {

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long id;
  private String name;
  private String phone;
  private String sex;
  private String idNumber;
  private String avatar;
  private Integer status;



}
