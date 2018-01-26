package com.xiangshangban.att_simple.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xiangshangban.att_simple.bean.Application;
import com.xiangshangban.att_simple.bean.ApplicationLeave;
import com.xiangshangban.att_simple.bean.Paging;
@Mapper
public interface ApplicationLeaveMapper {
	/**
	 * 根据申请单号查询详细内容
	 * @param applicationNo
	 * @return
	 */
	Application selectDetailsByApplicationNo(String applicationNo);
	/**
	 * 添加请假记录
	 * @param application
	 * @return
	 */
	int insertApplicationRecord(ApplicationLeave applicationLeave);
	/**
	 * 查询申请的时间段是否有重复
	 * @param application
	 * @return
	 */
	Integer selectStartTimeIsRepeat(Application application);
	
	/**
	 * 请假记录模糊分页查询
	 * @param paging
	 * @return
	 */
	List<ApplicationLeave> selectCompleteLeave(Paging paging);
	
	/**
	 * 按照请假记录的模糊条件查询数据条数
	 * @param paging
	 * @return
	 */
	int selectTotalNum(Paging paging);
	
	/**
	 * 查询公司事假、年休、调休的请假次数
	 * @param companyId
	 * @return
	 */
	int selectLeaveKeyData(@Param("companyId")String companyId,@Param("applicationTime")String applicationTime,@Param("leaveType")String leaveType);
}