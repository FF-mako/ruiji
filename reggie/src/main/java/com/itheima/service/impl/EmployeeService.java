package com.itheima.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.common.R;
import com.itheima.domain.Employee;

public interface EmployeeService extends IService<Employee> {
    R login(Employee employee);

    void addEmp(Employee employee);

    Page pageEmp(int page, int pageSize, String name);
}
