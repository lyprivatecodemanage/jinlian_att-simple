package com.xiangshangban.att_simple.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xiangshangban.att_simple.bean.ClassesType;

/**
 * 班次类型dao
 * @author 王勇辉
 */
@Mapper
public interface ClassesTypeMapper {
    int deleteByPrimaryKey(String id);

	int insert(ClassesType record);

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
	int removeAppointClassesType(Map map);
	
	/**
	 * 查询当前公司所有班次类型的默认信息
	 * @param companyId
	 * @return
	 */
	List<ClassesType> selectAllClassesTypeInfo(@Param("companyId") String companyId);
	
	/**
	 * 根据主键查询班次类型信息
	 * @param id
	 * @return
	 */
	ClassesType selectByPrimaryKey(@Param("id") String id);
}