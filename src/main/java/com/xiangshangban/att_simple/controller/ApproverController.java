package com.xiangshangban.att_simple.controller;

import java.util.List;

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
import com.xiangshangban.att_simple.bean.ApplicationTotalRecord;
import com.xiangshangban.att_simple.bean.ReturnData;
import com.xiangshangban.att_simple.service.ApproverService;

/** app所有假勤审批处理器
 * 
 */	
@RestController 
@RequestMapping("/approver")
public class ApproverController {
	private static final Logger logger = Logger.getLogger(ApproverController.class);
	@Autowired
	private ApproverService approverService;
	/**
	 * 审批首页列表/历史列表/条件筛选
	 * @param jsonString
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/approverIndexPage",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public ReturnData approverIndexPage(String jsonString,HttpServletRequest request){
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
					if("1".equals(approver.getIsCopy())&&!StringUtils.isEmpty(approver.getCopyPersonId())
							&&employeeId.equals(approver.getCopyPersonId())){
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
			ApplicationTotalRecord approverDetails = approverService.approverDetails(applicationNo);
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
}