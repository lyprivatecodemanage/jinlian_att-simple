package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.OutgoingApplicationRecord;
@Mapper
public interface OutgoingApplicationRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(OutgoingApplicationRecord record);

    int insertSelective(OutgoingApplicationRecord record);

    OutgoingApplicationRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OutgoingApplicationRecord record);

    int updateByPrimaryKey(OutgoingApplicationRecord record);
}