package com.xiangshangban.att_simple.dao;

import com.xiangshangban.att_simple.bean.ApplicationTransferRecord;

public interface ApplicationTransferRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(ApplicationTransferRecord record);

    int insertSelective(ApplicationTransferRecord record);

    ApplicationTransferRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ApplicationTransferRecord record);

    int updateByPrimaryKey(ApplicationTransferRecord record);
}