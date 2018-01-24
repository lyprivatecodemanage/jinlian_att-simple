package com.xiangshangban.att_simple.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.xiangshangban.att_simple.bean.ReturnData;

@Transactional(propagation=Propagation.REQUIRES_NEW)
public interface ReportDailyService {

	ReturnData CheckingKeyData(String companyId);
	
}
