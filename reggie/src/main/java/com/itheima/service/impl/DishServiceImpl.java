package com.itheima.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.dao.DishDao;
import com.itheima.domain.Dish;
import com.itheima.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl extends ServiceImpl<DishDao, Dish> implements DishService {
}
