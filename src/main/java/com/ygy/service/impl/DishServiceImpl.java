package com.ygy.service.impl;

import com.ygy.dao.DishDao;
import com.ygy.dto.DishDto;
import com.ygy.pojo.Category;
import com.ygy.pojo.Dish;
import com.ygy.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishDao dishDao;



    public boolean batchStatus(List ids, Integer status){
        Integer i = dishDao.batchStatus(ids, status);
        return i!=0;
    }

    public boolean batchDelete(List ids){
        int i = dishDao.deleteBatchIds(ids);
        return i!=0;
    }

    public boolean save(DishDto dishDto, Long userid) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        dishDto.setCode("1");
        dishDto.setSort(1);
        dishDto.setCreateTime(ts);
        dishDto.setUpdateTime(ts);
        dishDto.setCreateUser(userid);
        dishDto.setUpdateUser(userid);
        dishDto.setIsDeleted(0);
        Integer i = dishDao.save(dishDto);
        return i!=0;
    }

    @Override
    public boolean update(DishDto dishDto, Long userid) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        dishDto.setUpdateUser(userid);
        dishDto.setUpdateTime(ts);
        Integer i = dishDao.update(dishDto);
        return i!=0;
    }
}
