package com.xiangshangban.att_simple.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.att_simple.bean.ReturnData;
import com.xiangshangban.att_simple.bean.Paging;
import com.xiangshangban.att_simple.service.VacationService;

@RestController
@RequestMapping("/VacationController")
public class VacationController {
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	
	@Autowired
	VacationService vacationService;
	
	/**
	 * 焦振/假期列表模糊分页查询
	 * @param jsonString
	 * @return
	 */
	@RequestMapping(value="/SelectFuzzyPagel",produces = "application/json;charset=utf-8",method=RequestMethod.POST)
	public ReturnData SelectFuzzyPagel(@RequestBody String jsonString,HttpServletRequest request){
		ReturnData result = new ReturnData();
		Paging v = new Paging();
		JSONObject json = JSON.parseObject(jsonString);
		String companyId = request.getHeader("companyId");
		
		v.setCompanyId(companyId);
		v.setDepartmentId(json.getString("departmentId"));
		v.setEmployeeName(json.getString("employeeName"));
		v.setAnnualLeaveTotalRank(json.getString("annualLeaveTotalRank"));
		v.setAnnualLeaveBalanceRank(json.getString("annualLeaveBalanceRank"));
		v.setAdjustRestTotalRank(json.getString("adjustRestTotalRank"));
		v.setAdjustRestBalanceRank(json.getString("adjustRestBalanceRank"));
		v.setVarPageNo(json.getString("pageNum"));
		v.setPageNum(json.getString("pageRecordNum"));
		v.setYear(json.getString("year"));
		v.setPageExcludeNumber(String.valueOf((Integer.parseInt(json.getString("pageNum"))-1)*Integer.parseInt(json.getString("pageRecordNum"))));
		
		result = vacationService.SelectFuzzyPagel(v);
		
		return result;
	}
	
	/**
	 * 焦振/年假调整
	 * @param jsonString
	 * @return
	 */
	@RequestMapping(value="/AnnualLeaveAdjustment",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public ReturnData AnnualLeaveAdjustment(@RequestBody String jsonStirng,HttpServletRequest request){
		ReturnData result = new ReturnData();
		
		String auditorEmployeeId = request.getHeader("accessUserId");
		
		JSONObject obj = JSON.parseObject(jsonStirng);
		String vacationId = obj.getString("vacationId");
		String vacationMold = obj.getString("vacationMold");
		String annualLeave = obj.getString("annualLeave");
		String adjustingInstruction = obj.getString("adjustingInstruction");
		String year = obj.getString("year");
		
		result = vacationService.AnnualLeaveAdjustment(vacationId, vacationMold, annualLeave, adjustingInstruction, auditorEmployeeId,year);
		
		return result;
	}
	
	/**
	 * 焦振/调休调整
	 * @param jsonStirng
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/AdjustRestAdjustment",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public ReturnData AdjustRestAdjustment(@RequestBody String jsonStirng,HttpServletRequest request){
		ReturnData result = new ReturnData();
		
		String auditorEmployeeId = request.getHeader("accessUserId");
		
		JSONObject obj = JSON.parseObject(jsonStirng);
		String vacationId = obj.getString("vacationId");
		String vacationMold = obj.getString("vacationMold");
		String adjustRest = obj.getString("adjustRest");
		String adjustingInstruction = obj.getString("adjustingInstruction");
		String year = "0";
		
		result = vacationService.AdjustRestAdjustment(vacationId, vacationMold, adjustRest, adjustingInstruction, auditorEmployeeId,year);
		
		return result;
	}
	
	/**
	 * 焦振/年假一键清零
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/ResetAnnualLeave",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public ReturnData ResetAnnualLeave(HttpServletRequest request){
		ReturnData result = new ReturnData();
		String companyId = request.getHeader("companyId");
		String auditorEmployeeId = request.getHeader("accessUserId");
		
		//获取上一年的年份
		Calendar now = Calendar.getInstance();
		String year = now.get(Calendar.YEAR)-1+"";
		
		result = vacationService.ResetAnnualLeave(companyId, year,auditorEmployeeId);
		
		return result;
	}
	
	/**
	 * 焦振/年假一键生成
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/AnnualLeaveGenerate",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public ReturnData AnnualLeaveGenerate(HttpServletRequest request){
		ReturnData result = new ReturnData();
		String companyId = request.getHeader("companyId");
		
		//获取上一年的年份
		Calendar now = Calendar.getInstance();
		String year = now.get(Calendar.YEAR)+"";
		
		result = vacationService.AnnualLeaveGenerate(companyId, year);
		
		return result;
	}
	
}
