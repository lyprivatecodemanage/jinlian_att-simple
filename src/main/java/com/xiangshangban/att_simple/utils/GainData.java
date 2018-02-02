package com.xiangshangban.att_simple.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

public class GainData {
	private boolean flag=false;
	private boolean webOrApp;//true:web,false:app
	private HttpServletRequest request;
	private Map<String,Object> result;
	private int type = 0;
	@SuppressWarnings("unchecked")
	public GainData(String jsonString,HttpServletRequest request){
		String type = request.getHeader("type");
		String token = request.getHeader("token");
		String clientId = request.getHeader("clientId");
		if(StringUtils.isEmpty(type)){
			result.put("flag", flag);
			result.put("message", "请求头错误");
		}else{
			flag =true;
			/*if("0".equals(type)){*/
			try{
				webOrApp = true;
				result = JSON.parseObject(jsonString, Map.class);
			}catch(Exception e){
				this.type = 1;
				webOrApp = false;
				this.request = request;
			}
			/*}else{
				webOrApp = false;
				this.request = request;
			}*/
		}
	}
	public Object getData(String str){
		if(flag){
			if(webOrApp){
				return result.get(str);
			}else{
				return request.getParameter(str);
			}
		}else{
			return result.get("message");
		}
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Map<String, Object> getResult() {
		return result;
	}
	public void setResult(Map<String, Object> result) {
		this.result = result;
	}
	
	
}
