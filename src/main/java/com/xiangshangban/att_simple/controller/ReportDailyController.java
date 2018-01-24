package com.xiangshangban.att_simple.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xiangshangban.att_simple.bean.ReturnData;
import com.xiangshangban.att_simple.dao.EmployeeDao;
import com.xiangshangban.att_simple.service.ReportDailyService;

@RestController
@RequestMapping("/ReportDailyController")
public class ReportDailyController {

	@Autowired
	ReportDailyService reportDailyService;
	
	//查询日报关键数据*******
	@RequestMapping(value="/CheckingKeyData",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public ReturnData CheckingKeyData(HttpServletRequest request){
		ReturnData result = new ReturnData();
		String companyId = request.getHeader("companyId");
		
		result = reportDailyService.CheckingKeyData(companyId);
		
		return result;
	}
	
}
