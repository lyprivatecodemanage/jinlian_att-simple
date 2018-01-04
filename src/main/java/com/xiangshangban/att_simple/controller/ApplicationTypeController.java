package com.xiangshangban.att_simple.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiangshangban.att_simple.service.ApplicationTypeService;

@RestController
@RequestMapping("/applicationType")
public class ApplicationTypeController {
	@Autowired
	private ApplicationTypeService applicationTypeService;
	
	

}
