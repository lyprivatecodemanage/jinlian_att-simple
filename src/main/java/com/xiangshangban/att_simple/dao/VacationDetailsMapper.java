package com.xiangshangban.att_simple.dao;

import com.xiangshangban.att_simple.bean.VacationDetails;

public interface VacationDetailsMapper {

	int deleteByPrimaryKey(String vacationDetailsId);

    int insert(VacationDetails record);

    int insertSelective(VacationDetails record);

    VacationDetails selectByPrimaryKey(String vacationDetailsId);

    int updateByPrimaryKeySelective(VacationDetails record);

    int updateByPrimaryKey(VacationDetails record);
}