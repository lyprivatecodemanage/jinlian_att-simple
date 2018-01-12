package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.Application;
import com.xiangshangban.att_simple.bean.ApplicationLeave;
@Mapper
public interface ApplicationLeaveMapper {
	
	int insertApplicationRecord(Application application);
	
	
	
	
	
	
	
	
	
	
	
}