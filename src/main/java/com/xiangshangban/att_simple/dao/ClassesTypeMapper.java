package com.xiangshangban.att_simple.dao;

import com.xiangshangban.att_simple.bean.ClassesType;

public interface ClassesTypeMapper {
    int deleteByPrimaryKey(String id);

	int insert(ClassesType record);

	int insertSelective(ClassesType record);

	ClassesType selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ClassesType record);

	int updateByPrimaryKey(ClassesType record);
}