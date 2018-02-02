package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.Application;
import com.xiangshangban.att_simple.bean.ApplicationOvertime;
@Mapper
public interface ApplicationOvertimeMapper {
	/**
	 * 添加加班申请记录
	 * @param applicationOvertime
	 * @return
	 */
	int insertApplicationOvertimeRecord(ApplicationOvertime applicationOvertime);
	/**
	 * 根据申请单号查询详细内容
	 * @param applicationNo
	 * @return
	 */
	Application selectDetailsByApplicationNo(String applicationNo);
}