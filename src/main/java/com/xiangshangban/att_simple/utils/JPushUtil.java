package com.xiangshangban.att_simple.utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.AndroidNotification.Builder;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class JPushUtil {
	
	private static final Log LOG = LogFactory.getLog(JPushUtil.class);
	public static final String JPUSH_BRADCAST = "BRADCAST";
	public static final String JPUSH_ALIS = "ALIS";
	public static final String EXTRAKEY = "type";
	public static final String CODE = "push";
	private  JPushClient client;

	
	public JPushUtil(){
		init();
		}
		
	/**
	 * 初始化
	 */
	public void init(){
		String appkey ="";
		String mastersecret ="";
		try {
		 appkey = PropertiesUtils.jpushProperty("AppKey");
		 mastersecret = PropertiesUtils.jpushProperty("MasterSecret");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client = new JPushClient(mastersecret,appkey,3);
	}

	/**
	 * 广播
	 * @param content
	 * @return
	 */
	public static PushPayload buildPushObjectAll(String content) {
	    return PushPayload.alertAll(content);
	}
	
	/**
	 * 针对个体推送
	 * @param content 推送内容
	 * @param notificationTitile 安卓通知栏标题
	 * @param extraKey  额外键
	 * @param extraValue 额外键值
	 * @param alias 发送的别名数组
	 * @return
	 * .setOptions(Options.newBuilder().setApnsProduction(true).build())*/
public static PushPayload buildPushObject(String content,String notificationTitile,String resourceId,
		String inforType,String extraKey,String extraValue,String...alias) {
    	
    	return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())//设置推送平台
                .setAudience(Audience.alias(alias))//设置推送用户别名
                .setNotification(Notification.newBuilder()
                		.setAlert(content)
                		.addPlatformNotification(AndroidNotification.newBuilder()
                				.setTitle(notificationTitile)
                				.addExtra(extraKey,extraValue)
                				.addExtra("resourceId", resourceId)
                				.addExtra("inforType", inforType)
                				.build())
                		.addPlatformNotification(IosNotification.newBuilder()
                				.incrBadge(+1)
                				.addExtra(extraKey,extraValue)
                				.addExtra("resourceId", resourceId)
                				.addExtra("inforType", inforType)
                				.build())
                		.build())
                .build();
    }
	/**
	 * 别名推送
	 * @param alert
	 * @param apnsProduction true:生产环境 false:开发环境
	 * @param alias
	 * @return
	 */
    public static PushPayload buildPushObject(String alert,boolean apnsProduction,String...alias){
    	return PushPayload.newBuilder()
    			.setPlatform(Platform.android_ios())//推送平台ios和android
    			.setAudience(Audience.alias(alias))
    			.setNotification(Notification.newBuilder()
    					.setAlert(alert)
    					.addPlatformNotification(AndroidNotification.newBuilder()
    							.build())
    					.addPlatformNotification(IosNotification.newBuilder()
    							.incrBadge(+1)
    							.build())
    					.build())
    			.setOptions(Options.newBuilder()
    					.setApnsProduction(apnsProduction)
    					.build())
    			.build();
    }
    public static PushPayload BuildPushObject(String title,String alert,boolean apnsProduction,
    		Map<String,String> extraMap,String...alias){
    	return PushPayload.newBuilder()
    			.setPlatform(Platform.android_ios())//推送平台ios和android
    			.setAudience(Audience.alias(alias))
    			.setNotification(Notification.newBuilder()
    					.addPlatformNotification(AndroidNotification.newBuilder()
    							.setAlert(alert)
    							.setTitle(title)
    							.addExtras(extraMap)//添加额外键值
    							.build())
    					.addPlatformNotification(IosNotification.newBuilder()
    							.setAlert(title+"\n"+alert)
    							.incrBadge(1)
    							.setSound("default")
    							.addExtras(extraMap)
    							.build())
    					.build())
    			.setOptions(Options.newBuilder()
    					.setApnsProduction(apnsProduction)
    					.build())
    			.build();
    }
    public String sendPush(String sendType,String title,String alert,Map<String,String> extraMap,
    		String...alias){
    	PushPayload payload = null;
    	String jpushEnvironment = "test";
		try {
			jpushEnvironment = PropertiesUtils.jpushProperty("jpushEnvironment");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	boolean apnsProduction = false;
    	if("product".equals(jpushEnvironment)){
    		apnsProduction = true;
    	}
    	if(JPUSH_BRADCAST.equals(sendType))
    		payload = buildPushObjectAll(alert);//广播
    	else
    		payload = BuildPushObject(title,alert, apnsProduction, extraMap, alias);
    	try {
            PushResult result = client.sendPush(payload);
            LOG.debug("推送结果: " + result);
            return "1";
        } catch (APIConnectionException apiConnectionException) {
            LOG.error("连接JPush失败:", apiConnectionException);
            return "3";
        } catch (APIRequestException apiRequestException) {
            LOG.error("JPush服务器返回错误结果:", apiRequestException);
            LOG.debug("HTTP Status: " + apiRequestException.getStatus());
            LOG.debug("Error Code: " + apiRequestException.getErrorCode());
            LOG.debug("Error Message: " + apiRequestException.getErrorMessage());
            LOG.debug("Msg ID: " + apiRequestException.getMsgId());
        }
    	 return "3";
    }
    /**
     * 推送
     * @param sendType 推送类型
     * @param content 推送内容
     * @param notificationTitile 安卓通知栏标题
	 * @param extraKey  额外键
	 * @param extraValue 额外键值
	 * @param alias 发送的别名数组
     */
    public String sendPush(String sendType,String content,String informationId,String inforType,
    		String notificationTitile,String extraKey,String extraValue,String...alias){
    	PushPayload payload = null;
    	String jpushEnvironment = "test";
		try {
			jpushEnvironment = PropertiesUtils.jpushProperty("jpushEnvironment");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	boolean apnsProduction = false;
    	if("product".equals(jpushEnvironment)){
    		apnsProduction = true;
    	}
    	if(JPUSH_BRADCAST.equals(sendType))
    		payload = buildPushObjectAll(content);
    	else
    		/*payload = buildPushObject(content, notificationTitile, informationId ,inforType,
    				extraKey, extraValue, alias);*/
    		payload = buildPushObject(content,apnsProduction,alias);
    	try {
            PushResult result = client.sendPush(payload);
            LOG.debug("推送结果: " + result);
            return "1";
        } catch (APIConnectionException apiConnectionException) {
            LOG.error("连接JPush失败:", apiConnectionException);
            return "3";
        } catch (APIRequestException apiRequestException) {
            LOG.error("JPush服务器返回错误结果:", apiRequestException);
            LOG.debug("HTTP Status: " + apiRequestException.getStatus());
            LOG.debug("Error Code: " + apiRequestException.getErrorCode());
            LOG.debug("Error Message: " + apiRequestException.getErrorMessage());
            LOG.debug("Msg ID: " + apiRequestException.getMsgId());
        }
    	 return "3";
    }
	
}
