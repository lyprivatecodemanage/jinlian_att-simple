package com.xiangshangban.att_simple.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 考勤计算结果
 * @author 韦友弟
 *
 */
public class AlgorithmResult {
	private ReportDaily reportDaily = new ReportDaily();//日报
	private List<ReportExcept> ReportExcept = new ArrayList<ReportExcept>();//异常列表
	public ReportDaily getReportDaily() {
		return reportDaily;
	}
	public void setReportDaily(ReportDaily reportDaily) {
		this.reportDaily = reportDaily;
	}
	public List<ReportExcept> getReportExcept() {
		return ReportExcept;
	}
	public void setReportExcept(List<ReportExcept> reportExcept) {
		ReportExcept = reportExcept;
	}
	
}
