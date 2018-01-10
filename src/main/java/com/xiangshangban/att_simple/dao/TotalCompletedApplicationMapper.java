package com.xiangshangban.att_simple.dao;

import com.xiangshangban.att_simple.bean.TotalCompletedApplication;

public interface TotalCompletedApplicationMapper {
    int deleteByPrimaryKey(String applcationNo);

    int insert(TotalCompletedApplication record);

    int insertSelective(TotalCompletedApplication record);

    TotalCompletedApplication selectByPrimaryKey(String applcationNo);

    int updateByPrimaryKeySelective(TotalCompletedApplication record);

    int updateByPrimaryKey(TotalCompletedApplication record);
}