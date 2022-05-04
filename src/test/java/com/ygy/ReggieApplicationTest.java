package com.ygy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ygy.controller.CommonController;
import com.ygy.controller.EmployeeController;
import com.ygy.dao.CategoryDao;
import com.ygy.dao.DishFlavorDao;
import com.ygy.dao.EmployeeDao;
import com.ygy.dao.ShoppingCartDao;
import com.ygy.pojo.*;
import com.ygy.service.CategorySevice;
import com.ygy.service.DishService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static com.ygy.util.MD5Util.generateMD5;

@SpringBootTest
public class ReggieApplicationTest {
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private ShoppingCartDao shoppingCartDao;
    @Test
    public void test() throws IOException {

    }
}
