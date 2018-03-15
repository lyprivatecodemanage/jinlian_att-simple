package com.xiangshangban.att_simple.aop;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.xiangshangban.att_simple.bean.Application;
import com.xiangshangban.att_simple.bean.Company;
import com.xiangshangban.att_simple.bean.Employee;
import com.xiangshangban.att_simple.bean.PhoneClientId;
import com.xiangshangban.att_simple.bean.ReturnData;
import com.xiangshangban.att_simple.bean.TabJpush;
import com.xiangshangban.att_simple.bean.Uusers;
import com.xiangshangban.att_simple.dao.CompanyDao;
import com.xiangshangban.att_simple.dao.EmployeeDao;
import com.xiangshangban.att_simple.dao.PhoneClientIdMapper;
import com.xiangshangban.att_simple.dao.TabJpushMapper;
import com.xiangshangban.att_simple.utils.FormatUtil;
import com.xiangshangban.att_simple.utils.JPushUtil;

@Aspect
@Configuration
public class AppApplicationJPush {
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private PhoneClientIdMapper phoneClientIdMapper;
	@Autowired
	private TabJpushMapper tabJpushMapper;
	@Pointcut("execution(* com.xiangshangban.att_simple.controller.ApplicationController.allTypeApplication(..))")
	public void p(){}
	@AfterReturning("p()")
	public void pushNotificationToApp(){
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		 HttpServletRequest request = attributes.getRequest();
		 String employeeId = request.getHeader("accessUserId");
		 String companyId = request.getHeader("companyId");
		 Application application = (Application) request.getAttribute("application");
		 ReturnData returnData = (ReturnData)request.getAttribute("returnData");
		 Company company;
		 if(returnData!=null&&"3000".equals(returnData.getReturnCode())){
			 //申请人
			 Employee empApplication = employeeDao.selectEmployeeByCompanyIdAndEmployeeId(employeeId, companyId);
			//审批人id
			 String approverId = application.getApprover();
			//审批人用户信息
			 Uusers approver = employeeDao.selectPhoneByEmployeeIdFromUUsers(approverId);
			//审批人在当前申请人公司的员工信息
			 Employee empApprover = employeeDao.selectEmployeeByCompanyIdAndEmployeeId(approverId, companyId);
			 //查询审批人当前的默认公司
			 PhoneClientId approverDefaultCompany = phoneClientIdMapper.selectDefaultCompanyByUserId(approverId);
			 //判断当前审批人的默认公司是否与申请人一致，一致则发送通知
			 /*if(companyId.equals(approverDefaultCompany.getCompanyId())){*///当前审批人默认公司和申请人在同一公司,发送通知
				 company = companyDao.selectByCompany(companyId);
				 //判断当前审批人的phone，clientId关系是是否存在，存在则发送通知
				 PhoneClientId approverPhoneClientId = phoneClientIdMapper.selectByPhone(approver.getPhone());
				 if(approverPhoneClientId!=null){//存在
					 JPushUtil client = new JPushUtil();
					 String applicationType = "";
					 switch(application.getApplicationType()){
					 case "1":
						 applicationType="请假";
						 break;
					 case "2":
						 applicationType="加班";
						 break;
					 case "3":
						 applicationType="出差";
						 break;
					 case "4":
						 applicationType="外出";
						 break;
					 case "5":
						 applicationType="补卡";
						 break;
					 }
					 String title=company.getCompanyName();
					 String body =empApplication.getEmployeeName()+",发起一条"+applicationType+"申请。";
					 Map<String ,String> contentMap = new HashMap<String ,String>();
					 contentMap.put("title", title);
					 contentMap.put("body", body);
					 String alert = JSON.toJSONString(contentMap);
					 Map<String,String> extraMap = new HashMap<String, String>();
					 //添加额外键值对
					 extraMap.put("companyId", companyId);
					 extraMap.put("companyName", company.getCompanyName());
					 extraMap.put("notificationType", TabJpush.approver);//通知类型,0:申请,1:审批 
					 String[] alias={approverPhoneClientId.getClientId()};//别名
					 //String[] alias={"1111"};
					 String str = client.sendPush(JPushUtil.JPUSH_ALIS,title, body, extraMap, alias);
					 if("1".equals(str)){
						 tabJpushMapper.insertTabJpush(new TabJpush(FormatUtil.createUuid(), 
								 companyId, employeeId, approverId, TabJpush.approver, 
								 title+","+body, application.getApplicationNo(), null));
					 }
					 System.out.println(str);
				 }
			/* }*/
		 }
	}
}
