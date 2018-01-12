package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.ApplicationLeave;
@Mapper
public interface ApplicationLeaveMapper {
    int deleteByPrimaryKey(String id);

    int insert(ApplicationLeave record);

    int insertSelective(ApplicationLeave record);

    ApplicationLeave selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ApplicationLeave record);

    int updateByPrimaryKey(ApplicationLeave record);
}