package com.ygy.controller;


import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ygy.dao.CategoryDao;
import com.ygy.dao.SetmealDao;
import com.ygy.dao.SetmealDishDao;
import com.ygy.dto.SetmealDto;
import com.ygy.pojo.Category;
import com.ygy.pojo.Dish;
import com.ygy.pojo.Setmeal;
import com.ygy.pojo.SetmealDish;
import com.ygy.service.SetmealDishService;
import com.ygy.service.SetmealService;
import com.ygy.util.RsaUtil;
import jdk.jfr.StackTrace;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/setmeal")
@ResponseBody
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private SetmealDishDao setmealDishDao;
    @Autowired
    private CategoryDao categoryDao;
    /**
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result page(int page, int pageSize, String name){
        if(name==null){
            name="";
        }
        List<SetmealDto> setmealDtos = new ArrayList<>();
        Integer count = setmealDao.count();
        List<Setmeal> setmeals = setmealDao.selectPageLikeName(pageSize, pageSize * (page - 1), name);
        for (int i = 0; i < setmeals.size(); i++) {
            SetmealDto setmealDto = new SetmealDto();
            Long categoryId = setmeals.get(i).getCategoryId();
            Category category = categoryDao.selectById(categoryId);
            String categoryName = category.getName();
            BeanUtils.copyProperties(setmeals.get(i),setmealDto);
            setmealDto.setCategoryName(categoryName);
            setmealDtos.add(setmealDto);
        }

        return new Result(Code.GET_OK, setmealDtos, count);
//        Page pageInfo = new Page(page, pageSize);
//
//        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper();
//        AbstractWrapper like = queryWrapper.like(StringUtils.isNotEmpty(name), Setmeal::getName, name);
//        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
//        setmealService.page(pageInfo,queryWrapper);
//        return new Result(Code.GET_OK, pageInfo);
    }

    /**
     * SetmealDto(
     * super=Setmeal(id=null, categoryId=1413342269393674242, name=test, price=108800.0, status=1, code=, description=test, image=b87deb62-dff1-4c12-9ae5-20822e216856.jpg, createTime=null, updateTime=null, createUser=null, updateUser=null, isDeleted=null),
     * categoryName=test,
     * setmealDishes=[
     * SetmealDish(id=null, setmealId=null, dishId=1397849739276890114, name=辣子鸡, price=7800.0, copies=1, sort=null, createTime=null, updateTime=null, createUser=null, updateUser=null, isDeleted=null),
     * SetmealDish(id=null, setmealId=null, dishId=1397852391150759938, name=辣子鸡丁, price=8800.0, copies=1, sort=null, createTime=null, updateTime=null, createUser=null, updateUser=null, isDeleted=null),
     * SetmealDish(id=null, setmealId=null, dishId=1397860242057375745, name=脆皮烧鹅, price=12800.0, copies=1, sort=null, createTime=null, updateTime=null, createUser=null, updateUser=null, isDeleted=null)
     * ])
     * @param setmealDto
     * @return
     */
    @Transactional
    @PostMapping
    public Result addSetmeal(@RequestBody SetmealDto setmealDto, HttpServletRequest request) throws Exception {
        boolean flag = true;
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        //获取时间戳
        Timestamp ts = new Timestamp(System.currentTimeMillis());

        //获取userId
        String encodeUserid = (String) request.getSession().getAttribute("USERID");
        Long userId = Long.parseLong(RsaUtil.decrypt(encodeUserid, RsaUtil.PRIVATE_KEY));

        //复制SermealDto到Setmeal
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDto, setmeal);
        //先保存Setmeal到数据库, 获取Setmeal的id, 也就是setmealId
        boolean setmealSave = setmealService.save(setmeal, userId, ts);
        flag = flag && setmealSave;
        //获取setmealId
        Long id = setmeal.getId();
        String setmealId = Long.toString(id);

        //保存setmealDishes到数据库
        for(SetmealDish sd:setmealDishes){
            boolean save = setmealDishService.save(sd, setmealId, userId, ts);
            flag = flag && save;
        }

        if(flag){
            return new Result(Code.SAVE_OK, flag);
        } else {
            return new Result(Code.SAVE_ERROR, flag, "保存失败, 请稍后再试");
        }


    }

    /**
     * 修改套餐的时候获取的原始套餐数据
     * @return 修改套餐的时候获取的原始套餐数据
     */
    @GetMapping("/{id}")
    public Result getSetmeal(@PathVariable String id) {
        Long longId = Long.parseLong(id);
        //获取Setmeal和SetmealDish
        Setmeal setmeal = setmealDao.selectById(longId);
        List<SetmealDish> setmealDishes = setmealDishDao.selectAllBySetmealId(longId);
        if(setmealDishes != null && setmeal != null){
            //new一个setmealDto返回给前端
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(setmeal, setmealDto);
            setmealDto.setSetmealDishes(setmealDishes);
            return new Result(Code.GET_OK, setmealDto);
        } else {
            return new Result(Code.GET_ERROR, false, "获取失败, 请稍后再试");
        }
    }
    @DeleteMapping
    public Result deleteById(@RequestParam("ids") String ids){
        Long longId = Long.parseLong(ids);
        int delete = setmealDao.deleteById(longId);
        int ishDelete = setmealDishDao.deleteBySetmealId(longId);
        if(delete!=0 && ishDelete!=0){
            return new Result(Code.DELETE_OK, true);
        }else{
            return new Result(Code.DELETE_ERROR, false, "删除失败, 请稍后再试");
        }
    }

    /**
     * Setmeal(id=1415580119015145474, categoryId=1413386191767675000, name=儿童套餐A计划, price=4000.0, status=1, code=, description=, image=61d20592-b37f-4d72-a864-07ad5bb8f3bb.jpg, createTime=2021-07-15 15:52:55.0, updateTime=null, createUser=1415576781934608400, updateUser=1415576781934608400, isDeleted=0)
     * SetmealDish(id=null, setmealId=null, dishId=1397862198033297410, name=老火靓汤, price=49800.0, copies=1, sort=null, createTime=null, updateTime=null, createUser=null, updateUser=null, isDeleted=null)
     */
    @Transactional
    @PutMapping
    public Result changeSetmeal(@RequestBody SetmealDto setmealDto, HttpServletRequest request) throws Exception {
        //获取时间戳
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        //获取userId
        String encodeUserid = (String) request.getSession().getAttribute("USERID");
        Long userId = Long.parseLong(RsaUtil.decrypt(encodeUserid, RsaUtil.PRIVATE_KEY));
        //获取setmealId
        Long setmealId = setmealDto.getId();

        //new List<SetmealDish> 和 setmeal
        List<SetmealDish> setmealDishes = new ArrayList<>();
        Setmeal setmeal = new Setmeal();
        //赋值
        setmealDishes = setmealDto.getSetmealDishes();
        BeanUtils.copyProperties(setmealDto, setmeal);
        boolean setmealFlag = setmealService.changeSetmeal(setmeal, userId, ts);
        boolean dishFlag = setmealDishService.changeSetmealDishBySetmealId(setmealDishes,setmealId,userId,ts);
        if(setmealFlag&&dishFlag){
            return new Result(Code.UPDATE_OK, setmealFlag&&dishFlag);
        } else {
            return new Result(Code.UPDATE_ERROR, setmealFlag&&dishFlag);
        }
    }

    @Transactional
    @PostMapping("/status/{status}")
    public Result batchStatus(@PathVariable int status, @RequestParam("ids") List<String> list ){
        List<Long> ids = new ArrayList<>();
        for(String l: list){
            ids.add(Long.parseLong(l));
        }
        boolean flag = setmealService.batchStatus(ids, status);
        if(flag){
            return new Result(Code.UPDATE_OK, flag);
        } else {
            return new Result(Code.UPDATE_ERROR, flag,"修改失败, 请稍后再试");
        }
    }
    @GetMapping("/list")
    public Result list(String categoryId, String status){
        if(categoryId!=null && status != null ){
            Long id = Long.parseLong(categoryId);
            Integer intStatus = Integer.parseInt(status);
            List<Dish> dishes = setmealDao.selectByCategoryIdAndStatus(id, intStatus);
            if(dishes!=null){
                return new Result(Code.GET_OK, dishes);
            }
            return new Result(Code.GET_ERROR, false, "获取失败");
        }
        return new Result(Code.GET_ERROR, false, "获取失败");
    }


}
