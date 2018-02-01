package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.Application;
import com.xiangshangban.att_simple.bean.ApplicationOutgoing;
@Mapper
public interface ApplicationOutgoingMapper {
	/**
	 * 添加外出申请记录
	 * @param applicationOutgoing
	 * @return
	 */
	int insertApplicationOutgoingRecord(ApplicationOutgoing applicationOutgoing);
	/**
	 * 根据申请单号查询详细内容
	 * @param applicationNo
	 * @return
	 */
	Application selectDetailsByApplicationNo(String applicationNo);
}