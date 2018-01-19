package com.xiangshangban.att_simple.service;

import java.util.List;

import com.xiangshangban.att_simple.bean.Festival;

/**
 * 法定节假日业务层
 * @author Administrator
 *
 */
public interface FestivalService {
	
	List<Festival> queryAllFestivalInfo();
}
