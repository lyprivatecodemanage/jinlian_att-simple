package com.xiangshangban.att_simple.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xiangshangban.att_simple.bean.ApplicationType;
import com.xiangshangban.att_simple.service.ApplicationService;
import com.xiangshangban.att_simple.utils.GainData;

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
		public  Map<String,Object> applicationIndexPage(String jsonString ,HttpServletRequest request){
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

		public Map<String,Object> leaveApplication() {
	      
	      return null;
	   }
	   
	   public Map<String,Object> overtimeApplication() {
	      
	      return null;
	   }
	   
	   public Map<String,Object> businessTravelApplication() {
	      
	      return null;
	   }
	   
	   public Map<String,Object> outgoingApplication() {
	      
	      return null;
	   }
	   
	   public Map<String,Object> fillCardApplication() {
	      
	      return null;
	   }
	   
	   public Map<String,Object> applicationList() {
	      
	      return null;
	   }
	   
	   public Map<String,Object> applicationDetails() {
	      
	      return null;
	   }

	}
