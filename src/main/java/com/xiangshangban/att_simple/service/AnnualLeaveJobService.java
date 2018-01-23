package com.xiangshangban.att_simple.service;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.AnnualLeaveJob;

public interface AnnualLeaveJobService {
    int deleteByPrimaryKey(String jobId);

    int insert(AnnualLeaveJob record);

    int insertSelective(AnnualLeaveJob record);

    AnnualLeaveJob selectByPrimaryKey(String jobId);

    int updateByPrimaryKeySelective(AnnualLeaveJob record);

    int updateByPrimaryKey(AnnualLeaveJob record);
}