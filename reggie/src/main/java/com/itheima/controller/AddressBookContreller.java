package com.itheima.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.common.BaseContext;
import com.itheima.common.R;
import com.itheima.domain.AddressBook;
import com.itheima.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addressBook")
public class AddressBookContreller {

    @Autowired
    private AddressBookService addressBookService;

    //新增
    @PostMapping
    public R save(@RequestBody AddressBook addressBook){
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookService.save(addressBook);
        return R.success(null);
    }

    //查询
    @GetMapping("/list")
    public R list (AddressBook addressBook){
        List<AddressBook> list = addressBookService.addressBooklist(addressBook);
        return R.success(list);
    }
    //设置默认地址
    @PutMapping("/default")
    public R setDefault(@RequestBody AddressBook addressBook){
        addressBookService.setDefaultAddress(addressBook);
        return R.success(null);
    }

    //修改地址回显
    @GetMapping("/{id}")
    public R get(@PathVariable Long id){
        AddressBook byId = addressBookService.getById(id);
        if (byId != null){
            return R.success(byId);
        }else{
            return R.error("没有找到该对象");
        }
    }

    //修改地址保存
    @PutMapping
    public R update(@RequestBody AddressBook addressBook){
        addressBookService.updateById(addressBook);
        return R.success(null);
    }

    //删除地址
    @DeleteMapping
    public R delete(Long ids){
        addressBookService.removeById(ids);
        return R.success(null);
    }
}
