package com.ygy.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class Orders {
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long id;
  private String number;
  private Integer status;
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long userId;
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long addressBookId;
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private java.sql.Timestamp orderTime;
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private java.sql.Timestamp checkoutTime;
  private Integer payMethod;
  private Double amount;
  private String remark;
  private String phone;
  private String address;
  private String userName;
  private String consignee;


}
