package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.VacationDetails;

@Mapper
public interface VacationDetailsMapper {

	int deleteByPrimaryKey(String vacationDetailsId);

    int insert(VacationDetails record);

    int insertSelective(VacationDetails record);

    VacationDetails selectByPrimaryKey(String vacationDetailsId);

    int updateByPrimaryKeySelective(VacationDetails record);

    int updateByPrimaryKey(VacationDetails record);
}