package com.ygy.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ygy.dao.CategoryDao;
import com.ygy.dao.DishDao;
import com.ygy.dao.DishFlavorDao;
import com.ygy.dto.DishDto;
import com.ygy.dto.SetmealDto;
import com.ygy.pojo.Category;
import com.ygy.pojo.Dish;
import com.ygy.pojo.DishFlavor;
import com.ygy.service.DishFlavorService;
import com.ygy.service.DishService;
import com.ygy.util.RsaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dish")
@ResponseBody
public class DIshController {

    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private DishFlavorDao dishFlavorDao;
    @Autowired
    private DishService dishService;
    @Autowired
    private DishDao dishDao;

    @Autowired
    private CategoryDao categoryDao;
    @GetMapping("/page")
    public Result paginationAndLikeName(int pageSize, int page, String name){
        if(name==null){
            name="";
        }
        List<DishDto> dishDtos = new ArrayList<>();
        Integer count = dishDao.count();
        List<Dish> dishes = dishDao.selectByPage(pageSize, pageSize * (page - 1), name);

        for (int i = 0; i < dishes.size(); i++) {
            List<DishFlavor> dishFlavors = new ArrayList<>();
            Long categoryId = dishes.get(i).getCategoryId();
            Category category = categoryDao.selectById(categoryId);

            // new 一个Dishdto
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dishes.get(i), dishDto);
            dishDto.setCategoryName(category.getName());

            dishDtos.add(dishDto);
        }
        return new Result(Code.GET_OK,dishDtos,count);


//        List<Object> objects = dishService.selectByPage(pageSize, pageSize * (page - 1));
//        return new Result(Code.GET_OK, objects);
    }

    @PostMapping("/status/{status}")
    public Result batchStatus(@PathVariable int status, @RequestParam("ids") List<String> list){
        List<Long> ids = new ArrayList<>();
        for(String l: list){
            ids.add(Long.parseLong(l));
        }
        boolean flag = dishService.batchStatus(ids, status);
        if(flag){
            return new Result(Code.UPDATE_OK, flag);
        } else {
            return new Result(Code.UPDATE_ERROR, flag,"修改失败, 请稍后再试");
        }
    }

    @DeleteMapping
    public Result batchDelete(@RequestParam("ids") List<String> list){
        List<Long> ids = new ArrayList<>();
        for(String l: list){
            ids.add(Long.parseLong(l));
        }
        boolean flag = dishService.batchDelete(ids);

        if(flag){
            return new Result(Code.DELETE_OK, flag);
        } else {
            return new Result(Code.DELETE_ERROR, flag,"删除失败, 请稍后再试");
        }
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable String id){
        Long dishId = Long.parseLong(id);
        Dish dish = dishDao.selectById(dishId);
        List<DishFlavor> flavors = dishFlavorService.selectByDishId(dishId);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        dishDto.setFlavors(flavors);
        if (dish != null){
            return new Result(Code.GET_OK, dishDto);
        } else {
            return new Result(Code.GET_ERROR, false, "获取菜品信息失败, 请稍后再试");
        }
    }



    @Transactional
    @PostMapping
    public Result save(@RequestBody DishDto dishDto, HttpServletRequest request) throws Exception {
        String encodeUserid = (String) request.getSession().getAttribute("USERID");
        Long userid = Long.parseLong(RsaUtil.decrypt(encodeUserid, RsaUtil.PRIVATE_KEY));
        List<DishFlavor> flavors = dishDto.getFlavors();
        dishService.save(dishDto, userid);
        dishFlavorService.save(flavors, userid, dishDto.getId());
        return new Result(Code.SAVE_OK, true, "保存成功");

    }
    @Transactional
    @PutMapping
    public Result update(@RequestBody DishDto dishDto, HttpServletRequest request) throws Exception {
        String encodeUserid = (String) request.getSession().getAttribute("USERID");
        Long userid = Long.parseLong(RsaUtil.decrypt(encodeUserid, RsaUtil.PRIVATE_KEY));
        List<DishFlavor> flavors = dishDto.getFlavors();
        dishService.update(dishDto, userid);
        dishFlavorService.update(flavors, userid, dishDto.getId());
        return new Result(Code.UPDATE_OK, true, "保存成功");
    }

    @GetMapping("/list")
    public Result getByCategoryId(String categoryId, String status){
        Long cId = Long.parseLong(categoryId);
        if(status==null){
            List<Dish> dishes = dishDao.selectByCategoryId(cId);
            return new Result(Code.GET_OK, dishes);
        } else {
            Integer intStatus = Integer.parseInt(status);
            List<Dish> dishes = dishDao.selectByCategoryIdAndStatus(cId, intStatus);
            List<DishDto> dishDtos = new ArrayList<>();
            for (int i = 0; i < dishes.size(); i++) {
                DishDto dishDto = new DishDto();
                Long dishId = dishes.get(i).getId();
                List<DishFlavor> dishFlavors = dishFlavorDao.selectByDishId(dishId);
                BeanUtils.copyProperties(dishes.get(i), dishDto);
                dishDto.setFlavors(dishFlavors);
                dishDtos.add(dishDto);
            }
            return new Result(Code.GET_OK, dishDtos);
        }

    }
}
