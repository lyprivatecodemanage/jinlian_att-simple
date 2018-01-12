package com.xiangshangban.att_simple.service;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class ClassesServiceImpl implements ClassesService{

	@Override
	public boolean addNewClassesType(String requestParam, String companyId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Map> queryAllClassesTypeInfo(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteAppointClassesType(String requestParam, String companyId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Map> queryClassesInfo(String requestParam, String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean oneButtonScheduling(String requestParam, String companyId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addEmpDutyTime(String requestParam, String companyId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteEmpDutyTime(String requestParam, String companyId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void exportRecordToExcel(String requestParam, String excelName, OutputStream out, String companyId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean addNotClockingInEmpInfo(String requestParam, String companyId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean autoScheduling() {
		// TODO Auto-generated method stub
		return false;
	}
}
