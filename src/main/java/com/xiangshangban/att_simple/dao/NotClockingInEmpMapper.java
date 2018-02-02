package com.xiangshangban.att_simple.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xiangshangban.att_simple.bean.NotClockingInEmp;

/**
 * 无需考勤人员dao
 * @author 王勇辉
 *
 */
@Mapper
public interface NotClockingInEmpMapper {
	
	/**
	 * 添加无需考勤人员
	 * @param notClockingInEmp
	 * @return
	 */
	int insertNotClockingInEmp(NotClockingInEmp notClockingInEmp);
	
	/**
	 * 查询当前公司无需考勤人员列表
	 * @param companyId
	 * @return
	 */
	List<Map> selectNotClockingInEmp(@Param("companyId") String companyId);
	
	/**
	 * 删除指定公司无需考勤人员
	 * @param empList
	 * @return
	 */
	int removeNotClockingInEmp(@Param("companyId") String companyId);
}