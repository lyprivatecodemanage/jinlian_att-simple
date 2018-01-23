package com.xiangshangban.att_simple.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiangshangban.att_simple.bean.AnnualLeaveJob;
import com.xiangshangban.att_simple.dao.AnnualLeaveJobMapper;

@Service("annualLeaveJobService")
public class AnnualLeaveJobServiceImpl implements AnnualLeaveJobService {

	@Autowired
	AnnualLeaveJobMapper annualLeaveJobMapper;
	
	@Override
	public int deleteByPrimaryKey(String jobId) {
		// TODO Auto-generated method stub
		return annualLeaveJobMapper.deleteByPrimaryKey(jobId);
	}

	@Override
	public int insert(AnnualLeaveJob record) {
		// TODO Auto-generated method stub
		return annualLeaveJobMapper.insert(record);
	}

	@Override
	public int insertSelective(AnnualLeaveJob record) {
		// TODO Auto-generated method stub
		return annualLeaveJobMapper.insertSelective(record);
	}

	@Override
	public AnnualLeaveJob selectByPrimaryKey(String jobId) {
		// TODO Auto-generated method stub
		return annualLeaveJobMapper.selectByPrimaryKey(jobId);
	}

	@Override
	public int updateByPrimaryKeySelective(AnnualLeaveJob record) {
		// TODO Auto-generated method stub
		return annualLeaveJobMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(AnnualLeaveJob record) {
		// TODO Auto-generated method stub
		return annualLeaveJobMapper.updateByPrimaryKey(record);
	}

}
