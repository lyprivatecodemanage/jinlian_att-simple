package com.xiangshangban.att_simple.service;

import java.util.List;
import java.util.Map;

import com.xiangshangban.att_simple.bean.Application;
import com.xiangshangban.att_simple.bean.ApplicationCommonContactPeople;
import com.xiangshangban.att_simple.bean.ReturnData;

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
	ReturnData leaveApplication(Application application);
	/**
	 * 加班申请
	 * @param params
	 * @return
	 */
	ReturnData overTimeApplication(Application application);
	/**
	 * 出差申请
	 * @param params
	 * @return
	 */
	ReturnData businessTravelApplication(Application application);
	/**
	 * 外出申请
	 * @param params
	 * @return
	 */
	ReturnData outgoingApplication(Application application);
	/**
	 * 补卡申请
	 * @param params
	 * @return
	 */
	ReturnData fillCardApplication(Application application);
	/**
	 * 判断申请的时间段是否和别的重复
	 * @return
	 */
	boolean isRepeatWithOtherApplication(Application application);
	/**
	 * 设置常用联系人
	 * @param application
	 * @return
	 */
	boolean commonContactPeople(Application application);
	/**
	 * 获取常用联系人列表
	 * @param employeeId
	 * @param companyId
	 * @param type
	 * @param page
	 * @param count
	 * @return
	 */
	List<ApplicationCommonContactPeople> commonContactPeopleList(String employeeId,String companyId,String type,String page,String count);
	
	List<Application> applicationList(String employeeId,String CompanyId,String page,String count);
}
