package com.itheima.utils;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.itheima.exception.CategoryException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * 短信发送工具类
 */
@Slf4j
public class SMSUtils2 {

    private static final String accountSid = "8aaf0708809721d00180d6cfc62e11c4";
    private static final String accountToken = "1038ea8a98904245b5d0d674452950c4";
    private static final String appId = "8aaf0708809721d00180d6cfc70911cb";


    private static CCPRestSmsSDK sdk;

    static {
        sdk = new CCPRestSmsSDK();
        sdk.init("app.cloopen.com", "8883");
        sdk.setAccount(accountSid, accountToken);
        sdk.setAppId(appId);
    }

    /**
     * 发送短信
     *
     * @param phoneNumbers 手机号
     * @param param        验证码
     */
    public static void sendMessage(String phoneNumbers, String param) {
        // 发送验证码
        String[] datas = {param, "30"}; // 你的验证码是 {} , 请在 {} 分钟之内使用
        HashMap<String, Object> result = sdk.sendTemplateSMS(phoneNumbers, "1", datas);
        System.out.println("result: " + result);
        if (result.get("statusCode").equals("000000")) {
            log.info("短信发送成功");
        } else {
            throw new CategoryException("短信发送失败");
        }
    }

}

/*
步骤:
1. 容联云: 注册,登录
2. 进入控制台:
    1. 获取三个值 : accountSid/accountToken/appId
    2. 设置测试号码
3. 复制我的工具类,调用sendMessage发送短信
 */