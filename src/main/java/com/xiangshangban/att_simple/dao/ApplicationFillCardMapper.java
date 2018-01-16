package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.ApplicationFillCard;
@Mapper
public interface ApplicationFillCardMapper {
	int insertApplicationFillCardRecord(ApplicationFillCard applicationFillCard);
}