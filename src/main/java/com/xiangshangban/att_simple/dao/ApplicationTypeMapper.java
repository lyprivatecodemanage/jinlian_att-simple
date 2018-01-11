package com.xiangshangban.att_simple.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.ApplicationType;

@Mapper
public interface ApplicationTypeMapper {
	
    int deleteByPrimaryKey(ApplicationType record);

    int insert(ApplicationType record);

    int insertSelective(ApplicationType record);

    ApplicationType selectByPrimaryKey(ApplicationType record);

    int updateByPrimaryKeySelective(ApplicationType record);

    int updateByPrimaryKey(ApplicationType record);
    
    List<ApplicationType> getApplicationTypeList();
    
    
}