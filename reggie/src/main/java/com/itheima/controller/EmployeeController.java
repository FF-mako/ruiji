package com.itheima.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.common.R;
import com.itheima.domain.Employee;
import com.itheima.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R login(HttpSession session, @RequestBody Employee employee) {
        R r = employeeService.login(employee);
        if (r.getCode() == 1) {
            Employee e = (Employee) r.getData();
            session.setAttribute("employee", e.getId());
        }
        return r;
    }

    @PostMapping("/logout")
    public R loout(HttpSession session) {
        session.invalidate();
        return R.success(null);
    }

    //新增员工
    @PostMapping
    public R add(HttpSession session, @RequestBody Employee employee) {
        log.info("新增员工：{}", employee);
//        //设置创建人和修改人ID值
//        Long id = (Long) session.getAttribute("employee");
//        employee.setUpdateUser(id);
//        employee.setCreateUser(id);
        employeeService.addEmp(employee);
        return R.success(null);

    }

    /**
     * 员工信息分页查询
     *
     * @param page     当前查询页码
     * @param pageSize 每页展示记录数
     * @param name     员工姓名 - 可选参数
     * @return
     */
    @GetMapping("/page")
    public R page(int page, int pageSize, String name) {
        log.info("page = {},pageSize = {},name = {}", page, pageSize, name);
        //构造分页构造器
        Page pageInfo = new Page(page, pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        //添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        //执行查询
        employeeService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }


    //修改状态
    @PutMapping
    private R update(HttpSession session, @RequestBody Employee employee) {
        Long id = (Long) session.getAttribute("employee");
        employee.setUpdateUser(id);
        employee.setUpdateTime(LocalDateTime.now());
        employeeService.updateById(employee);
        return R.success(null);
    }

    //根据ID查询用户信息
    @GetMapping("/{id}")
    public R update(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        return R.success(employee);

    }

}



































