package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xiangshangban.att_simple.bean.ReportExcept;
@Mapper
public interface ReportExceptMapper {
    int deleteByPrimaryKey(String exceptId);

    int insert(ReportExcept record);

    int insertSelective(ReportExcept record);

    ReportExcept selectByPrimaryKey(String exceptId);

    int updateByPrimaryKeySelective(ReportExcept record);

    int updateByPrimaryKey(ReportExcept record);

	int deleteExceptByDate(@Param("companyId")String companyId, 
		@Param("employeeId")String employeeId, @Param("attDate")String attDate);
}