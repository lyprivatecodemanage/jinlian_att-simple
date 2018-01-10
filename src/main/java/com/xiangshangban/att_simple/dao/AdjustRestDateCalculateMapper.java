package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.AdjustRestDateCalculate;

@Mapper
public interface AdjustRestDateCalculateMapper {

	int deleteByPrimaryKey(String adjustRestId);

    int insert(AdjustRestDateCalculate record);

    int insertSelective(AdjustRestDateCalculate record);

    AdjustRestDateCalculate selectByPrimaryKey(String adjustRestId);

    int updateByPrimaryKeySelective(AdjustRestDateCalculate record);

    int updateByPrimaryKey(AdjustRestDateCalculate record);
}