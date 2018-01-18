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
import com.xiangshangban.att_simple.bean.Employee;
import com.xiangshangban.att_simple.bean.ReportDaily;
import com.xiangshangban.att_simple.bean.ReportExcept;
import com.xiangshangban.att_simple.dao.AlgorithmMapper;
import com.xiangshangban.att_simple.dao.ClassesEmployeeMapper;
import com.xiangshangban.att_simple.dao.ReportDailyMapper;
import com.xiangshangban.att_simple.dao.ReportExceptMapper;
import com.xiangshangban.att_simple.utils.TimeUtil;

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
			this.calculateStandard(algorithmParam, result);
			this.postProcess(result);
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
				&& StringUtils.isNotEmpty(algorithmParam.getClassesEmployee().getClassesId())){
			//休息时间
			algorithmParam.getClassesEmployee().getClassesType().getRestTime();
			return this.havePlan(algorithmParam, result);
		}else{//无排班
			return this.noPlan(algorithmParam, result);
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
		this.getSign(algorithmParam, result);
		//根据centerLine区分签到签退的中间线， attBeginLine应在岗的最早时间，attEndLine应在岗的最晚时间， 签到签退时间，检查异常
		this.checkException(algorithmParam, result);
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
		long timeLength = this.countApplyByClasses(algorithmParam, result, applicationList);
		
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
		long timeLength = this.countApplyByClasses(algorithmParam, result, applicationList);
		//半小时为单位，处理
		
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
		long timeLength = this.countApplyByClasses(algorithmParam, result, applicationList);
		
	}
	/**
	 * 计算请假、外出、出差；改变应在岗的最早时间，应在岗的最晚时间
	 * @param algorithmParam
	 * @param result
	 * @return 总时长，单位分钟
	 */
	public long countApplyByClasses(AlgorithmParam algorithmParam, AlgorithmResult result, List<Application> applicationList){
		long timeLength=0;
		String attBeginLine = algorithmParam.getAttBeginLine();//应在岗的最早时间
		String attEndLine = algorithmParam.getAttEndLine();//应在岗的最晚时间
		//String restBeginTime = 
		//请假类型(1:事假;2:年假;3:调休假;4:婚假;5:产假;6:丧假)
		for(Application application : applicationList){
			if(TimeUtil.compareTime(attBeginLine, application.getStartTime())){//左，外
				
			}else{
				
			}
			
			if(TimeUtil.compareTime(attBeginLine, application.getStartTime())){//左，外
				
			}
		}
		return 0;
	}
	/**
	 * 检查异常，并记录异常
	 * @param algorithmParam
	 * @param result
	 */
	public void checkException(AlgorithmParam algorithmParam, AlgorithmResult result) {
		//检查异常
		
		//记录异常
		
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
	public void getSign(AlgorithmParam algorithmParam, AlgorithmResult result){
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
	
	
	
	/**
	 * 设置报表请假时长
	 * @param result 计算结果
	 * @param leaveModalId 请假模板ID
	 * @param leaveBegin 请假开始时间（与班次的交集开始）
	 * @param leaveEnd 请假结束时间（与班次的交集结束）
	 * @param restBegin 休息开始时间
	 * @param restEnd 休息结束时间
	 */
	public void setLeaveTime(AlgorithmResult result, String leaveModalId, 
			String leaveBegin, String leaveEnd, String restBegin, String restEnd) {
		//扣除休息时间
		long restLeave = 0;//休息时间与请假时间的交叉时长
		int restLeaveRelation = TimeUtil.timeRelation(restBegin, restEnd, leaveBegin, leaveEnd);
		if(restLeaveRelation==2){//左交叉
			restLeave = TimeUtil.getTimeLength(restEnd, leaveBegin);
		}else if(restLeaveRelation==4){//右交叉
			restLeave = TimeUtil.getTimeLength(leaveEnd, leaveBegin);
		}else if(restLeaveRelation==5){//休息时间全部在请假时间内
			restLeave = TimeUtil.getTimeLength(restEnd, restBegin);
		}
		long leaveTime = TimeUtil.getTimeLength(leaveBegin, leaveEnd)-restLeave; //请假时长扣除休息时间
		ReportDaily reportDaily  = result.getReportDaily();
		switch (leaveModalId) {
		case "V001"://事假
			AlgorithmReport.setLeaveAbsence(leaveTime+"", reportDaily);
			break;
		case "V002"://调休假
			AlgorithmReport.setLeaveDaysOff(leaveTime+"", reportDaily);
			break;
		case "V003"://年假
			AlgorithmReport.setLeaveAnnual(leaveTime+"", reportDaily);
			break;
		case "V004"://病假
			AlgorithmReport.setLeaveSick(leaveTime+"", reportDaily);
			break;
		case "V005"://婚假
			AlgorithmReport.setLeaveMarriage(leaveTime+"", reportDaily);
			break;
		case "V006"://产假
			AlgorithmReport.setLeaveMaternity(leaveTime+"", reportDaily);
			break;
		case "V007"://陪产假
			AlgorithmReport.setLeavePaternity(leaveTime+"", reportDaily);
			break;
		case "V008"://产检假
			AlgorithmReport.setLeaveMaternityCheck(leaveTime+"", reportDaily);
			break;
		case "V009"://哺乳假（请假时间为固定的）
			AlgorithmReport.setLeaveNursing(leaveTime+"", reportDaily);
			break;
		case "V010"://丧假
			AlgorithmReport.setLeaveFuneral(leaveTime+"", reportDaily);
			break;
		case "V011"://流产假
			AlgorithmReport.setLeaveAmblosis(leaveTime+"", reportDaily);
			break;
		case "V012"://孕期工间休息假（请假时间为固定的）
			AlgorithmReport.setLeavePregnancy(leaveTime+"", reportDaily);
			break;
		case "V013"://长病假
			AlgorithmReport.setLeaveLongSick(leaveTime+"", reportDaily);
			break;
		case "V014"://其他假
			AlgorithmReport.setLeaveOther(leaveTime+"", reportDaily);
			break;
		default:
			break;
		}
		
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
		//***此处有查询，查询今天的排班
		
		return algorithmParam;
	}


	@Override
	public void postProcess(AlgorithmResult algorithmResult) {
		//报表处理
		reportDailyMapper.insertSelective(algorithmResult.getReportDaily());
		//异常处理
		for(ReportExcept reportExcept:algorithmResult.getReportExcept()){
			reportExceptMapper.insertSelective(reportExcept);
		}
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
