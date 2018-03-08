package com.xiangshangban.att_simple.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.xiangshangban.att_simple.bean.FillCardRecord;
import com.xiangshangban.att_simple.bean.ReturnData;
import com.xiangshangban.att_simple.service.FillCardRecordService;

@RestController
@RequestMapping("/fillCard")
public class FillCardController {
	@Autowired
	private FillCardRecordService fillCardService;
	@RequestMapping(value="/outFillCard",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public ReturnData outFillCard(@RequestBody String jsonString,HttpServletRequest request){
		ReturnData returnData = new ReturnData();
		String companyId = request.getHeader("companyId");
		String employeeId = request.getHeader("accessUserId");
		FillCardRecord fillCardRecord = JSON.parseObject(jsonString, FillCardRecord.class);
		if(StringUtils.isEmpty(employeeId)||StringUtils.isEmpty(companyId)){
			returnData.setMessage("请求信息错误");
			returnData.setReturnCode("3012");
			return returnData;
		}
		fillCardRecord.setEmployeeId(employeeId);
		fillCardRecord.setCompanyId(companyId);
		if(StringUtils.isEmpty(fillCardRecord.getAttTime())||StringUtils.isEmpty(fillCardRecord.getPlace())
				||StringUtils.isEmpty(fillCardRecord.getCustomerName())||StringUtils.isEmpty(fillCardRecord.getDescription())
				||StringUtils.isEmpty(fillCardRecord.getLongitude())||StringUtils.isEmpty(fillCardRecord.getLatitude())){
			returnData.setMessage("必传参数为空");
			returnData.setReturnCode("3006");
			return returnData;
		}
		int i = fillCardService.insertFillCardRecord(fillCardRecord);
		if(i>0){
			returnData.setMessage("成功");
			returnData.setReturnCode("3000");
		}else{
			returnData.setMessage("打卡失败");
			returnData.setReturnCode("9999");
		}
		return returnData;
	}
}
