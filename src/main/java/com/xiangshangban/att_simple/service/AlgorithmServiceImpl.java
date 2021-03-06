package com.xiangshangban.att_simple.service;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiangshangban.att_simple.bean.AlgorithmParam;
import com.xiangshangban.att_simple.bean.AlgorithmReport;
import com.xiangshangban.att_simple.bean.AlgorithmResult;
import com.xiangshangban.att_simple.bean.AlgorithmSign;
import com.xiangshangban.att_simple.bean.Application;
import com.xiangshangban.att_simple.bean.ClassesEmployee;
import com.xiangshangban.att_simple.bean.Company;
import com.xiangshangban.att_simple.bean.Employee;
import com.xiangshangban.att_simple.bean.ReportDaily;
import com.xiangshangban.att_simple.bean.ReportExcept;
import com.xiangshangban.att_simple.dao.AlgorithmMapper;
import com.xiangshangban.att_simple.dao.ClassesEmployeeMapper;
import com.xiangshangban.att_simple.dao.EmployeeDao;
import com.xiangshangban.att_simple.dao.ReportDailyMapper;
import com.xiangshangban.att_simple.dao.ReportExceptMapper;
import com.xiangshangban.att_simple.utils.FormatUtil;
import com.xiangshangban.att_simple.utils.TimeUtil;
/**
 * 考勤日报计算
 * @author 韦友弟
 *
 */
@Service("AlgorithmService")
public class AlgorithmServiceImpl implements AlgorithmService {
	private final Logger logger = Logger.getLogger(AlgorithmServiceImpl.class);
	@Autowired
	ClassesEmployeeMapper ClassesEmployeeMapper;
	@Autowired
	ReportDailyMapper reportDailyMapper;
	@Autowired
	ReportExceptMapper reportExceptMapper;
	@Autowired
	AlgorithmMapper algorithmMapper;
	@Autowired
	VacationService vacationService;
	@Autowired
	EmployeeDao employeeDao;
	@Override
	public void calculate(String countDate) {
		List<Company> companyIdList = algorithmMapper.getAllCompanyList();
		for(Company company : companyIdList){
			List<Employee> empIdList = algorithmMapper.getEmployeeOnJobList(
					company.getCompanyId(), countDate);
			for(Employee emp:empIdList){
				try {
					this.calculate(company.getCompanyId(), emp.getEmployeeId(), countDate);
				} catch (Exception e) {
					logger.info("公司ID："+company.getCompanyId()+", 员工ID："+emp.getEmployeeId()+", "+countDate+ "日报计算异常");
					logger.info(FormatUtil.getExceptionInfo(e));
				}
			}
		}
	}
	@Override
	public void calculate(String beginDate, String endDate) {
		
		beginDate = beginDate.substring(0, 10);
		endDate = endDate.substring(0, 10);
		logger.info(beginDate+"到"+endDate+"日报所有公司计算开始");
		String date = beginDate;
		String newEndDate = TimeUtil.getLongAfterDate(endDate, 1, Calendar.DATE);
		while(TimeUtil.compareTime(newEndDate+" 00:00:00", date+" 00:00:00")){//循环日期段
			List<Company> companyIdList = algorithmMapper.getAllCompanyList();
			for(Company company : companyIdList){
				List<Employee> empIdList = algorithmMapper.getEmployeeOnJobList(
						company.getCompanyId(), date);
				for(Employee emp:empIdList){
					try {
						this.calculate(company.getCompanyId(), emp.getEmployeeId(), date);
					} catch (Exception e) {
						logger.info("公司ID："+company.getCompanyId()+", 员工ID："+emp.getEmployeeId()+", "+date+ "日报计算异常");
						logger.info(FormatUtil.getExceptionInfo(e));
					}
				}
			}
			date = TimeUtil.getLongAfterDate(date+" 00:00:00", 1, Calendar.DATE);
		}
		logger.info(beginDate+"到"+endDate+"日报所有公司计算结束");
	}
	@Override
	public void calculateByCompany(String companyId, String beginDate, String endDate) {
		beginDate = beginDate.substring(0, 10);
		endDate = endDate.substring(0, 10);
		logger.info(beginDate+"到"+endDate+"日报计算开始");
		String date = beginDate;
		String newEndDate = TimeUtil.getLongAfterDate(endDate, 1, Calendar.DATE);
		while(TimeUtil.compareTime(newEndDate+" 00:00:00", date+" 00:00:00")){//循环日期段
			List<Employee> empIdList = algorithmMapper.getEmployeeOnJobList(
					companyId, "");
			for(Employee emp:empIdList){
				try {
					
					this.calculate(companyId, emp.getEmployeeId(), date);
					
				} catch (Exception e) {
					logger.info("公司ID："+companyId+", 员工ID："+emp.getEmployeeId()+", "+date+ "日报计算异常");
					logger.info(FormatUtil.getExceptionInfo(e));
				}
			}
			date = TimeUtil.getLongAfterDate(date+" 00:00:00", 1, Calendar.DATE);
		}
		logger.info(beginDate+"到"+endDate+"日报计算结束");
	}
	/**
	 * 计算一段日期区间的日报
	 * @param companyId 公司ID
	 * @param employeeId 员工ID
	 * @param beginDate 开始日期，  格式yyyy-MM-dd
	 * @param endDate 结束日期 ， 格式yyyy-MM-dd
	 */
	@Override
	public void calculate(String companyId, String employeeId, String beginDate, String endDate) {
		beginDate = beginDate.substring(0, 10);
		endDate = endDate.substring(0, 10);
		logger.info(beginDate+"到"+endDate+"日报计算开始");
		String date = beginDate;
		String newEndDate = TimeUtil.getLongAfterDate(endDate, 1, Calendar.DATE);
		while(TimeUtil.compareTime(newEndDate+" 00:00:00", date+" 00:00:00")){//循环日期段
			this.calculate(companyId, employeeId, date);
			date = TimeUtil.getLongAfterDate(date+" 00:00:00", 1, Calendar.DATE);
		}
		logger.info(beginDate+"到"+endDate+"日报计算结束");
		
	}
	/**
	 * 核心计算开始
	 */
	@Override
	public void calculate(String companyId,String employeeId, String countDate) {
		countDate = countDate.substring(0, 10);
		if(this.preCondition(companyId, employeeId, countDate)){
			AlgorithmParam algorithmParam = this.generateAlgorithmParam(companyId, employeeId, countDate);//各种查询
			AlgorithmResult result = new AlgorithmResult();
			result.getReportDaily().setAttDate(countDate);
			result.getReportDaily().setEmployeeId(employeeId);
			result.getReportDaily().setCompanyId(companyId);
			result.getReportDaily().setDeptId(algorithmParam.getEmployee().getDepartmentId());
			this.calculateStandard(algorithmParam, result);//计算
			this.postProcess(algorithmParam, result);//处理计算结果数据
		}
	}
	/**
	 * 区分有排班、无排班
	 * @param algorithmParam
	 * @return
	 */
	public AlgorithmResult calculateStandard(AlgorithmParam algorithmParam, AlgorithmResult result) {
		//有排班
		if(algorithmParam.getClassesEmployee()!=null 
				&& StringUtils.isNotEmpty(algorithmParam.getClassesEmployee().getOnDutySchedulingDate())){
			algorithmParam.setHasPlan(true);
			this.havePlan(algorithmParam, result);
		}else{//无排班
			this.noPlan(algorithmParam, result);
		}
		
		//计算实际出勤时长
		if(StringUtils.isNotEmpty(result.getReportDaily().getSignInTime()) 
				&& StringUtils.isNotEmpty(result.getReportDaily().getSignOutTime())){
			AlgorithmReport.setRealWorkTime(
					TimeUtil.getTimeLength(result.getReportDaily().getSignOutTime(),
							result.getReportDaily().getSignInTime())+"", result.getReportDaily());
		}
		//计算加班
		this.countOverTime(algorithmParam, result);
		return result;
	}
	/**
	 * 加班计算
	 * @param algorithmParam
	 * @param result
	 */
	public void countOverTime(AlgorithmParam algorithmParam, AlgorithmResult result) {
		List<Application> applicationList = algorithmMapper.getOverByTime(algorithmParam.getCompanyId(),
				algorithmParam.getEmployeeId(), algorithmParam.getCountDate());//查询开始日期在当天的加班记录
		for(Application application : applicationList){
			//查询当天是否有排班
			//初始化：加班打卡有效开始时间=加班申请开始时间-2小时;加班打卡有效结束时间=加班申请结束时间+2小时
			String overSignValidBegin=TimeUtil.getLongAfter(application.getStartTime(), -2, Calendar.HOUR);
			String overSignValidEnd=TimeUtil.getLongAfter(application.getEndTime(), 2, Calendar.HOUR);
			//当天有排班
			if(algorithmParam.getClassesEmployee()!=null 
					&& StringUtils.isNotEmpty(algorithmParam.getClassesEmployee().getOnDutySchedulingDate())){
				if(1==TimeUtil.timeRelation(algorithmParam.getClassesEmployee().getOnDutySchedulingDate(), 
						algorithmParam.getClassesEmployee().getOffDutySchedulingDate(),
						application.getStartTime(), application.getEndTime())){//是否申请时段早于排班上班时间（左外申请）
					//前一天是否有排班
					if(algorithmParam.getClassesEmployeeLastDay()!=null 
							&& StringUtils.isNotEmpty(algorithmParam.getClassesEmployeeLastDay().getOnDutySchedulingDate())){//有
						//影响值：加班打卡有效开始时间=前一天打卡有效开始时间。取前一天打卡有效开始时间，可避免跨天班时，班次后加班抓不到加班记录的情况
						overSignValidBegin = TimeUtil.getLongAfter(
								algorithmParam.getClassesEmployeeLastDay().getOnDutySchedulingDate(),
								-1*Integer.parseInt(algorithmParam.getClassesEmployee().getOnPunchCardRule()),
								Calendar.MINUTE);
					}
					//影响值：加班打卡有效结束时间=今日打卡有效结束时间
					overSignValidEnd = TimeUtil.getLongAfter(
							algorithmParam.getClassesEmployee().getOffDutySchedulingDate(), 
							Integer.parseInt(algorithmParam.getClassesEmployee().getOffPunchCardRule()),
							Calendar.MINUTE);
				}else{//右外申请
					//次日是否有排班
					if(algorithmParam.getClassesEmployeeNextDay()!=null 
							&& StringUtils.isNotEmpty(algorithmParam.getClassesEmployeeNextDay().getOffDutySchedulingDate())){//有
						overSignValidBegin = TimeUtil.getLongAfter(
								algorithmParam.getClassesEmployee().getOnDutySchedulingDate(), 
								-1*Integer.parseInt(algorithmParam.getClassesEmployee().getOnPunchCardRule()),
								Calendar.MINUTE);
					}
					overSignValidEnd = TimeUtil.getLongAfter(
							algorithmParam.getClassesEmployeeNextDay().getOffDutySchedulingDate(),
							Integer.parseInt(algorithmParam.getClassesEmployee().getOffPunchCardRule()),
							Calendar.MINUTE);
				}
			}else{//无排班
				//检查前一天班次打卡结束时间是否在当天
				ClassesEmployee classesLast = algorithmParam.getClassesEmployeeLastDay();
				if(classesLast!=null && StringUtils.isNotEmpty(classesLast.getOffDutySchedulingDate())
						&& StringUtils.isNotEmpty(classesLast.getOnDutySchedulingDate())){
					String lastDateCardEnd = TimeUtil.getLongAfter(
							classesLast.getOffDutySchedulingDate(), 
							Integer.parseInt(classesLast.getOffPunchCardRule()), 
							Calendar.MINUTE);//前一天班次打卡结束时间
					if(!TimeUtil.compareTime(algorithmParam.getCountDate()+" 00:00:00", lastDateCardEnd)){//前一天班次打卡结束时间在当天
						overSignValidBegin = TimeUtil.getLongAfter(
								algorithmParam.getClassesEmployeeLastDay().getOnDutySchedulingDate(),
								-1*Integer.parseInt(algorithmParam.getClassesEmployee().getOnPunchCardRule()),
								Calendar.MINUTE);
					}
				}
				//抓取后一天的排班班次
				//检查后一天班次打卡开始时间是否在当天
				ClassesEmployee classesNext = algorithmMapper.getPlanByDate(algorithmParam.getCompanyId(),
						algorithmParam.getEmployeeId(), TimeUtil.getLongAfter(
								algorithmParam.getCountDate()+" 00:00:00", 1, Calendar.DATE));
				//检查次日班次打卡开始时间是否在当天
				if(classesNext!=null && StringUtils.isNotEmpty(classesNext.getOnDutySchedulingDate())
						&& StringUtils.isNotEmpty(classesNext.getOffDutySchedulingDate())){
					String lastDateCardBegin = TimeUtil.getLongAfter(
							classesNext.getOnDutySchedulingDate(), 
							-1*Integer.parseInt(classesNext.getOnPunchCardRule()), 
							Calendar.MINUTE);//次日班次打卡开始时间
					if(TimeUtil.compareTime(algorithmParam.getCountDate()+" 23:59:59", lastDateCardBegin)){//次日班次打卡开始时间在当天
						overSignValidEnd = TimeUtil.getLongAfter(
								algorithmParam.getClassesEmployee().getOffDutySchedulingDate(), 
								Integer.parseInt(algorithmParam.getClassesEmployee().getOffPunchCardRule()),
								Calendar.MINUTE);
					}
				}
			}
			String overSignIn = algorithmMapper.getMinSignIn(algorithmParam.getCompanyId(), 
					algorithmParam.getEmployeeId(), overSignValidBegin, overSignValidEnd);
			String overSignOut = algorithmMapper.getMaxSignOut(algorithmParam.getCompanyId(), 
					algorithmParam.getEmployeeId(), overSignValidBegin, overSignValidEnd);
			//是否既有“加班签到时间”又有“加班签退时间”
			//是否加班签到-签退区间与加班申请区间有交集
			if(StringUtils.isNotEmpty(overSignIn) && StringUtils.isNotEmpty(overSignOut)){
				long length = TimeUtil.containTimeLength(application.getStartTime(), application.getEndTime(), overSignIn, overSignOut);
				AlgorithmReport.setNormalOverWork(
						length+"",
						result.getReportDaily());
			}
			
		}
	}
	/**
	 * 无排班的计算方式
	 * @param algorithmParam
	 * @return
	 */
	public AlgorithmResult noPlan(AlgorithmParam algorithmParam, AlgorithmResult result) {
		//开始确定打卡有效区间
		//抓取前一天的排班班次
		ClassesEmployee classesLast = algorithmMapper.getPlanByDate(algorithmParam.getCompanyId(),
				algorithmParam.getEmployeeId(), TimeUtil.getLongAfter(
						algorithmParam.getCountDate()+" 00:00:00", -1, Calendar.DATE));
		//检查前一天班次打卡结束时间是否在当天
		if(classesLast!=null && StringUtils.isNotEmpty(classesLast.getOffDutySchedulingDate())
				&& StringUtils.isNotEmpty(classesLast.getOnDutySchedulingDate()) 
				&& classesLast!=null 
				&& StringUtils.isNotEmpty(classesLast.getOffPunchCardRule())){
			String lastDateCardEnd = TimeUtil.getLongAfter(
					classesLast.getOffDutySchedulingDate(), 
					Integer.parseInt(classesLast.getOffPunchCardRule()), 
					Calendar.MINUTE);//前一天班次打卡结束时间
			if(!TimeUtil.compareTime(algorithmParam.getCountDate()+" 00:00:00", lastDateCardEnd)){//前一天班次打卡结束时间在当天
				algorithmParam.setSignInLimitLine(lastDateCardEnd);
			}else{
				algorithmParam.setSignInLimitLine(algorithmParam.getCountDate()+" 00:00:00");
			}
		}else{//前一天无排班
			algorithmParam.setSignInLimitLine(algorithmParam.getCountDate()+" 00:00:00");
		}
		//抓取后一天的排班班次
		//检查后一天班次打卡结束时间是否在当天
		ClassesEmployee classesNext = algorithmMapper.getPlanByDate(algorithmParam.getCompanyId(),
				algorithmParam.getEmployeeId(), TimeUtil.getLongAfter(
						algorithmParam.getCountDate()+" 00:00:00", 1, Calendar.DATE));
		//检查次日班次打卡开始时间是否在当天
		if(classesNext!=null && StringUtils.isNotEmpty(classesNext.getOnDutySchedulingDate())
				&& StringUtils.isNotEmpty(classesNext.getOffDutySchedulingDate()) 
				&& classesNext!=null 
				&& StringUtils.isNotEmpty(classesNext.getOnPunchCardRule())){
			String lastDateCardEnd = TimeUtil.getLongAfter(
					classesNext.getOffDutySchedulingDate(), 
					-1*Integer.parseInt(classesNext.getOffPunchCardRule()), 
					Calendar.MINUTE);//次日班次打卡开始时间
			if(TimeUtil.compareTime(lastDateCardEnd, algorithmParam.getCountDate()+" 23:59:59")){//次日班次打卡开始时间在当天
				algorithmParam.setSignOutLimitLine(lastDateCardEnd);
			}else{
				algorithmParam.setSignOutLimitLine(algorithmParam.getCountDate()+" 23:59:59");
			}
		}else{//次日无排班
			algorithmParam.setSignOutLimitLine(algorithmParam.getCountDate()+" 23:59:59");
		}
		this.getNoPlanSign(algorithmParam, result);//签到签退、实出
		this.countOutNoPlan(algorithmParam, result);//外出
		this.countBusinessNoPlan(algorithmParam, result);//出差
		return result;
	}
	
	/**
	 * 有排班的计算方式，区分有请假和无请假方式
	 * @param algorithmParam
	 * @return
	 */
	public AlgorithmResult havePlan(AlgorithmParam algorithmParam, AlgorithmResult result) {
		//设置初始值
		String attBeginLine = algorithmParam.getClassesEmployee().getOnDutySchedulingDate();//应在岗的最早时间
		String attEndLine = algorithmParam.getClassesEmployee().getOffDutySchedulingDate();//应在岗的最晚时间
		algorithmParam.setAttBeginLine(attBeginLine);
		algorithmParam.setAttEndLine(attEndLine);
		//请假、外出、出差，将影响应在岗时间
		this.countLeave(algorithmParam, result);
		this.countOut(algorithmParam, result);
		this.countBusiness(algorithmParam, result);
		//根据centerLine区分签到签退的中间线， attBeginLine应在岗的最早时间，attEndLine应在岗的最晚时间， 获得签到签退时间
		this.getHasPlanSign(algorithmParam, result);
		this.countWorkTime(algorithmParam, result);//应出
		return result;
	}
	/**
	 * 有排班：计算应出时长、应出天数
	 * @param algorithmParam
	 * @param result
	 */
	public void countWorkTime(AlgorithmParam algorithmParam, AlgorithmResult result) {
		ClassesEmployee ClassesEmployee = algorithmParam.getClassesEmployee();
		long restTime = 0;
		if(StringUtils.isNotEmpty(ClassesEmployee.getRestStartTime()) &&
				StringUtils.isNotEmpty(ClassesEmployee.getRestEndTime())){
			restTime = TimeUtil.getTimeLength(ClassesEmployee.getRestEndTime(),
					ClassesEmployee.getRestStartTime());
		}
		long workTime = TimeUtil.getTimeLength(
				ClassesEmployee.getOffDutySchedulingDate(), 
				ClassesEmployee.getOnDutySchedulingDate())-restTime;
		
		AlgorithmReport.setWorkTime(workTime+"", result.getReportDaily());
		result.getReportDaily().setWorkDay("1");
	}
	/**
	 * 计算请假
	 * @param algorithmParam
	 * @param result
	 */
	public void countLeave(AlgorithmParam algorithmParam, AlgorithmResult result) {
		//查询与班次有交集的请假
		List<Application> applicationList = algorithmMapper.getLeaveByTime(algorithmParam.getCompanyId(),
				algorithmParam.getEmployeeId(), algorithmParam.getClassesEmployee().getOnDutySchedulingDate(),
				algorithmParam.getClassesEmployee().getOffDutySchedulingDate());
		this.countApplyByClasses(algorithmParam, result, applicationList);
		
	}
	/**
	 * 计算外出
	 * @param algorithmParam
	 * @param result
	 */
	public void countOut(AlgorithmParam algorithmParam, AlgorithmResult result) {
		//查询与班次有交集的外出
		List<Application> applicationList = algorithmMapper.getOutByTime(algorithmParam.getCompanyId(),
				algorithmParam.getEmployeeId(), algorithmParam.getClassesEmployee().getOnDutySchedulingDate(),
				algorithmParam.getClassesEmployee().getOffDutySchedulingDate());
		this.countApplyByClasses(algorithmParam, result, applicationList);
	}
	/**
	 * 计算出差
	 * @param algorithmParam
	 * @param result
	 */
	public void countBusiness(AlgorithmParam algorithmParam, AlgorithmResult result) {
		//查询与班次有交集的出差
		List<Application> applicationList = algorithmMapper.getBusinessByTime(algorithmParam.getCompanyId(),
				algorithmParam.getEmployeeId(), algorithmParam.getClassesEmployee().getOnDutySchedulingDate(),
				algorithmParam.getClassesEmployee().getOffDutySchedulingDate());
		this.countApplyByClasses(algorithmParam, result, applicationList);
		
	}
	/**
	 * 计算请假、外出、出差；改变应在岗的最早时间，应在岗的最晚时间
	 * @param algorithmParam
	 * @param result
	 * @return 总时长，单位分钟
	 */
	public double countApplyByClasses(AlgorithmParam algorithmParam, AlgorithmResult result, List<Application> applicationList){
		double timeLength=0;
		String attBeginLine = algorithmParam.getAttBeginLine();//应在岗的最早时间
		String attEndLine = algorithmParam.getAttEndLine();//应在岗的最晚时间
		String restBeginTime = algorithmParam.getClassesEmployee().getRestStartTime();//休息开始时间
		String restEndTime = algorithmParam.getClassesEmployee().getRestEndTime();//休息结束时间
		
		for(Application application : applicationList){
			double currentApply = 0;//当前申请时长
			double leaveRestTime = 0;//申请与休息时间交叉的时长
			String currentApplyBegin=application.getStartTime();
			String currentApplyEnd=application.getEndTime();
			//申请时间与应在岗时间的关系
			int appAttRelation = TimeUtil.timeRelation(attBeginLine, attEndLine,
					application.getStartTime(), application.getEndTime());
			//申请时间与休息时间的关系
			int appRestRelation = 1;
			//未设置休息时间
			if(StringUtils.isEmpty(restBeginTime) || StringUtils.isEmpty(restEndTime)){
				appRestRelation = 1;
			}else{
				appRestRelation = TimeUtil.timeRelation(restBeginTime, restEndTime,
						application.getStartTime(), application.getEndTime());
			}
			if(appAttRelation==2 ){//左申请
				currentApplyBegin=attBeginLine;
				switch (appRestRelation) {
				case 1://与休息时间不交叉
					attBeginLine=currentApplyEnd;
					leaveRestTime=0;
					break;
				case 2://左交叉，休息时间在右边
					attBeginLine=restEndTime;//在岗时间等于休息结束时间
					currentApplyEnd=restBeginTime;//当前申请参与计算的结束时间等于休息开始时间
					leaveRestTime=0;//计算休息时间与申请时间的交集时长
					break;
				case 5://申请时间包含休息时间
					attBeginLine=currentApplyEnd;
					leaveRestTime= TimeUtil.getTimeLength(restEndTime, restBeginTime);
					break;
				default:
					break;
				}
			}
			if(appAttRelation==3){//内申请
				switch (appRestRelation) {
				case 1://与休息时间不交叉
					leaveRestTime=0;
					break;
				case 2://左交叉，休息时间在右边
					currentApplyEnd=restBeginTime;//当前申请参与计算的结束时间等于休息开始时间
					leaveRestTime=0;//计算休息时间与申请时间的交集时长
					break;
				case 3://申请时间包含休息时间
					leaveRestTime= TimeUtil.getTimeLength(restEndTime, restBeginTime);
				case 4://右交叉，休息时间在左边
					currentApplyBegin=restEndTime;//当前申请参与计算的开始时间等于休息结束时间
					leaveRestTime=0;//计算休息时间与申请时间的交集时长
					break;
				case 5://申请时间包含休息时间
					leaveRestTime= TimeUtil.getTimeLength(restEndTime, restBeginTime);
					break;
				case 6://与休息时间不交叉
					leaveRestTime=0;
					break;
				default:
					break;
				}
			}
			if(appAttRelation==4){//右申请
				currentApplyEnd=attEndLine;
				switch (appRestRelation) {
				case 4://右交叉，休息时间在左边
					attEndLine=restBeginTime;//在岗时间等于休息结束时间
					currentApplyBegin=restEndTime;//当前申请参与计算的开始时间等于休息结束时间
					leaveRestTime=0;//计算休息时间与申请时间的交集时长
					break;
				case 5://申请时间包含休息时间
					attEndLine=currentApplyBegin;
					leaveRestTime= TimeUtil.getTimeLength(restEndTime, restBeginTime);
					break;
				case 6://与休息时间不交叉
					leaveRestTime=0;
					break;
				default:
					break;
				}
			}
			if(appAttRelation==5){//外申请
				currentApplyBegin=attBeginLine;
				currentApplyEnd=attEndLine;
				attBeginLine = algorithmParam.getAttEndLine();//应在岗的最早时间
				attEndLine = algorithmParam.getAttBeginLine();//应在岗的最晚时间
				leaveRestTime= TimeUtil.getTimeLength(restEndTime, restBeginTime);
			}
			currentApply = TimeUtil.getTimeLength(currentApplyEnd, currentApplyBegin)-leaveRestTime;
			timeLength=timeLength+currentApply;
			//申请类型(申请类型(请假:'1';加班:'2';出差'3';外出:'4';'补卡5'))
			//请假类型(1:事假;2:年假;3:调休假;4:婚假;5:产假;6:丧假)
			this.setApplyTime(result, application, currentApply);
		}
		algorithmParam.setAttBeginLine(attBeginLine);
		algorithmParam.setAttEndLine(attEndLine);
		return timeLength;
	}
	public void setApplyTime(AlgorithmResult result, Application application, double currentApply) {
		switch (application.getApplicationType()) {
		case "1"://请假
			switch (application.getApplicationChildrenType()) {
			case "1"://事假
				AlgorithmReport.setLeaveAbsence(currentApply+"", result.getReportDaily());//事假
				break;
			case "2"://年假
				AlgorithmReport.setLeaveAnnual(currentApply+"", result.getReportDaily());//事假
				break;
			case "3"://调休假
				AlgorithmReport.setLeaveDaysOff(currentApply+"", result.getReportDaily());
				break;
			case "4"://婚假
				AlgorithmReport.setLeaveMarriage(currentApply+"", result.getReportDaily());
				break;
			case "5"://产假
				AlgorithmReport.setLeaveMaternity(currentApply+"", result.getReportDaily());
				break;
			case "6"://丧假
				AlgorithmReport.setLeaveFuneral(currentApply+"", result.getReportDaily());
				break;
			case "7"://病假
				AlgorithmReport.setLeaveSick(currentApply+"", result.getReportDaily());
				break;
			default:
				break;
			}
			break;
		case "3"://出差
			AlgorithmReport.setEvectionTimeWork(currentApply+"", result.getReportDaily());
			break;
		case "4"://外出
			AlgorithmReport.setOutTimeWork(currentApply+"", result.getReportDaily());
			break;
		default:
			break;
		}
	}
	/**
	 * 无排班时：出差和外出的计算
	 * @param algorithmParam
	 * @param result
	 */
	public void countOutNoPlan(AlgorithmParam algorithmParam, AlgorithmResult result) {
		//查询与今日有交集的外出、出差
		List<Application> applicationOutList = algorithmMapper.getOutByTime(algorithmParam.getCompanyId(),
				algorithmParam.getEmployeeId(), algorithmParam.getSignInLimitLine(),
				algorithmParam.getSignOutLimitLine());
		this.countApplyNoPlan(result, applicationOutList);
	}
	/**
	 * 无排班时：出差的计算
	 * @param algorithmParam
	 * @param result
	 */
	public void countBusinessNoPlan(AlgorithmParam algorithmParam, AlgorithmResult result) {
		//查询与今日有交集的外出、出差
		List<Application> applicationBusinessList = algorithmMapper.getBusinessByTime(algorithmParam.getCompanyId(),
				algorithmParam.getEmployeeId(), algorithmParam.getSignInLimitLine(),
				algorithmParam.getSignOutLimitLine());
		this.countApplyNoPlan(result, applicationBusinessList);
		
	}
	/**
	 * 无排班时：外出、出差的申请计算
	 * @param result
	 * @param applicationBusinessList
	 */
	public void countApplyNoPlan(AlgorithmResult result, List<Application> applicationList) {
		for(Application application : applicationList){
			if("0".equals(application.getIsSkipRestDay())){//不跳过休息日，则计算;（只有外出和出差需要此状态）是否跳过休息日（0：否；1：是）
				long currentApply = TimeUtil.getTimeLength(
						application.getEndTime(), application.getEndTime());
				AlgorithmReport.setOutTimeWork(currentApply+"", result.getReportDaily());
			}
		}
	}
	/**
	 * 检查异常，并记录异常。
	 * 异常类型： 1迟到  ;2早退；3未签到; 4未签退 ； 5	无效; 6旷工； 7	过早打卡; 8过晚打卡；9重复打卡;10无排班打卡；11其它
	 * @param algorithmParam
	 * @param result
	 */
	public void checkException(AlgorithmParam algorithmParam, AlgorithmResult result) {
		//检查异常
		String signInTime = result.getReportDaily().getSignInTime();
		String signOutTime = result.getReportDaily().getSignOutTime();
		String employeeId = algorithmParam.getEmployeeId();
		String companyId = algorithmParam.getCompanyId();
		String countDate = algorithmParam.getCountDate();
		if(StringUtils.isEmpty(signInTime) && StringUtils.isEmpty(signOutTime)){//旷工
			ReportExcept reportExcept = new ReportExcept();
			reportExcept.setEmployeeId(employeeId);
			reportExcept.setCompanyId(companyId);
			reportExcept.setExceptDate(countDate);
			reportExcept.setExceptType("5");
			result.getReportExcept().add(reportExcept);
			result.getReportDaily().setAbsent("1");
		}else{
			String lateLine = TimeUtil.getLongAfter(
					algorithmParam.getAttBeginLine(), 
					Integer.parseInt(algorithmParam.getClassesEmployee().getSignInRule()), 
					Calendar.MINUTE);//迟到线
			String earlyLine = TimeUtil.getLongAfter(
					algorithmParam.getAttEndLine(), 
					-1*Integer.parseInt(algorithmParam.getClassesEmployee().getSignOutRule()), 
					Calendar.MINUTE);//早退线
			if(StringUtils.isEmpty(signInTime)){//未签到
				ReportExcept reportExcept = new ReportExcept();
				reportExcept.setEmployeeId(employeeId);
				reportExcept.setCompanyId(companyId);
				reportExcept.setExceptDate(countDate);
				reportExcept.setExceptType("3");
				result.getReportExcept().add(reportExcept);
			}else if(TimeUtil.compareTime(signInTime, lateLine)){//迟到
				ReportExcept reportExcept = new ReportExcept();
				reportExcept.setEmployeeId(employeeId);
				reportExcept.setCompanyId(companyId);
				reportExcept.setExceptDate(countDate);
				reportExcept.setExceptType("1");
				result.getReportExcept().add(reportExcept);
				result.getReportDaily().setLate("1");//迟到次数
			}
			if(StringUtils.isEmpty(signOutTime)){//未签退
				ReportExcept reportExcept = new ReportExcept();
				reportExcept.setEmployeeId(employeeId);
				reportExcept.setCompanyId(companyId);
				reportExcept.setExceptDate(countDate);
				reportExcept.setExceptType("4");
				result.getReportExcept().add(reportExcept);
			}else if(TimeUtil.compareTime(earlyLine, signOutTime)){//早退
				ReportExcept reportExcept = new ReportExcept();
				reportExcept.setEmployeeId(employeeId);
				reportExcept.setCompanyId(companyId);
				reportExcept.setExceptDate(countDate);
				reportExcept.setExceptType("2");
				result.getReportExcept().add(reportExcept);
				result.getReportDaily().setEarly("1");//早退次数
			}
		}
	}
	/**
	 * 无排班情况下，
	 * 根据signInLimitLine上班打卡开始时间，signOutLimitLine下班班打卡结束时间
	 * 获得签到签退时间
	 * @param algorithmParam
	 * @param result
	 */
	public void getNoPlanSign(AlgorithmParam algorithmParam, AlgorithmResult result){
		result.getReportDaily().setSignInTime(
				algorithmMapper.getMinSignIn(algorithmParam.getCompanyId(),
						algorithmParam.getEmployeeId(), algorithmParam.getSignInLimitLine(),
						algorithmParam.getSignOutLimitLine()));
		result.getReportDaily().setSignOutTime(
				algorithmMapper.getMaxSignOut(algorithmParam.getCompanyId(),
						algorithmParam.getEmployeeId(), algorithmParam.getSignInLimitLine(),
						algorithmParam.getSignOutLimitLine()));
	}
	/**
	 * 有排班情况下，
	 * 根据centerLine区分签到签退的中间线，
	 * attBeginLine应在岗的最早时间，attEndLine应在岗的最晚时间，
	 * signInLimitLine上班打卡开始时间，signOutLimitLine下班班打卡结束时间
	 * 获得签到签退时间
	 * @param algorithmParam
	 * @param result
	 */
	public void getHasPlanSign(AlgorithmParam algorithmParam, AlgorithmResult result){
		algorithmParam.setSignInLimitLine(
				TimeUtil.getLongAfter(
						algorithmParam.getClassesEmployee().getOnDutySchedulingDate(), 
						-1*Integer.parseInt(algorithmParam.getClassesEmployee().getOnPunchCardRule()), 
						Calendar.MINUTE));//上班打卡开始时间
		algorithmParam.setSignOutLimitLine(
				TimeUtil.getLongAfter(
						algorithmParam.getClassesEmployee().getOffDutySchedulingDate(), 
						Integer.parseInt(algorithmParam.getClassesEmployee().getOffPunchCardRule()), 
						Calendar.MINUTE));//下班打卡结束时间
		if(!TimeUtil.compareTime(algorithmParam.getAttEndLine(), algorithmParam.getAttBeginLine())){
			//申请覆盖了上班时间，则不计算异常；签到签退时间按正常抓取
			String centerLine = TimeUtil.getCenter(
					algorithmParam.getClassesEmployee().getOnDutySchedulingDate(), 
					algorithmParam.getClassesEmployee().getOffDutySchedulingDate());
			algorithmParam.setCenterLine(centerLine);
			result.getReportDaily().setSignInTime(
					algorithmMapper.getMinSignIn(algorithmParam.getCompanyId(),
							algorithmParam.getEmployeeId(), algorithmParam.getClassesEmployee().getOnDutySchedulingDate(),
							centerLine));
			result.getReportDaily().setSignOutTime(
					algorithmMapper.getMaxSignOut(algorithmParam.getCompanyId(),
							algorithmParam.getEmployeeId(), centerLine,
							algorithmParam.getClassesEmployee().getOffDutySchedulingDate()));
		}else{
			String centerLine = TimeUtil.getCenter(algorithmParam.getAttBeginLine(), algorithmParam.getAttEndLine());
			algorithmParam.setCenterLine(centerLine);
			result.getReportDaily().setSignInTime(
					algorithmMapper.getMinSignIn(algorithmParam.getCompanyId(),
							algorithmParam.getEmployeeId(), algorithmParam.getSignInLimitLine(),
							centerLine));
			result.getReportDaily().setSignOutTime(
					algorithmMapper.getMaxSignOut(algorithmParam.getCompanyId(),
							algorithmParam.getEmployeeId(), centerLine,
							algorithmParam.getSignOutLimitLine()));
			//根据centerLine区分签到签退的中间线， attBeginLine应在岗的最早时间，attEndLine应在岗的最晚时间， 签到签退时间，检查异常
			this.checkException(algorithmParam, result);
		}
		
	}
	
	@Override
	public boolean preCondition(String companyId, String employeeId, String countDate) {
		int noCheckAtt = algorithmMapper.getIsCheck(companyId, employeeId);
		//查询是否为不考勤人员
		if(noCheckAtt>0){//不考勤
			return false;
		}
		return true;
	}

	@Override
	public AlgorithmParam generateAlgorithmParam(String companyId, String employeeId, String countDate) {
		AlgorithmParam algorithmParam = new AlgorithmParam();
		algorithmParam.setEmployeeId(employeeId);
		Employee employee = employeeDao.selectByEmployee(employeeId, companyId);
		algorithmParam.setEmployee(employee );
		algorithmParam.setCompanyId(companyId);
		algorithmParam.setCountDate(countDate);
		/*if("4D758DEFD8B04A4F96FE289E48E09EB4".equals(companyId) && "4F1F7958F7AA4F539335859E84820869".equals(employeeId)){
			logger.info("查询");
		}*/
		//查询当天的排班
		algorithmParam.setClassesEmployee(
				algorithmMapper.getPlanByDate(companyId, employee.getEmployeeId(), countDate));
		//查询前一天的排班
		algorithmParam.setClassesEmployeeLastDay(
				algorithmMapper.getPlanByDate(companyId, 
						employee.getEmployeeId(), TimeUtil.getLastDayDate(countDate)));
		//查询次日的排班
		algorithmParam.setClassesEmployeeNextDay(
				algorithmMapper.getPlanByDate(companyId, 
						employee.getEmployeeId(), TimeUtil.getNextDayDate(countDate)));
		return algorithmParam;
	}


	@Override
	public void postProcess(AlgorithmParam algorithmParam, AlgorithmResult algorithmResult) {
		//查询日报数据是否存在
		ReportDaily oldReportDaily = reportDailyMapper.selectByDate(algorithmParam.getCompanyId(), 
				algorithmParam.getEmployeeId(), algorithmResult.getReportDaily().getAttDate());
		if(oldReportDaily!=null && "1".equals(oldReportDaily.getIsProcess())){
			return;
		}
		//proReportData必须放在proException之前，原因是日报计算结果处理方法中也会产生早退异常，并且也会影响旷工统计
		this.proReportData(algorithmParam, algorithmResult);
		this.proException(algorithmParam, algorithmResult);
		//加班转调休生成处理，将对应日期生成的调休时长改动
		String vacationId = algorithmMapper.getVacationId(algorithmParam.getCompanyId(), 
				algorithmParam.getEmployeeId(), algorithmResult.getReportDaily().getAttDate());
		if(oldReportDaily!=null && StringUtils.isNotEmpty(oldReportDaily.getChangeTime()) 
				&& 0!=Double.parseDouble(oldReportDaily.getChangeTime())){//存在，则扣除调休时长
			//调整状态(0:收入,1:支出)
			vacationService.AdjustRestAdjustment(
					vacationId, "1", 
					Double.parseDouble(algorithmResult.getReportDaily().getNormalOverWork())/60.0+"", 
					"日报重新生成调休", "0", algorithmResult.getReportDaily().getAttDate().split("-")[0]);//先扣除
		}
		//删除旧的日报
		reportDailyMapper.deleteByDate(algorithmParam.getCompanyId(), 
				algorithmParam.getEmployeeId(), algorithmResult.getReportDaily().getAttDate());
		//报表处理（单位处理：由分转为半小时）
		algorithmResult.getReportDaily().setReportId(FormatUtil.createUuid());;
		reportDailyMapper.insertSelective(algorithmResult.getReportDaily());
		
		//调整状态(0:收入,1:支出)
		if(StringUtils.isNotEmpty(algorithmResult.getReportDaily().getNormalOverWork())
				&& 0!=Double.parseDouble(algorithmResult.getReportDaily().getNormalOverWork())){//调休时长大于0
			//调整状态(0:收入,1:支出)
			vacationService.AdjustRestAdjustment(
					vacationId, "0", 
					Double.parseDouble(algorithmResult.getReportDaily().getNormalOverWork())/60.0+"", 
					"日报计算生成调休", "0", algorithmResult.getReportDaily().getAttDate().split("-")[0]);
		}
		
	}
	/**
	 * 考勤异常数据处理
	 * @param algorithmParam
	 * @param algorithmResult
	 */
	private void proException(AlgorithmParam algorithmParam, AlgorithmResult algorithmResult) {
		//删除已生成的异常
		reportExceptMapper.deleteExceptByDate(algorithmParam.getCompanyId(), 
				algorithmParam.getEmployeeId(), algorithmResult.getReportDaily().getAttDate());
		
		//异常处理
		if(algorithmResult.getReportExcept().size()>0){
			String exceptionMark = "";
			for(ReportExcept reportExcept:algorithmResult.getReportExcept()){
				reportExcept.setExceptId(FormatUtil.createUuid());
				reportExceptMapper.insertSelective(reportExcept);
				
				exceptionMark=exceptionMark+reportExcept.getExceptType()+",";
			}
			
			algorithmResult.getReportDaily().setHasException("1");
			//异常类型： 1迟到  ;2早退；3未签到; 4未签退 ； 5	旷工; 6 无效； 7	过早打卡; 8过晚打卡；9重复打卡;10无排班打卡；11其它
			//报表中异常类型(1.迟到 2.早退 3.未到 4.未退 5.迟到且早退 6.迟到且未退 7.未到且早退 8.未到且未退(旷工))
			switch (exceptionMark) {
			case "1,":
				exceptionMark="1";
				break;
			case "2,":
				exceptionMark="2";	
				break;
			case "3,":
				exceptionMark="3";
				break;
			case "4,":
				exceptionMark="4";
				break;
			case "1,2,":
				exceptionMark="5";
				break;
			case "1,4,":
				exceptionMark="6";	
				break;
			case "3,2,":
				exceptionMark="7";
				break;
			case "5,":
				//此处要与日报中旷工数据一致，若日报体现没有旷工，则不记录旷工异常
				if(algorithmResult.getReportDaily().getAbsent()!=null 
				&& Integer.parseInt(algorithmResult.getReportDaily().getAbsent())>0){
					exceptionMark="8";
				}
				break;
			default:
				break;
			}
			algorithmResult.getReportDaily().setExceptionMark(exceptionMark);
		}
	}
	/**
	 * 处理前文计算的日报数据：秒-》分钟
	 * @param algorithmResult
	 */
	public void proReportData(AlgorithmParam algorithmParam, AlgorithmResult algorithmResult) {
		//应出时长
		algorithmResult.getReportDaily().setWorkTime(
				TimeUtil.parseSecondToMinuteHalfHourUnit(algorithmResult.getReportDaily().getWorkTime()));
		//实出时长
		algorithmResult.getReportDaily().setRealWorkTime(
				TimeUtil.parseSecondToMinuteHalfHourFloorUnit(algorithmResult.getReportDaily().getRealWorkTime()));
		
		//事假
		String leaveAbsence = TimeUtil.parseSecondToMinuteHalfHourUnit(
				algorithmResult.getReportDaily().getLeaveAbsence());
		algorithmResult.getReportDaily().setLeaveAbsence(leaveAbsence);
		//年假
		String leaveAnnual = TimeUtil.parseSecondToMinuteHalfDayUnit(
				algorithmResult.getReportDaily().getLeaveAnnual());
		algorithmResult.getReportDaily().setLeaveAnnual(leaveAnnual);
		//调休假
		String leaveDaysOff = TimeUtil.parseSecondToMinuteHalfHourUnit(
				algorithmResult.getReportDaily().getLeaveDaysOff());
		algorithmResult.getReportDaily().setLeaveDaysOff(leaveDaysOff);
		//婚假
		String leaveMarriage = TimeUtil.parseSecondToMinuteHalfHourUnit(
				algorithmResult.getReportDaily().getLeaveMarriage());
		algorithmResult.getReportDaily().setLeaveMarriage(leaveMarriage);
		//产假
		String leaveMaternity = TimeUtil.parseSecondToMinuteHalfHourUnit(
				algorithmResult.getReportDaily().getLeaveMaternity());
		algorithmResult.getReportDaily().setLeaveMaternity(leaveMaternity);
		//丧假
		String leaveFuneral= TimeUtil.parseSecondToMinuteHalfHourUnit(
				algorithmResult.getReportDaily().getLeaveFuneral());
		algorithmResult.getReportDaily().setLeaveFuneral(leaveFuneral);
		//病假
		String leaveSick = TimeUtil.parseSecondToMinuteHalfHourUnit(
				algorithmResult.getReportDaily().getLeaveSick());
		algorithmResult.getReportDaily().setLeaveSick(leaveSick);
		//出差
		algorithmResult.getReportDaily().setEvectionTimeWork(
				TimeUtil.parseSecondToMinuteHalfHourUnit(algorithmResult.getReportDaily().getEvectionTimeWork()));
		//外出
		algorithmResult.getReportDaily().setOutTimeWork(
				TimeUtil.parseSecondToMinuteHalfHourUnit(algorithmResult.getReportDaily().getOutTimeWork()));
		//加班
		algorithmResult.getReportDaily().setNormalOverWork(
				TimeUtil.parseSecondToMinuteHalfHourFloorUnit(algorithmResult.getReportDaily().getNormalOverWork()));
		//新增调休
		algorithmResult.getReportDaily().setChangeTime(algorithmResult.getReportDaily().getNormalOverWork());
		//请假总时长
		int leaveTime = Integer.parseInt(leaveAbsence)+Integer.parseInt(leaveAnnual)+
				Integer.parseInt(leaveDaysOff)+Integer.parseInt(leaveMarriage)+
				Integer.parseInt(leaveMaternity)+Integer.parseInt(leaveFuneral)+
				Integer.parseInt(leaveSick);
		//旷工时长=应出-请假时长
		if(algorithmResult.getReportDaily().getAbsent()!=null && 
				Integer.parseInt(algorithmResult.getReportDaily().getAbsent())>0){
			int absentTime = Integer.parseInt(algorithmResult.getReportDaily().getWorkTime())-leaveTime;
			algorithmResult.getReportDaily().setAbsentTime(absentTime+"");
			if(absentTime==0){//如果旷工时长为0，则消除旷工次数
				algorithmResult.getReportDaily().setAbsent("0");
			}
		}
		//计算实际有效出勤时长
		if(algorithmParam.isHasPlan()){//有排班
			if(StringUtils.isNotEmpty(algorithmResult.getReportDaily().getSignInTime()) 
					&& StringUtils.isNotEmpty(algorithmResult.getReportDaily().getSignOutTime())){
				
				//签到签退时间区间与班次的交集，扣除休息时间
				long containAtt = TimeUtil.containTimeLength(
							algorithmResult.getReportDaily().getSignInTime(), 
							algorithmResult.getReportDaily().getSignOutTime(), 
							algorithmParam.getClassesEmployee().getOnDutySchedulingDate(), 
							algorithmParam.getClassesEmployee().getRestStartTime())+
						TimeUtil.containTimeLength(
								algorithmResult.getReportDaily().getSignInTime(), 
								algorithmResult.getReportDaily().getSignOutTime(), 
								algorithmParam.getClassesEmployee().getRestEndTime(), 
								algorithmParam.getClassesEmployee().getOffDutySchedulingDate());
				
				//应出勤扣除请假
				long workTimeLeaveSub = (Long.parseLong(algorithmResult.getReportDaily().getWorkTime())-
						leaveTime)*60;
				workTimeLeaveSub = workTimeLeaveSub<0?0:workTimeLeaveSub;//不可小于0
				if(containAtt>workTimeLeaveSub){//有效出勤时长，最大等于(应出时长-请假时长)
					containAtt = workTimeLeaveSub;
				}
				algorithmResult.getReportDaily().setRealAttendanceTime(
						TimeUtil.parseSecondToMinuteHalfHourFloorUnit(containAtt+""));
				//判断是否存在迟到、早退，都不存在，则检查出勤是否足够，不够，则产生早退异常。已经有一个早退，则不需要再产生一次早退
				if((StringUtils.isEmpty(algorithmResult.getReportDaily().getLate())
							||Integer.parseInt(algorithmResult.getReportDaily().getLate())==0)
						&& (StringUtils.isEmpty(algorithmResult.getReportDaily().getEarly())
							|| Integer.parseInt(algorithmResult.getReportDaily().getEarly())==0
						)){
					if(containAtt<workTimeLeaveSub){
						ReportExcept reportExcept = new ReportExcept();
						reportExcept.setEmployeeId(algorithmParam.getEmployeeId());
						reportExcept.setCompanyId(algorithmParam.getCompanyId());
						reportExcept.setExceptDate(algorithmParam.getCountDate());
						reportExcept.setExceptType("2");
						algorithmResult.getReportExcept().add(reportExcept);
						algorithmResult.getReportDaily().setEarly("1");//早退次数
					}
				}
				
			}
		}else{//无排班时，有效出勤时长=签到-签退，得到的时长
			algorithmResult.getReportDaily().setRealAttendanceTime(algorithmResult.getReportDaily().getRealWorkTime());
		}
		
	}
	@Override
	public void calculateMonth(String countDate) {
		List<Company> companyIdList = algorithmMapper.getAllCompanyList();
		for(Company company : companyIdList){
			List<Employee> empIdList = algorithmMapper.getEmployeeOnJobList(
					company.getCompanyId(), countDate);
			for(Employee emp:empIdList){
				try {
					if(this.preCondition(company.getCompanyId(), emp.getEmployeeId(), countDate)){
						this.calculate(company.getCompanyId(), emp.getEmployeeId(), countDate);
						String date = countDate;
						String endDate = TimeUtil.getLongAfterDate(
								TimeUtil.getCurrentLastDate(countDate), 1, Calendar.DATE);
						while(TimeUtil.compareTime(endDate+" 00:00:00", date+" 00:00:00")){
							this.calculateMonth(company.getCompanyId(), emp.getEmployeeId(), date);
							date = TimeUtil.getLongAfterDate(date+" 00:00:00", 1, Calendar.DATE);
						}
					}
					
				} catch (Exception e) {
					logger.info("公司ID："+company.getCompanyId()+", 员工ID："+emp.getEmployeeId()+", "+countDate+ "后应出时长计算异常");
					logger.info(FormatUtil.getExceptionInfo(e));
				}
			}
		}
		
		
	}
	/**
	 * 用于月报统计时的应出计算
	 * @param companyId
	 * @param employeeId
	 * @param countDate
	 */
	public void calculateMonth(String companyId, String employeeId, String countDate) {
		AlgorithmParam algorithmParam = new AlgorithmParam();
		algorithmParam.setEmployeeId(employeeId);
		Employee employee = employeeDao.selectByEmployee(employeeId, companyId);
		algorithmParam.setEmployee(employee );
		algorithmParam.setCompanyId(companyId);
		algorithmParam.setCountDate(countDate);
		
		AlgorithmResult result = new AlgorithmResult();
		result.getReportDaily().setAttDate(countDate);
		result.getReportDaily().setEmployeeId(employeeId);
		result.getReportDaily().setCompanyId(companyId);
		result.getReportDaily().setDeptId(algorithmParam.getEmployee().getDepartmentId());
		
		//查询当天的排班
		algorithmParam.setClassesEmployee(
				algorithmMapper.getPlanByDate(companyId, employee.getEmployeeId(), countDate));
		//有排班
		if(algorithmParam.getClassesEmployee()!=null 
				&& StringUtils.isNotEmpty(algorithmParam.getClassesEmployee().getOnDutySchedulingDate())){
			this.countWorkTime(algorithmParam, result);
			//应出时长
			result.getReportDaily().setWorkTime(
					TimeUtil.parseSecondToMinuteHalfHourUnit(result.getReportDaily().getWorkTime()));
			//查询日报数据是否存在
			ReportDaily oldReportDaily = reportDailyMapper.selectByDate(algorithmParam.getCompanyId(), 
					algorithmParam.getEmployeeId(), result.getReportDaily().getAttDate());
			if(oldReportDaily!=null && StringUtils.isNotEmpty(oldReportDaily.getAttDate())){//存在，则修改应出时长
				reportDailyMapper.updateWorkTime(result.getReportDaily());
			}else{//不存在则更新
				result.getReportDaily().setReportId(FormatUtil.createUuid());
				reportDailyMapper.insertSelective(result.getReportDaily());
			}
			
		}
		
	
		
	}
	
	
}