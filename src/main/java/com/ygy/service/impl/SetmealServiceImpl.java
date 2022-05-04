package com.ygy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ygy.dao.SetmealDao;
import com.ygy.pojo.Setmeal;
import com.ygy.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealDao, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;
    public boolean save(Setmeal setmeal, Long userId, Timestamp ts){
        setmeal.setCode(null);
        setmeal.setIsDeleted(0);
        setmeal.setCreateTime(ts);
        setmeal.setUpdateTime(ts);
        setmeal.setUpdateUser(userId);
        setmeal.setCreateUser(userId);
        int insert = setmealDao.insert(setmeal);
        return insert!=0;
    }

    public boolean changeSetmeal(Setmeal setmeal, Long userId, Timestamp ts){
        setmeal.setUpdateTime(ts);
        setmeal.setUpdateUser(userId);
        int update = setmealDao.updateById(setmeal);
        return update!=0;
    }

    public boolean batchStatus(List ids, Integer status){
        Integer i = setmealDao.batchStatus(ids, status);
        return i!=0;
    }
}
