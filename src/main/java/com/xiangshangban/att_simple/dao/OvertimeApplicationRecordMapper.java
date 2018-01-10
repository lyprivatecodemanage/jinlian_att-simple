package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.OvertimeApplicationRecord;
@Mapper
public interface OvertimeApplicationRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(OvertimeApplicationRecord record);

    int insertSelective(OvertimeApplicationRecord record);

    OvertimeApplicationRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OvertimeApplicationRecord record);

    int updateByPrimaryKey(OvertimeApplicationRecord record);
}