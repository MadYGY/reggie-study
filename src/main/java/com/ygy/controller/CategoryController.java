package com.ygy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ygy.dao.CategoryDao;
import com.ygy.pojo.Category;
import com.ygy.service.CategorySevice;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/category")
@ResponseBody
public class CategoryController {
    @Autowired
    private CategorySevice categorySevice;
    @Autowired
    private CategoryDao categoryDao;
    @GetMapping("/page")
    public Result selectByPage(@RequestParam(name = "pageSize") int pageSize, @RequestParam(name = "page") int page){
        List<Object> categories = categorySevice.selectByPage(pageSize, pageSize * (page - 1));

        return new Result(Code.GET_OK, categories);
    }


    @PostMapping
    public Result addCategory(@RequestBody Category category, HttpServletRequest request) throws Exception {
        boolean flag = categorySevice.addCategory(category, request);
        if(flag){
            return new Result(Code.SAVE_OK, flag);
        } else {
            return new Result(Code.SAVE_ERROR, flag);
        }
    }

    @DeleteMapping
    public Result deleteById(@RequestParam("ids") String id){
        Long ids = Long.parseLong(id);
        boolean flag = categorySevice.deleteById(ids);
        if(flag){
            return new Result(Code.DELETE_OK, flag);
        } else{
            return new Result(Code.DELETE_ERROR, flag);
        }
    }

    @PutMapping
    public Result update(@RequestBody Category category){
        boolean flag = categorySevice.updateById(category);
        if(flag){
            return new Result(Code.UPDATE_OK, flag);
        } else{
            return new Result(Code.UPDATE_ERROR, flag);
        }
    }
    @GetMapping("/list")
    public Result getTypeList(String type){
        if(type!=null){
            int type1 = Integer.parseInt(type);
            List<Category> allNameByType = categoryDao.getAllNameByType(type1);
            return new Result(Code.GET_OK, allNameByType);
        }else{
            List<Category> categories = categoryDao.selectList(null);
            return new Result(Code.GET_OK,categories);
        }


    }

}
