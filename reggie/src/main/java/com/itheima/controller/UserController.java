package com.itheima.controller;

import com.itheima.common.R;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.utils.SMSUtils2;
import com.itheima.utils.ValidateCodeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public R sendMsg(@RequestBody User user, HttpSession session) {
        //获取手机号
        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)) {
            //生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            System.out.println(code);

            //SMSUtils2.sendMessage(phone,code);

            session.setAttribute(phone, code);
            return R.success("手机验证码短信发送成功");
        }
        return R.error("短信发送失败");
    }

    @PostMapping("/login")
    public R login(@RequestBody User user, HttpSession session) {
        //获取手机号
        String phone = user.getPhone();
        //获取验证码
        String code = user.getCode();
        //从Session中获取保存的验证码
        Object codesessin = session.getAttribute(phone);

        //进行验证码的比对（页面提交的验证码和Session中保存的验证码比对）
        if (codesessin != null && codesessin.equals(code)) {
            //如果能够比对成功，说明登录成功
            //根据手机号查询用户
            User u = userService.findUser(phone);

            //保存登录状态
            session.setAttribute("user",u.getId());
            //返回
            return R.success(null);
        }
        return R.error("验证码错误");
    }

    @PostMapping("/loginout")
    public R loginout(HttpSession session){
        session.invalidate();
        return R.success(null);
    }
}
