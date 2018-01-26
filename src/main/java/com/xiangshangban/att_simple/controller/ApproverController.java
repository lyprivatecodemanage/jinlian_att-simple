package com.xiangshangban.att_simple.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.att_simple.bean.Application;
import com.xiangshangban.att_simple.bean.ApplicationTotalRecord;
import com.xiangshangban.att_simple.bean.Company;
import com.xiangshangban.att_simple.bean.ReturnData;
import com.xiangshangban.att_simple.service.ApproverService;
import com.xiangshangban.att_simple.service.CompanyService;
import com.xiangshangban.att_simple.service.OSSFileService;

/** app所有假勤审批处理器
 * 
 */	
@RestController 
@RequestMapping("/approver")
public class ApproverController {
	private static final Logger logger = Logger.getLogger(ApproverController.class);
	@Autowired
	private ApproverService approverService;
	@Autowired
	private OSSFileService oSSFileService;
	@Autowired
	private CompanyService companyService;
	/**
	 * 审批首页列表/历史列表/条件筛选
	 * @param jsonString
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/approverIndexPage",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public ReturnData approverIndexPage(@RequestBody String jsonString,HttpServletRequest request){
		ReturnData returnData = new ReturnData();
		String employeeId = "";
		String companyId = "";
		String page = "";
		String count = "";
		String applicationType = "";
		String statusDescription = "";
		String applicationTimeDescription = "";
		String applicatrionPersonName = "";
		try{
			employeeId = request.getHeader("accessUserId");//员工id
			companyId = request.getHeader("companyId");//公司id
			if(StringUtils.isEmpty(companyId)||StringUtils.isEmpty(employeeId)){
				returnData.setMessage("请求信息错误");
				returnData.setReturnCode("3012");
				return returnData;
			}
			try{
				JSONObject jobj = JSON.parseObject(jsonString);
				page = jobj.getString("page");
				count = jobj.getString("count");
				applicationType = jobj.getString("applicationType");//申请类型
				statusDescription = jobj.getString("statusDescription");//申请状态
				applicationTimeDescription = jobj.getString("applicationTimeDescription");//申请时间
				applicatrionPersonName = jobj.getString("applicatrionPersonName");//申请人
			}catch(Exception e){
				logger.info(e);
				e.printStackTrace();
				returnData.setMessage("参数错误");
				returnData.setReturnCode("3006");
				return returnData;
			}
			if(StringUtils.isEmpty(page)||StringUtils.isEmpty(count)){
				returnData.setMessage("必传参数为空");
				returnData.setReturnCode("3006");
				return returnData;
			}
			List<ApplicationTotalRecord> approverIndexPage = approverService.approverIndexPage(employeeId,
					companyId,page,count,applicationType,statusDescription,applicationTimeDescription,applicatrionPersonName);
			if(approverIndexPage!=null&&approverIndexPage.size()>0){
				for(ApplicationTotalRecord approver:approverIndexPage){
					if("1".equals(approver.getIsComplete())){
						if("0".equals(approver.getIsReject())){
							approver.setStatusDescription("已通过");
						}else{
							approver.setStatusDescription("已驳回");
						}
					}else{
						if(employeeId.equals(approver.getLastApprover())){
							approver.setStatusDescription("未审批");
						}else if(!employeeId.equals(approver.getLastApprover())&&"1".equals(approver.getIsTransfer())
								&&employeeId.equals(approver.getTransferPersonId())){
							approver.setStatusDescription("已转移");
						}
					}
					if("1".equals(approver.getIsCopy())&&!StringUtils.isEmpty(approver.getAppCopyPersonId())
							&&employeeId.equals(approver.getAppCopyPersonId())){
						approver.setStatusDescription("抄送");
					}
				}
			}
			returnData.setMessage("成功");
			returnData.setReturnCode("3000");
			returnData.setData(approverIndexPage);
			return returnData;
		}catch(Exception e){
			logger.info(e);
			e.printStackTrace();
			returnData.setMessage("服务器错误");
			returnData.setReturnCode("3001");
			return returnData;
		}
	}
	/**
	 * 审批详情
	 * @param jsonString
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/approverDetails",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public ReturnData approverDetails(@RequestBody String jsonString,HttpServletRequest request){
		ReturnData returnData = new ReturnData();
		String employeeId = "";
		String companyId = "";
		String applicationNo = "";
		//String statusDescription = "";
		try{
			employeeId = request.getHeader("accessUserId");//员工id
			companyId = request.getHeader("companyId");//公司id
			if(StringUtils.isEmpty(companyId)||StringUtils.isEmpty(employeeId)){
				returnData.setMessage("请求信息错误");
				returnData.setReturnCode("3012");
				return returnData;
			}
			try{
				JSONObject jobj = JSON.parseObject(jsonString);
				applicationNo = jobj.getString("applicationNo");
				//statusDescription = jobj.getString("statusDescription");
			}catch(Exception e){
				logger.info(e);
				e.printStackTrace();
				returnData.setMessage("参数错误");
				returnData.setReturnCode("3006");
				return returnData;
			}
			if(StringUtils.isEmpty(applicationNo)){
				returnData.setMessage("必传参数为空");
				returnData.setReturnCode("3006");
				return returnData;
			}
			ApplicationTotalRecord approverDetails = approverService.approverDetails(applicationNo,companyId);
			returnData.setMessage("成功");
			returnData.setReturnCode("3000");
			returnData.setData(approverDetails);
			return returnData;
		}catch(Exception e){
			logger.info(e);
			e.printStackTrace();
			returnData.setMessage("服务器错误");
			returnData.setReturnCode("3001");
			return returnData;
		}
	}
	/**
	 * 待审批数目
	 * @param jsonString
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/willApproverCount",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public ReturnData willApproverCount(String jsonString,HttpServletRequest request){
		ReturnData returnData = new ReturnData();
		String employeeId = "";
		String companyId = "";
		try{
			employeeId = request.getHeader("accessUserId");//员工id
			companyId = request.getHeader("companyId");//公司id
			if(StringUtils.isEmpty(companyId)||StringUtils.isEmpty(employeeId)){
				returnData.setMessage("请求信息错误");
				returnData.setReturnCode("3012");
				return returnData;
			}
			int i = approverService.willApproverCount(employeeId, companyId);
			returnData.setMessage("成功");
			returnData.setReturnCode("3000");
			returnData.setData(i);
			return returnData;
		}catch(Exception e){
			logger.info(e);
			e.printStackTrace();
			returnData.setMessage("服务器错误");
			returnData.setReturnCode("3001");
			return returnData;
		}
	}
	/**
	 * 审批申请
	 * @param jsonString
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/approverApplication",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public ReturnData approverApplication(@RequestBody String jsonString,HttpServletRequest request){
		ReturnData returnData = new ReturnData();
		String employeeId = "";
		String companyId = "";
		String applicationNo = "";//单号
		String approverDescription = "";//审批操作说明
		String postscriptason = "";//附言
		String transferPersonId ="";//移交人id
		String transferPersionAccessId="";//移交目标人id
		JSONObject jobj = null;
		try{
			employeeId = request.getHeader("accessUserId");//员工id
			companyId = request.getHeader("companyId");//公司id
			if(StringUtils.isEmpty(companyId)||StringUtils.isEmpty(employeeId)){
				returnData.setMessage("请求信息错误");
				returnData.setReturnCode("3012");
				return returnData;
			}
			try{
				jobj = JSON.parseObject(jsonString);
				applicationNo = jobj.getString("applicationNo");
				approverDescription = jobj.getString("approverDescription");
				postscriptason = jobj.getString("postscriptason");
			}catch(Exception e){
				logger.info(e);
				e.printStackTrace();
				returnData.setMessage("参数错误");
				returnData.setReturnCode("3006");
				return returnData;
			}
			if(StringUtils.isEmpty(applicationNo)||StringUtils.isEmpty(approverDescription)){
				returnData.setMessage("必传参数为空");
				returnData.setReturnCode("3006");
				return returnData;
			}
			if("转移".equals(approverDescription)){
				transferPersonId = employeeId;
				//transferPersonId = jobj.getString("transferPersonId");
				transferPersionAccessId = jobj.getString("transferPersionAccessId");
				if(/*StringUtils.isEmpty(transferPersonId)||*/StringUtils.isEmpty(transferPersionAccessId)){
					returnData.setMessage("必传参数为空");
					returnData.setReturnCode("3006");
					return returnData;
				}
			}
			
			
			
			returnData.setMessage("成功");
			returnData.setReturnCode("3000");
			returnData.setData("");
			return returnData;
		}catch(Exception e){
			logger.info(e);
			e.printStackTrace();
			returnData.setMessage("服务器错误");
			returnData.setReturnCode("3001");
			return returnData;
		}
	}
	/**
	 * ****************web审批中心
	 */
	/**
	 * 未完成工单数和本月已完成工单数
	 * @param jsonString
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/webApproverCentreHeader",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public ReturnData webApproverCentreHeader(@RequestBody String jsonString,HttpServletRequest request){
		ReturnData returnData = new ReturnData();
		String employeeId = "";//管理员id
		String companyId = "";//公司id
		try{
			employeeId = request.getHeader("accessUserId");//员工id
			companyId = request.getHeader("companyId");//公司id
			if(StringUtils.isEmpty(companyId)||StringUtils.isEmpty(employeeId)){
				returnData.setMessage("请求信息错误");
				returnData.setReturnCode("3012");
				return returnData;
			}
			Map<String ,String > result = approverService.webApproverCentreHeader(companyId, employeeId);
			returnData.setData(result);
			returnData.setMessage("成功");
			returnData.setReturnCode("3000");
			return returnData;
		}catch(Exception e){
			logger.info(e);
			e.printStackTrace();
			returnData.setMessage("服务器错误");
			returnData.setReturnCode("3001");
			return returnData;
		}
	}
	/**
	 * web审批中心列表
	 * @param jsonString
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/webApproverCentreList",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public ReturnData webApproverCentreList(@RequestBody String jsonString,HttpServletRequest request){
		ReturnData returnData = new ReturnData();
		String employeeId = "";
		String companyId = "";
		String page = "";
		String count ="";
		String departmentId = "";
		String applicationType = "";
		String isComplete = "";
		String employeeName = "";
		String startTime = "";
		String endTime = "";
		try{
			employeeId = request.getHeader("accessUserId");//员工id
			companyId = request.getHeader("companyId");//公司id
			if(StringUtils.isEmpty(companyId)||StringUtils.isEmpty(employeeId)){
				returnData.setMessage("请求信息错误");
				returnData.setReturnCode("3012");
				return returnData;
			}
			try{
				JSONObject jobj = JSON.parseObject(jsonString);
				page = jobj.getString("page");
				count = jobj.getString("count");
				departmentId = jobj.getString("departmentId");
				applicationType = jobj.getString("applicationType");
				isComplete = jobj.getString("isComplete");
				employeeName = jobj.getString("employeeName");
				startTime = jobj.getString("startTime");
				endTime = jobj.getString("endTime");
				if(StringUtils.isEmpty(page)||StringUtils.isEmpty(count)){
					returnData.setMessage("必传参数为空");
					returnData.setReturnCode("3006");
					return returnData;
				}
			}catch(Exception e){
				logger.info(e);
				e.printStackTrace();
				returnData.setMessage("参数错误");
				returnData.setReturnCode("3006");
				return returnData;
			}
			if(!StringUtils.isEmpty(employeeName)){
				employeeName = "%"+employeeName+"%";
			}
			if(!StringUtils.isEmpty(startTime)&&!StringUtils.isEmpty(endTime)){
				startTime = startTime+" 00:00:00";
				endTime = endTime+" 23:59:59";
			}
			page = String.valueOf((Integer.valueOf(page)-1)*Integer.valueOf(count));
			List<ApplicationTotalRecord> webApproverCentreList = approverService.webApproverCentreList(companyId,
					page, count, departmentId, applicationType,
					isComplete, employeeName, startTime, endTime);
			returnData.setMessage("成功");
			returnData.setReturnCode("3000");
			returnData.setData(webApproverCentreList);
			return returnData;
		}catch(Exception e){
			logger.info(e);
			e.printStackTrace();
			returnData.setMessage("服务器错误");
			returnData.setReturnCode("3001");
			return returnData;
		}
	}
	/**
	 * web审批中心-查看
	 * @param jsonString
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/webApproverCentreLook",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public ReturnData webApproverCentreLook(@RequestBody String jsonString,HttpServletRequest request){
		ReturnData returnData = new ReturnData();
		String employeeId = "";
		String companyId = "";
		String applicationNo = "";
		try{
			employeeId = request.getHeader("accessUserId");//员工id
			companyId = request.getHeader("companyId");//公司id
			if(StringUtils.isEmpty(companyId)||StringUtils.isEmpty(employeeId)){
				returnData.setMessage("请求信息错误");
				returnData.setReturnCode("3012");
				return returnData;
			}
			try{
				JSONObject jobj = JSON.parseObject(jsonString);
				applicationNo = jobj.getString("applicationNo");
				if(StringUtils.isEmpty(applicationNo)){
					returnData.setMessage("必传参数为空");
					returnData.setReturnCode("3006");
					return returnData;
				}
			}catch(Exception e){
				logger.info(e);
				e.printStackTrace();
				returnData.setMessage("参数错误");
				returnData.setReturnCode("3006");
				return returnData;
			}
			Application application = approverService.webApproverCentreLook(companyId, employeeId, applicationNo);
			this.getUploadVoucher(companyId, application);
			returnData.setMessage("成功");
			returnData.setReturnCode("3000");
			returnData.setData(application);
			return returnData;
		}catch(Exception e){
			logger.info(e);
			e.printStackTrace();
			returnData.setMessage("服务器错误");
			returnData.setReturnCode("3001");
			return returnData;
		}
	}
	
	private void getUploadVoucher(String companyId,Application application){
		Company company = companyService.selectCompany(companyId);
		String uploadVoucher = oSSFileService.getPathByKey(company.getCompanyNo(),
				"approval", application.getUploadVoucher());
		application.setUploadVoucher(uploadVoucher);
	}
}