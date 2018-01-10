package com.xiangshangban.att_simple.dao;

import com.xiangshangban.att_simple.bean.OvertimeApplicationRecord;

public interface OvertimeApplicationRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(OvertimeApplicationRecord record);

    int insertSelective(OvertimeApplicationRecord record);

    OvertimeApplicationRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OvertimeApplicationRecord record);

    int updateByPrimaryKey(OvertimeApplicationRecord record);
}