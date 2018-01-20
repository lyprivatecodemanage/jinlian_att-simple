package com.xiangshangban.att_simple.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xiangshangban.att_simple.bean.Application;
import com.xiangshangban.att_simple.bean.ClassesEmployee;
import com.xiangshangban.att_simple.bean.Employee;

@Mapper
public interface AlgorithmMapper {
	/**
	 * 获得某一天的排班
	 * @param companyId
	 * @param employeeId
	 * @param countDate
	 * @return
	 */
	ClassesEmployee getPlanByDate(@Param("companyId")String companyId, 
			@Param("employeeId")String employeeId, @Param("countDate") String countDate);
	/**
	 * 获得某个时间区间内最早的打卡/补勤记录
	 * @param companyId
	 * @param employeeId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	String getMinSignIn(@Param("companyId")String companyId, 
			@Param("employeeId")String employeeId, @Param("beginTime") String beginTime,
			@Param("endTime") String endTime);
	/**
	 * 获得某个时间区间内最晚的打卡/补勤记录
	 * @param companyId
	 * @param employeeId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	String getMaxSignOut(@Param("companyId")String companyId, 
			@Param("employeeId")String employeeId, @Param("beginTime") String beginTime,
			@Param("endTime") String endTime);
	/**
	 * 获得与某个时间段有交集的请假（主要用于查找与班次有交集的请假）
	 * @param companyId
	 * @param employeeId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	List<Application> getLeaveByTime(@Param("companyId")String companyId, 
			@Param("employeeId")String employeeId, @Param("beginTime") String beginTime,
			@Param("endTime") String endTime);
	/**
	 * 获得与某个时间段有交集的外出（主要用于查找与班次有交集的外出）
	 * @param companyId
	 * @param employeeId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	List<Application> getOutByTime(@Param("companyId")String companyId, 
			@Param("employeeId")String employeeId, @Param("beginTime") String beginTime,
			@Param("endTime") String endTime);
	/**
	 * 获得与某个时间段有交集的出差（主要用于查找与班次有交集的出差）
	 * @param companyId
	 * @param employeeId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	List<Application> getBusinessByTime(@Param("companyId")String companyId, 
			@Param("employeeId")String employeeId, @Param("beginTime") String beginTime,
			@Param("endTime") String endTime);
	/**
	 * 查询某天的加班记录
	 * @param companyId
	 * @param employeeId
	 * @param beginTime
	 * @return
	 */
	List<Application> getOverByTime(@Param("companyId")String companyId, 
			@Param("employeeId")String employeeId, @Param("beginTime") String beginTime);
	/**
	 * 获取员工信息
	 * @param companyId
	 * @param employeeId
	 * @return
	 */
	Employee getEmployeeInfoById(@Param("companyId")String companyId, 
			@Param("employeeId")String employeeId);
}
