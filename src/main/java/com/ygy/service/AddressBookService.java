package com.ygy.service;

import com.ygy.pojo.AddressBook;

import java.util.List;

public interface AddressBookService {
    AddressBook getDefault(AddressBook addressBook);

    List<AddressBook> list(AddressBook addressBook);

    Integer addAddress(AddressBook addressBook);

    Integer setDefault(AddressBook addressBook);

    AddressBook getById(Long id);

    Integer updateAddress(AddressBook addressBook);
}
