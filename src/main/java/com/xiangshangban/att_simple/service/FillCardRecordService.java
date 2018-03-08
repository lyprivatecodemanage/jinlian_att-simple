package com.xiangshangban.att_simple.service;

import com.xiangshangban.att_simple.bean.FillCardRecord;

public interface FillCardRecordService {
	/**
	 * 添加外勤记录
	 * @param fillCardRecord
	 * @return
	 */
	Integer insertFillCardRecord(FillCardRecord fillCardRecord);
}
