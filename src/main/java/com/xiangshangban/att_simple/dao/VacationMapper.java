package com.xiangshangban.att_simple.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xiangshangban.att_simple.bean.Vacation;
import com.xiangshangban.att_simple.bean.Paging;

@Mapper
public interface VacationMapper {

	int deleteByPrimaryKey(String vacationId);

    int insert(Vacation record);

    int insertSelective(Vacation record);

    Vacation selectByPrimaryKey(String vacationId);

    int updateByPrimaryKeySelective(Vacation record);

    int updateByPrimaryKey(Vacation record);
    
    /*-------------------------------------华丽的分割线-----------------------------------------*/
    
    /**
     * 按照公司ID、部门ID、人员ID查询单条假期记录
     * @param companyId
     * @param deparmentId
     * @param employeeId
     * @return
     */
    Vacation SelectEmployeeVacation(@Param("companyId")String companyId,@Param("deparmentId")String deparmentId,@Param("employeeId")String employeeId);
    
    /**
     * 查询Vacation表中记录总条数
     * @return
     */
    int SelectTotalNumber(@Param("companyId")String companyId,@Param("departmentId")String departmentId,@Param("employeeName")String employeeName,@Param("year")String year);
    
    /**
     * 按照   {部门、姓名、年假总额排序、调休总额排序、年假剩余排序、调休剩余排序}  进行模糊分页查询
     * @param vacation
     * @return
     */
    List<Vacation> SelectFuzzyPagel(Paging paging);
    
    /**
     * 根据vacationId 对单个员工进行年假调整 修改年假总数、剩余
     * @param companyId
     * @param employeeId
     * @param annualLeaveTotal
     * @param annualLeaveBalance
     * @return
     */
    int UpdateAnnualLeave(@Param("vacationId")String vacationId,@Param("annualLeaveTotal")String annualLeaveTotal,@Param("annualLeaveBalance")String annualLeaveBalance,@Param("year")String year);
    
    /**
     * 根据vacationId 对单个员工进行调休调整 修改调休总数、剩余
     * @param vacationId
     * @param adjustRestTotal
     * @param adjustRestBalance
     * @return
     */
    int UpdateAdjustRest(@Param("vacationId")String vacationId,@Param("adjustRestTotal")String adjustRestTotal,@Param("adjustRestBalance")String adjustRestBalance);
}