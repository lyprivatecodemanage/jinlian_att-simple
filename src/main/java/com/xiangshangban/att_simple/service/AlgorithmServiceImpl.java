package com.xiangshangban.att_simple.service;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiangshangban.att_simple.bean.AlgorithmParam;
import com.xiangshangban.att_simple.bean.AlgorithmReport;
import com.xiangshangban.att_simple.bean.AlgorithmResult;
import com.xiangshangban.att_simple.bean.AlgorithmSign;
import com.xiangshangban.att_simple.bean.Application;
import com.xiangshangban.att_simple.bean.ApplicationOutgoing;
import com.xiangshangban.att_simple.bean.ClassesEmployee;
import com.xiangshangban.att_simple.bean.ReportDaily;
import com.xiangshangban.att_simple.bean.ReportExcept;
import com.xiangshangban.att_simple.dao.AlgorithmMapper;
import com.xiangshangban.att_simple.dao.ClassesEmployeeMapper;
import com.xiangshangban.att_simple.dao.ReportDailyMapper;
import com.xiangshangban.att_simple.dao.ReportExceptMapper;
import com.xiangshangban.att_simple.utils.TimeUtil;
import com.xiangshangban.organization.bean.Employee;

@Service("AlgorithmService")
public class AlgorithmServiceImpl implements AlgorithmService {
	@Autowired
	ClassesEmployeeMapper ClassesEmployeeMapper;
	@Autowired
	ReportDailyMapper reportDailyMapper;
	@Autowired
	ReportExceptMapper reportExceptMapper;
	@Autowired
	AlgorithmMapper algorithmMapper;
	/**
	 * 核心计算开始
	 */
	@Override
	public void calculate(String companyId, Employee employee, String countDate) {
		if(this.preCondition(companyId, employee.getEmployeeId(), countDate)){
			AlgorithmParam algorithmParam = this.generateAlgorithmParam(companyId, employee, countDate);//各种查询
			AlgorithmResult result = new AlgorithmResult();
			result.getReportDaily().setAttDate(countDate);
			this.calculateStandard(algorithmParam, result);//计算
			this.postProcess(result);//处理计算结果数据
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
						overSignValidBegin = algorithmParam.getClassesEmployeeLastDay().getOnDutySchedulingDate();
					}else{
						
					}
				}else{//右外申请
					
				}
			}else{//无排班
				
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
				&& classesLast.getClassesType()!=null 
				&& StringUtils.isNotEmpty(classesLast.getClassesType().getOffPunchCardTime())){
			String lastDateCardEnd = TimeUtil.getLongAfter(
					classesLast.getOffDutySchedulingDate(), 
					Integer.parseInt(classesLast.getClassesType().getOffPunchCardTime()), 
					Calendar.MINUTE);//前一天班次打卡结束时间
			if(TimeUtil.compareTime(lastDateCardEnd, algorithmParam.getCountDate()+" 00:00:00")){//前一天班次打卡结束时间在当天
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
				&& classesNext.getClassesType()!=null 
				&& StringUtils.isNotEmpty(classesNext.getClassesType().getOnPunchCardTime())){
			String lastDateCardEnd = TimeUtil.getLongAfter(
					classesNext.getOffDutySchedulingDate(), 
					-1*Integer.parseInt(classesNext.getClassesType().getOffPunchCardTime()), 
					Calendar.MINUTE);//次日班次打卡开始时间
			if(TimeUtil.compareTime(lastDateCardEnd, algorithmParam.getCountDate()+" 00:00:00")){//次日班次打卡开始时间在当天
				algorithmParam.setSignOutLimitLine(lastDateCardEnd);
			}else{
				algorithmParam.setSignOutLimitLine(algorithmParam.getCountDate()+" 00:00:00");
			}
		}else{//次日无排班
			algorithmParam.setSignOutLimitLine(algorithmParam.getCountDate()+" 00:00:00");
		}
		this.getNoPlanSign(algorithmParam, result);
		return result;
	}
	/**
	 * 有排班的计算方式，区分有请假和无请假方式
	 * @param algorithmParam
	 * @return
	 */
	public AlgorithmResult havePlan(AlgorithmParam algorithmParam, AlgorithmResult result) {
		//请假、外出、出差，将影响应在岗时间
		this.countLeave(algorithmParam, result);
		this.countOut(algorithmParam, result);
		this.countBusiness(algorithmParam, result);
		//根据centerLine区分签到签退的中间线， attBeginLine应在岗的最早时间，attEndLine应在岗的最晚时间， 获得签到签退时间
		this.getHasPlanSign(algorithmParam, result);
		return result;
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
			int appAttRelation = TimeUtil.timeRelation(application.getStartTime(), 
					application.getEndTime(), restBeginTime, restEndTime);
			//申请时间与休息时间的关系
			int appRestRelation = TimeUtil.timeRelation(application.getStartTime(), 
					application.getEndTime(), restBeginTime, restEndTime);
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
				leaveRestTime= TimeUtil.getTimeLength(restEndTime, restBeginTime);
			}
			currentApply = TimeUtil.getTimeLength(currentApplyEnd, currentApplyBegin)-leaveRestTime;
			timeLength=timeLength+currentApply;
			//申请类型(申请类型(请假:'1';加班:'2';出差'3';外出:'4';'补卡5'))
			//请假类型(1:事假;2:年假;3:调休假;4:婚假;5:产假;6:丧假)
			this.setApplyTime(result, application, currentApply);
		}
		
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
				AlgorithmReport.setChangeTime(currentApply+"", result.getReportDaily());
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
				AlgorithmReport.setLeaveFuneral(currentApply+"", result.getReportDaily());
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
			reportExcept.setExceptType("6");
			result.getReportExcept().add(reportExcept);
		}else{
			String lateLine = TimeUtil.getLongAfter(
					algorithmParam.getAttBeginLine(), 
					Integer.parseInt(algorithmParam.getClassesEmployee().getClassesType().getSignInRule()), 
					Calendar.MINUTE);//迟到线
			String earlyLine = TimeUtil.getLongAfter(
					algorithmParam.getAttBeginLine(), 
					-1*Integer.parseInt(algorithmParam.getClassesEmployee().getClassesType().getSignOutRule()), 
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
			}
			if(StringUtils.isEmpty(signInTime)){//未签到
				ReportExcept reportExcept = new ReportExcept();
				reportExcept.setEmployeeId(employeeId);
				reportExcept.setCompanyId(companyId);
				reportExcept.setExceptDate(countDate);
				reportExcept.setExceptType("4");
				result.getReportExcept().add(reportExcept);
			}else if(TimeUtil.compareTime(earlyLine, signInTime)){//早退
				ReportExcept reportExcept = new ReportExcept();
				reportExcept.setEmployeeId(employeeId);
				reportExcept.setCompanyId(companyId);
				reportExcept.setExceptDate(countDate);
				reportExcept.setExceptType("2");
				result.getReportExcept().add(reportExcept);
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
	
	/**
	 * 更新最新的上班时间（左请假，内请假执行）
	 * @param checkBeginTime 实际上班时间
	 * @param leaveBeginTime 请假开始时间
	 * @param leaveEndTime 结束时间
	 * @param restBeginTime 休息开始时间
	 * @param restEndTime 休息结束时间
	 * @return checkBeginTime 上班时间
	 */
	public AlgorithmSign getNewCheckBeginTime(AlgorithmSign algorithmSign, String leaveBeginTime,
			String leaveEndTime, String restBeginTime, String restEndTime) {
		String checkBeginTime = algorithmSign.getCheckBeginTime();
		//检查是否 后延上班时间
		if(!TimeUtil.compareTime(leaveBeginTime, checkBeginTime) &&
				!TimeUtil.compareTime(checkBeginTime, leaveBeginTime)){
			checkBeginTime=leaveEndTime;
		}
		//检查是否包含休息时间，若包含，则上班时间后延到休息时间结束
		if(TimeUtil.compareTime(restEndTime, checkBeginTime) &&
				TimeUtil.compareTime(checkBeginTime, restBeginTime)){
			checkBeginTime = restEndTime;
		}
		algorithmSign.setCheckBeginTime(checkBeginTime);
		return algorithmSign;
	}
	/**
	 *  更新最新的下班时间（右请假，内请假执行）
	 * @param checkEndTime 实际下班时间
	 * @return checkEndTime 下班时间
	 * @param leaveBeginTime 请假开始时间
	 * @param leaveEndTime 请假结束时间
	 * @param restBeginTime 休息开始时间
	 * @param restEndTime 休息结束时间
	 * @return checkEndTime 下班时间
	 */
	public AlgorithmSign getNewCheckEndTime(AlgorithmSign algorithmSign, String leaveBeginTime,
			String leaveEndTime, String restBeginTime, String restEndTime) {
		String checkEndTime = algorithmSign.getCheckEndTime();
		//检查是否 提前下班时间
		if(!TimeUtil.compareTime(checkEndTime, leaveEndTime) &&
				!TimeUtil.compareTime(leaveEndTime, checkEndTime)){
			checkEndTime=leaveEndTime;
		}
		//检查是否包含休息时间，若包含，则上班时间后提前休息时间开始
		if(TimeUtil.compareTime(leaveEndTime, restBeginTime) &&
				TimeUtil.compareTime(restEndTime, checkEndTime)){
			checkEndTime = restEndTime;
		}
		algorithmSign.setCheckEndTime(checkEndTime);
		return algorithmSign;
	}
	
	@Override
	public boolean preCondition(String companyId, String employeeId, String countDate) {
		boolean noCheckAtt = true;//***此处有查询
		//查询是否为不考勤人员
		if(noCheckAtt){//不考勤
			return false;
		}
		return true;
	}

	@Override
	public AlgorithmParam generateAlgorithmParam(String companyId, Employee employee, String countDate) {
		AlgorithmParam algorithmParam = new AlgorithmParam();
		algorithmParam.setEmployeeId(employee.getEmployeeId());
		algorithmParam.setEmployee(employee);
		algorithmParam.setCompanyId(companyId);
		algorithmParam.setCountDate(countDate);
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
	public void postProcess(AlgorithmResult algorithmResult) {
		//报表处理（单位处理：由分转为半小时）
		reportDailyMapper.insertSelective(algorithmResult.getReportDaily());
		//异常处理
		for(ReportExcept reportExcept:algorithmResult.getReportExcept()){
			reportExceptMapper.insertSelective(reportExcept);
		}
		//加班转调休生成处理，将对应日期生成的调休时长改动
		
	}
	/**
     * 判断某一天是什么日子
     * @return 0：节假日，1：休息日   2：工作日
     */
    /*public AttendenceSettingCalendar whatDay(AlgorithmParam algorithmParam){
    	AttendenceSettingCalendar calendar = attendenceSettingCalendarMapper.getCalendarBySettingIdAndDate(
    			algorithmParam.getAttendenceSetting().getAttentenceSettingId(),
    			algorithmParam.getCountDate());
    	algorithmParam.setCalendar(calendar);
		return calendar;
	}*/
	
}
