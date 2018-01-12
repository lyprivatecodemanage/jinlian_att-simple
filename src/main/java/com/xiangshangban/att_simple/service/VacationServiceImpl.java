package com.xiangshangban.att_simple.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiangshangban.att_simple.bean.Vacation;
import com.xiangshangban.att_simple.dao.VacationMapper;

@Service("vacationService")
public class VacationServiceImpl implements VacationService {

	@Autowired
	VacationMapper vacationMapper;
	
	@Override
	public int deleteByPrimaryKey(String vacationId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Vacation record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(Vacation record) {
		// TODO Auto-generated method stub
		return vacationMapper.insertSelective(record);
	}

	@Override
	public Vacation selectByPrimaryKey(String vacationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(Vacation record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Vacation record) {
		// TODO Auto-generated method stub
		return 0;
	}

}
