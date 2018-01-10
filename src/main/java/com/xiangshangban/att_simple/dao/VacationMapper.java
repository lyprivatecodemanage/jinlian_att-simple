package com.xiangshangban.att_simple.dao;

import com.xiangshangban.att_simple.bean.Vacation;

public interface VacationMapper {

	int deleteByPrimaryKey(String vacationId);

    int insert(Vacation record);

    int insertSelective(Vacation record);

    Vacation selectByPrimaryKey(String vacationId);

    int updateByPrimaryKeySelective(Vacation record);

    int updateByPrimaryKey(Vacation record);
}