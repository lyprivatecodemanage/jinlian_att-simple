package com.xiangshangban.att_simple.dao;

import com.xiangshangban.att_simple.bean.AnnualLeaveDateCalculate;

public interface AnnualLeaveDateCalculateMapper {

	int deleteByPrimaryKey(String annualLeaveId);

    int insert(AnnualLeaveDateCalculate record);

    int insertSelective(AnnualLeaveDateCalculate record);

    AnnualLeaveDateCalculate selectByPrimaryKey(String annualLeaveId);

    int updateByPrimaryKeySelective(AnnualLeaveDateCalculate record);

    int updateByPrimaryKey(AnnualLeaveDateCalculate record);
}