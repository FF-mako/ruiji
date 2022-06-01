package com.itheima.controller;

import com.itheima.common.R;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.utils.ValidateCodeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/sendMsg")
    public R sendMsg(@RequestBody User user, HttpSession session) {
        //获取手机号
        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)) {
            //生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            System.out.println(code);

            //需要将生成的验证码保存到Redis,设置过期时间
            //redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);

            //容联云短信服务
            //SMSUtils2.sendMessage(phone,code);

            //传统session
            session.setAttribute(phone, code);
            return R.success("手机验证码短信发送成功");
        }
        return R.error("短信发送失败");
    }

    @PostMapping("/login")
    public R login(@RequestBody User user,HttpSession session){
        String phone = user.getPhone();
        String code = user.getCode();

        //从Session中获取保存的验证码
        Object codesessin = session.getAttribute(phone);

//        //从Redis中获取缓存的验证码
//        Object codesessin = redisTemplate.opsForValue().get(phone);

        //进行验证码的比对
        if (codesessin != null && codesessin.equals(code)){
            //如果能够比对成功，说明登录成功
            //根据手机号查询用户
            User u = userService.findUser(phone);

            //保存登录状态
            session.setAttribute("user",u.getId());

//            //从Redis中删除缓存的验证码
//            redisTemplate.delete(phone);

            //返回
            return R.success(null);
        }
        return R.error("验证码错误");
    }

    //退出登录
    @PostMapping("/loginout")
    public R loginout(HttpSession session){
        session.invalidate();
        return R.success(null);
    }
}
