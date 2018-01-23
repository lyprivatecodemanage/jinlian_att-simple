package com.xiangshangban.att_simple.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.att_simple.bean.Paging;
import com.xiangshangban.att_simple.bean.ReturnData;
import com.xiangshangban.att_simple.service.ApplicationLeaveService;

@RestController
@RequestMapping("ApplicationLeaveController")
public class ApplicationLeaveController {

	@Autowired
	ApplicationLeaveService applicationLeaveService;
	
	/**
	 * 焦振/请假记录模糊分页查询
	 * @param objectString
	 * @return
	 */
	@RequestMapping(value="selectCompleteLeave",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public ReturnData selectCompleteLeave(@RequestBody String objectString,HttpServletRequest request){
		ReturnData returndata = new ReturnData();
		JSONObject job = JSON.parseObject(objectString);
		String conpamyId = request.getHeader("companyId");
		Paging pg = new Paging();
		pg.setCompanyId(conpamyId);
		pg.setYear(job.getString("year"));
		pg.setMonth(job.getString("month"));
		pg.setDepartmentId(job.getString("departmentId"));
		pg.setLeaveType(job.getString("leaveType"));
		pg.setEmployeeName(job.getString("employeeName"));
		pg.setApplicationHourRank(job.getString("applicationHourRank"));
		pg.setApplicationTimeRank(job.getString("applicationTimeRank"));
		pg.setPageNum(job.getString("pageNum"));
		pg.setPageExcludeNumber(String.valueOf((Integer.parseInt(job.getString("varPageNo"))-1)*Integer.parseInt(job.getString("pageNum"))));
		
		returndata = applicationLeaveService.selectCompleteLeave(pg);
		
		return returndata;
	}
	
	/**
	 * 焦振 /查询请假记录关键数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="selectLeaveKeyData",produces="application/json;chatset=utf-8",method=RequestMethod.POST)
	public ReturnData selectLeaveKeyData(HttpServletRequest request){
		ReturnData returndata = new ReturnData();
		String companyId = request.getHeader("companyId");
		
		returndata = applicationLeaveService.selectLeaveKeyData(companyId);
		
		return returndata;
	}
	
}
