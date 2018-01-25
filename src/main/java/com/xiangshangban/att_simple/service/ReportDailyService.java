package com.xiangshangban.att_simple.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xiangshangban.att_simple.bean.Paging;
import com.xiangshangban.att_simple.bean.ReturnData;

@Transactional(propagation=Propagation.REQUIRES_NEW)
public interface ReportDailyService {

	ReturnData CheckingKeyData(String companyId);
	
	ReturnData oneKeyChecking(String [] reportIds,String companyId);
	
	ReturnData selectReportDaily(Paging paging);
	
	ReturnData replaceReplenishChecking(String companyId,String reportId,String beginDate,String beginTime,String endDate,String endTime,String reason);
}
