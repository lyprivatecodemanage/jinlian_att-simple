package com.xiangshangban.att_simple.dao;

import com.xiangshangban.att_simple.bean.CopyPersonWithApplication;

public interface CopyPersonWithApplicationMapper {
    int deleteByPrimaryKey(String id);

    int insert(CopyPersonWithApplication record);

    int insertSelective(CopyPersonWithApplication record);

    CopyPersonWithApplication selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CopyPersonWithApplication record);

    int updateByPrimaryKey(CopyPersonWithApplication record);
}