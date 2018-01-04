package com.xiangshangban.att_simple.service;

import java.util.List;

import com.xiangshangban.att_simple.bean.ApplicationType;

public interface ApplicationTypeService {
	/**
	 * @author 李业/查询所有的申请类型
	 * @return
	 */
	List<ApplicationType> selectApplicationType();
}
