package com.xiangshangban.att_simple.dao;

import com.xiangshangban.att_simple.bean.ReportExcept;

public interface ReportExceptMapper {
    int deleteByPrimaryKey(String exceptId);

    int insert(ReportExcept record);

    int insertSelective(ReportExcept record);

    ReportExcept selectByPrimaryKey(String exceptId);

    int updateByPrimaryKeySelective(ReportExcept record);

    int updateByPrimaryKey(ReportExcept record);
}