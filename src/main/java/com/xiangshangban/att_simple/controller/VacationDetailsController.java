package com.xiangshangban.att_simple.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.att_simple.bean.ReturnData;
import com.xiangshangban.att_simple.service.VacationDetailsService;

@RestController
@RequestMapping("VacationDetailsController")
public class VacationDetailsController {

	@Autowired
	VacationDetailsService vacationDetailsService;
	
	/**
	 * 焦振 / 获取单人假期详情查看列表 和 关键数据查询
	 * @param jsonString
	 * @return
	 */
	@RequestMapping(value="SelectVacationDetailsAndKeyData",produces = "application/json;charset=utf-8",method=RequestMethod.POST)
	public ReturnData SelectVacationDetailsAndKeyData(@RequestBody String jsonString){
		ReturnData resulet = new ReturnData();
		
		JSONObject obj = JSON.parseObject(jsonString);
		String vacationId =	obj.getString("vacationId");
		String vacationType = obj.getString("vacationType"); 
		String changingReason = obj.getString("changingReason");
		String changeingDateRank = obj.getString("changeingDateRank");
		String varPageNo = obj.getString("pageNum");
		String pageNum = obj.getString("pageRecordNum");
		String pageExcludeNumber = String.valueOf((Integer.parseInt(varPageNo)-1)*Integer.parseInt(pageNum));
		
		vacationType = StringUtils.isEmpty(vacationType)?null:vacationType;
		changingReason = StringUtils.isEmpty(changingReason)?null:changingReason;
		
		resulet = vacationDetailsService.SelectVacationDetails(vacationId, vacationType, changingReason, changeingDateRank, pageExcludeNumber, pageNum);
			
		return resulet;
	}
}
