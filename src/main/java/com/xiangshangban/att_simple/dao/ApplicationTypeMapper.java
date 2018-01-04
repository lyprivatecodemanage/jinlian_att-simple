package com.xiangshangban.att_simple.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.ApplicationType;
@Mapper
public interface ApplicationTypeMapper {
	/**
	 * @author 李业/App首页查询所有的申请类型
	 * @return
	 */
	List<ApplicationType> selectAllApplicationType();
	
	
	
	
	
	
	
    int deleteByPrimaryKey(ApplicationType key);

    int insert(ApplicationType record);

    int insertSelective(ApplicationType record);

    ApplicationType selectByPrimaryKey(ApplicationType key);

    int updateByPrimaryKeySelective(ApplicationType record);

    int updateByPrimaryKey(ApplicationType record);
}