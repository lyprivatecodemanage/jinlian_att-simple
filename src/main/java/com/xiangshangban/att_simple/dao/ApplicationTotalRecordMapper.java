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
	 * 审批列表分页查询
	 * @param employeeId
	 * @param companyId
	 * @param page
	 * @param count
	 * @return
	 */
	List<ApplicationTotalRecord> selectApproverList(@Param("employeeId")String employeeId,
			@Param("companyId")String companyId,@Param("page")String page,@Param("count")String count,
			@Param("applicationType")String applicationType,@Param("isComplete")String isComplete,
			@Param("isTranser")String isTranser,@Param("isReject")String isReject,@Param("isCopy")String isCopy);
	
	
	
	
	
	
	
	
	
	
	
	
	/** 系统 **/
	
    int deleteByPrimaryKey(String applicationNo);

    int insert(ApplicationTotalRecord record);

    int insertSelective(ApplicationTotalRecord record);

    ApplicationTotalRecord selectByPrimaryKey(String applicationNo);

    int updateByPrimaryKeySelective(ApplicationTotalRecord record);

    int updateByPrimaryKey(ApplicationTotalRecord record);
}