package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.Application;
import com.xiangshangban.att_simple.bean.ApplicationBusinessTravel;
@Mapper
public interface ApplicationBusinessTravelMapper {
	/**
	 * 添加出差申请记录
	 * @param applicationBusinessTravel
	 * @return
	 */
	int insertApplicationBusinessTravelRecord(ApplicationBusinessTravel applicationBusinessTravel);
	/**
	 * 根据申请单号查询详细内容
	 * @param applicationNo
	 * @return
	 */
	Application selectDetailsByApplicationNo(String applicationNo);
}