package com.xiangshangban.att_simple.service;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xiangshangban.att_simple.bean.Employee;
import com.xiangshangban.att_simple.bean.Paging;
import com.xiangshangban.att_simple.bean.ReportDaily;
import com.xiangshangban.att_simple.bean.ReturnData;
import com.xiangshangban.att_simple.dao.EmployeeDao;
import com.xiangshangban.att_simple.dao.ReportDailyMapper;

@Service("monthReportService")
public class MonthReportServiceImpl implements MonthReportService {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	EmployeeDao employeeDao;
	
	@Autowired
	ReportDailyMapper reportDailyMapper;
	
	@Override
	public ReturnData monthReportKeyData(String companyId,String year,String month) {
		// TODO Auto-generated method stub
		ReturnData returndata = new ReturnData();
		Map<String,Object> map = new HashMap<>();
		List<Employee> list = new ArrayList<>();
		int leaveCount = 0;
		
		try{
			/**
			 * 获得公司人员数
			 */
			int employeeNum = employeeDao.findAllEmployeeByCompanyId(companyId).size();
			
			/**
			 * 当月全勤
			 */
			//获取所有在职人员信息
			List<Employee> empList = employeeDao.findAllEmployeeByCompanyId(companyId);
			
			
			//循环人员数据查询整月有无考勤异常
			for (Employee e : empList) {
				String attDate = year+"-"+month;
				
				String dateTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				
				int num = reportDailyMapper.selectMonthEcxeptionEmployee(companyId, e.getEmployeeId(),attDate,dateTime);
				
				//计算公司某月总请假次数
				leaveCount += reportDailyMapper.selectLeaveCount(companyId,e.getEmployeeId(), attDate,dateTime);
				
				if(num==0){
					list.add(e);
				}
			}
			//全勤人数
			int checkingNum = list.size();
			
			map.put("employeeNum",employeeNum);
			map.put("checkingNum",checkingNum);
			map.put("leaveCount",leaveCount);
			
			returndata.setData(JSON.toJSON(map));
			returndata.setReturnCode("3000");
			returndata.setMessage("数据请求成功");
			return returndata;
		}catch(Exception e){
			e.printStackTrace();
			returndata.setReturnCode("3001");
			returndata.setMessage("服务器错误");
			return returndata;
		}
	}

	@Override
	public ReturnData SelectMonthReportFuzzy(Paging paging) {
		// TODO Auto-generated method stub
		ReturnData result = new ReturnData();
		
		List<ReportDaily> list = reportDailyMapper.selectMonthReportFuzzy(paging);
		
		for (ReportDaily reportDaily : list) {
			reportDaily.setWorkTime(String.valueOf(Math.floor(Integer.parseInt(reportDaily.getWorkTime())/30)/2));
			reportDaily.setRealWorkTime(String.valueOf(Math.floor(Integer.parseInt(reportDaily.getRealWorkTime())/30)/2));
			reportDaily.setMatterLeave(String.valueOf(Math.floor(Integer.parseInt(reportDaily.getMatterLeave())/30)/2));
			reportDaily.setLeaveAnnual(String.valueOf(Math.floor(Integer.parseInt(reportDaily.getLeaveAnnual())/30)/2));
			reportDaily.setLeaveDaysOff(String.valueOf(Math.floor(Integer.parseInt(reportDaily.getLeaveDaysOff())/30)/2));
		}
		
		if(list!=null){
			int TotalNum = reportDailyMapper.selectMonthReportFuzzyTotalNumber(paging);
			
			result.setData(JSON.toJSON(list));
			result.setTotalPages(TotalNum);
			int pageCountNum = TotalNum%Integer.parseInt(paging.getPageNum())==0?TotalNum/Integer.parseInt(paging.getPageNum()):TotalNum/Integer.parseInt(paging.getPageNum())+1;
			result.setPagecountNum(pageCountNum);
			result.setReturnCode("3000");
			result.setMessage("数据请求成功");
			return result;
		}
		
		result.setReturnCode("3001");
		result.setMessage("服务器错误");
		return result;
	}

	@Override
	public void MonthReportExcel(String excelName, OutputStream out, String companyId, String year,
			String month) {
		// TODO Auto-generated method stub
		String attDate = year+"-"+month;
		
		String dateTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		
		List<ReportDaily> list = reportDailyMapper.MonthReportExcel(companyId, attDate,dateTime);
		
		String[] headers = new String[]{"部门","姓名*","应出勤(h)","实出勤(h)","事假(h)","年假(h)","调休(h)"};  
		 // 第一步，创建一个webbook，对应一个Excel文件  
		HSSFWorkbook workbook = new HSSFWorkbook();  
        //生成一个表格  
        HSSFSheet sheet = workbook.createSheet(excelName);  
        //设置表格默认列宽度为15个字符  
        sheet.setDefaultColumnWidth(20);  
        //生成一个样式，用来设置标题样式  
        HSSFCellStyle style = workbook.createCellStyle();  
        //设置这些样式  
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);  
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        //生成一个字体  
        HSSFFont font = workbook.createFont();  
        font.setColor(HSSFColor.VIOLET.index);  
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        //把字体应用到当前的样式  
        style.setFont(font);  
        // 生成并设置另一个样式,用于设置内容样式  
        HSSFCellStyle style2 = workbook.createCellStyle();  
        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);  
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
        // 生成另一个字体  
        HSSFFont font2 = workbook.createFont();  
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);  
        // 把字体应用到当前的样式  
        style2.setFont(font2);  
        //产生表格标题行  
        HSSFRow row = sheet.createRow(0);  
        for(int i = 0; i<headers.length;i++){  
            HSSFCell cell = row.createCell(i);  
            cell.setCellStyle(style);  
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);  
            cell.setCellValue(text);  
        }  
        for (int i=0;i<list.size();i++) {  
           ReportDaily rd = list.get(i);  
            row = sheet.createRow(i+1);  
            int j = 0;  
            //"部门","姓名*","应出勤(h)","实出勤(h)","事假(h)","年假(h)","调休(h)"
            row.createCell(j++).setCellValue(rd.getDepartmentName());//部门
            row.createCell(j++).setCellValue(rd.getEmployeeName());//姓名
            //应出勤时间
            double wt = Double.parseDouble(rd.getWorkTime())/60;
            wt = Math.floor(wt*10)/10;
            row.createCell(j++).setCellValue(wt);
            //实出勤时间
            double rwt = Double.parseDouble(rd.getRealWorkTime())/60;
            rwt = Math.floor(rwt*10)/10;
            row.createCell(j++).setCellValue(rwt);
            //事假
            double ml = Double.parseDouble(rd.getMatterLeave())/60;
            ml = Math.floor(ml*10)/10;
            row.createCell(j++).setCellValue(ml);
            //年假
            double la = Double.parseDouble(rd.getLeaveAnnual())/60;
            la = Math.floor(la*10)/10;
            row.createCell(j++).setCellValue(la);
            //调休
            double ldo = Double.parseDouble(rd.getLeaveDaysOff())/60;
            ldo = Math.floor(ldo*10)/10;
            row.createCell(j++).setCellValue(ldo);
        }  
        try {  
            workbook.write(out); 
            
        } catch (IOException e) {  
            e.printStackTrace();  
        }
	}

	/**
	 * 计算趋势图数据
	 */
	@Override
	public ReturnData CheckingTendencyChart(String companyId,String beginDate,String endDate,String type) {
		// TODO Auto-generated method stub
		ReturnData result = new ReturnData();

		String attDate = sdf.format(new Date());
		
		if(StringUtils.isEmpty(companyId)){
			result.setReturnCode("3013");
			result.setMessage("请求头参数缺失");
			return result;
		}
		
		if(StringUtils.isEmpty(beginDate)||StringUtils.isEmpty(endDate)||StringUtils.isEmpty(type)){
			result.setReturnCode("3006");
			result.setMessage("必传参数为空");
			return result;
		}
		
		Map<String,String> map = new HashMap<>();
		map.put(beginDate,"0");
		
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(beginDate));
			while (true) {
				c.add(Calendar.DAY_OF_MONTH,1);
				Date date = c.getTime(); 
				String datetime = sdf.format(date);
				map.put(datetime,"0");
				if(datetime.equals(endDate)){
					break;
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		switch(type){
			case "1"://全勤人数
				if(true){
					List<ReportDaily> list = reportDailyMapper.CheckingTendencyChart(companyId, beginDate, endDate,attDate);
					
					if(list.size()>0){
						
						for (ReportDaily reportDaily : list) {
							map.put(reportDaily.getAttDate(),reportDaily.getCount());
						}
						
						result.setData(JSON.toJSON(map));
						result.setReturnCode("3000");
						result.setMessage("数据请求成功");
						return result;
					}
					result.setData(JSON.toJSON(map));
					result.setReturnCode("3000");
					result.setMessage("数据请求成功");
					return result;
				}
			case "2"://请假人数
				if(true){
					List<ReportDaily> list = reportDailyMapper.LeaveTendencyChart(companyId, beginDate, endDate,attDate);
					
					if(list.size()>0){
						
						for (ReportDaily reportDaily : list) {
							map.put(reportDaily.getAttDate(),reportDaily.getCount());
						}
						
						result.setData(JSON.toJSON(map));
						result.setReturnCode("3000");
						result.setMessage("数据请求成功");
						return result;
					}
					result.setData(JSON.toJSON(map));
					result.setReturnCode("3000");
					result.setMessage("数据请求成功");
					return result;
				}
			case "3"://迟到人数
				if(true){
					List<ReportDaily> list = reportDailyMapper.beLateTendencyChart(companyId, beginDate, endDate,attDate);
					
					if(list.size()>0){
						
						for (ReportDaily reportDaily : list) {
							map.put(reportDaily.getAttDate(),reportDaily.getCount());
						}
						
						result.setData(JSON.toJSON(map));
						result.setReturnCode("3000");
						result.setMessage("数据请求成功");
						return result;
					}
					result.setData(JSON.toJSON(map));
					result.setReturnCode("3000");
					result.setMessage("数据请求成功");
					return result;
				}
			case "4"://早退人数
				if(true){
					List<ReportDaily> list = reportDailyMapper.leaveEarlyTendencyChart(companyId, beginDate, endDate,attDate);
					
					if(list.size()>0){
						
						for (ReportDaily reportDaily : list) {
							map.put(reportDaily.getAttDate(),reportDaily.getCount());
						}
						
						result.setData(JSON.toJSON(map));
						result.setReturnCode("3000");
						result.setMessage("数据请求成功");
						return result;
					}
					result.setData(JSON.toJSON(map));
					result.setReturnCode("3000");
					result.setMessage("数据请求成功");
					return result;
				}
			case "5"://加班人数
				if(true){
					List<ReportDaily> list = reportDailyMapper.overTimeTendencyChart(companyId, beginDate, endDate,attDate);
					
					if(list.size()>0){
						
						for (ReportDaily reportDaily : list) {
							map.put(reportDaily.getAttDate(),reportDaily.getCount());
						}
						
						result.setData(JSON.toJSON(map));
						result.setReturnCode("3000");
						result.setMessage("数据请求成功");
						return result;
					}
					result.setData(JSON.toJSON(map));
					result.setReturnCode("3000");
					result.setMessage("数据请求成功");
					return result;
				}
		}
		
		result.setReturnCode("3001");
		result.setMessage("服务器错误");
		return result;
	}

	@Override
	public ReturnData TendencyChartKeyData(String companyId, String year, String month) {
		// TODO Auto-generated method stub
		ReturnData returndata = new ReturnData();
		Map<String,Object> map = new HashMap<>();
		List<Employee> checkinglist = new ArrayList<>();
		List<Employee> exceptionlist = new ArrayList<>();
		
		try{
			/**
			 * 获得公司人员数
			 */
			int employeeNum = employeeDao.findAllEmployeeByCompanyId(companyId).size();
			
			/**
			 * 当月全勤
			 */
			//获取所有在职人员信息
			List<Employee> empList = employeeDao.findAllEmployeeByCompanyId(companyId);
			
			
			//循环人员数据查询整月有无考勤异常
			for (Employee e : empList) {
				String attDate = year+"-"+month;
				
				String dateTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				
				int num = reportDailyMapper.selectMonthEcxeptionEmployee(companyId, e.getEmployeeId(),attDate,dateTime);
				
				if(num==0){
					checkinglist.add(e);
				}
				if(num>0){
					exceptionlist.add(e);
				}
			}
			//全勤人数
			int checkingNum = checkinglist.size();
			
			//异常人数
			int exceptionNum = exceptionlist.size();
			
			map.put("employeeNum",employeeNum);
			map.put("checkingNum",checkingNum);
			map.put("exceptionNum",exceptionNum);
			
			returndata.setData(JSON.toJSON(map));
			returndata.setReturnCode("3000");
			returndata.setMessage("数据请求成功");
			return returndata;
		}catch(Exception e){
			e.printStackTrace();
			returndata.setReturnCode("3001");
			returndata.setMessage("服务器错误");
			return returndata;
		}
	}

}
