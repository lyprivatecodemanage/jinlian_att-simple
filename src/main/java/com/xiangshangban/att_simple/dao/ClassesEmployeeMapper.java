package com.xiangshangban.att_simple.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.ClassesEmployee;
@Mapper
public interface ClassesEmployeeMapper {
    int deleteByPrimaryKey(String id);

    int insert(ClassesEmployee record);

    ClassesEmployee selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ClassesEmployee record);

    int updateByPrimaryKey(ClassesEmployee record);
    
    /**
     * 根据条件查询数据的总行数（进行分页操作的时候需要返回给前端，除了总行数外还要计算分的总页数）
     * @param map
     * @return
     */
    int selectCountByCondition(Map map);
    
    /**
	 * 获取当前公司人员班次信息
	 * @param map
	 * @return
	 */
	List<Map> selectClassesInfo(Map map);
	
	/**
	 * 查询指定人员排的最后一个班次的时间
	 * @param empId 人员ID
	 * @param companyId
	 * @return
	 */
	String selectEmpLastClassesDate(String empId,String companyId);
	
	/**
	 * 查询指定人员的班次类型信息(班次类型下班时间是次日的，存入的日期要加一天)
	 * @param empId
	 * @param companyId
	 * @return
	 */
	Map selectEmpClassesType(String empId,String companyId);
	
	/**
	 * 添加新的人员班次
	 * @param record
	 * @return
	 */
	int insertSelective(ClassesEmployee record);
	
	/**
	 * 删除指定人员指定日期的排班
	 * @param map
	 * @return
	 */
	int deleteAppointEmpDateClasses(Map map);
	
	//########################<自动排班>############################
	
	/**
	 * 查询当前公司所有人员的班次类型以及班次的周期等信息
	 * @param currentDate 当前日期(计算时间差的时候使用)
	 * @return
	 */
	List<Map> selectAllEmpClassesType(String currentDate);
}