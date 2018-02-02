package com.xiangshangban.att_simple.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xiangshangban.att_simple.bean.ReturnData;

@Transactional(propagation=Propagation.REQUIRES_NEW)
public interface VacationDetailsService {

	ReturnData SelectVacationDetails(String vacationId,String vacationType,String changingReason,String changeingDateRank,
			String pageExcludeNumber,String pageNum,String year);
}
