package com.ygy.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.ygy.dao.EmployeeDao;
import com.ygy.pojo.Employee;
import com.ygy.service.EmployeeService;
import com.ygy.util.RsaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/employee")
@ResponseBody
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeDao employeeDao;

    @PostMapping("/login")
    public Result login(@RequestBody Employee employee, HttpServletResponse response, HttpServletRequest request) throws Exception {
        Employee employee1 = employeeService.checkPassword(employee.getUsername(), employee.getPassword());
        if (employee1 != null) {
            String encodeId = RsaUtil.encrypt(Long.toString(employee1.getId()), RsaUtil.PUBLIC_KEY);
            Cookie cookie = new Cookie("USERID", encodeId);
            cookie.setMaxAge(60 * 60 * 24 * 7);
            cookie.setPath("/");
            response.addCookie(cookie);
            request.getSession().setAttribute("USERID", encodeId);
            return new Result(Code.LOGIN_SUCCESS, employee1);
        } else {
            return new Result(Code.LOGIN_FAILED, false, "登录失败, 用户名或密码错误");
        }

    }

    @PostMapping("/logout")
    public Result logout(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies=request.getCookies();
        if(cookies!=null){
            for(Cookie cookie:cookies){
                cookie.setMaxAge(0);
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
            }
        }
        return new Result(Code.LOGOUT_SUCCESS, true);
    }

    @PostMapping
    public Result save(@RequestBody Employee employee, HttpServletRequest request) throws Exception {
        boolean save = employeeService.save(employee, request);
        if(save){
            return new Result(Code.SAVE_OK, true);
        } else {
            return new Result(Code.SAVE_ERROR, false, "保存失败, 请稍后再试");
        }
    }

    /**
     * 分页功能加模糊查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result paginationAndLikeName(int page, int pageSize, String name){
        if(name==null){
            name="";
        }
        List<Employee> employees = employeeService.selectByPage(pageSize, pageSize*(page - 1), name);
        Integer count = employeeDao.count();
        return new Result(Code.GET_OK, employees, count);
    }

    @GetMapping("/{id}")
    public Result selectById(@PathVariable Long id){
        Employee employee = employeeDao.selectById(id);
        return new Result(Code.GET_OK, employee);
    }

    @PutMapping
    public Result updateById(@RequestBody Employee employee){
        boolean flag = employeeService.updateById(employee);
        if(flag){
            return new Result(Code.SAVE_OK, flag);

        } else {
            return new Result(Code.SAVE_ERROR, flag, "修改失败");
        }
    }
}

