package com.xiangshangban.att_simple.dao;

import com.xiangshangban.att_simple.bean.VacationQuota;

public interface VacationQuotaMapper {
    int deleteByPrimaryKey(String id);

    int insert(VacationQuota record);

    int insertSelective(VacationQuota record);

    VacationQuota selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(VacationQuota record);

    int updateByPrimaryKey(VacationQuota record);
}