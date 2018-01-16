package com.xiangshangban.att_simple.service;

import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation=Propagation.REQUIRES_NEW)
public interface VacationDetailsService {

	Map<String,Object> SelectVacationDetails(String vacationId,String vacationType,String changingReason,String changeingDateRank,
			String pageExcludeNumber,String pageNum);
}
