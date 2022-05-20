package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.common.BaseContext;
import com.itheima.dao.AddressBookDao;
import com.itheima.domain.AddressBook;
import com.itheima.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookDao, AddressBook> implements AddressBookService {

    @Autowired
    private AddressBookDao addressBookDao;

    @Override
    public List<AddressBook> addressBooklist(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        LambdaQueryWrapper<AddressBook> qw = new LambdaQueryWrapper<>();
        qw.eq(null != addressBook.getUserId(), AddressBook::getUserId, addressBook.getUserId());
        List<AddressBook> addressBooks = addressBookDao.selectList(qw);
        return addressBooks;
    }

    @Override
    public void setDefaultAddress(AddressBook addressBook) {
        LambdaQueryWrapper<AddressBook> qw = new LambdaQueryWrapper<>();
        qw.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        qw.eq(AddressBook::getIsDefault, 1);
        AddressBook one = getOne(qw);
        one.setIsDefault(0);
        updateById(one);
        AddressBook byId = getById(addressBook.getId());
        byId.setIsDefault(1);
        updateById(byId);


    }
}
