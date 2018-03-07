package com.xiangshangban.att_simple.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xiangshangban.att_simple.service.AlgorithmService;
import com.xiangshangban.att_simple.utils.TimeUtil;

/**
 * 用于手动触发某个方法
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/JNTest")
public class TestController {
	private static final Logger logger = Logger.getLogger(ApplicationController.class);
	@Autowired
	private AlgorithmService algorithmService;
	/**
	 * 计算日报
	 * @param companyId
	 * @param employeeId
	 * @param beginDate
	 * @param endDate
	 * @param type 0：具体某个公司某个人员一段区间的日报计算，1：某个公司的所有人员一段区间的日报计算，2：所有公司所有人一段区间日报计算
	 * @return
	 */
	@RequestMapping(value = "/countReport",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public  Map<String,Object> applicationIndexPage(@RequestParam("companyId") String companyId, 
			@RequestParam("employeeId") String employeeId, 
			@RequestParam("beginDate") String beginDate, @RequestParam("endDate") String endDate,
			@RequestParam("type") String type){
		logger.info("接口手动触发开始:companyId="+companyId+",employeeId"+employeeId+",beginDate="+beginDate+",endDate="+endDate); 
		switch (type) {
		case "0":
			algorithmService.calculate(companyId, employeeId, beginDate, endDate);
			break;
		case "1":
			algorithmService.calculateByCompany(companyId, beginDate, endDate);
			break;
		case "2":
			algorithmService.calculate(beginDate, endDate);
			break;
		default:
			break;
		}
		logger.info("接口手动触发完成");
		logger.info("月报计算完成时间"+TimeUtil.getCurrentTime()); 
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("message", "触发计算成功:companyId="+companyId+",employeeId"+employeeId+",beginDate="+beginDate+",endDate="+endDate);
		result.put("returnCode", "3000");
		return result;
	}
}
