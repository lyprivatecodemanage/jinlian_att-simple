package com.xiangshangban.att_simple.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.NotClockingInEmp;
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
	List<Map> selectNotClockingInEmp(String companyId);
	
	/**
	 * 删除指定公司无需考勤人员
	 * @param empList
	 * @return
	 */
	int removeNotClockingInEmp(String companyId);
}