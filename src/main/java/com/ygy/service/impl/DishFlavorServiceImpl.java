package com.ygy.service.impl;

import com.ygy.dao.DishFlavorDao;
import com.ygy.pojo.DishFlavor;
import com.ygy.service.DishFlavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;


@Service
public class DishFlavorServiceImpl implements DishFlavorService {
    @Autowired
    private DishFlavorDao dishFlavorDao;
    @Override
    public boolean save(List<DishFlavor> flavors, Long userid, Long dishId) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        for(DishFlavor flavor:flavors){
            flavor.setCreateUser(userid);
            flavor.setUpdateUser(userid);
            flavor.setDishId(dishId);
            flavor.setIsDeleted(0);
            flavor.setCreateTime(ts);
            flavor.setUpdateTime(ts);
            dishFlavorDao.save(flavor);
        }
        return true;
    }

    @Override
    public List<DishFlavor> selectByDishId(Long id) {
        List<DishFlavor> flavors = dishFlavorDao.selectByDishId(id);
        return flavors;
    }

    @Override
    public boolean update(List<DishFlavor> newFlavors, Long userid, Long dishId) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        List<DishFlavor> oldFlavors = dishFlavorDao.selectByDishId(dishId);
        Iterator<DishFlavor> oldIterator = oldFlavors.iterator();
        while (oldIterator.hasNext()){
            DishFlavor i = oldIterator.next();
            Iterator<DishFlavor> newIterator = newFlavors.iterator();
            while (newIterator.hasNext()){
                DishFlavor j = newIterator.next();
                if(j.getName().equals(i.getName())){
                    i.setValue(j.getValue());
                    i.setIsDeleted(0);
                    i.setUpdateTime(ts);
                    i.setUpdateUser(userid);
                    dishFlavorDao.update(i);
                    newIterator.remove();
                    oldIterator.remove();
                }
            }
        }
        for(DishFlavor flavor:oldFlavors){
            flavor.setIsDeleted(1);
            flavor.setUpdateTime(ts);
            flavor.setUpdateUser(userid);
            dishFlavorDao.update(flavor);
        }

        for(DishFlavor flavor: newFlavors){
            flavor.setCreateUser(userid);
            flavor.setUpdateUser(userid);
            flavor.setDishId(dishId);
            flavor.setIsDeleted(0);
            flavor.setCreateTime(ts);
            flavor.setUpdateTime(ts);
            dishFlavorDao.save(flavor);
        }


        return true;
    }


}
