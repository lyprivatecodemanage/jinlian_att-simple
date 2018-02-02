package com.xiangshangban.att_simple.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.ApplicationToCopyPerson;
@Mapper
public interface ApplicationToCopyPersonMapper {
	/**
	 * 根据单号查询抄送人集合
	 * @param applicationNo
	 * @return
	 */
	List<ApplicationToCopyPerson> selectCopyPersonByApplicationNo(String applicationNo);
	
	
	
	
	
	
	
	
	//系统-------------------------------------
    int deleteByPrimaryKey(String id);

    int insert(ApplicationToCopyPerson record);

    int insertSelective(ApplicationToCopyPerson record);

    ApplicationToCopyPerson selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ApplicationToCopyPerson record);

    int updateByPrimaryKey(ApplicationToCopyPerson record);
}