package com.itheima.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.dao.SetmealDao;
import com.itheima.domain.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.stereotype.Service;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealDao,Setmeal> implements SetmealService {
}
