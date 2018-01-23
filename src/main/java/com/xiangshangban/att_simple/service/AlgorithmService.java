package com.xiangshangban.att_simple.service;

import com.xiangshangban.att_simple.bean.AlgorithmParam;
import com.xiangshangban.att_simple.bean.AlgorithmResult;

public interface AlgorithmService {
	/**
	 * 核心计算开始
	 * @param companyId 公司ID
	 * @param employeeId 员工ID
	 * @param countDate 计算日期
	 */
	public void calculate(String companyId,String employeeId, String countDate);
	
	/**
	 * 预计算：计算某一个用户在某一天是否可以计算
	 * @param companyId 公司ID
	 * @param employeeId 员工ID
	 * @param countDate 计算日期
	 * @return true：可以计算，false：不能计算
	 */
    public boolean preCondition(String companyId,String employeeId, String countDate);
    /**
     * 准备计算参数（考勤体系，当天排班，前一天跨天时段在当天的排班，外出/出差/派工申请，加班申请，请假申请，门禁/外勤记录）
     * @param companyId 公司ID
	 * @param employee 员工信息（必须包含员工姓名，岗位，部门，工号等）
	 * @param countDate 计算日期
     * @return 计算参数
     */
    public AlgorithmParam generateAlgorithmParam(String companyId, String employeeId, String countDate);
    /**
     * 计算完成后，处理报表、异常结果数据（保存、推送等）
     * @param algorithmResult
     */
    public void postProcess(AlgorithmParam algorithmParam, AlgorithmResult algorithmResult);
    /**
     * 判断某一天是什么日子
     * @return 0：节假日，1：休息日   2：工作日
     */
    //public AttendenceSettingCalendar whatDay(AlgorithmParam algorithmParam);
}
