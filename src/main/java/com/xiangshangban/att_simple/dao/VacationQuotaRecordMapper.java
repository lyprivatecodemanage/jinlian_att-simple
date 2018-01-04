package com.xiangshangban.att_simple.dao;

import com.xiangshangban.att_simple.bean.VacationQuotaRecord;

public interface VacationQuotaRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(VacationQuotaRecord record);

    int insertSelective(VacationQuotaRecord record);

    VacationQuotaRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(VacationQuotaRecord record);

    int updateByPrimaryKey(VacationQuotaRecord record);
}