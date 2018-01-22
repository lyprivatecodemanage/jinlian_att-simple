package com.xiangshangban.att_simple.service;

import java.util.List;
import java.util.Map;

/**
 * 无需考勤人员业务层
 * @author 王勇辉
 *
 */
public interface NotClockingInEmpService {
	
	/**
	 * 添加无需考勤人员
	 * @param requestParam
	 * @param companyId
	 * @return
	 */
	boolean addNotClockingInEmp(String requestParam,String companyId);
	
	/**
	 * 查询当前公司无需考勤人员列表
	 * @param companyId
	 * @return
	 */
	List<Map> queryNotClockingInEmp(String companyId);
	
}
