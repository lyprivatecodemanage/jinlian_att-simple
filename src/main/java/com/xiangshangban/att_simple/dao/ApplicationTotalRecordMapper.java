package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.Application;
import com.xiangshangban.att_simple.bean.ApplicationTotalRecord;
@Mapper
public interface ApplicationTotalRecordMapper {
	
	int insertApplicationRecord(Application application);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/** 系统 **/
	
    int deleteByPrimaryKey(String applcationNo);

    int insert(ApplicationTotalRecord record);

    int insertSelective(ApplicationTotalRecord record);

    ApplicationTotalRecord selectByPrimaryKey(String applcationNo);

    int updateByPrimaryKeySelective(ApplicationTotalRecord record);

    int updateByPrimaryKey(ApplicationTotalRecord record);
}