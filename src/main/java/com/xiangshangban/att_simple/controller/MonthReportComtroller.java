package com.xiangshangban.att_simple.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.att_simple.bean.Paging;
import com.xiangshangban.att_simple.bean.ReturnData;
import com.xiangshangban.att_simple.service.MonthReportService;

@RestController
@RequestMapping("/MonthReportComtroller")
public class MonthReportComtroller {

	@Autowired
	MonthReportService monthReportService;
	
	/**
	 * 焦振 /月报数据统计
	 * @param object
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/MonthReportKeyData",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public ReturnData MonthReportKeyData(@RequestBody String object,HttpServletRequest request){
		ReturnData result = new ReturnData();
		
		String companyId = request.getHeader("compnayId");
		
		JSONObject obj = JSON.parseObject(object);
		String year = obj.getString("year");
		String month = obj.getString("month");
		
		result = monthReportService.monthReportKeyData(companyId, year, month);
		
		return result;
	}
	
	
	/**
	 * 焦振 /数字报表(考勤月报模糊分页查询)
	 * @param object
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/SelectMonthReportFuzzy",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public ReturnData SelectMonthReportFuzzy(@RequestBody String object,HttpServletRequest request){
		ReturnData result = new ReturnData();
		
		String companyId = request.getHeader("compnayId");
		JSONObject obj = JSON.parseObject(object);
		String year = obj.getString("year");
		String month = obj.getString("month");
		String varPageNo = obj.getString("pageNum");
		String pageNum = obj.getString("pageRecordNum");
		
		Paging p = new Paging();
		p.setCompanyId(companyId);
		p.setYear(year);
		p.setMonth(month);
		p.setPageExcludeNumber(String.valueOf((Integer.parseInt(varPageNo)-1)*Integer.parseInt(pageNum)));
		p.setPageNum(pageNum);
		
		result = monthReportService.SelectMonthReportFuzzy(p);
		
		return result;
	}
}
