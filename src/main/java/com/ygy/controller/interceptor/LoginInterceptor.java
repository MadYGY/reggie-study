package com.ygy.controller.interceptor;

import com.ygy.dao.EmployeeDao;
import com.ygy.pojo.Employee;
import com.ygy.util.RsaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private EmployeeDao employeeDao;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getServletPath();
        Cookie[] cookies = request.getCookies();
        String sessionUserId = (String) request.getSession().getAttribute("USERID");
        log.info("session user id: "+sessionUserId);
        if(cookies == null){
            if (path.equals("/backend/page/login/login.html")){
                return true;
            }
            response.setStatus(302);
            response.setHeader("location", "/backend/page/login/login.html");
            return true;
        } else{
            int i;
            for (i = 0; i < cookies.length; i++) {
                String value = cookies[i].getValue();
                log.info("cookie user id: "+value);
                if(value.equals(sessionUserId)){
                    if (path.equals("/backend/page/login/login.html")){
                        log.info("cookie和session都存在, 检测到登陆页面, 跳转到index");
                        response.setStatus(302);
                        response.setHeader("location", "/backend/index.html");
                    }
                    return true;
                }
            }
            //循环次数判断, 如果遍历完数组仍没有找到cookie和session, 就强制重新登陆
            if (i == cookies.length){
                if (path.equals("/backend/page/login/login.html")){
                    return true;
                }
                response.setStatus(302);
                response.setHeader("location", "/backend/page/login/login.html");
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
