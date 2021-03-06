package com.xiangshangban.att_simple.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.Festival;
@Mapper
public interface FestivalMapper {
    int deleteByPrimaryKey(String festivalId);

	int insert(Festival record);

	int insertSelective(Festival record);

	Festival selectByPrimaryKey(String festivalId);

	int updateByPrimaryKeySelective(Festival record);

	int updateByPrimaryKey(Festival record);

	//查询当年所有的法定节假日
	List<Festival> selectAllFestivalInfo();
	
}