package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.CommonSubmitPeople;
@Mapper
public interface CommonSubmitPeopleMapper {
    int deleteByPrimaryKey(String id);

    int insert(CommonSubmitPeople record);

    int insertSelective(CommonSubmitPeople record);

    CommonSubmitPeople selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CommonSubmitPeople record);

    int updateByPrimaryKey(CommonSubmitPeople record);
}