package com.xiangshangban.att_simple.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.ClassesType;
@Mapper
public interface ClassesTypeMapper {
    int deleteByPrimaryKey(String id);

	int insert(ClassesType record);

	ClassesType selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ClassesType record);

	int updateByPrimaryKey(ClassesType record);
	
	/**
	 * 添加新的班次类型
	 * @param record 班次信息实体类
	 * @return
	 */
	int insertSelective(ClassesType record);
	
	/**
	 * 删除指定的班次类型
	 * @param map
	 * @return
	 */
	int deleteAppointClassesType(Map map);
	
	/**
	 * 查询当前公司所有班次类型的默认信息
	 * @param companyId
	 * @return
	 */
	List<Map> selectAllClassesTypeInfo(String companyId);
}