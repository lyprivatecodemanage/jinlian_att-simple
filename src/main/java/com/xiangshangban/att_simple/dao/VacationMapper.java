package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xiangshangban.att_simple.bean.Vacation;

@Mapper
public interface VacationMapper {

	int deleteByPrimaryKey(String vacationId);

    int insert(Vacation record);

    int insertSelective(Vacation record);

    Vacation selectByPrimaryKey(String vacationId);

    int updateByPrimaryKeySelective(Vacation record);

    int updateByPrimaryKey(Vacation record);
    
    /*-------------------------------------华丽的分割线-----------------------------------------*/
    
    //按照公司ID、部门ID、人员ID查询单条假期记录
    Vacation SelectEmployeeVacation(@Param("companyId")String companyId,@Param("deparmentId")String deparmentId,@Param("employeeId")String employeeId);
    
}