package com.xiangshangban.att_simple.dao;

import com.xiangshangban.att_simple.bean.LeaveApplicationRecord;

public interface LeaveApplicationRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(LeaveApplicationRecord record);

    int insertSelective(LeaveApplicationRecord record);

    LeaveApplicationRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LeaveApplicationRecord record);

    int updateByPrimaryKey(LeaveApplicationRecord record);
}