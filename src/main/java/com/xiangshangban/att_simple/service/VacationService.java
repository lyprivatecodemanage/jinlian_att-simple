package com.xiangshangban.att_simple.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xiangshangban.att_simple.bean.ReturnData;
import com.xiangshangban.att_simple.bean.Paging;

@Transactional(propagation=Propagation.REQUIRES_NEW)
public interface VacationService {

	 /**
     * 按照   {部门、姓名、年假总额排序、调休总额排序、年假剩余排序、调休剩余排序}  进行模糊分页查询
     * @param vacation
     * @return
     */
	ReturnData SelectFuzzyPagel(Paging paging);
	
	/**
	 * 年假微调
	 * @param vacationId
	 * @param vacationMold
	 * @param annualLeave
	 * @param changingReason
	 * @param auditorEmployeeId
	 * @return
	 */
	ReturnData AnnualLeaveAdjustment(String vacationId,String vacationMold,String annualLeave,String adjustingInstruction,String auditorEmployeeId,String year);
	
	/**
	 * 调休调整
	 * @param vacationId
	 * @param vacationMold
	 * @param adjustRest
	 * @param changingReason
	 * @param auditorEmployeeId
	 * @return
	 */
	ReturnData AdjustRestAdjustment(String vacationId,String vacationMold,String adjustRest,String changingReason,String auditorEmployeeId,String year);
    
}