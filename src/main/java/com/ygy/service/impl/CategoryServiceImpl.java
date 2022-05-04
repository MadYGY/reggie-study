package com.ygy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ygy.dao.CategoryDao;
import com.ygy.pojo.Category;
import com.ygy.pojo.Employee;
import com.ygy.service.CategorySevice;
import com.ygy.util.RsaUtil;
import com.ygy.util.TsTransToDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

@Slf4j
@Service
public class CategoryServiceImpl implements CategorySevice {
    @Autowired
    public CategoryDao categoryDao;
    @Override
    public List<Object> selectByPage(int pageSize, int page) {
        List<Category> categories = categoryDao.selectByPage(pageSize, page);
        Integer count = categoryDao.count();
        List<Object> objects = new ArrayList<>();
        HashMap<String, Integer> map = new HashMap<>();
        map.put("count",count);
        objects.add(categories);
        objects.add(map);
        return objects;
    }

    @Override
    public boolean addCategory(Category category, HttpServletRequest request) throws Exception {
        String userid = (String) request.getSession().getAttribute("USERID");
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        userid = RsaUtil.decrypt(userid, RsaUtil.PRIVATE_KEY);
        category.setCreateUser(Long.parseLong(userid));
        category.setUpdateUser(Long.parseLong(userid));
        category.setUpdateTime(ts);
        category.setCreateTime(ts);
        int i = categoryDao.insert(category);
        return i>0;
    }

    @Override
    public boolean deleteById(Long id) {
        int i = categoryDao.deleteById(id);
        return i!=0;
    }

    @Override
    public boolean updateById(Category category) {
        int i = categoryDao.updateById(category);
        return i!=0;
    }
}
