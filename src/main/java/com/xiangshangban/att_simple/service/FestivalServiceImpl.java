package com.xiangshangban.att_simple.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xiangshangban.att_simple.bean.Festival;
import com.xiangshangban.att_simple.dao.FestivalMapper;

public class FestivalServiceImpl implements FestivalService {
	
	@Autowired
	private FestivalMapper festivalMapper;
	
	@Override
	public List<Festival> queryAllFestivalInfo() {
		
		List<Festival> allFestivalInfo = festivalMapper.selectAllFestivalInfo();
		
		return allFestivalInfo;
	}

}
