package com.ygy.service.impl;

import com.ygy.dao.SetmealDishDao;
import com.ygy.pojo.DishFlavor;
import com.ygy.pojo.SetmealDish;
import com.ygy.service.SetmealDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

@Service
public class SetmealDishServiceImpl implements SetmealDishService {
    @Autowired
    private SetmealDishDao setmealDishDao;
    public boolean save(SetmealDish setmealDish, String setmealId, Long userId, Timestamp ts) {
        setmealDish.setSetmealId(setmealId);
        setmealDish.setSort(1);
        setmealDish.setCreateTime(ts);
        setmealDish.setUpdateTime(ts);
        setmealDish.setCreateUser(userId);
        setmealDish.setUpdateUser(userId);
        setmealDish.setIsDeleted(0);
        int insert = setmealDishDao.insert(setmealDish);
        return insert!=0;
    }

    public boolean changeSetmealDishBySetmealId(List<SetmealDish> setmealDishes, Long setmealId, Long userId, Timestamp ts){

        List<SetmealDish> oldList = setmealDishDao.selectAllBySetmealId(setmealId);
        Iterator<SetmealDish> oldIterator = oldList.iterator();
        while (oldIterator.hasNext()){
            SetmealDish i = oldIterator.next();
            Iterator<SetmealDish> newIterator = setmealDishes.iterator();
            while (newIterator.hasNext()){
                SetmealDish j = newIterator.next();
                if(j.getName().equals(i.getName())){
                    i.setIsDeleted(0);
                    i.setUpdateTime(ts);
                    i.setUpdateUser(userId);
                    setmealDishDao.updateById(i);
                    newIterator.remove();
                    oldIterator.remove();
                }
            }
        }
        boolean flag = true;

        for(SetmealDish setmealDish:oldList){
            int i = setmealDishDao.deleteById(setmealDish.getId());
            boolean flag1 = i != 0;
            flag = flag && flag1;
        }

        for(SetmealDish setmealDish:setmealDishes){
            setmealDish.setSetmealId(Long.toString(setmealId));
            setmealDish.setSort(1);
            setmealDish.setCreateTime(ts);
            setmealDish.setUpdateTime(ts);
            setmealDish.setCreateUser(userId);
            setmealDish.setUpdateUser(userId);
            setmealDish.setIsDeleted(0);
            int i = setmealDishDao.insert(setmealDish);
            boolean flag1 = i != 0;
            flag = flag && flag1;
        }
        return flag;
    }



}
