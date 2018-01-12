package com.xiangshangban.att_simple.dao;

import java.util.List;

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
    
    //按照   {部门、姓名、年假总额排序、调休总额排序、年假剩余排序、调休剩余排序}  进行模糊分页查询
    List<Vacation> SelectFuzzyPagel(Vacation vacation);
    
}