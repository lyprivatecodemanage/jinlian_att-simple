package com.xiangshangban.att_simple.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.xiangshangban.att_simple.bean.Application;
import com.xiangshangban.att_simple.bean.ApplicationBusinessTravel;
import com.xiangshangban.att_simple.bean.ApplicationCommonContactPeople;
import com.xiangshangban.att_simple.bean.ApplicationFillCard;
import com.xiangshangban.att_simple.bean.ApplicationLeave;
import com.xiangshangban.att_simple.bean.ApplicationOutgoing;
import com.xiangshangban.att_simple.bean.ApplicationOvertime;
import com.xiangshangban.att_simple.bean.ApplicationToCopyPerson;
import com.xiangshangban.att_simple.bean.ApplicationTotalRecord;
import com.xiangshangban.att_simple.bean.ApplicationTransferRecord;
import com.xiangshangban.att_simple.bean.ApplicationType;
import com.xiangshangban.att_simple.bean.Employee;
import com.xiangshangban.att_simple.bean.MessageBean;
import com.xiangshangban.att_simple.bean.ReturnData;
import com.xiangshangban.att_simple.bean.Vacation;
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
import com.xiangshangban.att_simple.dao.VacationMapper;
import com.xiangshangban.att_simple.utils.FormatUtil;

@Service("applicationService")
@Transactional 
public class ApplicationServiceImpl implements ApplicationService {
	private static final Logger logger = Logger.getLogger(ApplicationServiceImpl.class);
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
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public Map<String, Object> applicationIndexPage(String employeeId, String companyId) {
		Map<String, Object> result = new HashMap<String,Object>();
		//查询申请类型
		List<ApplicationType> applicationTypeList = applicationTypeMapper.getApplicationTypeList();
		//查询年假剩余,年假额度,调休剩余,调休额度
		Vacation vacation = vacationMapper.SelectEmployeeVacation(companyId, null, employeeId);
		result.put("applicaitonTypeList", applicationTypeList);
		result.put("vacation", vacation);
		return result;
	}
	/**
	 * 请假申请
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public ReturnData leaveApplication(Application application) {
		ReturnData returnData = new ReturnData();
		try{
			//判断请假类型是否是年假和调休
			if("2".equals(application.getApplicationChildrenType())||"3".equals(application.getApplicationChildrenType())){
				//查询额度
				Vacation vacation = vacationMapper.SelectEmployeeVacation(application.getCompanyId(), null, application.getApplicationId());
				if("2".equals(application.getApplicationChildrenType())&&Integer.valueOf(application.getApplicationHour())>Integer.valueOf(vacation.getAnnualLeaveBalance())){
					returnData.setMessage("年假剩余不足");
					returnData.setReturnCode("4400");
					return returnData;
				}
				if("3".equals(application.getApplicationChildrenType())&&Integer.valueOf(application.getApplicationHour())>Integer.valueOf(vacation.getAdjustRestBalance())){
					returnData.setMessage("调休剩余不足");
					returnData.setReturnCode("4401");
					return returnData;
				}
			}
			Employee employee =  employeeDao.selectByEmployee(application.getApplicationId(), application.getCompanyId());
			application.setDepartmentId(employee.getEmployeeBirthday());
			//生成申请记录
			applicationTotalRecordMapper.insertApplicationRecord(application);
			//生成请假申请记录
			Object obj = this.setValue("1", null, application,Class.forName("com.xiangshangban.att_simple.bean.ApplicationLeave"), null);
			ApplicationLeave applicationLeave = (ApplicationLeave)obj;
			applicationLeaveMapper.insertApplicationRecord(applicationLeave);
			//是否抄送
			if("1".equals(application.getIsCopy())){
				//添加抄送记录
				for(ApplicationToCopyPerson applicationToCopyPerson :application.getCopyPersonList()){
					applicationToCopyPerson.setId(FormatUtil.createUuid());
					applicationToCopyPerson.setApplicationNo(application.getApplicationNo());
					applicationToCopyPerson.setOperaterTime(application.getApplicationTime());
					applicationToCopyPersonMapper.insertSelective(applicationToCopyPerson);
				}
			}
			returnData.setMessage("成功");
			returnData.setReturnCode("3000");
			return	returnData;
		}catch(Exception e){
			logger.info(e);
			returnData.setMessage("服务器错误");
			returnData.setReturnCode("3001");
			return	returnData;
		}
	}
	/**
	 * 加班申请
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public ReturnData overTimeApplication(Application application) {
		ReturnData returnData = new ReturnData();
		try{
		if(StringUtils.isEmpty(application)||StringUtils.isEmpty(application.getApplicationChildrenType())){
			returnData.setMessage("必传参数为空");
			returnData.setReturnCode("3006");
			return returnData;
		}
		//设置部门id
		Employee employee =  employeeDao.selectByEmployee(application.getApplicationId(), application.getCompanyId());
		application.setDepartmentId(employee.getEmployeeBirthday());//部门id
		//生成申请记录
		applicationTotalRecordMapper.insertApplicationRecord(application);
		//生成加班申请记录
		ApplicationOvertime applicationOvertime = (ApplicationOvertime)this.setValue("2", null, application, Class.forName("com.xiangshangban.att_simple.bean.ApplicationOvertime"),null);
		applicationOvertimeMapper.insertApplicationOvertimeRecord(applicationOvertime);
		returnData.setMessage("成功");
		returnData.setReturnCode("3000");
		return	returnData;
	}catch(Exception e){
		logger.info(e);
		returnData.setMessage("服务器错误");
		returnData.setReturnCode("3001");
		return	returnData;
	}
		
	}
	/**
	 * 出差申请
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public ReturnData businessTravelApplication(Application application) {
		ReturnData returnData = new ReturnData();
		try{
			if(StringUtils.isEmpty(application)||StringUtils.isEmpty(application.getApplicationChildrenType())){
				returnData.setMessage("必传参数为空");
				returnData.setReturnCode("3006");
				return returnData;
			}
			//设置部门id
			Employee employee =  employeeDao.selectByEmployee(application.getApplicationId(), application.getCompanyId());
			application.setDepartmentId(employee.getEmployeeBirthday());//部门id
			//生成申请记录
			applicationTotalRecordMapper.insertApplicationRecord(application);
			//生成出差记录
			ApplicationBusinessTravel applicationBusinessTravel = (ApplicationBusinessTravel)this.setValue("3", null, application, Class.forName("com.xiangshangban.att_simple.bean.ApplicationBusinessTravel"), null);
			applicationBusinessTravelMapper.insertApplicationBusinessTravelRecord(applicationBusinessTravel);
			returnData.setMessage("成功");
			returnData.setReturnCode("3000");
			return	returnData;
		}catch(Exception e){
			logger.info(e);
			returnData.setMessage("服务器错误");
			returnData.setReturnCode("3001");
			return	returnData;
		}
	}
	/**
	 * 外出申请
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public ReturnData outgoingApplication(Application application) {
		ReturnData returnData = new ReturnData();
		try{
			if(StringUtils.isEmpty(application)||StringUtils.isEmpty(application.getOutgoingLocation())){
				returnData.setMessage("必传参数为空");
				returnData.setReturnCode("3006");
				return returnData;
			}
			//设置部门id
			Employee employee =  employeeDao.selectByEmployee(application.getApplicationId(), application.getCompanyId());
			application.setDepartmentId(employee.getEmployeeBirthday());//部门id
			//生成申请记录
			applicationTotalRecordMapper.insertApplicationRecord(application);
			//生成外出记录
			ApplicationOutgoing applicationOutgoing = (ApplicationOutgoing)this.setValue("4", null, application, Class.forName("com.xiangshangban.att_simple.bean.ApplicationOutgoing"), null);
			applicationOutgoingMapper.insertApplicationOutgoingRecord(applicationOutgoing);
			returnData.setMessage("成功");
			returnData.setReturnCode("3000");
			return	returnData;
		}catch(Exception e){
			logger.info(e);
			returnData.setMessage("服务器错误");
			returnData.setReturnCode("3001");
			return	returnData;
		}
	}
	/**
	 * 补卡申请
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public ReturnData fillCardApplication(Application application) {
		ReturnData returnData = new ReturnData();
		try{
			if(StringUtils.isEmpty(application)||StringUtils.isEmpty(application.getApplicationChildrenType())){
				returnData.setMessage("必传参数为空");
				returnData.setReturnCode("3006");
				return returnData;
			}
			//设置部门id
			Employee employee =  employeeDao.selectByEmployee(application.getApplicationId(), application.getCompanyId());
			application.setDepartmentId(employee.getEmployeeBirthday());//部门id
			//生成申请记录
			applicationTotalRecordMapper.insertApplicationRecord(application);
			//生成外出记录
			ApplicationFillCard applicationFillCard = (ApplicationFillCard)this.setValue("5", null, application, Class.forName("com.xiangshangban.att_simple.bean.ApplicationFillCard"), null);
			applicationFillCardMapper.insertApplicationFillCardRecord(applicationFillCard);
			returnData.setMessage("成功");
			returnData.setReturnCode("3000");
			return	returnData;
		}catch(Exception e){
			logger.info(e);
			returnData.setMessage("服务器错误");
			returnData.setReturnCode("3001");
			return	returnData;
		}
	}
	
	/**
	 * 判断申请的时间段是否和别的重复
	 * @return
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public boolean isRepeatWithOtherApplication(Application application){
		Integer i = applicationLeaveMapper.selectStartTimeIsRepeat(application);
		if(i>0){
			//有重复
			return true;
		}
		//没有重复
		return false;
	}
	/**
	 * 设置常用联系人
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public boolean commonContactPeople(Application application) {
		String companyId = application.getCompanyId();
		String employeeId = application.getApplicationId();
		String type = application.getCommonContactPeopleList().get(0).getType();
		try{
			//先将之前的常用联系人删除之后,在重新添加常用联系人
			applicationCommonContactPeopleMapper.deleteByEmployeeIdAndCompanyIdAndType(employeeId, companyId, type);
			List<ApplicationCommonContactPeople> commonContactPeopleList = application.getCommonContactPeopleList();
			for(ApplicationCommonContactPeople contactPeople:commonContactPeopleList){
				Integer i = applicationCommonContactPeopleMapper.selectByAll(contactPeople);
				if(i<1){
					contactPeople.setId(FormatUtil.createUuid());
					contactPeople.setEmployeeId(application.getApplicationId());
					contactPeople.setCompanyId(application.getCompanyId());
					applicationCommonContactPeopleMapper.insert(contactPeople);
				}
			}
		}catch(Exception e){
			logger.info(e);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * 获取常用联系人列表
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public List<ApplicationCommonContactPeople> commonContactPeopleList(String employeeId,String companyId,String type,String page,String count){
		List<ApplicationCommonContactPeople> commonContactPeopleList = null;
		try{
		page = String.valueOf((Integer.valueOf(page)-1)*Integer.valueOf(count));//将页数转成记录条数
		Integer commonContactPeopleCount = applicationCommonContactPeopleMapper.selectCountByEmployeeIdAndCompanyId(employeeId,companyId,type);
		if(commonContactPeopleCount>0&&Integer.valueOf(page)<commonContactPeopleCount){//有常用联系人并且满足分页条件
			commonContactPeopleList = applicationCommonContactPeopleMapper.commonContactPeopleList(employeeId, companyId, type, page, count);
				int size = commonContactPeopleList.size();
				if(size<Integer.valueOf(count)){//常用联系人数目不够
					count = String.valueOf(Integer.valueOf(count)-size);
					page = String.valueOf(0);
					//查询公司的人员
					List<Employee> employeeList = employeeDao.selectCommonContactPeoplePage(employeeId,companyId, page, count);
					this.setValue("6",employeeList, null, null, commonContactPeopleList);
				}
		}else if(commonContactPeopleCount>0&&Integer.valueOf(page)>commonContactPeopleCount){//不满足分页条件
			commonContactPeopleList = new ArrayList<ApplicationCommonContactPeople>();
			page = String.valueOf(Integer.valueOf(page)-commonContactPeopleCount);
			List<Employee> employeeList = employeeDao.selectCommonContactPeoplePage(employeeId,companyId, page, count);
			this.setValue("6",employeeList, null, null, commonContactPeopleList);
		}else{//没有常用联系人
			commonContactPeopleList = new ArrayList<ApplicationCommonContactPeople>();
			List<Employee> employeeList = employeeDao.selectCommonContactPeoplePage(employeeId,companyId, page, count);
			this.setValue("6",employeeList, null, null, commonContactPeopleList);
		}
		
		}catch(Exception e){
			logger.info(e);
			e.printStackTrace();
		}
		return commonContactPeopleList;
	}
	
	public <T>T setValue(String setValueType,List<Employee> employeeList,Application application,Class<T> c,
			List<ApplicationCommonContactPeople> applicationCommonContactPeopleList){
		try {
			T t = null;
			MessageBean<T> mb  = null;
		if(c!=null){
			t = c.newInstance();
			mb = new MessageBean<T>(t);
		}
		if("1".equals(setValueType)||"2".equals(setValueType)||
				"3".equals(setValueType)||"4".equals(setValueType)||
				"5".equals(setValueType)){
			mb.setApplicationNo(application.getApplicationNo());//申请单号
			mb.setEmployeeId(application.getApplicationId());//员工id
			mb.setDepartmentId(application.getDepartmentId());//部门id
			mb.setCompanyId(application.getCompanyId());//公司id
			mb.setReason(application.getReason());//理由
			mb.setUploadVoucher(application.getUploadVoucher());//凭证
			mb.setApprover(application.getApprover());//审批人id
			mb.setOperaterTime(application.getApplicationTime());//操作时间
			mb.setIsTransfer(application.getIsTransfer());//是否转移
			mb.setApplicationHour(application.getApplicationHour());//申请小时数
			mb.setApplicationTime(application.getApplicationTime());//申请时间
		}
		if("1".equals(setValueType)||"2".equals(setValueType)||
				"3".equals(setValueType)||"4".equals(setValueType)){
			mb.setStartTime(application.getStartTime());//开始时间
			mb.setEndTime(application.getEndTime());//结束时间
		}
		switch(setValueType){
		case"1"://请假申请实体类设值
			mb.setLeaveType(application.getApplicationChildrenType());
			break;
		case"2"://加班申请实体类设值
			mb.setOvertimeType(application.getApplicationChildrenType());
			break;
		case"3"://出差申请实体类设值
			mb.setBusinessTravelType(application.getApplicationChildrenType());
			break;
		case"4"://外出申请实体类设值
			mb.setOutgoingLocation(application.getOutgoingLocation());
			break;
		case"5"://补卡申请实体类设值
			mb.setFillCardType(application.getApplicationChildrenType());
			mb.setFillCardTime(application.getFillCardTime());
			break;
		case"6":
			for(int i=0;i<employeeList.size();i++){
				ApplicationCommonContactPeople applicationCommonContactPeople = new ApplicationCommonContactPeople();
				MessageBean<ApplicationCommonContactPeople> mbc = new MessageBean<ApplicationCommonContactPeople>(applicationCommonContactPeople);
				mbc.setEmployeeId(employeeList.get(i).getEmployeeId());
				mbc.setCommonContactPeopleId(employeeList.get(i).getEmployeeId());
				mbc.setCommonContactPeopleName(employeeList.get(i).getEmployeeName());
				mbc.setType("0");
				boolean flag = true;
				if(applicationCommonContactPeopleList.size()>0){
					for(int j=0;j<applicationCommonContactPeopleList.size();j++){
						if(applicationCommonContactPeopleList.get(j).getCommonContactPeopleId().equals(employeeList.get(i).getEmployeeId())){
							flag=false;
						}
					}
					if(flag){
						applicationCommonContactPeopleList.add(applicationCommonContactPeople);
					}
				}else{
					applicationCommonContactPeopleList.add(applicationCommonContactPeople);
				}
			}
			break;
		}
		return t;
		} catch (InstantiationException e) {
			logger.info(e);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			logger.info(e);
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 申请列表
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public List<ApplicationTotalRecord> applicationList(String employeeId, String companyId, String page, String count) {
		page = String.valueOf((Integer.valueOf(page)-1)*Integer.valueOf(count));
		List<ApplicationTotalRecord> applicationTotalList = applicationTotalRecordMapper.selectApplicationList(employeeId, companyId, page, count);
		return applicationTotalList;
	}
	/**
	 * 申请详情
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public Application applicationDetails(String applicationNo,String employeeId,String departmentId,String companyId) {
		Application application = new Application();
		Application applicationDetails =null;
		ApplicationTotalRecord applicationRecordStatus = applicationTotalRecordMapper.selectByPrimaryKey(applicationNo);
		if("1".equals(applicationRecordStatus.getIsCopy())){//抄送
			List<ApplicationToCopyPerson> copyPersonList = applicationToCopyPersonMapper.selectCopyPersonByApplicationNo(applicationNo);
			for(int i=0;i<copyPersonList.size();i++){
				Employee emp=employeeDao.selectNameByEmployeeIdAndDepartmentIdAndCompanyId(copyPersonList.get(i).getAppCopyPersonId(), null, companyId);
				copyPersonList.get(i).setAppCopyPersonName(emp.getEmployeeName());
			}
			application.setCopyPersonList(copyPersonList);
		}
		String applicationType = applicationRecordStatus.getApplicationType();
		//if("0".equals(applicationRecordStatus.getIsComplete())){//未完成
			if("0".equals(applicationRecordStatus.getIsTransfer())){//未转移
				applicationDetails = this.getApplicationDetails(application, applicationRecordStatus, applicationNo, employeeId, departmentId, companyId);
			}else{//已转移
				applicationDetails = this.getApplicationDetails(application, applicationRecordStatus, applicationNo, employeeId, departmentId, companyId);
				List<ApplicationTransferRecord> transferRecordList = applicationTransferRecordMapper.selectTransferByApplicationNo(applicationNo);
				for(int i=0;i<transferRecordList.size();i++){
					Employee emp=employeeDao.selectNameByEmployeeIdAndDepartmentIdAndCompanyId(transferRecordList.get(i).getTransferPersonId(), null, companyId);
					transferRecordList.get(i).setTransferPersonName(emp.getEmployeeName());
					Employee emp1=employeeDao.selectNameByEmployeeIdAndDepartmentIdAndCompanyId(transferRecordList.get(i).getTransferPersionAccessId(), null, companyId);
					transferRecordList.get(i).setTransferPersionAccessName(emp.getEmployeeName());
				}
				applicationDetails.setTransferRecordList(transferRecordList);
			}
		/*}else{//已完成
		}*/
		return applicationDetails;
	}
	
	private Application getApplicationDetails(Application application,ApplicationTotalRecord applicationRecordStatus,String applicationNo,String employeeId,String departmentId,String companyId){
		Application applicationDetails = null;
		switch (applicationRecordStatus.getApplicationType()) {
		case "1":
			applicationDetails = applicationLeaveMapper.selectDetailsByApplicationNo(applicationNo);
			applicationDetails.setApplicationType("1");
			break;
		case "2":
			applicationDetails = applicationOvertimeMapper.selectDetailsByApplicationNo(applicationNo);
			applicationDetails.setApplicationType("2");
			break;
		case "3":
			applicationDetails = applicationBusinessTravelMapper.selectDetailsByApplicationNo(applicationNo);
			applicationDetails.setApplicationType("3");
			break;
		case "4":
			applicationDetails = applicationOutgoingMapper.selectDetailsByApplicationNo(applicationNo);
			applicationDetails.setApplicationType("4");
			break;
		case "5":
			applicationDetails = applicationFillCardMapper.selectDetailsByApplicationNo(applicationNo);
			applicationDetails.setApplicationType("5");
			break;
		}
		Employee emp = employeeDao.selectNameByEmployeeIdAndDepartmentIdAndCompanyId(employeeId, departmentId, companyId);
		applicationDetails.setApplicatrionPersonName(emp.getEmployeeName());
		applicationDetails.setDepartmentName(emp.getDepartmentName());
		applicationDetails.setCompanyName(emp.getCompanyName());
		if(!StringUtils.isEmpty(application.getCopyPersonList())){
			applicationDetails.setCopyPersonList(application.getCopyPersonList());
			applicationDetails.setIsCopy("1");
		}
		applicationDetails.setIsReject(applicationRecordStatus.getIsReject());
		applicationDetails.setIsComplete(applicationRecordStatus.getIsComplete());
		applicationDetails.setIsTransfer(applicationRecordStatus.getIsTransfer());
		if("1".equals(applicationRecordStatus.getIsComplete())){
			if("0".equals(applicationRecordStatus.getIsReject())){
				applicationDetails.setStatusDescription("已通过");
			}else{
				applicationDetails.setStatusDescription("已驳回");
			}
		}else{
			applicationDetails.setStatusDescription("审批中");
		}
		return applicationDetails;
	}
	/**
	 * 获取申请子类型
	 */
	@Override
	public List<ApplicationType> getApplicationChildrenTypeList(String applicationType) {
		List<ApplicationType> applicationChildrenTypeList = applicationTypeMapper.getApplicationChildrenTypeList(applicationType);
		return applicationChildrenTypeList;
	}
	/**
	 * 申请撤回
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public ReturnData applicationRevoke(String applicationNo, String companyId, String employeeId) {
		ReturnData returnData = new ReturnData();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date(System.currentTimeMillis());
		try{
		now = sdf.parse(sdf.format(now));
		ApplicationTotalRecord selectByPrimaryKey = applicationTotalRecordMapper.selectByPrimaryKey(applicationNo);
		if("0".equals(selectByPrimaryKey.getIsComplete())&&"0".equals(selectByPrimaryKey.getIsTransfer())){
			Date operaterTime = sdf.parse(sdf.format(selectByPrimaryKey.getOperaterTime()));
			int minutes = (int)((now.getTime()-operaterTime.getTime())/1000/60);
			if(minutes>=10){
				returnData.setMessage("您的申请已超过十分钟,无法撤回");
				returnData.setReturnCode("9999");
				return returnData;
			}else{//撤回操作
				ApplicationTotalRecord record = new ApplicationTotalRecord();
				record.setApplicationNo(applicationNo);
				record.setApplicationStatus("2");
				int i = applicationTotalRecordMapper.updateByPrimaryKeySelective(record);
				if(i>0){
					returnData.setMessage("成功");
					returnData.setReturnCode("3000");
					return returnData;
				}else{
					returnData.setMessage("撤回失败");
					returnData.setReturnCode("9999");
					return returnData;
				}
			}
		}else{
			returnData.setMessage("您的申请已被处理,无法撤回");
			returnData.setReturnCode("9999");
			return returnData;
		}
		}catch(Exception e){
			logger.info(e);
			e.printStackTrace();
			returnData.setMessage("服务器错误");
			returnData.setReturnCode("3001");
			return returnData;
		}
	}
	
}
