package com.xiangshangban.att_simple.service;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.xiangshangban.att_simple.bean.ClassesType;

/**
 * @author 王勇辉
 * TODO 班次管理业务层 
 */
public interface ClassesService {
	
	/**
	 * 添加新的班次类型
	 * @param requestParam （参数内容同Controller层对应接口）
	 * @param companyId
	 * @return
	 */
	boolean addNewClassesType(String requestParam,String companyId);
	
	/**
	 * 查询当前公司所有班次类型信息（默认显示）
	 * @param companyId 公司ID
	 * @return
	 */
	List<Map> queryAllClassesTypeInfo(String companyId);
	
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
	List<Map> queryClassesInfo(String requestParam,String companyId);

	/**
	 * 一键排班
	 * @param requestParam
	 * @param companyId
	 * @return
	 */
	boolean oneButtonScheduling(String requestParam,String companyId);
	
	/**
	 * 给指定的人员添加指定日期的排班(班次类型不变，允许微调上下班时间)
	 * @param requestParam
	 * @param companyId
	 * @return
	 */
	boolean addEmpDutyTime(String requestParam,String companyId);
	
	/**
	 * 删除指定人员指定日期的排班(数据列表中点击某天的排班，进行删除操作) 	
	 * @param requestParam
	 * @param companyId
	 */
	boolean deleteEmpDutyTime(String requestParam,String companyId);
	
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
