package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.ApplicationBusinessTravel;
@Mapper
public interface ApplicationBusinessTravelMapper {
	int insertApplicationBusinessTravelRecord(ApplicationBusinessTravel applicationBusinessTravel);
}