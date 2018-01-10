package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.CopyPersonWithApplication;
@Mapper
public interface CopyPersonWithApplicationMapper {
    int deleteByPrimaryKey(String id);

    int insert(CopyPersonWithApplication record);

    int insertSelective(CopyPersonWithApplication record);

    CopyPersonWithApplication selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CopyPersonWithApplication record);

    int updateByPrimaryKey(CopyPersonWithApplication record);
}