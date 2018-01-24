package com.xiangshangban.att_simple.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xiangshangban.att_simple.bean.AnnualLeaveJob;

@Mapper
public interface AnnualLeaveJobMapper {
    int deleteByPrimaryKey(String jobId);

    int insert(AnnualLeaveJob record);

    int insertSelective(AnnualLeaveJob record);

    AnnualLeaveJob selectByPrimaryKey(String jobId);

    int updateByPrimaryKeySelective(AnnualLeaveJob record);

    int updateByPrimaryKey(AnnualLeaveJob record);
    
    /**
     * 按年月日查询该日任务信息
     * @param year
     * @param month
     * @param day
     * @return
     */
    List<AnnualLeaveJob> SelectTodayJob(@Param("year")String year,@Param("month")String month,@Param("day")String day);
    
    /**
     * 查询公司是否设置过同一种并未执行的定时任务信息
     * @param companyId
     * @param jobType
     * @return
     */
    List<AnnualLeaveJob> selectExamineJob(@Param("companyId")String companyId,@Param("jobType")String jobType);
}