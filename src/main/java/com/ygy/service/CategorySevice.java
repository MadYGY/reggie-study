package com.ygy.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ygy.pojo.Category;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

public interface CategorySevice{
    List<Object> selectByPage(int pageSize, int page);

    boolean addCategory(Category category, HttpServletRequest request) throws Exception;

    boolean deleteById(Long id);

    boolean updateById(Category category);
}
