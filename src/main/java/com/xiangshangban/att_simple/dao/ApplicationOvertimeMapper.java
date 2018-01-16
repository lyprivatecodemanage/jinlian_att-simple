package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.ApplicationOvertime;
@Mapper
public interface ApplicationOvertimeMapper {
	
	int insertApplicationOvertimeRecord(ApplicationOvertime applicationOvertime);
}