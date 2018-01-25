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
import com.xiangshangban.att_simple.service.ReportDailyService;

@RestController
@RequestMapping("/ReportDailyController")
public class ReportDailyController {

	@Autowired
	ReportDailyService reportDailyService;
	
	/**
	 * 焦振/查询日报关键数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/checkingKeyData",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public ReturnData checkingKeyData(HttpServletRequest request){
		ReturnData result = new ReturnData();
		String companyId = request.getHeader("companyId");
		
		result = reportDailyService.CheckingKeyData(companyId);
		
		return result;
	}
	
	/**
	 * 焦振/一键补勤
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/oneKeyChecking",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public ReturnData oneKeyChecking(@RequestBody String obejctString,HttpServletRequest request){
		ReturnData result = new ReturnData();
		JSONObject obj = JSON.parseObject(obejctString);
		String [] reportIds = (String [])obj.get("reportIds");
		String companyId = request.getHeader("companyId");
		
		result = reportDailyService.oneKeyChecking(reportIds,companyId);
		
		return result;
	}
	
	/**
	 * 焦振 /日报模糊查询列表
	 * @param obejctString
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/selectReportDaily",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public ReturnData selectReportDaily(@RequestBody String obejctString,HttpServletRequest request){
		ReturnData result = new ReturnData();
		String companyId = request.getHeader("companyId");
		JSONObject obj = JSON.parseObject(obejctString);
		
		String beginDate = obj.getString("beginDate");
		String endDate = obj.getString("endDate");
		String departmentId = obj.getString("departmentId");
		String exceptionMark = obj.getString("exceptionMark");
		String employeeName = obj.getString("employeeName");
		String signInTimeRank = obj.getString("signInTimeRank");
		String signOutTimeRank = obj.getString("signOutTimeRank");
		String realWorkTimeRank = obj.getString("realWorkTimeRank");
		String varPageNo = obj.getString("pageNum");
		String pageNum = obj.getString("pageRecordNum");
		
		Paging p = new Paging();
		p.setBeginDate(beginDate);
		p.setEndDate(endDate);
		p.setDepartmentId(departmentId);
		p.setExceptionMark(exceptionMark);
		p.setEmployeeName(employeeName);
		p.setSignInTimeRank(signInTimeRank);
		p.setSignOutTimeRank(signOutTimeRank);
		p.setRealWorkTimeRank(realWorkTimeRank);
		p.setPageExcludeNumber(String.valueOf((Integer.parseInt(varPageNo)-1)*Integer.parseInt(pageNum)));
		p.setPageNum(pageNum);
		
		result = reportDailyService.selectReportDaily(p);
		
		return result;
	}
}
