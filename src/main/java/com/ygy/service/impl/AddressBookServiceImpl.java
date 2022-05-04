package com.ygy.service.impl;

import com.ygy.dao.AddressBookDao;
import com.ygy.pojo.AddressBook;
import com.ygy.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {
    @Autowired
    private AddressBookDao addressBookDao;

    @Override
    public AddressBook getDefault(AddressBook addressBook) {
        AddressBook addressBook1 = addressBookDao.selectByCityCodeAndIsDefault(addressBook);
        return addressBook1;
    }

    @Override
    public List<AddressBook> list(AddressBook addressBook) {
        return addressBookDao.selectAllByUserId(addressBook);
    }

    @Override
    public Integer addAddress(AddressBook addressBook) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        addressBook.setCreateTime(ts);
        addressBook.setUpdateTime(ts);
        return addressBookDao.insert(addressBook);
    }

    @Override
    public Integer setDefault(AddressBook addressBook) {
        return addressBookDao.updateIsDefault(addressBook);
    }

    @Override
    public AddressBook getById(Long id) {
        return addressBookDao.selectById(id);
    }

    @Override
    public Integer updateAddress(AddressBook addressBook) {
        return null;
    }


}
