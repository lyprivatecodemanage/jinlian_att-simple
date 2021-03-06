package com.xiangshangban.att_simple.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xiangshangban.att_simple.bean.Application;
import com.xiangshangban.att_simple.bean.ApplicationTotalRecord;
@Mapper
public interface ApplicationTotalRecordMapper {
	/**
	 * 添加申请记录
	 * @param application
	 * @return
	 */
	int insertApplicationRecord(Application application);
	/**
	 * 申请列表分页查询
	 * @param employeeId
	 * @param companyId
	 * @param page
	 * @param count
	 * @return
	 */
	List<ApplicationTotalRecord> selectApplicationList(@Param("employeeId")String employeeId,
			@Param("companyId")String companyId,@Param("page")String page,@Param("count")String count);
	/**
	 * 审批列表申请状态:非全部
	 * @param employeeId
	 * @param companyId
	 * @param page
	 * @param count
	 * @param applicationType
	 * @param isComplete
	 * @param isTranser
	 * @param isReject
	 * @param isCopy
	 * @param applicationTimeStart
	 * @param applicationTimeEnd
	 * @param employeeName
	 * @return
	 */
	List<ApplicationTotalRecord> selectApproverListCondition(
			@Param("employeeId")String employeeId,
			@Param("companyId")String companyId,
			@Param("page")String page,
			@Param("count")String count,
			@Param("applicationType")String applicationType,
			@Param("isComplete")String isComplete,
			@Param("isTranser")String isTranser,
			@Param("isReject")String isReject,
			@Param("isCopy")String isCopy,
			@Param("applicationTimeStart")String applicationTimeStart,
			@Param("applicationTimeEnd")String applicationTimeEnd,
			@Param("employeeName")String employeeName);
	/**
	 * 审批列表申请状态:全部
	 * @param employeeId
	 * @param companyId
	 * @param page
	 * @param count
	 * @param applicationType
	 * @param applicationTimeStart
	 * @param applicationTimeEnd
	 * @param employeeName
	 * @return
	 */
	List<ApplicationTotalRecord> selectApproverListTotal(
			@Param("employeeId")String employeeId,
			@Param("companyId")String companyId,
			@Param("page")String page,
			@Param("count")String count,
			@Param("applicationType")String applicationType,
			@Param("statusDescription")String statusDescription,
			@Param("applicationTimeStart")String applicationTimeStart,
			@Param("applicationTimeEnd")String applicationTimeEnd,
			@Param("employeeName")String employeeName);
	
	
	
	/**
	 * 审批详情
	 * @param applicationNo
	 * @return
	 */
	ApplicationTotalRecord selectApproverDetails(@Param("applicationNo")String applicationNo,@Param("companyId")String companyId);
	/**
	 * 待审批条数
	 * @param employeeId
	 * @param companyId
	 * @return
	 */
	Integer selectCountFromWillApprover(@Param("employeeId")String employeeId,
			@Param("companyId")String companyId);
	/**
	 * 未完成工单数
	 * @param employeeId
	 * @param companyId
	 * @return
	 */
	Integer selectCountWillApprover(@Param("companyId")String companyId);
	/**
	 * 本月已完成工单数
	 * @param employeeId
	 * @param companyId
	 * @return
	 */
	Integer selectCountCompletedApprover(@Param("companyId")String companyId,@Param("startTime")String startTime,
			@Param("endTime")String endTime);
	
	
	List<ApplicationTotalRecord> selectWebApproverList(@Param("companyId")String companyId,
			@Param("page")String page,
			@Param("count")String count,
			@Param("departmentId")String departmentId,
			@Param("applicationType")String applicationType,
			@Param("isComplete")String isComplete,
			@Param("employeeName")String employeeName,
			@Param("startTime")String startTime,
			@Param("endTime")String endTime,
			@Param("orderBy")String orderBy
			);
	Integer selectWebApproverListCount(@Param("companyId")String companyId,
			@Param("page")String page,
			@Param("count")String count,
			@Param("departmentId")String departmentId,
			@Param("applicationType")String applicationType,
			@Param("isComplete")String isComplete,
			@Param("employeeName")String employeeName,
			@Param("startTime")String startTime,
			@Param("endTime")String endTime,
			@Param("orderBy")String orderBy
			);
	
	
	/** 系统 **/
	
    int deleteByPrimaryKey(String applicationNo);

    int insert(ApplicationTotalRecord record);

    int insertSelective(ApplicationTotalRecord record);

    ApplicationTotalRecord selectByPrimaryKey(String applicationNo);

    int updateByPrimaryKeySelective(ApplicationTotalRecord record);

    int updateByPrimaryKey(ApplicationTotalRecord record);
}