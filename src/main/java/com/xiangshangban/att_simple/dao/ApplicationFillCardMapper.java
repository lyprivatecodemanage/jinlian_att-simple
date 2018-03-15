package com.xiangshangban.att_simple.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.Application;
import com.xiangshangban.att_simple.bean.ApplicationFillCard;
import com.xiangshangban.att_simple.bean.Paging;
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
	
	/**
	 * 补勤记录模糊分页查询
	 * @param paging
	 * @return
	 */
	List<ApplicationFillCard> SelectFuzzyPagel(Paging paging);
	
	/**
	 * 查询总数
	 * @param paging
	 * @return
	 */
	int FindAllNumber(Paging paging);
	
	/**
	 * 补勤记录单条详情
	 * @param ApplicationNo
	 * @return
	 */
	ApplicationFillCard SelectApplicationFillCardDetails(String ApplicationNo);
}