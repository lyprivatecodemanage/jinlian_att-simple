package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.ApplicationOvertime;
@Mapper
public interface ApplicationOvertimeMapper {
    int deleteByPrimaryKey(String id);

    int insert(ApplicationOvertime record);

    int insertSelective(ApplicationOvertime record);

    ApplicationOvertime selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ApplicationOvertime record);

    int updateByPrimaryKey(ApplicationOvertime record);
}