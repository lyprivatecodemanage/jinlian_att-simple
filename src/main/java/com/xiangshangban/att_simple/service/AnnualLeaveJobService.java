package com.xiangshangban.att_simple.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xiangshangban.att_simple.bean.AnnualLeaveJob;

public interface AnnualLeaveJobService {
    int deleteByPrimaryKey(String jobId);

    int insert(AnnualLeaveJob record);

    int insertSelective(AnnualLeaveJob record);

    AnnualLeaveJob selectByPrimaryKey(String jobId);

    int updateByPrimaryKeySelective(AnnualLeaveJob record);

    int updateByPrimaryKey(AnnualLeaveJob record);
    
    /**
     * 查询该日任务信息
     * @param year
     * @param month
     * @param day
     * @return
     */
    List<AnnualLeaveJob> SelectTodayJob(String year,String month,String day);
}