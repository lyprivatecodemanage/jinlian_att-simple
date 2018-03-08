package com.xiangshangban.att_simple.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiangshangban.att_simple.bean.FillCardRecord;
import com.xiangshangban.att_simple.dao.FillCardRecordMapper;

@Service
public class FillCardRecordServiceImpl implements FillCardRecordService {
	@Autowired
	private FillCardRecordMapper fillCardRecordMapper;
	@Override
	public Integer insertFillCardRecord(FillCardRecord fillCardRecord) {
		
		return fillCardRecordMapper.insertFillCardRecord(fillCardRecord);
	}

}
