package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.LeaveApplicationRecord;
@Mapper
public interface LeaveApplicationRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(LeaveApplicationRecord record);

    int insertSelective(LeaveApplicationRecord record);

    LeaveApplicationRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LeaveApplicationRecord record);

    int updateByPrimaryKey(LeaveApplicationRecord record);
}