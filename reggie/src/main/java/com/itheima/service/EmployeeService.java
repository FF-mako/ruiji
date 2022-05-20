package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.common.R;
import com.itheima.domain.Employee;

public interface EmployeeService extends IService<Employee> {
    public R login(Employee employee);
    public void addEmp(Employee employee);
}
