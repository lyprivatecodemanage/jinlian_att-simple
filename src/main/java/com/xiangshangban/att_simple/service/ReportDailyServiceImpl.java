package com.xiangshangban.att_simple.service;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
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
import com.xiangshangban.att_simple.bean.ApplicationFillCard;
import com.xiangshangban.att_simple.bean.ClassesEmployee;
import com.xiangshangban.att_simple.bean.Paging;
import com.xiangshangban.att_simple.bean.ReportDaily;
import com.xiangshangban.att_simple.bean.ReturnData;
import com.xiangshangban.att_simple.dao.ApplicationFillCardMapper;
import com.xiangshangban.att_simple.dao.ClassesEmployeeMapper;
import com.xiangshangban.att_simple.dao.EmployeeDao;
import com.xiangshangban.att_simple.dao.ReportDailyMapper;
import com.xiangshangban.att_simple.utils.FormatUtil;

@Service("reportDailyService")
public class ReportDailyServiceImpl implements ReportDailyService {

	Logger looger = Logger.getLogger(ReportDailyServiceImpl.class);
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	EmployeeDao employeeDao;
	
	@Autowired
	ReportDailyMapper reportDailyMapper;
	
	@Autowired
	ClassesEmployeeMapper classesEmployeeMapper;
	
	@Autowired
	ApplicationFillCardMapper applicationFillCardMapper;
	
	@Autowired
	AlgorithmService algorithmService;
	
	@Override
	public ReturnData CheckingKeyData(String companyId) {
		// TODO Auto-generated method stub
		ReturnData returndata = new ReturnData();
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		try {
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
			
			int leaveNum = reportDailyMapper.selectYesterdayLeaveNumber(companyId, attDate);
			
			int Exception = reportDailyMapper.selectYesterdayExcption(companyId, attDate);
			
			map.put("employeeNum", employeeNum);
			map.put("leaveNum", leaveNum);
			map.put("Exception", Exception);
			
			returndata.setData(JSON.toJSON(map));
			returndata.setReturnCode("3000");
			returndata.setMessage("数据请求成功");
			return returndata;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			looger.info(e);
			returndata.setReturnCode("3001");
			returndata.setMessage("服务器错误");
			return returndata;
		}
		
	}

	/**
	 * 一键补勤
	 */
	@Override
	public ReturnData oneKeyChecking(List<String> reportIds,String companyId) {
		// TODO Auto-generated method stub
		ReturnData returndata = new ReturnData();
		//获取存在考勤异常日报信息集合
		List<ReportDaily> list = new ArrayList<>();
		
		//选择为空直接返回
		if(reportIds.size()<1){
			returndata.setReturnCode("3000");
			returndata.setMessage("数据请求成功");
			return returndata;
		}
		
		for (String rid : reportIds) {
			ReportDaily rd = reportDailyMapper.selectById(rid);
			
			
			//当日报记录存在考勤异常  并且 公司ID与登录人的ID相等(预防缓存数据导致可操作别公司数据问题)时  加入集合准备补勤
			if(rd!=null && "1".equals(rd.getHasException())&& companyId.equals(rd.getCompanyId())){
				list.add(rd);
			}
		}
		
		//若选中人员不存在异常日报 则直接返回
		if(list.size()<1){
			returndata.setReturnCode("3000");
			returndata.setMessage("数据请求成功");
			return returndata;
		}
		
		
		//获得每一个考勤日报信息
		for (ReportDaily rd : list) {
			//根据日报提供的公司ID，人员ID，日报时间  查出当日排班信息
			ClassesEmployee ce = classesEmployeeMapper.replenishCheckingDate(companyId, rd.getEmployeeId(), rd.getAttDate());
			
			//获取系统当前时间
			String date = sdf.format(new Date());
			
			//根据考勤日报的考勤异常类型进行相应代考勤操作
			switch(rd.getExceptionMark()){
				case "1"://迟到异常
					if(ce!=null){
						ApplicationFillCard af = new ApplicationFillCard();
						af.setApplicationNo(FormatUtil.createUuid());
						af.setEmployeeId(rd.getEmployeeId());
						af.setDepartmentId(rd.getDeptId());
						af.setCompanyId(rd.getCompanyId());
						af.setFillCardType("3");
						af.setFillCardTime(ce.getOnDutySchedulingDate());
						af.setReason("一键补勤");
						af.setApprover("0");
						af.setOperaterTime(date);
						af.setIsTransfer("0");
						af.setApplicationTime(date);
						
						applicationFillCardMapper.insertApplicationFillCardRecord(af);
						
						algorithmService.calculate(rd.getCompanyId(),rd.getEmployeeId(),rd.getAttDate());
						
						returndata.setReturnCode("3000");
						returndata.setMessage("数据请求成功");
						return returndata;
					}
					returndata.setReturnCode("3001");
					returndata.setMessage("服务器错误");
					return returndata;
				case "2"://早退异常
					if(ce!=null){
						ApplicationFillCard af = new ApplicationFillCard();
						af.setApplicationNo(FormatUtil.createUuid());
						af.setEmployeeId(rd.getEmployeeId());
						af.setDepartmentId(rd.getDeptId());
						af.setCompanyId(rd.getCompanyId());
						af.setFillCardType("4");
						af.setFillCardTime(ce.getOffDutySchedulingDate());
						af.setReason("一键补勤");
						af.setApprover("0");
						af.setOperaterTime(date);
						af.setIsTransfer("0");
						af.setApplicationTime(date);
						
						applicationFillCardMapper.insertApplicationFillCardRecord(af);
						
						algorithmService.calculate(rd.getCompanyId(),rd.getEmployeeId(),rd.getAttDate());
						
						returndata.setReturnCode("3000");
						returndata.setMessage("数据请求成功");
						return returndata;
					}
					returndata.setReturnCode("3001");
					returndata.setMessage("服务器错误");
					return returndata;
				case "3"://未到异常
					if(ce!=null){
						ApplicationFillCard af = new ApplicationFillCard();
						af.setApplicationNo(FormatUtil.createUuid());
						af.setEmployeeId(rd.getEmployeeId());
						af.setDepartmentId(rd.getDeptId());
						af.setCompanyId(rd.getCompanyId());
						af.setFillCardType("1");
						af.setFillCardTime(ce.getOnDutySchedulingDate());
						af.setReason("一键补勤");
						af.setApprover("0");
						af.setOperaterTime(date);
						af.setIsTransfer("0");
						af.setApplicationTime(date);
						
						applicationFillCardMapper.insertApplicationFillCardRecord(af);
						
						algorithmService.calculate(rd.getCompanyId(),rd.getEmployeeId(),rd.getAttDate());
						
						returndata.setReturnCode("3000");
						returndata.setMessage("数据请求成功");
						return returndata;
					}
					returndata.setReturnCode("3001");
					returndata.setMessage("服务器错误");
					return returndata;
				case "4"://未退异常
					if(ce!=null){
						ApplicationFillCard af = new ApplicationFillCard();
						af.setApplicationNo(FormatUtil.createUuid());
						af.setEmployeeId(rd.getEmployeeId());
						af.setDepartmentId(rd.getDeptId());
						af.setCompanyId(rd.getCompanyId());
						af.setFillCardType("2"); 
						af.setFillCardTime(ce.getOffDutySchedulingDate());
						af.setReason("一键补勤");
						af.setApprover("0");
						af.setOperaterTime(date);
						af.setIsTransfer("0");
						af.setApplicationTime(date);
						
						applicationFillCardMapper.insertApplicationFillCardRecord(af);
						
						algorithmService.calculate(rd.getCompanyId(),rd.getEmployeeId(),rd.getAttDate());
						
						returndata.setReturnCode("3000");
						returndata.setMessage("数据请求成功");
						return returndata;
					}
					returndata.setReturnCode("3001");
					returndata.setMessage("服务器错误");
					return returndata;
				case "5"://迟到且早退异常
					if(ce!=null){
						ApplicationFillCard af = new ApplicationFillCard();
						af.setApplicationNo(FormatUtil.createUuid());
						af.setEmployeeId(rd.getEmployeeId());
						af.setDepartmentId(rd.getDeptId());
						af.setCompanyId(rd.getCompanyId());
						af.setFillCardType("3");
						af.setFillCardTime(ce.getOnDutySchedulingDate());
						af.setReason("一键补勤");
						af.setApprover("0");
						af.setOperaterTime(date);
						af.setIsTransfer("0");
						af.setApplicationTime(date);
						
						int num = applicationFillCardMapper.insertApplicationFillCardRecord(af);
						
						if(num>0){
							af.setFillCardType("4");
							af.setFillCardTime(ce.getOffDutySchedulingDate());
							
							applicationFillCardMapper.insertApplicationFillCardRecord(af);
							
							algorithmService.calculate(rd.getCompanyId(),rd.getEmployeeId(),rd.getAttDate());
							
							returndata.setReturnCode("3000");
							returndata.setMessage("数据请求成功");
							return returndata;
						}
					}
					returndata.setReturnCode("3001");
					returndata.setMessage("服务器错误");
					return returndata;
				case "6"://迟到且未退异常
					if(ce!=null){
						ApplicationFillCard af = new ApplicationFillCard();
						af.setApplicationNo(FormatUtil.createUuid());
						af.setEmployeeId(rd.getEmployeeId());
						af.setDepartmentId(rd.getDeptId());
						af.setCompanyId(rd.getCompanyId());
						af.setFillCardType("3"); 
						af.setFillCardTime(ce.getOnDutySchedulingDate());
						af.setReason("一键补勤");
						af.setApprover("0");
						af.setOperaterTime(date);
						af.setIsTransfer("0");
						af.setApplicationTime(date);
						
						int num = applicationFillCardMapper.insertApplicationFillCardRecord(af);
						
						if(num>0){
							af.setFillCardType("2");
							af.setFillCardTime(ce.getOffDutySchedulingDate());
							
							applicationFillCardMapper.insertApplicationFillCardRecord(af);
							//触发日报计算
							algorithmService.calculate(rd.getCompanyId(),rd.getEmployeeId(),rd.getAttDate());
							
							returndata.setReturnCode("3000");
							returndata.setMessage("数据请求成功");
							return returndata;
						}
					}
					returndata.setReturnCode("3001");
					returndata.setMessage("服务器错误");
					return returndata;
				case "7"://未到且早退异常
					if(ce!=null){
						ApplicationFillCard af = new ApplicationFillCard();
						af.setApplicationNo(FormatUtil.createUuid());
						af.setEmployeeId(rd.getEmployeeId());
						af.setDepartmentId(rd.getDeptId());
						af.setCompanyId(rd.getCompanyId());
						af.setFillCardType("1");
						af.setFillCardTime(ce.getOnDutySchedulingDate());
						af.setReason("一键补勤");
						af.setApprover("0");
						af.setOperaterTime(date);
						af.setIsTransfer("0");
						af.setApplicationTime(date);
						
						int num = applicationFillCardMapper.insertApplicationFillCardRecord(af);
						
						if(num>0){
							af.setFillCardType("4");
							af.setFillCardTime(ce.getOffDutySchedulingDate());
							
							applicationFillCardMapper.insertApplicationFillCardRecord(af);
							
							algorithmService.calculate(rd.getCompanyId(),rd.getEmployeeId(),rd.getAttDate());
							
							returndata.setReturnCode("3000");
							returndata.setMessage("数据请求成功");
							return returndata;
						}
					}
					returndata.setReturnCode("3001");
					returndata.setMessage("服务器错误");
					return returndata;
				case "8"://未到且未退异常
					if(ce!=null){
						ApplicationFillCard af = new ApplicationFillCard();
						af.setApplicationNo(FormatUtil.createUuid());
						af.setEmployeeId(rd.getEmployeeId());
						af.setDepartmentId(rd.getDeptId());
						af.setCompanyId(rd.getCompanyId());
						af.setFillCardType("1");
						af.setFillCardTime(ce.getOnDutySchedulingDate());
						af.setReason("一键补勤");
						af.setApprover("0");
						af.setOperaterTime(date);
						af.setIsTransfer("0");
						af.setApplicationTime(date);
						
						int num = applicationFillCardMapper.insertApplicationFillCardRecord(af);
						
						if(num>0){
							af.setFillCardType("2");
							af.setFillCardTime(ce.getOffDutySchedulingDate());
							
							applicationFillCardMapper.insertApplicationFillCardRecord(af);
							
							algorithmService.calculate(rd.getCompanyId(),rd.getEmployeeId(),rd.getAttDate());
							
							returndata.setReturnCode("3000");
							returndata.setMessage("数据请求成功");
							return returndata;
						}
					}
					returndata.setReturnCode("3001");
					returndata.setMessage("服务器错误");
					return returndata;
			}
		}
		
		returndata.setReturnCode("3001");
		returndata.setMessage("服务器错误");
		return returndata;
	}

	@Override
	public ReturnData selectReportDaily(Paging paging) {
		// TODO Auto-generated method stub
		ReturnData returndata = new ReturnData();
		
		List<ReportDaily> list = reportDailyMapper.selectReportDaily(paging);
		
		if(list!=null){
			int count = reportDailyMapper.selectReportDailyTotalNumber(paging);
			
			int pageNo = count%Integer.parseInt(paging.getPageNum())==0?count/Integer.parseInt(paging.getPageNum()):count/Integer.parseInt(paging.getPageNum())+1;
			
			returndata.setTotalPages(count);
			returndata.setPagecountNum(pageNo);
			returndata.setData(JSON.toJSON(list));
			returndata.setReturnCode("3000");
			returndata.setMessage("数据请求成功");
			return returndata;
		}
		returndata.setReturnCode("3001");
		returndata.setMessage("服务器错误");
		return returndata;
	}

	@Override
	public ReturnData replaceReplenishChecking(String companyId,String reportId, String beginDate, String beginTime, String endDate,
			String endTime, String reason) {
		// TODO Auto-generated method stub
		ReturnData returndata = new ReturnData();
		ReportDaily rd = reportDailyMapper.selectById(reportId);
		
		//获取系统当前时间
		String date = sdf.format(new Date());
		
		//当日报记录存在考勤异常  并且 公司ID与登录人的ID相等(预防缓存数据导致可操作别公司数据问题)时  加入集合准备补勤
		if("1".equals(rd.getHasException())&& companyId.equals(rd.getCompanyId())){
			//根据考勤日报的考勤异常类型进行相应代考勤操作
			switch(rd.getExceptionMark()){
			case "1"://迟到异常
					if(true){
						ApplicationFillCard af = new ApplicationFillCard();
						af.setApplicationNo(FormatUtil.createUuid());
						af.setEmployeeId(rd.getEmployeeId());
						af.setDepartmentId(rd.getDeptId());
						af.setCompanyId(rd.getCompanyId());
						af.setFillCardType("3");
						af.setFillCardTime(beginDate+" "+beginTime);
						af.setReason("代补勤");
						af.setApprover("1");
						af.setOperaterTime(date);
						af.setIsTransfer("0");
						af.setApplicationTime(date);
						//新增补卡申请
						int num = applicationFillCardMapper.insertApplicationFillCardRecord(af);
						if(num>0){
							//触发日报计算
							algorithmService.calculate(rd.getCompanyId(),rd.getEmployeeId(),rd.getAttDate());
							
							returndata.setReturnCode("3000");
							returndata.setMessage("数据请求成功");
							return returndata;
						}
						returndata.setReturnCode("3001");
						returndata.setMessage("服务器错误");
						return returndata;
					}
			case "2"://早退异常
					if(true){
						ApplicationFillCard af = new ApplicationFillCard();
						af.setApplicationNo(FormatUtil.createUuid());
						af.setEmployeeId(rd.getEmployeeId());
						af.setDepartmentId(rd.getDeptId());
						af.setCompanyId(rd.getCompanyId());
						af.setFillCardType("4");
						af.setFillCardTime(endDate+" "+endTime);
						af.setReason("代补勤");
						af.setApprover("1");
						af.setOperaterTime(date);
						af.setIsTransfer("0");
						af.setApplicationTime(date);
						
						int num = applicationFillCardMapper.insertApplicationFillCardRecord(af);
						
						if(num>0){
							algorithmService.calculate(rd.getCompanyId(),rd.getEmployeeId(),rd.getAttDate());
							
							returndata.setReturnCode("3000");
							returndata.setMessage("数据请求成功");
							return returndata;
						}
						returndata.setReturnCode("3001");
						returndata.setMessage("服务器错误");
						return returndata;
					}
			case "3"://未到异常
				if(true){
					ApplicationFillCard af = new ApplicationFillCard();
					af.setApplicationNo(FormatUtil.createUuid());
					af.setEmployeeId(rd.getEmployeeId());
					af.setDepartmentId(rd.getDeptId());
					af.setCompanyId(rd.getCompanyId());
					af.setFillCardType("1");
					af.setFillCardTime(beginDate+" "+beginTime);
					af.setReason("代补勤");
					af.setApprover("1");
					af.setOperaterTime(date);
					af.setIsTransfer("0");
					af.setApplicationTime(date);
					
					int num = applicationFillCardMapper.insertApplicationFillCardRecord(af);
					
					if(num>0){
						algorithmService.calculate(rd.getCompanyId(),rd.getEmployeeId(),rd.getAttDate());
						
						returndata.setReturnCode("3000");
						returndata.setMessage("数据请求成功");
						return returndata;
					}
					returndata.setReturnCode("3001");
					returndata.setMessage("服务器错误");
					return returndata;
				}
				
			case "4"://未退异常
				if(true){
					ApplicationFillCard af = new ApplicationFillCard();
					af.setApplicationNo(FormatUtil.createUuid());
					af.setEmployeeId(rd.getEmployeeId());
					af.setDepartmentId(rd.getDeptId());
					af.setCompanyId(rd.getCompanyId());
					af.setFillCardType("2"); 
					af.setFillCardTime(endDate+" "+endTime);
					af.setReason("代补勤");
					af.setApprover("1");
					af.setOperaterTime(date);
					af.setIsTransfer("0");
					af.setApplicationTime(date);
					
					int num = applicationFillCardMapper.insertApplicationFillCardRecord(af);
					
					if(num>0){
						algorithmService.calculate(rd.getCompanyId(),rd.getEmployeeId(),rd.getAttDate());
						
						returndata.setReturnCode("3000");
						returndata.setMessage("数据请求成功");
						return returndata;
					}
					returndata.setReturnCode("3001");
					returndata.setMessage("服务器错误");
					return returndata;
				}
			case "5"://迟到且早退异常
				if(true){
					ApplicationFillCard af = new ApplicationFillCard();
					af.setApplicationNo(FormatUtil.createUuid());
					af.setEmployeeId(rd.getEmployeeId());
					af.setDepartmentId(rd.getDeptId());
					af.setCompanyId(rd.getCompanyId());
					af.setFillCardType("3");
					af.setFillCardTime(beginDate+" "+beginTime);
					af.setReason("代补勤");
					af.setApprover("1");
					af.setOperaterTime(date);
					af.setIsTransfer("0");
					af.setApplicationTime(date);
					
					int num = applicationFillCardMapper.insertApplicationFillCardRecord(af);
					
					if(num>0){
						af.setFillCardType("4");
						af.setFillCardTime(endDate+" "+endTime);
						
						int i = applicationFillCardMapper.insertApplicationFillCardRecord(af);
						
						if(i>0){
							algorithmService.calculate(rd.getCompanyId(),rd.getEmployeeId(),rd.getAttDate());
							
							returndata.setReturnCode("3000");
							returndata.setMessage("数据请求成功");
							return returndata;
						}
					}
					returndata.setReturnCode("3001");
					returndata.setMessage("服务器错误");
					return returndata;
				}
			case "6"://迟到且未退异常
				if(true){
					ApplicationFillCard af = new ApplicationFillCard();
					af.setApplicationNo(FormatUtil.createUuid());
					af.setEmployeeId(rd.getEmployeeId());
					af.setDepartmentId(rd.getDeptId());
					af.setCompanyId(rd.getCompanyId());
					af.setFillCardType("3"); 
					af.setFillCardTime(beginDate+" "+beginTime);
					af.setReason("代补勤");
					af.setApprover("1");
					af.setOperaterTime(date);
					af.setIsTransfer("0");
					af.setApplicationTime(date);
					
					int num = applicationFillCardMapper.insertApplicationFillCardRecord(af);
					
					if(num>0){
						af.setFillCardType("2");
						af.setFillCardTime(endDate+" "+endTime);
						
						int i = applicationFillCardMapper.insertApplicationFillCardRecord(af);

						if(i>0){
							algorithmService.calculate(rd.getCompanyId(),rd.getEmployeeId(),rd.getAttDate());
							
							returndata.setReturnCode("3000");
							returndata.setMessage("数据请求成功");
							return returndata;
						}
					}
					returndata.setReturnCode("3001");
					returndata.setMessage("服务器错误");
					return returndata;
				}
			case "7"://未到且早退异常
				if(true){
					ApplicationFillCard af = new ApplicationFillCard();
					af.setApplicationNo(FormatUtil.createUuid());
					af.setEmployeeId(rd.getEmployeeId());
					af.setDepartmentId(rd.getDeptId());
					af.setCompanyId(rd.getCompanyId());
					af.setFillCardType("1");
					af.setFillCardTime(beginDate+" "+beginTime);
					af.setReason("代补勤");
					af.setApprover("1");
					af.setOperaterTime(date);
					af.setIsTransfer("0");
					af.setApplicationTime(date);
					
					int num = applicationFillCardMapper.insertApplicationFillCardRecord(af);
					
					if(num>0){
						af.setFillCardType("4");
						af.setFillCardTime(endDate+" "+endTime);
						
						int i = applicationFillCardMapper.insertApplicationFillCardRecord(af);
						
						if(i>0){
							algorithmService.calculate(rd.getCompanyId(),rd.getEmployeeId(),rd.getAttDate());
							
							returndata.setReturnCode("3000");
							returndata.setMessage("数据请求成功");
							return returndata;
						}
					}
					returndata.setReturnCode("3001");
					returndata.setMessage("服务器错误");
					return returndata;
				}
			case "8"://未到且未退异常
				if(true){
					ApplicationFillCard af = new ApplicationFillCard();
					af.setApplicationNo(FormatUtil.createUuid());
					af.setEmployeeId(rd.getEmployeeId());
					af.setDepartmentId(rd.getDeptId());
					af.setCompanyId(rd.getCompanyId());
					af.setFillCardType("1");
					af.setFillCardTime(beginDate+" "+beginTime);
					af.setReason("代补勤");
					af.setApprover("1");
					af.setOperaterTime(date);
					af.setIsTransfer("0");
					af.setApplicationTime(date);
					
					int num = applicationFillCardMapper.insertApplicationFillCardRecord(af);
					
					if(num>0){
						af.setFillCardType("2");
						af.setFillCardTime(endDate+" "+endTime);
						
						int i = applicationFillCardMapper.insertApplicationFillCardRecord(af);
						
						if(i>0){
							algorithmService.calculate(rd.getCompanyId(),rd.getEmployeeId(),rd.getAttDate());
							
							returndata.setReturnCode("3000");
							returndata.setMessage("数据请求成功");
							return returndata;
						}
					}
					returndata.setReturnCode("3001");
					returndata.setMessage("服务器错误");
					return returndata;
				}
			}
			returndata.setReturnCode("3000");
			returndata.setMessage("数据请求成功");
			return returndata;
		}

		if(!"1".equals(rd.getHasException())){
			returndata.setReturnCode("4301");
			returndata.setMessage("用户无异常");
			return returndata;
		}
		returndata.setReturnCode("3001");
		returndata.setMessage("服务器错误");
		return returndata;
	}

	@Override
	public ReturnData ReportDailyExcel(String excelName,OutputStream out,String companyId, String beginDate, String endDate) {
		// TODO Auto-generated method stub
		ReturnData returndata = new ReturnData();
		
		List<ReportDaily> list = reportDailyMapper.selectDateRangeReportDaily(companyId, beginDate, endDate);
		
		String[] headers = new String[]{"部门","姓名*","日期","签到时间","签退时间","出勤时长","异常情况",
				"加班时间","请假时间", "出差时间","外出时间"};  
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
            //"部门","姓名*","日期","签到时间","签退时间","出勤时长(h)","异常情况","加班时间(h)","请假时间(h)", "出差时间(h)","外出时间(h)"
            row.createCell(j++).setCellValue(rd.getDepartmentName());//部门
            row.createCell(j++).setCellValue(rd.getEmployeeName());//姓名
            row.createCell(j++).setCellValue(rd.getAttDate());//日期
            row.createCell(j++).setCellValue(rd.getSignInTime());//签到时间
            row.createCell(j++).setCellValue(rd.getSignOutTime());//签退时间
            //出勤时长
            double rwt = Double.parseDouble(rd.getRealWorkTime())/60;
            rwt = Math.floor(rwt*10)/10;
            row.createCell(j++).setCellValue(rwt);
            row.createCell(j++).setCellValue(rd.getExceptionMarkName());//异常情况
            //加班时间
            double now = Double.parseDouble(rd.getNormalOverWork())/60;
            now = Math.floor(now*10)/10;
            row.createCell(j++).setCellValue(now);
            //请假时间
            double ld = Double.parseDouble(rd.getLeaveDate())/60;
            ld = Math.floor(ld*10)/10;
            row.createCell(j++).setCellValue(ld);
            //出差时间
            double etw = Double.parseDouble(rd.getEvectionTimeWork())/60;
            etw = Math.floor(etw*10)/10;
            row.createCell(j++).setCellValue(etw);
            //外出时间
            double otw = Double.parseDouble(rd.getOutTimeWork())/60;
            otw = Math.floor(otw*10)/10;
            row.createCell(j++).setCellValue(otw);
        }  
        try {  
            workbook.write(out); 
            
            returndata.setReturnCode("3000");
    		returndata.setMessage("数据请求成功");
    		return returndata;
        } catch (IOException e) {  
            e.printStackTrace();  
            
            returndata.setReturnCode("3001");
    		returndata.setMessage("服务器错误");
    		return returndata;
        }
	}

}
