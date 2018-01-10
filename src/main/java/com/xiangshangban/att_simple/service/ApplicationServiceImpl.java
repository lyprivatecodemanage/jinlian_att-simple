package com.xiangshangban.att_simple.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiangshangban.att_simple.bean.ApplicationType;
import com.xiangshangban.att_simple.dao.ApplicationTypeMapper;
import com.xiangshangban.att_simple.dao.CopyPersonWithApplicationMapper;
import com.xiangshangban.att_simple.dao.LeaveApplicationRecordMapper;
import com.xiangshangban.att_simple.dao.TotalWillCompleteApplicationMapper;

@Service("applicationTypeService")
public class ApplicationServiceImpl implements ApplicationService {
	
	@Autowired
	private ApplicationTypeMapper applicationTypeMapper;
	@Autowired
	private TotalWillCompleteApplicationMapper  totalWillCompleteApplicationMapper;
	@Autowired
	private LeaveApplicationRecordMapper  leaveApplicationRecordDao;
	@Autowired
	private CopyPersonWithApplicationMapper  copyPersonWithApplicationMapper;

	@Override
	@Transactional
	public Map<String, Object> applicationIndexPage(String employeeId, String companyId) {
		Map<String, Object> result = new HashMap<String,Object>();
		//查询申请类型
		List<ApplicationType> applicationTypeList = applicationTypeMapper.getApplicationTypeList();
		//查询年假剩余,年假额度,调休剩余,调休额度
		//...
		return result;
	}

	@Override
	@Transactional
	public Map<String, Object> leaveApplication(Map<String, String> params) {
		
		return null;
	}

	
}
