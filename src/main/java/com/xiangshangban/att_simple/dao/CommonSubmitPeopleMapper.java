package com.xiangshangban.att_simple.dao;

import com.xiangshangban.att_simple.bean.CommonSubmitPeople;

public interface CommonSubmitPeopleMapper {
    int deleteByPrimaryKey(String id);

    int insert(CommonSubmitPeople record);

    int insertSelective(CommonSubmitPeople record);

    CommonSubmitPeople selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CommonSubmitPeople record);

    int updateByPrimaryKey(CommonSubmitPeople record);
}