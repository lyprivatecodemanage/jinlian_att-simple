package com.xiangshangban.att_simple.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xiangshangban.att_simple.service.AdjustRestDateCalculateService;
import com.xiangshangban.att_simple.service.VacationService;

@RestController
@RequestMapping("/VacationController")
public class VacationController {

	@Autowired
	VacationService vacationService;
	
	@Autowired
	AdjustRestDateCalculateService adjustRestDateCalculateService;
	
	@RequestMapping(value="/insert",method=RequestMethod.POST)
	public String insert(){
		
		return "!!!!";
	}
}
