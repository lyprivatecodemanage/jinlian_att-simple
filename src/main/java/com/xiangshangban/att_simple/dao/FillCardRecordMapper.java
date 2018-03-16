package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.FillCardRecord;

@Mapper
public interface FillCardRecordMapper {
	/**
	 * 添加外勤记录
	 * @param fillCardRecord
	 * @return
	 */
	Integer insertFillCardRecord(FillCardRecord fillCardRecord);
}
