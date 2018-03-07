package com.xiangshangban.att_simple.aop;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.att_simple.bean.ApplicationTotalRecord;
import com.xiangshangban.att_simple.bean.Company;
import com.xiangshangban.att_simple.bean.Employee;
import com.xiangshangban.att_simple.bean.PhoneClientId;
import com.xiangshangban.att_simple.bean.ReturnData;
import com.xiangshangban.att_simple.bean.Uusers;
import com.xiangshangban.att_simple.dao.ApplicationTotalRecordMapper;
import com.xiangshangban.att_simple.dao.CompanyDao;
import com.xiangshangban.att_simple.dao.EmployeeDao;
import com.xiangshangban.att_simple.dao.PhoneClientIdMapper;
import com.xiangshangban.att_simple.utils.JPushUtil;

@Aspect
@Configuration
public class AppApproverJpush {
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private PhoneClientIdMapper phoneClientIdMapper;
	@Autowired
	private ApplicationTotalRecordMapper applicationTotalRecordMapper;
	@Pointcut("execution(* com.xiangshangban.att_simple.controller.ApproverController.approverApplication(..))")
	public void p(){}
	@After("p()")
	public void pushNotificationToApp(){
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		 HttpServletRequest request = attributes.getRequest();
		 String employeeId = request.getHeader("accessUserId");
		 String companyId = request.getHeader("companyId");
		 //根据employeeId和companyId查询出审批人的信息
		 Employee approver = employeeDao.selectEmployeeByCompanyIdAndEmployeeId(employeeId, companyId);
		 //根据companyId查询出公司的信息
		 Company company = companyDao.selectByCompany(companyId);
		 //获取切点参数
		 String jsonString = request.getAttribute("jsonString").toString();
		 JSONObject jobj = JSON.parseObject(jsonString);
		 String applicationNo = jobj.getString("applicationNo");//单号
		 String approverDescription = jobj.getString("approverDescription");//审批操作说明
		 //String postscriptason = jobj.getString("postscriptason");//附言
	 	 String transferPersonId = employeeId;//移交人id
		 String transferPersionAccessId= jobj.getString("transferPersionAccessId");//移交目标人id
		 ReturnData returnData = (ReturnData)request.getAttribute("returnData");
		 //根据单号查询出申请人id信息
		 ApplicationTotalRecord applicationTotalRecord = applicationTotalRecordMapper.selectByPrimaryKey(applicationNo);
		 String applicationType = "";
		 switch(applicationTotalRecord.getApplicationType()){
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
		 //根据申请人id查询出申请人的用户信息
		 Uusers applicationUser = employeeDao.selectPhoneByEmployeeIdFromUUsers(applicationTotalRecord.getApplicationId());
		 String applicationId = applicationTotalRecord.getApplicationId();//申请人id
		 //根据申请人id查询出申请人的信息
		 Employee applicationEmp = employeeDao.selectEmployeeByCompanyIdAndEmployeeId(applicationId, companyId);
	 	if("3000".equals(returnData.getReturnCode())){//审批操作成功
			 JPushUtil client = new JPushUtil();
			 Map<String,String> extraMap = new HashMap<String,String>();//额外的键值对
			 extraMap.put("companyId", companyId);
			 extraMap.put("companyName", company.getCompanyName());
			 String title = company.getCompanyName();
			 String alert ="";//通知内容
			 if("转移".equals(approverDescription)){
				 //根据移交目标人id查询出移交目标人的用户信息
				 Uusers transferPersonAccessUser = employeeDao.selectPhoneByEmployeeIdFromUUsers(transferPersionAccessId);
				//查询移交目标人手机端当前的默认公司是否与移交人所在公司一致,一致则发送通知
				 PhoneClientId transferPersonAccessDefaultCompany = phoneClientIdMapper.selectDefaultCompanyByUserId(transferPersonId);
				 if(companyId.equals(transferPersonAccessDefaultCompany.getCompanyId())){//一致
					//判断当前移交目标人的phone，clientId关系是是否存在，存在则发送通知
					PhoneClientId transferPersonAccessPhoneClientId = phoneClientIdMapper.selectByPhone(transferPersonAccessUser.getPhone());
					if(transferPersonAccessPhoneClientId!=null){//存在
						String[] alias={transferPersonAccessPhoneClientId.getClientId()};//别名数组
						alert=applicationEmp.getEmployeeName()+"的"+applicationType+"申请,被"+approver.getEmployeeName()+approverDescription+"给您了,请查阅!";
						extraMap.put("notificationType", "1");
						String str = client.sendPush(JPushUtil.JPUSH_ALIS, title, alert, extraMap, alias);
						System.out.println(str);
					}
				 }
			 }else{
				 //查询申请人手机端当前的默认公司是否与审批人所在公司一致,一致则发送通知
				 PhoneClientId applicationDefaultCompany = phoneClientIdMapper.selectDefaultCompanyByUserId(applicationTotalRecord.getApplicationId());
				 if(companyId.equals(applicationDefaultCompany.getCompanyId())){//当前申请人移动端默认公司和审批人在同一公司,发送通知
					 //判断当前申请人的phone，clientId关系是是否存在，存在则发送通知
					 PhoneClientId applicationPhoneClientId = phoneClientIdMapper.selectByPhone(applicationUser.getPhone());
				 if(applicationPhoneClientId!=null){//存在
					 String[] alias={applicationPhoneClientId.getClientId()};//别名数组
					 extraMap.put("notificationType", "0");
					 alert="您的"+applicationType+"申请,被"+approver.getEmployeeName()+approverDescription+"了!";//通知内容
					 String str = client.sendPush(JPushUtil.JPUSH_ALIS,title, alert, extraMap, alias);
					 System.out.println(str);
				 	}
				 }
			 }
		 }
	}
}
