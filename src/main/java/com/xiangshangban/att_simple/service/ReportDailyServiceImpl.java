package com.xiangshangban.att_simple.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xiangshangban.att_simple.bean.ApplicationFillCard;
import com.xiangshangban.att_simple.bean.ClassesEmployee;
import com.xiangshangban.att_simple.bean.ClassesType;
import com.xiangshangban.att_simple.bean.Paging;
import com.xiangshangban.att_simple.bean.ReportDaily;
import com.xiangshangban.att_simple.bean.ReturnData;
import com.xiangshangban.att_simple.dao.ApplicationFillCardMapper;
import com.xiangshangban.att_simple.dao.ClassesEmployeeMapper;
import com.xiangshangban.att_simple.dao.ClassesTypeMapper;
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
	public ReturnData oneKeyChecking(String[] reportIds,String companyId) {
		// TODO Auto-generated method stub
		ReturnData returndata = new ReturnData();
		//获取存在考勤异常日报信息集合
		List<ReportDaily> list = new ArrayList<>();
		
		//选择为空直接返回
		if(reportIds.length<1){
			returndata.setReturnCode("3000");
			returndata.setMessage("数据请求成功");
			return returndata;
		}
		
		for (String rid : reportIds) {
			ReportDaily rd = reportDailyMapper.selectById(rid);
			
			//当日报记录存在考勤异常  并且 公司ID与登录人的ID相等(预防缓存数据导致可操作别公司数据问题)时  加入集合准备补勤
			if(rd.getHasException().equals("1")&& companyId.equals(rd.getCompanyId())){
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

}
