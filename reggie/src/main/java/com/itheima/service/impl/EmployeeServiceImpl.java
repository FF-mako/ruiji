package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.common.R;
import com.itheima.dao.EmployeeDao;
import com.itheima.domain.Employee;
import com.itheima.service.EmployeeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeDao, Employee> implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public R login(Employee employee) {
        //查询本地数据库
        LambdaQueryWrapper<Employee> qw = new LambdaQueryWrapper<>();
        qw.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeDao.selectOne(qw);
        //判断网页用户名是否存在
        if (emp == null){
            return R.error("该用户名不存在");
        }
        //md5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!emp.getPassword().equals(password)){
            return R.error("用户名或密码错误");
        }
        //账号状态校验
        if (emp.getStatus() == 0){
            return R.error("该账号已被禁用");
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
        employeeDao.insert(employee);
    }

    @Override
    public Page pageEmp(int page, int pageSize, String name) {
          //构造分页构造器
        Page pageInfo = new Page(page, pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Employee> qw = new LambdaQueryWrapper();
        //添加过滤条件
        qw.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        //qw.like(name != null, Employee::getName, name);

        //添加排序条件
        qw.orderByDesc(Employee::getUpdateTime);

        //执行查询
        Page pageInfo1 = employeeDao.selectPage(pageInfo, qw);

       return pageInfo1;
    }


}
