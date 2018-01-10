package com.xiangshangban.att_simple.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xiangshangban.att_simple.service.ApplicationService;
import com.xiangshangban.att_simple.utils.FormatUtil;
import com.xiangshangban.att_simple.utils.GainData;
import com.xiangshangban.att_simple.utils.RegexUtil;
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
			GainData data = new GainData(jsonString, request);
			String employeeId = data.getData("accessUserId").toString();//员工id
			String companyId = data.getData("companyId").toString();//公司id
			if(StringUtils.isEmpty(companyId)||StringUtils.isEmpty(employeeId)){
				result.put("message", "请求信息错误");
				result.put("returnCode", "3012");
				return result;
			}
			result = applicationService.applicationIndexPage(employeeId, companyId);
			return result;
		}
		/**
		 * 请假申请
		 * @param jsonString
		 * @param request
		 * @return
		 */
		@RequestMapping(value = "/leaveApplication",produces="application/json;charset=utf-8",method=RequestMethod.POST)
		public Map<String,Object> leaveApplication(@RequestBody String jsonString ,HttpServletRequest request) {
			Map<String,Object> result = new HashMap<String,Object>();
			Map<String,String> params = new HashMap<String,String>();
			try{
			GainData data = new GainData(jsonString, request);
			String employeeId =  data.getData("employeeId").toString();
			String companyId =  data.getData("companyId").toString();
			String leaveType =  data.getData("leaveType").toString();
			String startTime =  data.getData("startTime").toString();
			String endTime =  data.getData("endTime").toString();
			String reason =  data.getData("reason").toString();
			String uploadVoucher =  data.getData("uploadVoucher").toString();
			String approver =  data.getData("approver").toString();
			String copyToPerson =  data.getData("copyToPerson").toString();
			String isCopy = data.getData("isCopy").toString();
			if(StringUtils.isEmpty(employeeId) || StringUtils.isEmpty(companyId) || StringUtils.isEmpty(leaveType)
					|| StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime) || StringUtils.isEmpty(approver)){
				result.put("message", "必传参数为空");
				result.put("returnCode", "3006");
				return result;
			}
			if(!RegexUtil.matchDateTime(startTime) || !RegexUtil.matchDateTime(endTime)){
				result.put("message", "时间格式错误");
				result.put("returnCode", "3008");
				return result;
			}
			params.put("employeeId", employeeId);//员工id
			params.put("companyId", companyId);//公司id
			params.put("leaveType", leaveType);//请假类型
			params.put("startTime", startTime);//开始时间
			params.put("endTime", endTime);//结束时间
			params.put("reason", reason);//请假理由,非必填
			params.put("uploadVoucher", uploadVoucher);//上传凭证,非必填
			params.put("approver", approver);//审批人id
			params.put("copyToPerson", copyToPerson);//抄送人id,非必填
			params.put("isCopy", isCopy);//是否抄送(0:否,1:是)
			
			params.put("applicaitonNo", FormatUtil.createUuid());//生成申请单号
			String applicationHour = String.valueOf(TimeUtil.hourOfTime(startTime, endTime));
			params.put("applicationHour", applicationHour);//申请小时数
			params.put("applicationTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));//申请时间
			params.put("applicationType", "1");//请假类型,1:请假
			params.put("isTransfer", "0");//是否转移,0:否,1:是
			params.put("applicationStatus", "1");//申请单状态(0:删除;1:正常;2:撤回)
			result = applicationService.leaveApplication(params);
			return result;
			}catch(Exception e){
				logger.info(e);
				result.put("message", "服务器错误");
				result.put("returnCode", "3001");
				return result;
			}
	   }
	   /**
	    * 加班申请
	    * @param jsonString
	    * @param request
	    * @return
	    */
		@RequestMapping(value = "/overtimeApplication",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	   public Map<String,Object> overtimeApplication(@RequestBody String jsonString ,HttpServletRequest request) {
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
	    * 出差申请
	    * @param jsonString
	    * @param request
	    * @return
	    */
		@RequestMapping(value = "/businessTravelApplication",produces="application/json;charset=utf-8",method=RequestMethod.POST)
		public Map<String,Object> businessTravelApplication(@RequestBody String jsonString ,HttpServletRequest request) {
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
		 * 外出申请
		 * @param jsonString
		 * @param request
		 * @return
		 */
		@RequestMapping(value = "/outgoingApplication",produces="application/json;charset=utf-8",method=RequestMethod.POST)
		public Map<String,Object> outgoingApplication(@RequestBody String jsonString ,HttpServletRequest request) {
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
		 * 补卡申请
		 * @param jsonString
		 * @param request
		 * @return
		 */
		@RequestMapping(value = "/fillCardApplication",produces="application/json;charset=utf-8",method=RequestMethod.POST)
		public Map<String,Object> fillCardApplication(@RequestBody String jsonString ,HttpServletRequest request) {
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
