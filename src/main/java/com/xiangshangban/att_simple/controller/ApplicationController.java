package com.xiangshangban.att_simple.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.att_simple.bean.Application;
import com.xiangshangban.att_simple.bean.ApplicationCommonContactPeople;
import com.xiangshangban.att_simple.bean.ReturnData;
import com.xiangshangban.att_simple.service.ApplicationService;
import com.xiangshangban.att_simple.utils.DateCompareUtil;
import com.xiangshangban.att_simple.utils.FormatUtil;
import com.xiangshangban.att_simple.utils.GainData;
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
			if(StringUtils.isEmpty(companyId)||StringUtils.isEmpty(employeeId)){
				result.put("message", "请求信息错误");
				result.put("returnCode", "3012");
				return result;
			}
			result = applicationService.applicationIndexPage(employeeId, companyId);
			return result;
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
			//系统当前时间
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
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
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
						SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
						int m = TimeUtil.monthOfDate(sdf1.format(endTime),sdf1.format(date));
						if(m>1){
							returnData.setMessage("申请的起止时间只能在当前时间的下一个月之内");
							returnData.setReturnCode("9999");
							return returnData;
						}
					}
				}catch(Exception e){
					logger.info(e);
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
				   
				   for(){
					   if(startTime.getTime()>){
						   
					   }
				   }
				   application.setApplicationHour(applicationHour);
				   returnData = applicationService.leaveApplication(application);
				   break;
			   case "2"://加班
				   application.setApplicationHour(applicationHour);
				   returnData = applicationService.overTimeApplication(application);
				   break;
			   case "3"://出差
				   application.setApplicationHour(applicationHour);
				   returnData = applicationService.businessTravelApplication(application);
				   break;
			   case "4"://外出
				   application.setApplicationHour(applicationHour);
				   returnData = applicationService.outgoingApplication(application);
				   break;
			   case "5"://补卡
				   application.setApplicationHour(applicationHour);
				   returnData = applicationService.fillCardApplication(application);
				   break;
			   default :
				   returnData.setMessage("必传参数为空");
				   returnData.setReturnCode("3006");
				   return returnData;
			}
			//设置常用联系人
			if("1".equals(application.getIsSetCommonContactPeople())){
				if(application==null || StringUtils.isEmpty(application.getCommonContactPeopleList()) || application.getCommonContactPeopleList().size()<1){
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
		@RequestMapping(value = "/commonContactPeople",produces="application/json;charset=utf-8",method=RequestMethod.POST)
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
			Application application = null;
			GainData data = new GainData(jsonString, request);
			if(data.getType()==0){
				application = JSON.parseObject(JSONObject.toJSONString(data.getResult()), Application.class);
			}else if(data.getType()==1){
				
			}
			if(application==null || StringUtils.isEmpty(application.getCommonContactPeopleList()) || application.getCommonContactPeopleList().size()<1){
				for(ApplicationCommonContactPeople contactPeople :application.getCommonContactPeopleList()){
					if(StringUtils.isEmpty(contactPeople.getCommonContactPeopleId())||StringUtils.isEmpty(contactPeople.getCommonContactPeopleName())||StringUtils.isEmpty(contactPeople.getType())){
						returnData.setMessage("必传参数为空");
						returnData.setReturnCode("3006");
						return returnData;
					}
				}
			}
			
			}catch(Exception e){
				logger.info(e);
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
		public Map<String,Object> applicationList(@RequestBody String jsonString ,HttpServletRequest request) {
			Map<String,Object> result = new HashMap<String,Object>();
			try{
			GainData data = new GainData(jsonString, request);
			String employeeId = data.getData("accessUserId").toString();//员工id
			String companyId = data.getData("companyId").toString();//公司id
			return result;
			}catch(Exception e){
				logger.info(e);
				result.put("message", "服务器错误");
				result.put("returnCode", "3001");
				return result;
			}
		}
		/**
		 * 申请详情
		 * @param jsonString
		 * @param request
		 * @return
		 */
		@RequestMapping(value = "/applicationDetails",produces="application/json;charset=utf-8",method=RequestMethod.POST)
		public Map<String,Object> applicationDetails(@RequestBody String jsonString ,HttpServletRequest request) {
			Map<String,Object> result = new HashMap<String,Object>();
			try{
			GainData data = new GainData(jsonString, request);
			String employeeId = data.getData("accessUserId").toString();//员工id
			String companyId = data.getData("companyId").toString();//公司id
			return result;
			}catch(Exception e){
				logger.info(e);
				result.put("message", "服务器错误");
				result.put("returnCode", "3001");
				return result;
			}
		}
	}
