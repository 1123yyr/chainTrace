package com.example.careold.common;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;

import java.io.IOException;


public class SmsUtil {
		// TODO Auto-generated method stub
		public static void sendMassage(String phone){
				// 短信应用SDK AppID
		        int appid = 1400193017; // 1400开头

		        // 短信应用SDK AppKey
		        String appkey = "8ef2a12a577ed780255da0d71244b180";

		        // 需要发送短信的手机号码                 //自己，康帅，刘俊，王帆，胡涛
//		        String[] phoneNumbers = {"15179158704", "15979084355","18770645015","15007009771","15079158693","18470312023"};

		        // 短信模板ID，需要在短信应用中申请
		        // NOTE: 这里的模板ID`7839`只是一个示例，
		        // 真实的模板ID需要在短信控制台中申请
		        int templateId = 292416;

		        // 签名
		        // NOTE: 这里的签名"腾讯云"只是一个示例，
		        // 真实的签名需要在短信控制台中申请，另外
		        // 签名参数使用的是`签名内容`，而不是`签名ID`
		        String smsSign = "大智若娱快乐就好";

		        // 单发短信
		        try {
		            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
		            SmsSingleSenderResult result = ssender.send(0, "86", phone,
		                "你关注的养老院有新的公告动态了，请登录系统查看", "", "");
		            System.out.print(result);
		        } catch (HTTPException e) {
		            // HTTP响应码错误
		            e.printStackTrace();
		        } catch (JSONException e) {
		            // json解析错误
		            e.printStackTrace();
		        } catch (IOException e) {
		            // 网络IO错误
		            e.printStackTrace();
		        }


		    }
}
