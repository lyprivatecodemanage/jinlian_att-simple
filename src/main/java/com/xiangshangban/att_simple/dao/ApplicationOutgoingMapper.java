package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.ApplicationOutgoing;
@Mapper
public interface ApplicationOutgoingMapper {
    int deleteByPrimaryKey(String id);

    int insert(ApplicationOutgoing record);

    int insertSelective(ApplicationOutgoing record);

    ApplicationOutgoing selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ApplicationOutgoing record);

    int updateByPrimaryKey(ApplicationOutgoing record);
}