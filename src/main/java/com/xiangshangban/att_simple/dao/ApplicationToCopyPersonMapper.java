package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.ApplicationToCopyPerson;
@Mapper
public interface ApplicationToCopyPersonMapper {
    int deleteByPrimaryKey(String id);

    int insert(ApplicationToCopyPerson record);

    int insertSelective(ApplicationToCopyPerson record);

    ApplicationToCopyPerson selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ApplicationToCopyPerson record);

    int updateByPrimaryKey(ApplicationToCopyPerson record);
}