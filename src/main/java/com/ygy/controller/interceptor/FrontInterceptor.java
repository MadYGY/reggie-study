package com.ygy.controller.interceptor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@Slf4j
@Component
public class FrontInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Enumeration<String> attributeNames = session.getAttributeNames();
        boolean isCustomerIdExist = false;
        //检测session中是否有CUSTOMERID, 如果没有就强制跳转到登陆页面
        while (attributeNames.hasMoreElements()){
            if("CUSTOMERID".equals(attributeNames.nextElement())){
                isCustomerIdExist = true;
                break;
            }
        }
        if(isCustomerIdExist){
            return true;
        } else{
            response.setStatus(302);
            response.setHeader("location", "/front/page/login.html");
            return true;
        }

    }
}
