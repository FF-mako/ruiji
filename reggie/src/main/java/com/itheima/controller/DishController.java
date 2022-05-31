package com.itheima.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itheima.common.R;
import com.itheima.domain.Dish;
import com.itheima.domain.DishDto;
import com.itheima.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    //菜品管理分页查询
    @GetMapping("/page")
    public R page(int page, int pageSize, String name) {
        IPage<Dish> p = dishService.findpage(page, pageSize, name);
        return R.success(p);
    }

    //菜品保存
    @PostMapping
    public R save(@RequestBody DishDto dishDto) {
        dishService.saveDishAndFlavors(dishDto);
        return R.success(null);
    }

    //菜品修改回显
    @GetMapping("/{id}")
    public R get(@PathVariable Long id){
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    //修改菜品
    @PutMapping
    public R update(@RequestBody DishDto dishDto){
        dishService.updateWithFlavor(dishDto);
        return R.success("修改菜品成功");
    }

    //修改菜品状态
    @PostMapping("/status/{id}")
    public R updatestatus(@PathVariable int id, @RequestParam List<Long> ids){
        dishService.upupdatestatus(id,ids);
        return R.success("修改成功");
    }

    //删除菜品
    //未完待续
    @DeleteMapping()
    public R delete(@RequestParam List<Long> ids){
        dishService.removeByIds(ids);
        return R.success("删除成功");
    }

    //查询菜品
    @GetMapping("/list")
    public R findDishById(Long categoryId){
        List<Dish> dishList = dishService.findDishById(categoryId);
        return R.success(dishList);

    }

}
