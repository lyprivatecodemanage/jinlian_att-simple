package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.Application;
import com.xiangshangban.att_simple.bean.ApplicationLeave;
@Mapper
public interface ApplicationLeaveMapper {
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
	
	
	
	
	
	
	
	
}