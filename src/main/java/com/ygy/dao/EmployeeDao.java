package com.ygy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ygy.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeDao extends BaseMapper<Employee>{
    @Select("select * from employee where username = #{username};")
    public Employee selectByUsername(String username);

    @Select("select * from employee;")
    public List<Employee> selectAll();

    @Select("select count(*) from employee;")
    public Integer count();

    public List<Employee> selectByPage(@Param("i") Integer i, @Param("page")Integer page, @Param("name")String name);


    public int updateById(Employee employee);

    @Select("select * from employee where name = #{name};")
    public Employee selectByName(String name);

}
