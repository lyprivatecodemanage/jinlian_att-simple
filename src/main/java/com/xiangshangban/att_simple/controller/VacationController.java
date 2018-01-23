package com.xiangshangban.att_simple.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.att_simple.bean.ReturnData;
import com.xiangshangban.att_simple.bean.AnnualLeaveJob;
import com.xiangshangban.att_simple.bean.Paging;
import com.xiangshangban.att_simple.service.AnnualLeaveJobService;
import com.xiangshangban.att_simple.service.VacationService;
import com.xiangshangban.att_simple.utils.FormatUtil;

@RestController
@RequestMapping("/VacationController")
public class VacationController {
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	
	SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	SimpleDateFormat times = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	VacationService vacationService;
	
	@Autowired
	AnnualLeaveJobService annualLeaveJobService;
	
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
		v.setDepartmentId(StringUtils.isEmpty(json.getString("departmentId"))?null:json.getString("departmentId"));
		v.setEmployeeName(StringUtils.isEmpty(json.getString("employeeName"))?null:json.getString("employeeName"));
		v.setAnnualLeaveTotalRank(json.getString("annualLeaveTotalRank"));
		v.setAnnualLeaveBalanceRank(json.getString("annualLeaveBalanceRank"));
		v.setAdjustRestTotalRank(json.getString("adjustRestTotalRank"));
		v.setAdjustRestBalanceRank(json.getString("adjustRestBalanceRank"));
		v.setVarPageNo(StringUtils.isEmpty(json.getString("pageNum"))?null:json.getString("pageNum"));
		v.setPageNum(StringUtils.isEmpty(json.getString("pageRecordNum"))?null:json.getString("pageRecordNum"));
		v.setYear(StringUtils.isEmpty(json.getString("year"))?null:json.getString("year"));
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
		String companyId = request.getHeader("companyId");
		
		JSONObject obj = JSON.parseObject(jsonStirng);
		String vacationId = obj.getString("vacationId");
		String vacationMold = obj.getString("vacationMold");
		String annualLeave = obj.getString("annualLeave");
		String adjustingInstruction = obj.getString("adjustingInstruction");
		String year = obj.getString("year");
		
		result = vacationService.AnnualLeaveAdjustment(vacationId,vacationMold, annualLeave, adjustingInstruction, auditorEmployeeId,year);
		
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
	 * 查询计算年假所需资料不完善人员信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/IncompleteData",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public ReturnData IncompleteData(HttpServletRequest request){
		ReturnData result = new ReturnData();
		String companyId = request.getHeader("companyId");
		
		result = vacationService.IncompleteData(companyId);
		
		return result;
	}
	
	/**
	 * 焦振/年假一键清零
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/ResetAnnualLeave",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public ReturnData ResetAnnualLeave(@RequestBody String objectString,HttpServletRequest request){
		ReturnData result = new ReturnData();
		String companyId = request.getHeader("companyId");
		String auditorEmployeeId = request.getHeader("accessUserId");
		
		JSONObject obj = JSON.parseObject(objectString);
		//定时与否
		String timingJob = obj.getString("timingJob");
		//定时时间
		String timingJobDate = obj.getString("timingJobDate");
		
		if("0".equals(timingJob)){
			//获取上一年的年份
			Calendar now = Calendar.getInstance();
			String year = now.get(Calendar.YEAR)-1+"";
					
			result = vacationService.ResetAnnualLeave(companyId,year,auditorEmployeeId);
					
			return result;
		}else if("1".equals(timingJob)){
			try {
				Date date = times.parse(timingJobDate);
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				String year = c.get(Calendar.YEAR)-1+"";
				
				String createJobDate = time.format(new Date(System.currentTimeMillis()));
				
				AnnualLeaveJob alj = new AnnualLeaveJob(FormatUtil.createUuid(), companyId, auditorEmployeeId, year, timingJobDate, createJobDate, "1", "2");
				
				annualLeaveJobService.insertSelective(alj);
				
				result.setReturnCode("3000");
				result.setMessage("数据请求成功");
				return result;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result.setReturnCode("3001");
				result.setMessage("服务器错误");
				return result;
			}
		}
		
		result.setReturnCode("3001");
		result.setMessage("服务器错误");
		return result;
	}
	
	/**
	 * 焦振/年假一键生成
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/AnnualLeaveGenerate",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public ReturnData AnnualLeaveGenerate(@RequestBody String objectString,HttpServletRequest request){
		ReturnData result = new ReturnData();
		String companyId = request.getHeader("companyId");
		String auditorEmployeeId = request.getHeader("accessUserId");
		
		JSONObject obj = JSON.parseObject(objectString);
		//定时与否
		String timingJob = obj.getString("timingJob");
		//定时时间
		String timingJobDate = obj.getString("timingJobDate");
		
		//定时与否  0：未定时   1：已定时
		if("0".equals(timingJob)){
			//获取上一年的年份
			Calendar now = Calendar.getInstance();
			String year = now.get(Calendar.YEAR)+"";
			
			result = vacationService.AnnualLeaveGenerate(companyId, year,auditorEmployeeId);
			
			return result;
		}else if("1".equals(timingJob)){
			try {
				Date date = times.parse(timingJobDate);
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				String year = c.get(Calendar.YEAR)+"";
				
				String createJobDate = time.format(new Date(System.currentTimeMillis()));
				
				AnnualLeaveJob alj = new AnnualLeaveJob(FormatUtil.createUuid(), companyId, auditorEmployeeId, year, timingJobDate, createJobDate, "2", "2");
				
				annualLeaveJobService.insertSelective(alj);
				
				result.setReturnCode("3000");
				result.setMessage("数据请求成功");
				return result;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result.setReturnCode("3001");
				result.setMessage("服务器错误");
				return result;
			}
		}
		result.setReturnCode("3001");
		result.setMessage("服务器错误");
		return result;
	}
	
}
