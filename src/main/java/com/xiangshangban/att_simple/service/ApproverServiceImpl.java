package com.xiangshangban.att_simple.service;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xiangshangban.att_simple.bean.Application;
import com.xiangshangban.att_simple.bean.ApplicationTotalRecord;
import com.xiangshangban.att_simple.bean.ApplicationTransferRecord;
import com.xiangshangban.att_simple.bean.ReturnData;
import com.xiangshangban.att_simple.bean.Vacation;
import com.xiangshangban.att_simple.bean.VacationDetails;
import com.xiangshangban.att_simple.dao.ApplicationBusinessTravelMapper;
import com.xiangshangban.att_simple.dao.ApplicationCommonContactPeopleMapper;
import com.xiangshangban.att_simple.dao.ApplicationFillCardMapper;
import com.xiangshangban.att_simple.dao.ApplicationLeaveMapper;
import com.xiangshangban.att_simple.dao.ApplicationOutgoingMapper;
import com.xiangshangban.att_simple.dao.ApplicationOvertimeMapper;
import com.xiangshangban.att_simple.dao.ApplicationToCopyPersonMapper;
import com.xiangshangban.att_simple.dao.ApplicationTotalRecordMapper;
import com.xiangshangban.att_simple.dao.ApplicationTransferRecordMapper;
import com.xiangshangban.att_simple.dao.ApplicationTypeMapper;
import com.xiangshangban.att_simple.dao.EmployeeDao;
import com.xiangshangban.att_simple.dao.VacationDetailsMapper;
import com.xiangshangban.att_simple.dao.VacationMapper;
import com.xiangshangban.att_simple.utils.FormatUtil;
import com.xiangshangban.att_simple.utils.TimeUtil;

@Service("approverService")
public class ApproverServiceImpl implements ApproverService {
	private static final Logger logger = Logger.getLogger(ApproverServiceImpl.class);
	@Autowired
	private ApplicationTypeMapper applicationTypeMapper;//申请类型dao
	@Autowired
	private ApplicationLeaveMapper  applicationLeaveMapper;//请假记录dao
	@Autowired
	private ApplicationOvertimeMapper applicationOvertimeMapper;//加班记录dao
	@Autowired
	private ApplicationBusinessTravelMapper applicationBusinessTravelMapper;//出差记录dao
	@Autowired
	private ApplicationOutgoingMapper applicationOutgoingMapper;//外出记录dao
	@Autowired
	private ApplicationFillCardMapper applicationFillCardMapper;//补卡记录dao
	@Autowired
	private ApplicationToCopyPersonMapper  applicationToCopyPersonMapper;//抄送dao
	@Autowired
	private ApplicationTotalRecordMapper applicationTotalRecordMapper;//申请汇总记录dao
	@Autowired
	private VacationMapper vacationMapper;//假期dao
	@Autowired
	private EmployeeDao employeeDao;//员工dao
	@Autowired
	private ApplicationCommonContactPeopleMapper  applicationCommonContactPeopleMapper;//常用联系人dao
	@Autowired
	private ApplicationTransferRecordMapper applicationTransferRecordMapper;//转移记录dao
	@Autowired
	private VacationDetailsMapper vacationDetailsMapper;//假期详情dao
	
	/**
	 * 审批首页列表/历史列表/条件筛选
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public List<ApplicationTotalRecord> approverIndexPage(String employeeId,String companyId,String page,String count,
			String applicationType,String statusDescription,
			String applicationTimeDescription,String applicatrionPersonName) {
		String isComplete = "";
		String isTranser = "";
		String isReject = "";
		String isCopy = "";
		List<ApplicationTotalRecord> approverList = null;
		page = String.valueOf((Integer.valueOf(page)-1)*Integer.valueOf(count));
		//申请类型
		if(StringUtils.isEmpty(applicationType)||"全部".equals(applicationType)){
			applicationType = null;
		}else if("请假".equals(applicationType)){
			applicationType = "1";
		}else if("加班".equals(applicationType)){
			applicationType = "2";
		}else if("出差".equals(applicationType)){
			applicationType = "3";
		}else if("外出".equals(applicationType)){
			applicationType = "4";
		}else if("补卡".equals(applicationType)){
			applicationType = "5";
		}
		String applicationTimeStart = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String applicationTimeEnd = sdf.format(new Date(System.currentTimeMillis()));
		//申请时间
		if(StringUtils.isEmpty(applicationType)||"全部".equals(applicationTimeDescription)){
		}else if("近一周".equals(applicationTimeDescription)){
			applicationTimeStart = TimeUtil.getDateBefore(applicationTimeEnd, "6");
		}else if("近半月".equals(applicationTimeDescription)){
			applicationTimeStart = TimeUtil.getDateBefore(applicationTimeEnd, "14");
		}else if("近一月".equals(applicationTimeDescription)){
			applicationTimeStart = TimeUtil.getDateBefore(applicationTimeEnd, "29");
		}
		//申请人
		if(StringUtils.isNotEmpty(applicatrionPersonName)){
			applicatrionPersonName = "%"+applicatrionPersonName+"%";
		}
		//申请状态
		approverList = applicationTotalRecordMapper.selectApproverListTotal(employeeId,
				companyId, page, count, applicationType, statusDescription, 
				applicationTimeStart, applicationTimeEnd, applicatrionPersonName);
		/*if(StringUtils.isEmpty(statusDescription)||"全部".equals(statusDescription)){
			approverList = applicationTotalRecordMapper.selectApproverListTotal(employeeId, 
					companyId, page, count, applicationType, applicationTimeStart, applicationTimeEnd, applicatrionPersonName);
		}else{ 
			if("未审批".equals(statusDescription)){
				isComplete="0";
			}else if("已转移".equals(statusDescription)){
				isTranser = "1";
			}else if("已完成".equals(statusDescription)){
				isComplete = "1";
			}else if("已驳回".equals(statusDescription)){
				isComplete = "1";
				isReject = "1";
			}else if("抄送".equals(statusDescription)){
				isCopy="1";
			}
			approverList = applicationTotalRecordMapper.selectApproverListCondition(employeeId, 
					companyId, page, count, applicationType, isComplete, isTranser, isReject, 
					isCopy, applicationTimeStart, applicationTimeEnd, applicatrionPersonName);
		}*/
		return approverList;

	}
	/**
	 * 审批详情
	 */
	@Override
	public ApplicationTotalRecord approverDetails(String applicationNo,String companyId) {
		ApplicationTotalRecord selectApproverDetails = applicationTotalRecordMapper.selectApproverDetails(applicationNo,companyId);
		return selectApproverDetails;
	}
	/**
	 * 待审批条数
	 */
	@Override
	public int willApproverCount(String employeeId, String companyId) {
		int i = applicationTotalRecordMapper.selectCountFromWillApprover(employeeId, companyId);
		return i;
	}
	/**
	 * 审批申请
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public ReturnData approverApplication(String employeeId, String companyId, String applicationNo,
			String approverDescription, String postscriptason, String transferPersonId,
			String transferPersionAccessId) {
		ReturnData returnData = new ReturnData();
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
		Application selectDetailsByApplicationNo = applicationLeaveMapper.selectDetailsByApplicationNo(applicationNo);
		ApplicationTotalRecord selectByPrimaryKey = applicationTotalRecordMapper.selectByPrimaryKey(applicationNo);
		String previousOperaterTime = selectByPrimaryKey.getPreviousOperaterTime();
		if("同意".equals(approverDescription)){
			if(selectByPrimaryKey!=null&&StringUtils.isNotEmpty(selectByPrimaryKey.getApplicationType())){
				if("1".equals(selectByPrimaryKey.getApplicationType())){//请假
					if("2".equals(selectDetailsByApplicationNo.getApplicationChildrenType())){//年假
						//年假剩余扣减
					}
					if("3".equals(selectDetailsByApplicationNo.getApplicationChildrenType())){//调休假
						//调休剩余扣减
					}
				}
			selectByPrimaryKey.setIsComplete("1");
			selectByPrimaryKey.setRejectReason(postscriptason);
			applicationTotalRecordMapper.updateByPrimaryKeySelective(selectByPrimaryKey);
			returnData.setData(selectDetailsByApplicationNo);
			}else{
				returnData.setMessage("审批单数据异常");
				returnData.setReturnCode("9999");
				return returnData;
			}
		}else if("转移".equals(approverDescription)){
			ApplicationTransferRecord newApplicationTransferRecord = new ApplicationTransferRecord();
			newApplicationTransferRecord.setId(FormatUtil.createUuid());//id
			newApplicationTransferRecord.setCompanyId(companyId);//公司id
			newApplicationTransferRecord.setTransferPersonId(transferPersonId);//移交人id
			newApplicationTransferRecord.setApplicationNo(applicationNo);//申请单号
			newApplicationTransferRecord.setTransferPersionAccessId(transferPersionAccessId);//移交接收人id
			newApplicationTransferRecord.setOperaterTime(now);//操作时间
			newApplicationTransferRecord.setTransferExplain(postscriptason);
			if("0".equals(selectByPrimaryKey.getIsTransfer())){
				newApplicationTransferRecord.setTransferTimes("1");
			}else{
				List<ApplicationTransferRecord> selectTransferByApplicationNo = applicationTransferRecordMapper.selectTransferByApplicationNo(applicationNo);
				if(selectTransferByApplicationNo==null||selectTransferByApplicationNo.size()<1){
					returnData.setMessage("数据异常");
					returnData.setReturnCode("9999");
					return returnData;
				}else{
					newApplicationTransferRecord.setTransferTimes(String.valueOf(selectTransferByApplicationNo.size()+1));//转移次数
				}
			}
			applicationTransferRecordMapper.insertSelective(newApplicationTransferRecord);
			selectByPrimaryKey.setIsTransfer("1");
			selectByPrimaryKey.setOperaterId(employeeId);
			selectByPrimaryKey.setLastApprover(transferPersionAccessId);
			selectByPrimaryKey.setOperaterTime(now);
			selectByPrimaryKey.setPreviousOperaterTime(previousOperaterTime);
			applicationTotalRecordMapper.updateByPrimaryKeySelective(selectByPrimaryKey);
		}else if("驳回".equals(approverDescription)){
			selectByPrimaryKey.setIsComplete("1");
			selectByPrimaryKey.setIsReject("1");
			selectByPrimaryKey.setRejectReason(postscriptason);
			selectByPrimaryKey.setOperaterId(employeeId);
			selectByPrimaryKey.setOperaterTime(now);
			selectByPrimaryKey.setPreviousOperaterTime(previousOperaterTime);
			applicationTotalRecordMapper.updateByPrimaryKeySelective(selectByPrimaryKey);
		}
		return returnData;
	}
	
	
	/**
	 * *********************web审批中心
	 */
	
	/**
	 * web审批中心-未完成工单数和本月已完成工单数
	 */
	@Override
	public Map<String,String> webApproverCentreHeader(String companyId, String employeeId) {
		Map<String,String> result = new HashMap<String,String>();
		String startTime = TimeUtil.getCurrentMonthFirstDateOrMaxDate(false);
		String endTime = TimeUtil.getCurrentMonthFirstDateOrMaxDate(true);
		int willComleteNum = applicationTotalRecordMapper.selectCountWillApprover(companyId);
		int completedNum = applicationTotalRecordMapper.selectCountCompletedApprover(companyId, startTime, endTime);
		result.put("willComleteNum", String.valueOf(willComleteNum));
		result.put("completedNum", String.valueOf(completedNum));
		return result;
	}
	
	/**
	 * web审批中心-列表分页,条件搜索
	 */
	@Override
	public List<ApplicationTotalRecord> webApproverCentreList(String companyId,String page,String count,
			String departmentId,String applicationType,String isComplete,
			String employeeName,String startTime,String endTime) {
		ReturnData returnData = new ReturnData();
		List<ApplicationTotalRecord> selectWebApproverList = applicationTotalRecordMapper.selectWebApproverList(companyId, 
				page, count, departmentId, applicationType, isComplete, employeeName, startTime, endTime);
		return selectWebApproverList;
	}
	/**
	 * web审批中心-查看
	 */
	@Override
	public Application webApproverCentreLook(String companyId, String employeeId, String applicationNo) {
		ApplicationTotalRecord selectByPrimaryKey = applicationTotalRecordMapper.selectByPrimaryKey(applicationNo);
		Application selectDetailsByApplicationNo = null;
		if("1".equals(selectByPrimaryKey.getApplicationType())){//请假
			 selectDetailsByApplicationNo = applicationLeaveMapper.selectDetailsByApplicationNo(applicationNo);
		}else if("2".equals(selectByPrimaryKey.getApplicationType())){//加班
			 selectDetailsByApplicationNo = applicationOvertimeMapper.selectDetailsByApplicationNo(applicationNo);
		}else if("3".equals(selectByPrimaryKey.getApplicationType())){//出差
			 selectDetailsByApplicationNo = applicationBusinessTravelMapper.selectDetailsByApplicationNo(applicationNo);
		}else if("4".equals(selectByPrimaryKey.getApplicationType())){//外出
			 selectDetailsByApplicationNo = applicationOutgoingMapper.selectDetailsByApplicationNo(applicationNo);
		}else if("5".equals(selectByPrimaryKey.getApplicationType())){//补卡
			 selectDetailsByApplicationNo = applicationFillCardMapper.selectDetailsByApplicationNo(applicationNo);
		}
		
		return selectDetailsByApplicationNo;
	}
	
	/**
	 * 审批通过修改假期额度
	 * @param leaveType 假期类型(0:年假  1:调休)
	 * @param endDate 申请假期结束时间
	 * @param leaveDay 请假时长(h)
	 * @param employeeId 请假人员
	 * @param companyId 请假人员公司
	 * @param adjustingInstruction 请假理由
	 * @return
	 */
	private int updateVacation(String leaveType,String endDate,String leaveDay,String employeeId,String companyId,String adjustingInstruction){
		String year = endDate.substring(0,4);
		
		//年假
		if(leaveType.equals("0")){
			Vacation vacation = vacationMapper.SelectEmployeeVacation(companyId, null,employeeId,year);
			
			//查询年假假期详情最后一次修改的值
			VacationDetails vacationDetails = vacationDetailsMapper.SelectVacationIdByEndResult(vacation.getVacationId(),"0",year);
			
			
		} 
		
		//调休
		if(leaveType.equals("1")){
			
		}
		
		return 1;
	}
	
	
}
