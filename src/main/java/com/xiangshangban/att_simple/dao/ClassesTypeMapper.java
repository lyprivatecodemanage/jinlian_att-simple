package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.ClassesType;
@Mapper
public interface ClassesTypeMapper {
    int deleteByPrimaryKey(String id);

	int insert(ClassesType record);

	int insertSelective(ClassesType record);

	ClassesType selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ClassesType record);

	int updateByPrimaryKey(ClassesType record);
}