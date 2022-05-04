package com.ygy.controller;


import com.alibaba.fastjson.JSONException;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.ygy.dao.UserDao;
import com.ygy.pojo.User;
import com.ygy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;


    @RequestMapping("testTenccent" )
    @ResponseBody
    public void ta(String phone, String code) {
        // 短信应用 SDK AppID
        int appid = 1400673079; // SDK AppID 以1400开头
        // 短信应用 SDK AppKey
        String appkey = "c8c85342b8e800f26bbadc03a9084fe1";
        // 需要发送短信的手机号码
        String[] phoneNumbers = {phone};
        // 短信模板 ID，需要在短信应用中申请
        int templateId = 1389314; // NOTE: 这里的模板 ID`7839`只是示例，真实的模板 ID 需要在短信控制台中申请
        // 签名
        String smsSign = "吠呜小杨公众号"; // NOTE: 签名参数使用的是`签名内容`，而不是`签名ID`。这里的签名"腾讯云"只是示例，真实的签名需要在短信控制台申请

        try {
            String[] params = {code};
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers[0],
                    templateId, params, smsSign, "", "");
            System.out.println(result);
        } catch (HTTPException e) {
            // HTTP 响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // JSON 解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络 IO 错误
            e.printStackTrace();
        }
    }

    @PostMapping("/sendMsg")
    public Result sendMsg(@RequestBody User user, HttpSession session){
        String phone = user.getPhone();
        if(StringUtils.isNotEmpty(phone)){
            log.info(user.getPhone());
            Random random = new Random();
            int code = random.nextInt(999999-100000)+100000;
            session.setAttribute(phone, Integer.toString(code));
            ta(phone,Integer.toString(code));
            log.info(Integer.toString(code));
            return new Result(Code.SEND_CODE_OK, true);
        }

        return new Result(Code.SEND_CODE_ERROR, false);
    }
    @PostMapping("/login")
    public Result login(@RequestBody Map map, HttpSession session) {
        String phone = map.get("phone").toString();
        String codeInSession = (String) session.getAttribute(phone);
        String code = map.get("code").toString();
        if (codeInSession != null && codeInSession.equals(code)){
            User user = userDao.selectUserByPhone(phone);
            if(user==null){
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                int insert = userDao.insert(user);
            }
            session.setAttribute("CUSTOMERID",user.getId());
            return new Result(Code.SAVE_OK, true);
        }
        return new Result(Code.SAVE_ERROR, false, "登陆失败");
    }

}
