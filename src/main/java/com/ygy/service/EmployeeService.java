package com.ygy.service;

import com.ygy.pojo.Employee;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public interface EmployeeService {
    Employee checkPassword(String username, String password) throws NoSuchAlgorithmException;

    boolean save(Employee employee, HttpServletRequest request) throws Exception;

    List<Employee> selectByPage(int i, int page, String name);

    boolean updateById(Employee employee);
}
