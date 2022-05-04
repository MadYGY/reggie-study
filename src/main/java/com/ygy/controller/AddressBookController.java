package com.ygy.controller;

import com.ygy.pojo.AddressBook;
import com.ygy.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@ResponseBody
@RequestMapping("/addressBook")
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;
    @GetMapping("/default")
    public Result getDefault(HttpSession session){
        Long customerid = (Long) session.getAttribute("CUSTOMERID");
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(customerid);
        addressBook.setIsDefault(1);
        AddressBook aDefault = addressBookService.getDefault(addressBook);
        return new Result(1, aDefault);
    }

    @GetMapping("/list")
    public Result getUserAllAdress(HttpSession session){
        Long customerid = (Long) session.getAttribute("CUSTOMERID");
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(customerid);
        List<AddressBook> list = addressBookService.list(addressBook);
        if(list!=null){
            return new Result(1, list);
        } else {
            return new Result(0, false, "请添加收货地址");
        }
    }
    @PostMapping
    public Result add(@RequestBody AddressBook addressBook, HttpSession session){
        Long customerid = (Long) session.getAttribute("CUSTOMERID");
        addressBook.setUserId(customerid);
        addressBook.setCreateUser(customerid);
        addressBook.setUpdateUser(customerid);
        Integer integer = addressBookService.addAddress(addressBook);
        if(integer!=0){
            return new Result(1, true);
        }
        return new Result(0, false, "添加失败");
    }

    @PutMapping("/default")
    public Result setDefault(@RequestBody AddressBook addressBook, HttpSession session){
        Long customerid = (Long) session.getAttribute("CUSTOMERID");
        addressBook.setUserId(customerid);
        addressBook.setIsDefault(1);
        Integer integer = addressBookService.setDefault(addressBook);
        if(integer!=0){
            return new Result(1, true);
        }
        return new Result(0, false, "添加失败");
    }

//    @GetMapping("/{id}")
//    public Result getById(@PathVariable String stringId){
//        Long id = Long.parseLong(stringId);
//        AddressBook addressBook = addressBookService.getById(id);
//        if(addressBook!=null){
//            return new Result(1, addressBook);
//        } else {
//            return new Result(0, false, "获取失败");
//        }
//    }

//    @PutMapping
//    public Result changeAddress(@RequestBody AddressBook addressBook, HttpSession session){
//        Long customerid = (Long) session.getAttribute("CUSTOMERID");
//        //addressBook.setUpdateUser(customerid);
//        return null;
//    }
}
