package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.Application;
import com.xiangshangban.att_simple.bean.ApplicationFillCard;
@Mapper
public interface ApplicationFillCardMapper {
	/**
	 * 添加补卡申请记录
	 * @param applicationFillCard
	 * @return
	 */
	int insertApplicationFillCardRecord(ApplicationFillCard applicationFillCard);
	/**
	 * 根据申请单号查询详细内容
	 * @param applicationNo
	 * @return
	 */
	Application selectDetailsByApplicationNo(String applicationNo);
}