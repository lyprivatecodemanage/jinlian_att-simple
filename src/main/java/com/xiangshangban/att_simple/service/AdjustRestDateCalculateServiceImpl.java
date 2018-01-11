package com.xiangshangban.att_simple.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiangshangban.att_simple.bean.AdjustRestDateCalculate;
import com.xiangshangban.att_simple.dao.AdjustRestDateCalculateMapper;

@Service("adjustRestDateCalculateService")
public class AdjustRestDateCalculateServiceImpl implements AdjustRestDateCalculateService {

	@Autowired
	AdjustRestDateCalculateMapper adjustRestDateCalculateMapper;
	
	@Override
	public int deleteByPrimaryKey(String adjustRestId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(AdjustRestDateCalculate record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(AdjustRestDateCalculate record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AdjustRestDateCalculate selectByPrimaryKey(String adjustRestId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(AdjustRestDateCalculate record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(AdjustRestDateCalculate record) {
		// TODO Auto-generated method stub
		return 0;
	}

}
