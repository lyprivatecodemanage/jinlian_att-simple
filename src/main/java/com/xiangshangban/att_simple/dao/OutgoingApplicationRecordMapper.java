package com.xiangshangban.att_simple.dao;

import com.xiangshangban.att_simple.bean.OutgoingApplicationRecord;

public interface OutgoingApplicationRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(OutgoingApplicationRecord record);

    int insertSelective(OutgoingApplicationRecord record);

    OutgoingApplicationRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OutgoingApplicationRecord record);

    int updateByPrimaryKey(OutgoingApplicationRecord record);
}