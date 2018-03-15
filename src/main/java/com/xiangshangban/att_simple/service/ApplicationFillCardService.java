package com.xiangshangban.att_simple.service;

import com.xiangshangban.att_simple.bean.Paging;
import com.xiangshangban.att_simple.bean.ReturnData;

public interface ApplicationFillCardService {
	
	/**
	 * 补勤记录模糊分页查询
	 * @param paging
	 * @return
	 */
	ReturnData SelectFuzzyPagel(Paging paging);
}