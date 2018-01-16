package com.xiangshangban.att_simple.controller;

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
import com.xiangshangban.att_simple.bean.paging;
import com.xiangshangban.att_simple.service.AdjustRestDateCalculateService;
import com.xiangshangban.att_simple.service.VacationService;

@RestController
@RequestMapping("/VacationController")
public class VacationController {
	
	@Autowired
	VacationService vacationService;
	
	@Autowired
	AdjustRestDateCalculateService adjustRestDateCalculateService;
	
	/**
	 * 焦振/假期列表模糊分页查询
	 * @param jsonString
	 * @return
	 */
	@RequestMapping(value="/SelectFuzzyPagel",produces = "application/json;charset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> SelectFuzzyPagel(@RequestBody String jsonString,HttpServletRequest request){
		Map<String,Object> result = new HashMap<>();
		paging v = new paging();
		JSONObject json = JSON.parseObject(jsonString);
		String companyId = request.getHeader("companyId");
		
		v.setCompanyId(companyId);
		v.setDepartmentId(json.getString("departmentId"));
		v.setEmployeeName(json.getString("employeeName"));
		v.setAnnualLeaveTotalRank(json.getString("annualLeaveTotalRank"));
		v.setAnnualLeaveBalanceRank(json.getString("annualLeaveBalanceRank"));
		v.setAdjustRestTotalRank(json.getString("adjustRestTotalRank"));
		v.setAdjustRestBalanceRank(json.getString("adjustRestBalanceRank"));
		v.setVarPageNo(json.getString("varPageNo"));
		v.setPageNum(json.getString("pageNum"));
		v.setPageExcludeNumber(String.valueOf((Integer.parseInt(json.getString("varPageNo"))-1)*Integer.parseInt(json.getString("pageNum"))));
		
		result = vacationService.SelectFuzzyPagel(v);
		
		return result;
	}
	
	/**
	 * 焦振/年假调整
	 * @param jsonString
	 * @return
	 */
	@RequestMapping(value="AnnualLeaveAdjustment",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> AnnualLeaveAdjustment(@RequestBody String jsonStirng,HttpServletRequest request){
		Map<String,Object> result = new HashMap<>();
		
		String auditorEmployeeId = request.getHeader("accessUserId");
		
		JSONObject obj = JSON.parseObject(jsonStirng);
		String vacationId = obj.getString("vacationId");
		String vacationMold = obj.getString("vacationMold");
		String annualLeave = obj.getString("annualLeave");
		String adjustingInstruction = obj.getString("adjustingInstruction");
		
		result = vacationService.AnnualLeaveAdjustment(vacationId, vacationMold, annualLeave, adjustingInstruction, auditorEmployeeId);
		
		return result;
	}
	
	/**
	 * 焦振/调休调整
	 * @param jsonStirng
	 * @param request
	 * @return
	 */
	@RequestMapping(value="AdjustRestAdjustment",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> AdjustRestAdjustment(@RequestBody String jsonStirng,HttpServletRequest request){
		Map<String,Object> result = new HashMap<>();
		
		String auditorEmployeeId = request.getHeader("accessUserId");
		
		JSONObject obj = JSON.parseObject(jsonStirng);
		String vacationId = obj.getString("vacationId");
		String vacationMold = obj.getString("vacationMold");
		String adjustRest = obj.getString("adjustRest");
		String adjustingInstruction = obj.getString("adjustingInstruction");
		
		result = vacationService.AdjustRestAdjustment(vacationId, vacationMold, adjustRest, adjustingInstruction, auditorEmployeeId);
		
		return result;
	}
	
	
}
