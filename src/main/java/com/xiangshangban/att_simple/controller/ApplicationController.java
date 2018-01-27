package com.xiangshangban.att_simple.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.att_simple.bean.Application;
import com.xiangshangban.att_simple.bean.ApplicationCommonContactPeople;
import com.xiangshangban.att_simple.bean.ApplicationToCopyPerson;
import com.xiangshangban.att_simple.bean.ApplicationTotalRecord;
import com.xiangshangban.att_simple.bean.ApplicationTransferRecord;
import com.xiangshangban.att_simple.bean.ApplicationType;
import com.xiangshangban.att_simple.bean.ClassesEmployee;
import com.xiangshangban.att_simple.bean.Company;
import com.xiangshangban.att_simple.bean.ReturnData;
import com.xiangshangban.att_simple.service.ApplicationService;
import com.xiangshangban.att_simple.service.ClassesService;
import com.xiangshangban.att_simple.service.CompanyService;
import com.xiangshangban.att_simple.service.OSSFileService;
import com.xiangshangban.att_simple.utils.DateCompareUtil;
import com.xiangshangban.att_simple.utils.FormatUtil;
import com.xiangshangban.att_simple.utils.TimeUtil;

/**
 * 
 * @author 李业
 * app申请请求控制器
 */
@RestController
@RequestMapping("/application")
public class ApplicationController {
	
		private static final Logger logger = Logger.getLogger(ApplicationController.class);
		@Autowired
		private ApplicationService applicationService;
		@Autowired
		private ClassesService classesService;
		@Autowired
		private OSSFileService oSSFileService;
		@Autowired
		private CompanyService companyService;
		/**
		 * app申请页面
		 * @param jsonString
		 * @param request
		 * @return
		 */
		@RequestMapping(value = "/applicationIndexPage",produces="application/json;charset=utf-8",method=RequestMethod.POST)
		public  Map<String,Object> applicationIndexPage(@RequestBody String jsonString ,HttpServletRequest request){
			Map<String,Object> result = new HashMap<String,Object>();
			String employeeId = request.getHeader("accessUserId");//员工id
			String companyId = request.getHeader("companyId");//公司id
			String year = request.getHeader("year");//公司id
			if(StringUtils.isEmpty(companyId)||StringUtils.isEmpty(employeeId)){
				result.put("message", "请求信息错误");
				result.put("returnCode", "3012");
				return result;
			}
			result = applicationService.applicationIndexPage(employeeId, companyId,year);
			return result;
		}
		/**
		 * 申请子类型列表
		 * @param jsonString
		 * @param request
		 * @return
		 */
		@RequestMapping(value = "/applicationChildrenTypeList",produces="application/json;charset=utf-8",method=RequestMethod.POST)
		public ReturnData applicationChildrenTypeList(@RequestBody String jsonString ,HttpServletRequest request){
			ReturnData returnData = new ReturnData();
			try{
			String employeeId = request.getHeader("accessUserId");//员工id
			String companyId = request.getHeader("companyId");//公司id
			String applicationType = "";
			if(StringUtils.isEmpty(companyId)||StringUtils.isEmpty(employeeId)){
				returnData.setMessage("请求信息错误");
				returnData.setReturnCode("3012");
				return returnData;
			}
			try{
				JSONObject jobj = JSON.parseObject(jsonString);
				applicationType = jobj.getString("applicationType");
				if(StringUtils.isEmpty(applicationType)){
					returnData.setMessage("必传参数为空");
					returnData.setReturnCode("3006");
					return returnData;
				}
			}catch(Exception e){
				logger.info(e);
				e.printStackTrace();
				returnData.setMessage("请检查参数格式");
				returnData.setReturnCode("9999");
				return returnData;
			}
			List<ApplicationType> applicationChildrenTypeList = applicationService.getApplicationChildrenTypeList(applicationType);
			returnData.setMessage("成功");
			returnData.setReturnCode("3000");
			returnData.setData(applicationChildrenTypeList);
			return returnData;
			}catch(Exception e){
				logger.info(e);
				e.printStackTrace();
				returnData.setMessage("服务器错误");
				returnData.setReturnCode("3001");
				return returnData;
			}
		}
		/**
		 * 申请撤回
		 * @param jsonString
		 * @param request
		 * @return
		 */
		@RequestMapping(value = "/applicationRevoke",produces="application/json;charset=utf-8",method=RequestMethod.POST)
		public ReturnData applicationRevoke(@RequestBody String jsonString ,HttpServletRequest request){
			ReturnData returnData = new ReturnData();
			try{
				String employeeId = request.getHeader("accessUserId");//员工id
				String companyId = request.getHeader("companyId");//公司id
				String applicationNo = "";
				if(StringUtils.isEmpty(companyId)||StringUtils.isEmpty(employeeId)){
					returnData.setMessage("请求信息错误");
					returnData.setReturnCode("3012");
					return returnData;
				}
				try{
					JSONObject jobj = JSON.parseObject(jsonString);
					applicationNo = jobj.getString("applicationNo");
					if(StringUtils.isEmpty(applicationNo)){
						returnData.setMessage("必传参数为空");
						returnData.setReturnCode("3006");
						return returnData;
					}
				}catch(Exception e){
					logger.info(e);
					e.printStackTrace();
					returnData.setMessage("请检查参数格式");
					returnData.setReturnCode("9999");
					return returnData;
				}
				returnData = applicationService.applicationRevoke(applicationNo, companyId, employeeId);
				return returnData;
			}catch(Exception e){
				logger.info(e);
				e.printStackTrace();
				returnData.setMessage("服务器错误");
				returnData.setReturnCode("3001");
				return returnData;
			}
		}
		
		/**
		 * 请假,加班,出差,外出,补卡申请
		 * @param jsonString
		 * @param request
		 * @return
		 */
		@RequestMapping(value = "/allTypeApplication",produces="application/json;charset=utf-8",method=RequestMethod.POST)
		public ReturnData allTypeApplication(@RequestBody String jsonString ,HttpServletRequest request) {
			ReturnData returnData = new ReturnData();
			Map<String,String> params = new HashMap<String,String>();
			String employeeId = request.getHeader("accessUserId");
			String companyId = request.getHeader("companyId");
			String year = request.getHeader("year");
			if(StringUtils.isEmpty(companyId)||StringUtils.isEmpty(employeeId)){
				returnData.setMessage("请求头参数缺失");
				returnData.setReturnCode("3013");
				return returnData;
			}
			Application application = JSON.parseObject(jsonString, Application.class);
			if(application==null||StringUtils.isEmpty(application.getApplicationType())||StringUtils.isEmpty(application.getIsSetCommonContactPeople())
					||StringUtils.isEmpty(application.getIsCopy())){
				returnData.setMessage("必传参数为空");
				returnData.setReturnCode("3006");
				return returnData;
			}
			SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			//系统当前时间
			String date = sdf.format(new Date(System.currentTimeMillis()));
			application.setApplicationId(employeeId);//申请人id
			application.setCompanyId(companyId);//公司id
			application.setApplicationNo(FormatUtil.createUuid());//申请单号
			application.setOperaterId(employeeId);//设置本次操作人id
			application.setOperaterTime(date);//操作时间
			application.setApplicationTime(date);//申请发起时间
			application.setIsComplete("0");//未完成
			Date startTime =null;//开始时间
			Date endTime=null;//结束时间
			//申请类型{ 1:请假,2:加班,3:出差,4:外出,5:补卡 }
			if(!"5".equals(application.getApplicationType())){
				try{
					if(StringUtils.isEmpty(application.getStartTime())||StringUtils.isEmpty(application.getEndTime())){
						returnData.setMessage("必传参数为空");
						returnData.setReturnCode("3006");
						return returnData;
					}
					int i = DateCompareUtil.compareTowDate(sdf.parse(application.getStartTime()), sdf.parse(application.getEndTime()));
					if(i!=1){
						returnData.setMessage("开始时间必须小于结束时间");
						returnData.setReturnCode("9999");
						return returnData;
					}
					//判断申请时间段是否与已存在的申请重复
					if(applicationService.isRepeatWithOtherApplication(application)){
						returnData.setMessage("申请的起止时间与别的申请起止时间重叠");
						returnData.setReturnCode("9999");
						return returnData;
					}
					//请假、加班、出差、外出申请的开始时间和结束时间不能选择一个月以后的时间
					startTime = sdf.parse(application.getStartTime());//开始时间
					endTime = sdf.parse(application.getEndTime());//结束时间
					if(endTime.getTime()>sdf.parse(date).getTime()){
						int m = TimeUtil.monthOfDate(sdf1.format(endTime),sdf1.format(date));
						if(m>1){
							returnData.setMessage("申请的起止时间只能在当前时间的下一个月之内");
							returnData.setReturnCode("9999");
							return returnData;
						}
					}
				}catch(Exception e){
					logger.info(e);
					e.printStackTrace();
					returnData.setMessage("时间格式错误");
					returnData.setReturnCode("9999");
					return returnData;
				}
			}
			if("3".equals(application.getApplicationType())||"4".equals(application.getApplicationType())){
				if(StringUtils.isEmpty(application.getIsSkipRestDay())){
					returnData.setMessage("必传参数为空");
					returnData.setReturnCode("3006");
					return returnData;
				}
			}
			//调用申请小时数计算,并发起申请
			int applicationHour = 0;//计算得出的申请小时数
			switch(application.getApplicationType()){
			   case "1"://请假
				   applicationHour = this.calculateApplicationHour("1", application.getIsSkipRestDay(), employeeId, companyId, startTime, endTime, applicationHour);
				   if(applicationHour==0){
					   returnData.setMessage("请检查申请是时间是否填写正确");
					   returnData.setReturnCode("9999");
					   return returnData;
				   }else if(applicationHour==-2){
					   returnData.setMessage("您当前申请时间段内没有排班,请联系企业管理员");
					   returnData.setReturnCode("9999");
					   return returnData;
				   }
				   application.setApplicationHour(String.valueOf(applicationHour));
				   returnData = applicationService.leaveApplication(application,year);
				   break;
			   case "2"://加班
				   //加班的申请时长=结束时间-开始时间	
				   long between =(endTime.getTime()-startTime.getTime())/1000;
				   applicationHour=applicationHour+((int)Math.ceil(between/60/30))/2;
				   if(applicationHour==0){
					   returnData.setMessage("请检查申请是时间是否填写正确");
					   returnData.setReturnCode("9999");
					   return returnData;
				   }
				   application.setApplicationHour(String.valueOf(applicationHour));
				   returnData = applicationService.overTimeApplication(application);
				   break;
			   case "3"://出差
				   applicationHour = this.calculateApplicationHour("3", application.getIsSkipRestDay(), employeeId, companyId, startTime, endTime, applicationHour);
				   if(applicationHour==0){
					   returnData.setMessage("请检查申请是时间是否填写正确");
					   returnData.setReturnCode("9999");
					   return returnData;
				   }
				   application.setApplicationHour(String.valueOf(applicationHour));
				   returnData = applicationService.businessTravelApplication(application);
				   break;
			   case "4"://外出
				   applicationHour = this.calculateApplicationHour("4", application.getIsSkipRestDay(), employeeId, companyId, startTime, endTime, applicationHour);
				   if(applicationHour==0){
					   returnData.setMessage("请检查申请是时间是否填写正确");
					   returnData.setReturnCode("9999");
					   return returnData;
				   }
				   application.setApplicationHour(String.valueOf(applicationHour));
				   returnData = applicationService.outgoingApplication(application);
				   break;
			   case "5"://补卡
				   returnData = applicationService.fillCardApplication(application);
				   break;
			   default :
				   returnData.setMessage("必传参数为空");
				   returnData.setReturnCode("3006");
				   return returnData;
			}
			
			return returnData;
		}
		/**
		 * 常用联系人列表
		 * @param jsonString
		 * @param request
		 * @return
		 */
		@RequestMapping(value = "/commonContactPeopleList",produces="application/json;charset=utf-8",method=RequestMethod.POST)
		public ReturnData commonContactPeopleList(@RequestBody String jsonString ,HttpServletRequest request) {
			ReturnData returnData = new ReturnData();
			String employeeId = request.getHeader("accessUserId");//员工id
			String companyId = request.getHeader("companyId");//公司id
			if(StringUtils.isEmpty(companyId)||StringUtils.isEmpty(employeeId)){
				returnData.setMessage("请求头参数缺失");
				returnData.setReturnCode("3013");
				return returnData;
			}
			JSONObject jobj = JSON.parseObject(jsonString);
			String type = jobj.getString("type");
			String page = jobj.getString("page");
			String count = jobj.getString("count");
			if(StringUtils.isEmpty(type)||StringUtils.isEmpty(page)||StringUtils.isEmpty(count)){
				returnData.setMessage("必传参数为空");
				returnData.setReturnCode("3006");
				return returnData;
			}
			List<ApplicationCommonContactPeople> commonContactPeopleList = applicationService.commonContactPeopleList(employeeId, companyId, type, page, count);
			returnData.setData(commonContactPeopleList);
			returnData.setMessage("成功");
			returnData.setReturnCode("3000");
			return returnData;
		}
		/**
		 * 常用联系人设置
		 * @param jsonString
		 * @param request
		 * @return
		 */
		@RequestMapping(value = "/commonContactPeople",produces="application/json;charset=utf-8" ,method=RequestMethod.POST)
		public ReturnData commonContactPeople(@RequestBody String jsonString ,HttpServletRequest request) {
			
			ReturnData returnData = new ReturnData();
			try{
			String employeeId = request.getHeader("accessUserId");//员工id
			String companyId = request.getHeader("companyId");//公司id
			if(StringUtils.isEmpty(companyId)||StringUtils.isEmpty(employeeId)){
				returnData.setMessage("请求头参数缺失");
				returnData.setReturnCode("3013");
				return returnData;
			}
			Application application = JSON.parseObject(jsonString, Application.class);
			application.setCompanyId(companyId);
			application.setApplicationId(employeeId);
			//设置常用联系人
			if("1".equals(application.getIsSetCommonContactPeople())){
				if(application==null || application.getCommonContactPeopleList().size()<1 || StringUtils.isEmpty(application.getCommonContactPeopleList()) ){
					for(ApplicationCommonContactPeople contactPeople :application.getCommonContactPeopleList()){
						if(StringUtils.isEmpty(contactPeople.getCommonContactPeopleId())||StringUtils.isEmpty(contactPeople.getCommonContactPeopleName())||StringUtils.isEmpty(contactPeople.getType())){
							returnData.setMessage("必传参数为空");
							returnData.setReturnCode("3006");
							return returnData;
						}
					}
				}
				boolean flag = applicationService.commonContactPeople(application);
				if(!flag){
					returnData.setMessage("服务器错误");
					returnData.setReturnCode("3001");
					return returnData;
				}
				returnData.setMessage("成功");
				returnData.setReturnCode("3000");
			}
			
			}catch(Exception e){
				logger.info(e);
				e.printStackTrace();
				returnData.setMessage("服务器错误");
				returnData.setReturnCode("3001");
				return returnData;
			}
			return returnData;
		}
		/**
		 * 申请列表获取
		 * @param jsonString
		 * @param request
		 * @return
		 */
		@RequestMapping(value = "/applicationList",produces="application/json;charset=utf-8",method=RequestMethod.POST)
		public ReturnData applicationList(@RequestBody String jsonString ,HttpServletRequest request) {
			ReturnData returnData = new ReturnData();
			String employeeId = "";
			String companyId = "";
			String page = "";
			String count = "";
			try{
				employeeId = request.getHeader("accessUserId");//员工id
				companyId = request.getHeader("companyId");//公司id
			if(StringUtils.isEmpty(employeeId)||StringUtils.isEmpty(companyId)){
				returnData.setMessage("请求头参数缺失");
				returnData.setReturnCode("3013");
				return returnData;
			}
			try{
				JSONObject jobj = JSON.parseObject(jsonString);
				page = jobj.getString("page");
				count = jobj.getString("count");
			}catch(Exception e){
				logger.info(e);
				e.printStackTrace();
				returnData.setMessage("参数错误");
				returnData.setReturnCode("3006");
				return returnData;
			}
			if(StringUtils.isEmpty(page)||StringUtils.isEmpty(count)){
				returnData.setMessage("必传参数为空");
				returnData.setReturnCode("3006");
				return returnData;
			}
			List<ApplicationTotalRecord> applicationList = applicationService.applicationList(employeeId, companyId, page, count);
			if(applicationList!=null&&applicationList.size()>0){
				for(ApplicationTotalRecord applicationTotalRecord:applicationList){
					if("2".equals(applicationTotalRecord.getApplicationStatus())){
						applicationTotalRecord.setStatusDescription("已撤回");
					}else if("1".equals(applicationTotalRecord.getApplicationStatus())){//正常
						if("1".equals(applicationTotalRecord.getIsComplete())){
							if("0".equals(applicationTotalRecord.getIsReject())){
								applicationTotalRecord.setStatusDescription("已通过");
							}else{
								applicationTotalRecord.setStatusDescription("已驳回");
							}
						}else{
							applicationTotalRecord.setStatusDescription("审批中");
						}
					}
				}
			}
			returnData.setData(applicationList);
			returnData.setMessage("成功");
			returnData.setReturnCode("3000");
			return returnData;
			}catch(Exception e){
				logger.info(e);
				e.printStackTrace();
				returnData.setMessage("服务器错误");
				returnData.setReturnCode("3001");
				return returnData;
			}
		}
		/**
		 * 申请详情
		 * @param jsonString
		 * @param request
		 * @return
		 */
		@RequestMapping(value = "/applicationDetails",produces="application/json;charset=utf-8",method=RequestMethod.POST)
		public ReturnData applicationDetails(@RequestBody String jsonString ,HttpServletRequest request) {
			ReturnData returnData = new ReturnData();
			String applicationNo = "";
			//String statusDescription = "";
			try{
			String employeeId = request.getHeader("accessUserId");//员工id
			String companyId = request.getHeader("companyId");//公司id
			if(StringUtils.isEmpty(employeeId)||StringUtils.isEmpty(companyId)){
				returnData.setMessage("请求头参数缺失");
				returnData.setReturnCode("3013");
				return returnData;
			}
			try{
				JSONObject jobj = JSON.parseObject(jsonString);
				applicationNo = jobj.getString("applicationNo");
				//statusDescription = jobj.getString("statusDescription");
			}catch(Exception e){
				logger.info(e);
				e.printStackTrace();
				returnData.setMessage("参数错误");
				returnData.setReturnCode("3006");
				return returnData;
			}
			if(StringUtils.isEmpty(applicationNo)/*||StringUtils.isEmpty(statusDescription)*/){
				returnData.setMessage("必传参数为空");
				returnData.setReturnCode("3006");
				return returnData;
			}
			Application application = applicationService.applicationDetails(applicationNo, employeeId, null, companyId);
			this.getUploadVoucher(companyId, application);
			returnData.setMessage("成功");
			returnData.setReturnCode("3000");
			returnData.setData(application);
			return returnData;
			}catch(Exception e){
				logger.info(e);
				e.printStackTrace();
				returnData.setMessage("服务器错误");
				returnData.setReturnCode("3001");
				return returnData;
			}
		}
		/**
		 * 申请小时数计算
		 * @param type
		 * @param isSkipRestTime
		 * @param employeeId
		 * @param companyId
		 * @param startTime
		 * @param endTime
		 * @param applicationHour
		 * @return
		 */
		private int calculateApplicationHour(String type,String isSkipRestTime,String employeeId,String companyId,Date startTime,Date endTime,int applicationHour){
			SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			 List<ClassesEmployee> classesEmployeeList = classesService.queryPointTimeClasses(employeeId, companyId, new SimpleDateFormat("yyyy-MM-dd").format(startTime), new SimpleDateFormat("yyyy-MM-dd").format(endTime));
			 if("1".equals(type)){
				 if(classesEmployeeList.size()<1){
					 return -2;
				 }
			 }
			   try{
				   int i=0;
				   for(ClassesEmployee classesEmployee:classesEmployeeList){
					   String start = "";
					   String end = "";
					   if(!StringUtils.isEmpty(classesEmployee.getClassesId())&&!StringUtils.isEmpty(classesEmployee.getOnDutySchedulingDate())&&!StringUtils.isEmpty(classesEmployee.getOffDutySchedulingDate())){
						   if(startTime.getTime()>sdf.parse(classesEmployee.getOnDutySchedulingDate()).getTime()){
						         start = sdf.format(startTime);
						   }else{
							   start = classesEmployee.getOnDutySchedulingDate();
						   }
						   if(endTime.getTime()>sdf.parse(classesEmployee.getOffDutySchedulingDate()).getTime()){
							   end = classesEmployee.getOffDutySchedulingDate();
						   }else{
							   end = sdf.format(endTime);
						   }
						   long between=(dfs.parse(end).getTime()-dfs.parse(start).getTime())/1000;//除以1000是为了转换成秒
						   applicationHour=applicationHour+((int)Math.ceil(between/60/30))/2;
					   }else {
							  if("3".equals(type)||"4".equals(type)){
							   if("0".equals(isSkipRestTime)){
								   //休息日
								   if(i==0){
									   start = sdf.format(startTime);
									   if(sdf1.parse(sdf1.format(endTime)).getTime()>sdf1.parse(classesEmployee.getTheDate()).getTime()){
										   int time= (int)Math.ceil((double)(dfs.parse(classesEmployee.getTheDate()+" 23:59:59").getTime()-dfs.parse(dfs.format(startTime)).getTime()/1000/60/30)/2);
										   if(time>8){
											   applicationHour =applicationHour+8;
										   }else{
											   applicationHour = applicationHour +time;
										   }
									   }else{
										   int time= (int)Math.ceil((double)(dfs.parse(dfs.format(endTime)).getTime()-dfs.parse(dfs.format(startTime)).getTime()/1000/60/30)/2);
										   if(time>8){
											   applicationHour =applicationHour+8;
										   }else{
											   applicationHour = applicationHour +time;
										   }
									   }
								   }else{
									   if(!StringUtils.isEmpty(classesEmployeeList.get(i-1).getClassesId())&&!StringUtils.isEmpty(classesEmployeeList.get(i-1).getOnDutySchedulingDate())&&!StringUtils.isEmpty(classesEmployeeList.get(i-1).getOffDutySchedulingDate())){
										   start= classesEmployeeList.get(i-1).getOffDutySchedulingDate();
									   }else{
										   start = sdf.format(classesEmployee.getTheDate());
									   }
									   if(sdf1.parse(sdf1.format(endTime)).getTime()>sdf1.parse(classesEmployee.getTheDate()).getTime()){
										   int time= (int)Math.ceil((double)(dfs.parse(classesEmployee.getTheDate()+" 23:59:59").getTime()-dfs.parse(dfs.format(startTime)).getTime()/1000/60/30)/2);
										   if(time>8){
											   applicationHour =applicationHour+8;
										   }else{
											   applicationHour = applicationHour +time;
										   }
									   }else{
										   int time= (int)Math.ceil((double)(dfs.parse(dfs.format(endTime)).getTime()-dfs.parse(dfs.format(startTime)).getTime()/1000/60/30)/2);
										   if(time>8){
											   applicationHour =applicationHour+8;
										   }else{
											   applicationHour = applicationHour +time;
										   }
									   }
								   }
							   }
						   }
					   }
					   i+=1;
				   }
				   return applicationHour;
			   }catch(Exception e){
				   logger.info(e);
				   return -1;
			   }
		}
		/**
		 * application赋值
		 * @param jsonString
		 * @param application
		 */
		private void setApplication(String jsonString,Application application){
			JSONObject jobj = JSON.parseObject(jsonString);
			application.setApplicationType(jobj.getString("applicationType"));
			application.setApplicationChildrenType(jobj.getString("applicationChildrenType"));
			application.setStartTime(jobj.getString("startTime"));
			application.setEndTime(jobj.getString("endTime"));
			application.setReason(jobj.getString("reason"));
			application.setUploadVoucher(jobj.getString("uploadVoucher"));
			application.setApprover(jobj.getString("approver"));
			application.setIsCopy(jobj.getString("isCopy"));
			application.setIsReject(jobj.getString("isReject"));
			application.setIsTransfer(jobj.getString("isTransfer"));
			application.setIsSetCommonContactPeople(jobj.getString("isSetCommonContactPeople"));
			application.setApplicationStatus(jobj.getString("applicationStatus"));
			application.setRejectReason(jobj.getString("rejectReason"));
			application.setOutgoingLocation(jobj.getString("outgoingLocation"));
			application.setFillCardTime(jobj.getString("fillCardTime"));
			application.setIsSkipRestDay(jobj.getString("isSkipRestDay"));
			JSONArray copyPersonListArray = JSONArray.parseArray(jobj.getString("copyPersonList"));
			for(int i=0;i<copyPersonListArray.size();i++){
				JSONObject jobj1 = JSON.parseObject(copyPersonListArray.get(i).toString());
				ApplicationToCopyPerson copyPerson = new ApplicationToCopyPerson();
				copyPerson.setAppCopyPersonId(jobj1.getString("appCopyPersonId"));
				copyPerson.setAppCopyPersonName(jobj1.getString("appCopyPersonName"));
			}
			JSONArray transferRecordListArray = JSONArray.parseArray(jobj.getString("transferRecordList"));
			for(int i=0;i<transferRecordListArray.size();i++){
				JSONObject jobj1 = JSON.parseObject(transferRecordListArray.get(i).toString());
				ApplicationTransferRecord transferRecord = new ApplicationTransferRecord();
				transferRecord.setTransferPersonId(jobj1.getString("transferPersonId"));
				transferRecord.setTransferPersionAccessId(jobj1.getString("transferPersionAccessId"));
				transferRecord.setTransferExplain(jobj1.getString("transferExplain"));
			}
			JSONArray commonContactPeopleListArray = JSONArray.parseArray(jobj.getString("commonContactPeopleList"));
			for(int i=0;i<commonContactPeopleListArray.size();i++){
				JSONObject jobj1 = JSON.parseObject(commonContactPeopleListArray.get(i).toString());
				ApplicationCommonContactPeople commonContactPeople = new ApplicationCommonContactPeople();
				commonContactPeople.setCommonContactPeopleId(jobj1.getString("commonContactPeopleId"));
				commonContactPeople.setCommonContactPeopleName(jobj1.getString("commonContactPeopleName"));
				commonContactPeople.setType(jobj1.getString("type"));
			}
			
		}
		private void getUploadVoucher(String companyId,Application application){
			Company company = companyService.selectCompany(companyId);
			String uploadVoucher = oSSFileService.getPathByKey(company.getCompanyNo(),
					"approval", application.getUploadVoucher());
			application.setUploadVoucher(uploadVoucher);
		}
	}
