package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.ApplicationOutgoing;
@Mapper
public interface ApplicationOutgoingMapper {
	int insertApplicationOutgoingRecord(ApplicationOutgoing applicationOutgoing);
}