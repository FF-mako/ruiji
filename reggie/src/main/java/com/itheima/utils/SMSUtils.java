package com.itheima.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.itheima.exception.CategoryException;



/**
 * 短信发送工具类
 */
public class SMSUtils {

	/**
	 * 发送短信
	 * @param phoneNumbers 手机号
	 * @param param 验证码
	 */
	public static void sendMessage(String phoneNumbers, String param){
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4G5rJZuQdW127QJHRexk", "5EeLLxPuImJJXCHvNPBMrdKkydw19M");
		IAcsClient client = new DefaultAcsClient(profile);

		SendSmsRequest request = new SendSmsRequest();
		request.setSysRegionId("cn-hangzhou");
		request.setPhoneNumbers(phoneNumbers);
		request.setSignName("传智健康");
		request.setTemplateCode("SMS_198930661");
		request.setTemplateParam("{\"code\":\""+param+"\"}"); // {"code":1234}
		try {
			SendSmsResponse response = client.getAcsResponse(request);
			System.out.println("短信发送成功");
		} catch (ClientException e) {
			e.printStackTrace();
			throw new CategoryException("网络异常，请重试");
		}
	}

	public static void main(String[] args) {
		 // 1. 在控制台打印验证码（后期测试通一次就够了）
		 // 2. 在session存储一份（后期用于登录校验）

	}

}

/*
步骤:
1. 阿里云注册账号
2. 找到短信服务,申请签名/模板
3. 创建一个子账户(记得保存用户名密码),授权
4. 复制工具类,修改: 模板Code,用户名密码
 */