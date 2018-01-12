package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.ApplicationBusinessTravel;
@Mapper
public interface ApplicationBusinessTravelMapper {
    int deleteByPrimaryKey(String id);

    int insert(ApplicationBusinessTravel record);

    int insertSelective(ApplicationBusinessTravel record);

    ApplicationBusinessTravel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ApplicationBusinessTravel record);

    int updateByPrimaryKey(ApplicationBusinessTravel record);
}