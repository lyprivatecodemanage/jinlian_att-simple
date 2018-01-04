package com.xiangshangban.att_simple.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiangshangban.att_simple.bean.ApplicationType;
import com.xiangshangban.att_simple.dao.ApplicationTypeMapper;
@Service("applicationTypeService")
public class ApplicationTypeServiceImpl implements ApplicationTypeService {
	@Autowired
	private ApplicationTypeMapper applicationTypeMapper;
	@Override
	public List<ApplicationType> selectApplicationType() {
		
		return applicationTypeMapper.selectAllApplicationType();
	}

}
