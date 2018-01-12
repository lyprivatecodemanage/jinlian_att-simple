package com.xiangshangban.att_simple.service;

import java.util.Map;

import com.xiangshangban.att_simple.bean.Application;

public interface ApplicationService {
	/**
	 * 获取app申请首页显示信息
	 * @param employeeId
	 * @param companyId
	 * @return
	 */
	Map<String,Object> applicationIndexPage(String employeeId,String companyId);
	/**
	 * 请假申请
	 * @param params
	 * @return
	 */
	Map<String,Object> leaveApplication(Application application);
	/**
	 * 加班申请
	 * @param params
	 * @return
	 */
	Map<String,Object> overTimeApplication(Map<String,String> params);
	/**
	 * 出差申请
	 * @param params
	 * @return
	 */
	Map<String,Object> businessTravelApplication(Map<String,String> params);
	/**
	 * 外出申请
	 * @param params
	 * @return
	 */
	Map<String,Object> outgoingApplication(Map<String,String> params);
	/**
	 * 补卡申请
	 * @param params
	 * @return
	 */
	Map<String,Object> fillCardApplication(Map<String,String> params);
	
	
}
