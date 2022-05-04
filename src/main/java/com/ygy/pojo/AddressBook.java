package com.ygy.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class AddressBook {
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long id;
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long userId;
  private String consignee;
  private Integer sex;
  private String phone;
  private String provinceCode;
  private String provinceName;
  private String cityCode;
  private String cityName;
  private String districtCode;
  private String districtName;
  private String detail;
  private String label;
  private Integer isDefault;
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private java.sql.Timestamp createTime;
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private java.sql.Timestamp updateTime;
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long createUser;
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long updateUser;
  private Integer isDeleted;

}
