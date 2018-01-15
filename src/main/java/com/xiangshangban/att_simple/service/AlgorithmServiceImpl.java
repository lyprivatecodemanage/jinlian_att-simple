package com.xiangshangban.att_simple.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiangshangban.att_simple.bean.AlgorithmParam;
import com.xiangshangban.att_simple.bean.AlgorithmReport;
import com.xiangshangban.att_simple.bean.AlgorithmResult;
import com.xiangshangban.att_simple.bean.AlgorithmSign;
import com.xiangshangban.att_simple.bean.ApplicationOutgoing;
import com.xiangshangban.att_simple.bean.ReportDaily;
import com.xiangshangban.att_simple.bean.ReportExcept;
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
	 * 标准工时计算方式：区分有排班、无排班
	 * @param algorithmParam
	 * @return
	 */
	public AlgorithmResult calculateStandard(AlgorithmParam algorithmParam, AlgorithmResult result) {
		//有排班
		if(true){
			return this.havePlan(algorithmParam, result);
		}else{//无排班
			return this.noPlan(algorithmParam, result);
		}
	}
	/**
	 * 标准工时：有排班的计算方式，区分一个时段和两个时段
	 * @param algorithmParam
	 * @return
	 */
	public AlgorithmResult havePlan(AlgorithmParam algorithmParam, AlgorithmResult result) {
		
		return result;
	}
	/**
	 *  标准工时：有排班，排班仅有一个时段
	 * @param algorithmParam
	 * @param result
	 */
	public void oneTimePlan(AlgorithmParam algorithmParam, AlgorithmResult result) {
		/*algorithmParam.setPlanTimeNum("1");
		//预计算应出
		String beginTime1 = algorithmParam.getAttendancePlan().getBeginTime1();//班次时段1的开始时间
		String endTime1 = algorithmParam.getAttendancePlan().getEndTime1();//班次时段1的结束时间
		//排班描述
		result.getReportDaily().setPaibanRemark("时段："+beginTime1+"~"+endTime1);
		result.getReportDaily().setBeginTime1(beginTime1);
		result.getReportDaily().setEndTime1(endTime1);
		result.getReportDaily().setBeginTime2("");
		result.getReportDaily().setEndTime2("");
		
		//计算打卡有效时段
		String checkSignInMax = algorithmParam.getAttendancePlan().getCheckSignInMax();
		String checkSignOutMax = algorithmParam.getAttendancePlan().getCheckSignOutMax();
		String checkSignInBegin = TimeUtil.getLongAfter(beginTime1, -1*Integer.parseInt(checkSignInMax), Calendar.MINUTE);
		String checkSignOutEnd = TimeUtil.getLongAfter(endTime1, -1*Integer.parseInt(checkSignOutMax), Calendar.MINUTE);
		algorithmParam.getAttendancePlan().setCheckSignInBeginTime(
				TimeUtil.getLongAfter(beginTime1, 
						-1*Integer.parseInt(checkSignInMax), Calendar.MINUTE));
		algorithmParam.getAttendancePlan().setCheckSignOutEndTime(
				TimeUtil.getLongAfter(endTime1, 
						-1*Integer.parseInt(checkSignOutMax), Calendar.MINUTE));
		String restBeginTime1 = algorithmParam.getAttendancePlan().getRestBeginTime1();
		String restEndTime1 = algorithmParam.getAttendancePlan().getRestEndTime1();
		this.planTimeOne(algorithmParam, result, beginTime1, endTime1, 
				checkSignInBegin, checkSignOutEnd, restBeginTime1, restEndTime1, "1");
		//外出、派工、出差计算处理
		this.calculateOut(algorithmParam, result, beginTime1, endTime1, "", "", "1");*/
	}
	
	/**
	 * 出差、外出、派工计算处理
	 * @param algorithmParam
	 * @param result
	 * @param beginTime1
	 * @param endTime1
	 * @param beginTime2
	 * @param endTime2
	 * @param timeNum 时段数 0：无排班，1：排班中有一个时段，2：排班中有两个时段
	 */
	public void calculateOut(AlgorithmParam algorithmParam, AlgorithmResult result, String beginTime1, String endTime1,
			String beginTime2, String endTime2, String timeNum) {
		//出差
		/*for(ErrandApplication app:algorithmParam.getEvectionList()){
			this.getOutTime(algorithmParam, result, beginTime1, endTime1, beginTime2, endTime2, timeNum, app, "Evection");
		}
		//外出
		for(ErrandApplication app:algorithmParam.getEvectionList()){
			this.getOutTime(algorithmParam, result, beginTime1, endTime1, beginTime2, endTime2, timeNum, app, "Out");
		}*/
		
		
	}
	
	/**
	 * 计算出差，外出， 派工时长
	 * @param algorithmParam
	 * @param result
	 * @param beginTime1
	 * @param endTime1
	 * @param beginTime2
	 * @param endTime2
	 * @param timeNum
	 * @param errandApplication
	 * @param outType 外出类型，Evection：出差，Out:外出， Dispatch:派工
	 */
	public void getOutTime(AlgorithmParam algorithmParam, AlgorithmResult result, String beginTime1, String endTime1, String beginTime2,
			String endTime2, String timeNum, ApplicationOutgoing errandApplication, String outType) {
		
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
	/**
	 * 无排班的计算方式
	 * @param algorithmParam
	 * @return
	 */
	public AlgorithmResult noPlan(AlgorithmParam algorithmParam, AlgorithmResult result) {
		
		return result;
	}
	/**
	 * 综合工时计算方式
	 * @param algorithmParam
	 * @return
	 */
	public AlgorithmResult calculateSynthesize(AlgorithmParam algorithmParam, AlgorithmResult result) {
		
		return result;
	}
	/**
	 * 不定时工时计算方式
	 * @param algorithmParam
	 * @param result
	 * @return
	 */
	public AlgorithmResult calculateFlexible(AlgorithmParam algorithmParam, AlgorithmResult result) {
		
		return result;
	}
	@Override
	public boolean preCondition(String companyId, String employeeId, String countDate) {
		// 判断是否关账  ***此处有查询
		
		return true;
	}

	@Override
	public AlgorithmParam generateAlgorithmParam(String companyId, Employee employee, String countDate) {
		AlgorithmParam algorithmParam = new AlgorithmParam();//***此处有查询
		algorithmParam.setEmployeeId(employee.getEmployeeId());
		algorithmParam.setEmployee(employee);
		algorithmParam.setCompanyId(companyId);
		algorithmParam.setCountDate(countDate);
		//查询人员，部门，岗位
		//Employee employee = new Employee();
		
		
		
		//查询加班申请
		
		//查询请假申请
		
		//查询外出、派工、出差
		
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
