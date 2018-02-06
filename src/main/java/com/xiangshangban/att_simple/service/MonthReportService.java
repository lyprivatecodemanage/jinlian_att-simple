package com.xiangshangban.att_simple.service;

import java.io.OutputStream;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xiangshangban.att_simple.bean.Paging;
import com.xiangshangban.att_simple.bean.ReturnData;

@Transactional(propagation=Propagation.REQUIRES_NEW)
public interface MonthReportService {

	/**
	 * 月报关键数据查询
	 * @param companyId
	 * @param year
	 * @param month
	 * @return
	 */
	ReturnData monthReportKeyData(String companyId,String year,String month);
	
	/**
	 * 考勤月报模糊分页查询
	 * @param companyId
	 * @param year
	 * @param month
	 * @return
	 */
	ReturnData SelectMonthReportFuzzy(Paging paging);
	
	/**
	 * 月报报表导出
	 * @param excelName
	 * @param out
	 * @param companyId
	 * @param year
	 * @param month
	 * @return
	 */
	void MonthReportExcel(String excelName,OutputStream out,String companyId,String year,String month);
	
	/**
	 * 计算趋势图数据
	 * @param companyId
	 * @param dayNumber
	 * @return
	 */
	ReturnData CheckingTendencyChart(String companyId,String beginDate,String endDate,String type);
	
	/**
	 * 趋势分析 关键数据
	 * @param companyId
	 * @param year
	 * @param month
	 * @return
	 */
	ReturnData TendencyChartKeyData(String companyId,String year,String month);
}
