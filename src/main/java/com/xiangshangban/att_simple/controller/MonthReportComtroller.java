package com.xiangshangban.att_simple.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	 * 焦振 /月报关键数据
	 * @param object
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/MonthReportKeyData",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public ReturnData MonthReportKeyData(HttpServletRequest request){
		ReturnData result = new ReturnData();
		
		String companyId = request.getHeader("companyId");
		
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		
		String year = c.get(Calendar.YEAR)+"";
		String month = c.get(Calendar.DAY_OF_MONTH)+1+"";
		
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
		
		String companyId = request.getHeader("companyId");
		JSONObject obj = JSON.parseObject(object);
		String year = obj.getString("year");
		String month = obj.getString("month");
		String varPageNo = obj.getString("pageNum");
		String pageNum = obj.getString("pageRecordNum");
		
		Paging p = new Paging();
		p.setCompanyId(companyId);
		String attDate = year+"-"+month;
		p.setAttDate(attDate);
		p.setPageExcludeNumber(String.valueOf((Integer.parseInt(varPageNo)-1)*Integer.parseInt(pageNum)));
		p.setPageNum(pageNum);
		
		result = monthReportService.SelectMonthReportFuzzy(p);
		
		return result;
	}
	
	/**
	 * 导出月报表
	 * @param objectString
	 * @param request
	 * @return
	 */
	@RequestMapping(value="export/MonthReportExcel",produces="application/json;chatset=utf-8",method=RequestMethod.POST)
	public void MonthReportExcel(@RequestBody String objectString,HttpServletRequest request,HttpServletResponse response){
		try {
			response.setContentType("octets/stream"); 
			String agent = request.getHeader("USER-AGENT");
			JSONObject obj = JSON.parseObject(objectString);
			String year = obj.getString("year");
			String month = obj.getString("month");
			String excelName = "MonthReport.xls";
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
			
			monthReportService.MonthReportExcel(excelName, out,companyId, year, month);
			
			out.flush();  
		} catch (IOException e) {
			System.out.println("导出文件输出流出错了！"+e);
		}
	}
}
