package com.xiangshangban.att_simple.dao;

import com.xiangshangban.att_simple.bean.Festival;

public interface FestivalMapper {
    int deleteByPrimaryKey(String festivalId);

	int insert(Festival record);

	int insertSelective(Festival record);

	Festival selectByPrimaryKey(String festivalId);

	int updateByPrimaryKeySelective(Festival record);

	int updateByPrimaryKey(Festival record);


	
}