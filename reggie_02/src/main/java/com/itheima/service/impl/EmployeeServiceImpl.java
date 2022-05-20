package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.common.R;
import com.itheima.dao.EmployeeDao;
import com.itheima.domain.Employee;
import com.itheima.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeDao, Employee> implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public R login(Employee employee) {
        //从数据库中拿到匹配的username
        LambdaQueryWrapper<Employee> qw = new LambdaQueryWrapper<>();
        qw.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeDao.selectOne(qw);

        //判断username is null ?
        if (emp == null) {
            return R.error("该用户名不存在");
        }

        //md5
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!emp.getPassword().equals(password)){
            return R.error("用户名或密码错误");
        }

        //判断用户状态
        if (emp.getStatus() == 0){
            return R.error("该账号异常");
        }

        return R.success(emp);
    }

    @Override
    public void addEmp(Employee employee) {
        //设置默认密码
        String password = DigestUtils.md5DigestAsHex("123456".getBytes());
        employee.setPassword(password);
        //设置默认状态
        employee.setStatus(1);
        //设置创建时间和修改时间：为当前系统时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        save(employee);
    }
}
