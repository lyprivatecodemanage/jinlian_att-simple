package com.xiangshangban.att_simple.dao;

import com.xiangshangban.att_simple.bean.ReportDaily;
import com.xiangshangban.att_simple.bean.ReportDailyKey;

public interface ReportDailyMapper {
    int deleteByPrimaryKey(ReportDailyKey key);

    int insert(ReportDaily record);

    int insertSelective(ReportDaily record);

    ReportDaily selectByPrimaryKey(ReportDailyKey key);

    int updateByPrimaryKeySelective(ReportDaily record);

    int updateByPrimaryKey(ReportDaily record);
}