package com.xiangshangban.att_simple.service;

import java.util.List;

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

	/**
	 * 查询公司是否设置过同一种并未执行的定时任务信息
	 */
	@Override
	public int insertSelective(AnnualLeaveJob record) {
		// TODO Auto-generated method stub
		List<AnnualLeaveJob> list = annualLeaveJobMapper.selectExamineJob(record.getCompanyId(), record.getJobType());
		
		//当存在同一种定时任务并且处于未执行状态信息的信息时   将之前记录状态改为取消
		if(list.size()>0){
			for (AnnualLeaveJob alj : list) {
				alj.setJobStatus("3");
				
				annualLeaveJobMapper.updateByPrimaryKeySelective(alj);
			}
		}
		
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

	@Override
	public List<AnnualLeaveJob> SelectTodayJob(String year,String month,String day){
		// TODO Auto-generated method stub
		return annualLeaveJobMapper.SelectTodayJob(year, month, day);
	}
}
