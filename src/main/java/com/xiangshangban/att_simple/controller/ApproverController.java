package com.xiangshangban.att_simple.controller;

/***********************************************************************
 * Module:  ApproverController.java
 * Author:  cachee
 * Purpose: Defines the Class ApproverController
 ***********************************************************************/
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiangshangban.att_simple.service.ApproverService;

/** app所有假勤审批处理器
 * 
 */	
@RestController 
@RequestMapping("/approver")
public class ApproverController {
   
	
	@Autowired
	private ApproverService approverService;
   
	public Map<String,Object> approverIndexPage() {
		return null;
	}
   
	public Map<String,Object> approverList() {
		return null;
	}
   
	public Map<String,Object> approverDetails() {
		return null;
	}
   
	public Map<String,Object> approverTransfer() {
		return null;
	}

}