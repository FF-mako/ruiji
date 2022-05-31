package com.itheima.domain;

import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal{

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
