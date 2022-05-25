package com.itheima.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.common.R;
import com.itheima.domain.Employee;
import com.itheima.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
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
    public R login(@RequestBody Employee employee, HttpSession session) {
        R r = employeeService.login(employee);
        Employee e = (Employee) r.getData();
        session.setAttribute("employee",e.getId());
        return r;
    }

    //退出并删除session
    @PostMapping("/logout")
    public R logout(HttpSession session){
        session.invalidate();
        return R.success(null);
    }

    //新增员工
    @PostMapping
    public R add(@RequestBody Employee employee){
        employeeService.addEmp(employee);
        return R.success(null);
    }

    /**
     * 员工信息分页查询
     *
     * @param page     当前查询页码
     * @param pageSize 每页展示记录数
     * @param name     员工姓名 - 可选参数 - 单个查询使用
     * @return
     */
    @GetMapping("/page")
    public R page(int page, int pageSize, String name) {
       /* //构造分页构造器
        Page pageInfo = new Page(page, pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Employee> qw = new LambdaQueryWrapper();
        //添加过滤条件
        qw.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        //qw.like(name != null, Employee::getName, name);

        //添加排序条件
        qw.orderByDesc(Employee::getUpdateTime);

        //执行查询
        employeeService.page(pageInfo, qw);
        return R.success(pageInfo);*/

        Page pageInfo = employeeService.pageEmp(page,pageSize,name);
        return R.success(pageInfo);
    }

    //改变员工状态&&修改员工信息
    @PutMapping
    public R update (HttpSession session,@RequestBody Employee employee){
        Long id = (Long) session.getAttribute("employee");
        employee.setUpdateUser(id);
        employee.setUpdateTime(LocalDateTime.now());
        employeeService.updateById(employee);
        return R.success(null);
    }

    //修改员工数据回显
    @GetMapping("/{id}")
    public R getById(@PathVariable Long id){
        Employee emp = employeeService.getById(id);
        if (emp != null){
            return R.success(emp);
        }
        return R.error("没有查询到对应员工信息");
    }
}
