package com.xiangshangban.att_simple.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
	public ReturnData oneKeyChecking(@RequestBody String objectString,HttpServletRequest request){
		ReturnData result = new ReturnData();
		JSONObject obj = JSON.parseObject(objectString);
		List<String> reportIds =JSONArray.parseArray(obj.get("reportIds").toString(),String.class);
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
	public ReturnData selectReportDaily(@RequestBody String objectString,HttpServletRequest request){
		ReturnData result = new ReturnData();
		String companyId = request.getHeader("companyId");
		JSONObject obj = JSON.parseObject(objectString);
		
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
		p.setCompanyId(companyId);
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
	
	/**
	 * 焦振/代补勤
	 * @param obejctString
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/replaceReplenishChecking",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public ReturnData replaceReplenishChecking(@RequestBody String objectString,HttpServletRequest request){
		ReturnData result = new ReturnData();
		JSONObject obj = JSON.parseObject(objectString);
		String companyId = request.getHeader("companyId");
		
		String reportId = obj.getString("reportId");
		String beginDate = obj.getString("beginDate");
		String beginTime = obj.getString("beginTime");
		String endDate = obj.getString("endDate");
		String endTime = obj.getString("endTime");
		String reason = obj.getString("reason");
		
		result = reportDailyService.replaceReplenishChecking(companyId,reportId, beginDate, beginTime, endDate, endTime, reason);
		
		return result;
	}
	
	/**
	 * 导出用户设置时间段考勤日报表
	 * @param objectString
	 * @param request
	 * @return
	 */
	@RequestMapping(value="export/ReportDailyExcel",produces="application/json;charset=UTF-8",method=RequestMethod.POST)
	public void ReportDailyExcel(@RequestBody String objectString,HttpServletRequest request,HttpServletResponse response){
		ReturnData result = new ReturnData();
		try {
			response.setContentType("octets/stream"); 
			String agent = request.getHeader("USER-AGENT");
			JSONObject obj = JSON.parseObject(objectString);
			String beginDate = obj.getString("beginDate");
			String endDate = obj.getString("endDate");
			
			if(StringUtils.isEmpty(beginDate)){
				beginDate = null;
			}
			if(StringUtils.isEmpty(endDate)){
				endDate = null;
			}
			
			String excelName = "ReportDaily.xls";
			if(agent!=null && agent.indexOf("MSIE")==-1&&agent.indexOf("rv:11")==-1 && 
					agent.indexOf("Edge")==-1 && agent.indexOf("Apache-HttpClient")==-1){//非IE
				excelName = new String(excelName.getBytes("UTF-8"), "ISO-8859-1");
				response.addHeader("Content-Disposition", "attachment;filename="+excelName);
			}else{
				response.addHeader("Content-Disposition", "attachment;filename="+java.net.URLEncoder.encode(excelName,"UTF-8"));  	
			}
			response.addHeader("excelName", java.net.URLEncoder.encode(excelName,"UTF-8"));
			OutputStream out = response.getOutputStream();
			// 获取请求头信息
			String companyId = request.getHeader("companyId");
			reportDailyService.ReportDailyExcel(excelName, out,companyId, beginDate, endDate);
			out.flush();  
		} catch (IOException e) {
			System.out.println("导出文件输出流出错了！"+e);
		}
	}
	
}
