package com.xiangshangban.att_simple.service;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.xiangshangban.att_simple.bean.ClassesEmployee;
import com.xiangshangban.att_simple.bean.ClassesType;
import com.xiangshangban.att_simple.bean.ReturnData;

/**
 * @author 王勇辉
 * TODO 班次管理业务层 
 */
public interface ClassesService {
	
	/**
	 *  根据人员ID和公司ID查询人员的名称
	 * @param empId 
	 * @param companyId
	 * @return
	 */
	String queryEmpNameById(String empId,String companyId);
	
	/**
	 * 添加公司默认常规班
	 * @param company
	 * @return
	 */
	boolean addCompanyDefaultClasses(String company);
	
	/**
	 * 给新入职的员工排（默认常规班）
	 * @param requestParam
	 * @return
	 */
	boolean addDefaultEmpClasses(String companyId,JSONArray empArray);
	
	/**
	 * 添加新的班次类型
	 * @param requestParam （参数内容同Controller层对应接口）
	 * @param companyId
	 * @return
	 */
	boolean addNewClassesType(String requestParam,String companyId);
	
	/**
	 * 查询当前公司所有班次类型ID和名称
	 * @param companyId 公司ID
	 * @return
	 */
	List<ClassesType> queryAllClassesIdAndName(String companyId);
	
	/**
	 * 查询指定的班次类型信息（默认显示）
	 * @param companyId 公司ID
	 * @return
	 */
	Map queryPointClassesTypeInfo(String requestParam,String companyId);
	
	/**
	 * 查询指定班次类别名称
	 * @param classesTypeId
	 */
	ClassesType queryPointClassesTypeData(String classesTypeId);
	
	/**
	 * 删除指定的班次类型
	 * @param requestParam
	 * @param companyId
	 * @return
	 */
	boolean deleteAppointClassesType(String requestParam,String companyId);
	
	/**
	 * 根据条件查询当前公司人员的班次排列
	 * @param requestParam
	 * @param companyId
	 * @return
	 */
	ReturnData queryClassesInfo(String requestParam,String companyId);

	/**
	 * 一键排班
	 * @param companyId 公司ID
	 * @return
	 */
	boolean oneButtonScheduling(String companyId);
	
	/**
	 * 查询指定公司一周之内使用“一键排班”的次数
	 * @param monday 周一日期
	 * @param weekend 周末日期
	 * @param companyId 公司ID
	 * @return
	 */
	int queryOneKeyAccessCount(String monday,String weekend,String companyId);
	
	/**
	 * 给指定的人员添加指定日期的排班
	 * @param requestParam
	 * @param companyId
	 * @return
	 */
	boolean addEmpDutyTime(String requestParam,String companyId);
	
	/**
	 * 删除指定班次	
	 * @param requestParam
	 */
	boolean deleteEmpDutyTime(String requestParam);
	
	/**
	 * 获取指定人员指定日期的班次信息
	 * @param requestParam
	 * @param companyId
	 * @return
	 */
	Map queryPointEmpDateClasses(String empClassesId);
	
	/**
	 * 查询指定人员指定时间区间的班次信息
	 * @param empId 人员ID
	 * @param companyId 人员公司ID
	 * @param startTime 搜索开始时间    如:2018-01-01 
	 * @param endTime 结束时间    如:2018-01-02
	 * @return
	 */
	List<ClassesEmployee> queryPointTimeClasses(String empId,String companyId,String startTime,String endTime);
	
	/**
	 * 根据条件导出排班信息
	 * @param requestParam 查询条件
	 * @param excelName 导出的Excel表格的名称
	 * @param out 输出流
	 * @param companyId
	 */
	void exportRecordToExcel(String requestParam, String excelName, OutputStream out,String companyId);
	
	/**
	 * 自动排班
	 * @return
	 */
	boolean autoScheduling();
	
}
