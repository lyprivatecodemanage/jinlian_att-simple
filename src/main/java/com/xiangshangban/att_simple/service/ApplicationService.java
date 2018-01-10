package com.xiangshangban.att_simple.service;

import java.util.Map;

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
	Map<String,Object> leaveApplication(Map<String,String> params);
}
