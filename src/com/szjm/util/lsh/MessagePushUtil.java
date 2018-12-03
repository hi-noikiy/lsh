package com.szjm.util.lsh;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.api.JPushClient;
import org.apache.taglibs.standard.lang.jstl.Logger;

import com.szjm.util.Jurisdiction;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.*;

public class MessagePushUtil {
	public static String MessagePush(String alias,String appKey,String masterSecret,String remark,String title,Map<String,String> map ){
//		try {
//			 title = new String(title.getBytes("ISO-8859-1"), "UTF-8");
//			 remark = new String(remark.getBytes("ISO-8859-1"), "UTF-8");
//			} catch (UnsupportedEncodingException e1) {
//				e1.printStackTrace();
//			}
		String code = "0";
		try {
		    JPushClient jpushClient = new JPushClient(masterSecret, appKey);
		    //推送的关键,构造一个payload
		    PushPayload payload = null;
		    if(alias!=null&&!"".equals(alias)){
			    /*payload = PushPayload.newBuilder()
			        .setPlatform(Platform.all())//指定android平台的用户
			        .setAudience(Audience.alias(alias))//发给单个用户
			        .setNotification(Notification.android(remark,title, map))
			        .build();*/
		    	payload = PushPayload.newBuilder()
	                 //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
	                 .setPlatform(Platform.all())
	                 //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
	                 .setAudience(Audience.tag(alias))
	                 //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
	                 .setNotification(Notification.newBuilder()
	                         //指定当前推送的android通知
	                         .addPlatformNotification(AndroidNotification.newBuilder()
	                                 .setAlert(remark)
	                                 .setTitle(title)
	                                 //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
	                                 .build())
	                         //指定当前推送的iOS通知
	                         .addPlatformNotification(IosNotification.newBuilder()
	                                 //传一个IosAlert对象，指定apns title、title、subtitle等
	                                 .setAlert(remark)
	                                 //直接传alert
	                                 //此项是指定此推送的badge自动加1
	                                 .incrBadge(1)
	                                 //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
	                                 // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
	                                 .setSound("sound.caf")
	                                 //取消此注释，消息推送时ios将无法在锁屏情况接收
	                                 // .setContentAvailable(true)
	                                 .build())
	                         .build())
	                         .setOptions(Options.newBuilder()
		                          //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
		                          .setApnsProduction(true)
		                          //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
		                          .setSendno(1)
		                          //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天；
		                          .setTimeToLive(86400)
		                          .build())
	                 .build();
		    }else{
			    /*payload = PushPayload.newBuilder()
			        .setPlatform(Platform.android_ios())//指定android平台的用户
			        .setAudience(Audience.all())//你项目中的所有用户
			        .setNotification(Notification.android(remark,title, map))
			        .build();*/

		    	payload =  PushPayload.newBuilder()
	                 .setPlatform(Platform.android_ios())
	                 .setAudience(Audience.all())
	                 .setNotification(Notification.newBuilder()
	                         .addPlatformNotification(AndroidNotification.newBuilder()
	                                 .setAlert(remark)//内容
	                                 .setTitle(title)//标题
	                                 .build()
	                         )
	                         .addPlatformNotification(IosNotification.newBuilder()
	                                 //传一个IosAlert对象，指定apns title、title、subtitle等, 后台填的内容
	                                 .setAlert(remark)
	                                 //直接传alert
	                                 //此项是指定此推送的badge自动加1
	                                 .incrBadge(1)
	                                 //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
	                                 // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
	                                 .setSound("sound.caf")
	                                 //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
	                                 // .setContentAvailable(true)
	                                 .build()
	                         )
	                         .build())

	                         .setOptions(Options.newBuilder()
		                          //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
		                          .setApnsProduction(true)
		                          //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
		                          .setSendno(1)
		                          //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天；
		                          .setTimeToLive(86400)

		                          .build())
	                 .build();
		    }
		    //TODO 暂时注释
            PushResult pu = jpushClient.sendPush(payload);
	       // System.out.println(pu);
	        //System.out.println(payload);
	    } catch (APIConnectionException e) {
	    	return "1";
	    } catch (APIRequestException e) {
	    	return "1";
	    }
	   return code;
	}	
	public static void main(String[] args) {
		String messagePush = MessagePush("f8c6c225662f4decb2366970198398a7", "741b44999dc018521d9c1fdf", "3f22f8ccc3e8a18d41f6ad4e", "你的好友2018-06-06生日咯", "好友生日", null);
		System.out.println(messagePush);
	}
}
