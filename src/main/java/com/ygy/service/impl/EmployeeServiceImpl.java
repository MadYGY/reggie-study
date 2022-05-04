package com.ygy.service.impl;

import com.ygy.controller.Code;
import com.ygy.dao.EmployeeDao;
import com.ygy.pojo.Employee;
import com.ygy.service.EmployeeService;
import com.ygy.util.MD5Util;
import com.ygy.util.RsaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.List;

import static com.ygy.util.MD5Util.generateMD5;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    public EmployeeDao employeeDao;
    @Override
    public Employee checkPassword(String username, String password) throws NoSuchAlgorithmException {
        Employee employee = employeeDao.selectByUsername(username);
        try{
            if(employee == null){
                return null;
            } else if(employee.getStatus() == 0){
                return employee;
            }
        } catch (Exception e){
            log.info(String.valueOf(e));
        }

        String passwordMD5 = generateMD5(password);
        if(passwordMD5.equals(employee.getPassword())){
            return employee;
        } else {
            return null;
        }
    }

    @Override
    public boolean save(Employee employee, HttpServletRequest request) throws Exception {
        String userid = (String) request.getSession().getAttribute("USERID");
        userid = RsaUtil.decrypt(userid, RsaUtil.PRIVATE_KEY);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String passwordMD5 = MD5Util.generateMD5("123456");
        employee.setPassword(passwordMD5);
        employee.setStatus(1);
        employee.setCreateTime(ts);
        employee.setUpdateTime(ts);
        employee.setUpdateUser(Long.parseLong(userid));
        employee.setCreateUser(Long.parseLong(userid));
        int insert = employeeDao.insert(employee);
        return insert != 0;
    }

    @Override
    public List<Employee> selectByPage(int i, int page, String name) {
        List<Employee> employees = employeeDao.selectByPage(i, page, name);
        for (int j = 0; j < employees.size(); j++) {
            if("admin".equals(employees.get(j).getUsername())){
                employees.remove(j);
                return employees;
            }
        }
        return employees;
    }

    @Override
    public boolean updateById(Employee employee) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        employee.setUpdateTime(ts);
        int i = employeeDao.updateById(employee);
        return i!=0;
    }


}
