package com.xiangshangban.att_simple.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xiangshangban.att_simple.bean.ClassesEmployee;

/**
 *人员班次dao 
 * @author 王勇辉
 *
 */
@Mapper
public interface ClassesEmployeeMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(ClassesEmployee record);

    ClassesEmployee selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ClassesEmployee record);

    int updateByPrimaryKey(ClassesEmployee record);
    
    /**
     * 更改人员班次类型的名称
     * @return
     */
    int updateEmpClassesName(@Param("classesTypeId") String classesTypeId,@Param("classesName") String classesName);
    
   /**
    * 通过人员ID和公司ID查询人员的姓名
    * @param empId 人员ID
    * @param companyId 公司ID
    * @return
    */
    String selectEmpNameById(@Param("empId") String empId,@Param("companyId") String companyId);
    
    /**
     * 根据条件查询数据的总行数（进行分页操作的时候需要返回给前端，除了总行数外还要计算分的总页数）
     * @param map
     * @return
     */
    int selectCountByCondition(Map map);
    
    /**
     * 查询班次类型使用人数排行榜前三名
     * @param companyId
     * @return
     */
    List<Map<String,String>> selectTopThreeClassesType(@Param("companyId") String companyId);
    
    /**
	 * 获取当前公司人员班次信息
	 * @param map
	 * @return
	 */
	List<Map> selectClassesInfo(Map map);
	
	/**
	 * 查询当前公司使用指定班次的人员列表
	 */
	List<Map> selectPointClassesTypeEmp(Map map);
	
	/**
	 * 查询指定人员指定日期的班次信息
	 * @param map
	 * @return
	 */
	ClassesEmployee selectPointEmpDateClasses(@Param("empClassesId") String empClassesId);
	
	/**
	 * 查询指定人员指定时间区间的班次信息
	 * @param map
	 * 参数详情:
	 *	empId:ABC
	 *	companyId:123ABC
	 *	startTime:2018-01-01
	 *	endTime:2018-01-20
	 * @return
	 */
	List<ClassesEmployee> selectPointTimeClasses(Map map);
	
	//########################<一键排班/自动排班>############################
	
	/**
	 * 查询<当前/所有>公司所有已经排过班次的人员ID、班次类型ID(排除临时班次)、最后一次班次的时间
	 * @return
	 */
	List<Map> selectAllClassesEmp(@Param("companyId") String companyId);
	
	/**
	 * 添加新的人员班次
	 * @param record
	 * @return
	 */
	int insertSelective(ClassesEmployee record);
	
	/**
	 * 删除指定班次
	 * @param classesEmpId 班次ID
	 * @return
	 */
	int deleteAppointEmpDateClasses(@Param("empClassesId")String empClassesId);
	
	/**
	 * 删除指定班次类型，指定时间之后的人员排班
	 * @return
	 */
	int deleteAppointClassesTypeEmp(Map map);
	
	/**
	 * (更新/添加)指定人员指定日期的班次
	 * @param classesEmployee
	 * @return
	 */
	int updateAppointEmpDateClasses(ClassesEmployee classesEmployee);
	
	/**
	 * 查询当前公司一周只能使用“一键排班”的次数
	 * @param monday 周一日期
	 * @param weekend 周末日起
	 * @param companyId 公司ID
	 * @return
	 */
	int selectOneKeyAccessCount(@Param("monday") String monday,@Param("weekend")String weekend,@Param("companyId") String companyId);
	
	//########################<自动排班>############################
	
	/**
	 * 查询当前公司所有人员的班次类型以及班次的周期等信息
	 * @param currentDate 当前日期(计算时间差的时候使用)
	 * @return
	 */
	List<Map> selectAllEmpClassesType(@Param("classesEmpId")String currentDate);
	
	/**
	 * 查询该公司人员该日排班信息
	 * @param companyId
	 * @param employeeId
	 * @param theDate
	 * @return
	 */
	ClassesEmployee replenishCheckingDate(@Param("companyId")String companyId,@Param("employeeId")String employeeId,@Param("theDate")String theDate);
	
}