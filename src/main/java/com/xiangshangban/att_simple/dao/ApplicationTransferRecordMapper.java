package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.ApplicationTransferRecord;
@Mapper
public interface ApplicationTransferRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(ApplicationTransferRecord record);

    int insertSelective(ApplicationTransferRecord record);

    ApplicationTransferRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ApplicationTransferRecord record);

    int updateByPrimaryKey(ApplicationTransferRecord record);
}