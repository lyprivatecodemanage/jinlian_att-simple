package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.ClassesEmployee;
@Mapper
public interface ClassesEmployeeMapper {
    int deleteByPrimaryKey(String id);

    int insert(ClassesEmployee record);

    int insertSelective(ClassesEmployee record);

    ClassesEmployee selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ClassesEmployee record);

    int updateByPrimaryKey(ClassesEmployee record);
}