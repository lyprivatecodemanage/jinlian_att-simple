package com.xiangshangban.att_simple.dao;

import com.xiangshangban.att_simple.bean.BusinessTravelApplicationRecord;

public interface BusinessTravelApplicationRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusinessTravelApplicationRecord record);

    int insertSelective(BusinessTravelApplicationRecord record);

    BusinessTravelApplicationRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusinessTravelApplicationRecord record);

    int updateByPrimaryKey(BusinessTravelApplicationRecord record);
}