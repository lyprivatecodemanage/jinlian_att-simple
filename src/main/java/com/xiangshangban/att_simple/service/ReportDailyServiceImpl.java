package com.xiangshangban.att_simple.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiangshangban.att_simple.bean.ReturnData;
import com.xiangshangban.att_simple.dao.EmployeeDao;
import com.xiangshangban.att_simple.dao.ReportDailyMapper;

@Service("reportDailyService")
public class ReportDailyServiceImpl implements ReportDailyService {

	@Autowired
	EmployeeDao employeeDao;
	
	@Autowired
	ReportDailyMapper reportDailyMapper;
	
	@Override
	public ReturnData CheckingKeyData(String companyId) {
		// TODO Auto-generated method stub
		ReturnData returndata = new ReturnData();
		//获得公司人员数
		int employeeNum = employeeDao.findAllEmployeeByCompanyId(companyId).size();
		
		String attDate = "";
		
		Calendar calendar=Calendar.getInstance();   
		calendar.setTime(new Date()); 
		String year = calendar.get(Calendar.YEAR)+"";
		String month = calendar.get(Calendar.MONTH)+1+"";
		String day = calendar.get(Calendar.DAY_OF_MONTH)-1+"";
		
		if(month.length()<2){
			month = "0"+month;
		}
		if(day.length()<2){
			day = "0"+day;
		}
		
		attDate = year+"-"+month+"-"+day;
		
		reportDailyMapper.selectYesterdayLeaveNumber(companyId, attDate);
		
		
		return returndata;
	}

}
