package com.ygy.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.ygy.dao.ShoppingCartDao;
import com.ygy.pojo.ShoppingCart;
import com.ygy.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
@ResponseBody
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private ShoppingCartDao shoppingCartDao;
    @GetMapping("/list")
    public Result list(HttpSession session){
        Long customerid = (Long) session.getAttribute("CUSTOMERID");
        List<ShoppingCart> shoppingCarts = shoppingCartDao.selectByUserId(customerid);
        return new Result(Code.GET_OK, shoppingCarts);
    }

    @PostMapping("/add")
    public Result add(@RequestBody ShoppingCart cart, HttpSession session){
        Long customerid = (Long) session.getAttribute("CUSTOMERID");
        Long dishId = cart.getDishId();
        Double amount = cart.getAmount();
        String flavor = cart.getDishFlavor();
        Long setmealId = cart.getSetmealId();

        if(dishId!=null){
            Integer update = shoppingCartDao.updateByDishID(customerid, dishId);
            if(update==0){
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                cart.setCreateTime(timestamp);
                cart.setUserId(customerid);
                int insertFlag = shoppingCartDao.insert(cart);
            }
            ShoppingCart shoppingCart = shoppingCartDao.selectByDishIdOrSetmealId(customerid, dishId, flavor,null);
            return new Result(Code.SAVE_OK, shoppingCart);

        } else { //套餐插入和查询
            Integer update = shoppingCartDao.updateBySetmealId(customerid, setmealId);
            if(update==0){
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                cart.setCreateTime(timestamp);
                cart.setUserId(customerid);
                int insertFlag = shoppingCartDao.insert(cart);
            }
            ShoppingCart shoppingCart = shoppingCartDao.selectByDishIdOrSetmealId(customerid, null, null,setmealId);
            return new Result(Code.SAVE_OK, shoppingCart);

        }
    }
    @DeleteMapping("/clean")
    public Result clean(HttpSession session){
        Long customerid = (Long) session.getAttribute("CUSTOMERID");
        int delete = shoppingCartDao.deleteByUserId(customerid);
        if(delete!=0){
            return new Result(Code.DELETE_OK, true);
        }
        return new Result(Code.DELETE_ERROR, false, "删除失败");
    }
    @PostMapping("/sub")
    public Result sub(@RequestBody ShoppingCart cart, HttpSession session){
        Long customerid = (Long) session.getAttribute("CUSTOMERID");
        cart.setUserId(customerid);
        shoppingCartDao.subNumberByDishIdOrSetmealId(cart);
        ShoppingCart shoppingCart = shoppingCartDao.selectByDishIdOrSetmealId2(cart);
        int number = shoppingCart.getNumber();
        if (number==0){
            shoppingCartDao.deleteByByDishIdOrSetmealId(cart);
        }
        return new Result(Code.UPDATE_OK, true);
    }



}
