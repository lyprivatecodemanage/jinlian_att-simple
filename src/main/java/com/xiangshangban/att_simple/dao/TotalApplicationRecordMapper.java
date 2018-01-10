package com.xiangshangban.att_simple.dao;

import com.xiangshangban.att_simple.bean.TotalApplicationRecord;

public interface TotalApplicationRecordMapper {
    int deleteByPrimaryKey(String applcationNo);

    int insert(TotalApplicationRecord record);

    int insertSelective(TotalApplicationRecord record);

    TotalApplicationRecord selectByPrimaryKey(String applcationNo);

    int updateByPrimaryKeySelective(TotalApplicationRecord record);

    int updateByPrimaryKey(TotalApplicationRecord record);
}