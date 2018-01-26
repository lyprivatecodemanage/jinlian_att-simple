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
	 * 查询当前公司默认的班次类别
	 * @param companyId
	 * @return
	 */
	ClassesType selectDefaultClassesType(@Param("companyId") String companyId);
	
	/**
	 * 查询所有已经有班次类别的公司ID
	 * @return
	 */
	List<String> selectALLCompany();
	
	/**
	 * 删除指定的班次类型(更新班次类别信息的时候使用)
	 * @param map
	 * @return
	 */
	int removeAppointClassesType(Map map);
	
	/**
	 * 查询当前公司所有的班次类型ID和名称
	 */
	List<ClassesType> selectAllClassesIdAndName(@Param("companyId") String companyId);
	
	/**
	 * 查询指定班次类型的默认信息
	 * @param classesTypeId
	 * @return
	 */
	ClassesType selectPointClassesTypeInfo(@Param("classesTypeId") String classesTypeId);
	
	/**
	 * 根据主键查询班次类型信息
	 * @param id
	 * @return
	 */
	ClassesType selectByPrimaryKey(@Param("id") String id);
	
	/**
	 * 更新班次类别名称
	 * @param classesTypeId
	 * @param classesName
	 * @return
	 */
	int updateClassesTypeName(@Param("classesTypeId")String classesTypeId,@Param("classesName")String classesName);
}