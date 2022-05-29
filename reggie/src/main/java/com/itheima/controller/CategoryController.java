package com.itheima.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.common.R;
import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //查询菜品分类
    @GetMapping("/list")
    public R list(Integer type) {
        List<Category> list = categoryService.findCategoryByType(type);
        return R.success(list);
    }

    //新增套餐菜品分类
    @PostMapping
    public R save(@RequestBody Category category) {
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    //分类管理页面分类查询
    @GetMapping("/page")
    public R page(int page, int pageSize) {
        Page pageInfo = categoryService.pageEmp(page, pageSize);
        return R.success(pageInfo);
    }

    //删除菜品套餐分类
    @DeleteMapping
    public R delete(Long id){
        //若删除菜品套餐分类则需判断是否关联dish和Setmeal，故注之
        //categoryService.removeById(id);

        categoryService.remove(id);
        return R.success("分类信息删除成功");
    }

    //修改菜品套餐分类
    @PutMapping
    public R update(HttpSession session, @RequestBody Category category){
        Long id = (Long) session.getAttribute("employee");
        category.setUpdateUser(id);
        category.setUpdateTime(LocalDateTime.now());
        categoryService.updateById(category);
        return R.success("修改成功");

    }

}
