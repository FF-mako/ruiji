package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.domain.AddressBook;

import java.util.List;

public interface AddressBookService extends IService<AddressBook> {
    List<AddressBook> addressBooklist(AddressBook addressBook);

    void setDefaultAddress(AddressBook addressBook);
}
