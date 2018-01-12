package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.ApplicationCommonSubmitPeople;
@Mapper
public interface ApplicationCommonSubmitPeopleMapper {
    int deleteByPrimaryKey(String id);

    int insert(ApplicationCommonSubmitPeople record);

    int insertSelective(ApplicationCommonSubmitPeople record);

    ApplicationCommonSubmitPeople selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ApplicationCommonSubmitPeople record);

    int updateByPrimaryKey(ApplicationCommonSubmitPeople record);
}