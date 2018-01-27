package com.xiangshangban.att_simple.bean;

import org.apache.commons.lang.StringUtils;

/**
 * 日报统计（算法中累加计算中）
 * @author 韦友弟
 *
 */
public class AlgorithmReport {
	/**
	 * 应出
	 * @param workTime
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setWorkTime(String workTime, ReportDaily reportDaily) {
		reportDaily.setWorkTime((
				(StringUtils.isNotEmpty(reportDaily.getWorkTime())?Double.parseDouble(reportDaily.getWorkTime()):0)+
				Double.parseDouble(workTime))+"");
		return reportDaily;
	}
	/**
	 * 实出
	 * @param realWorkTime
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setRealWorkTime(String realWorkTime, ReportDaily reportDaily) {
		reportDaily.setRealWorkTime((
				(StringUtils.isNotEmpty(reportDaily.getRealWorkTime())?Double.parseDouble(reportDaily.getRealWorkTime()):0)+
				Double.parseDouble(realWorkTime))+"");
		return reportDaily;
	}
	/**
	 * 考勤时间
	 * @param realAttendanceTime
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setRealAttendanceTime(String realAttendanceTime, ReportDaily reportDaily) {
		reportDaily.setRealAttendanceTime((
				(StringUtils.isNotEmpty(reportDaily.getRealAttendanceTime())?Double.parseDouble(reportDaily.getRealAttendanceTime()):0)+
				Double.parseDouble(realAttendanceTime))+"");
		return reportDaily;
	}
	/**
	 * 迟到次数
	 * @param late
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setLate(String late, ReportDaily reportDaily) {
		reportDaily.setLate((
				(StringUtils.isNotEmpty(reportDaily.getLate())?Double.parseDouble(reportDaily.getLate()):0)+
				Double.parseDouble(late))+"");
		return reportDaily;
	}
	/**
	 * 迟到时长
	 * @param lateTime
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setLateTime(String lateTime, ReportDaily reportDaily) {
		reportDaily.setLateTime((
				(StringUtils.isNotEmpty(reportDaily.getLateTime())?Double.parseDouble(reportDaily.getLateTime()):0)+
				Double.parseDouble(lateTime))+"");
		return reportDaily;
	}
	/**
	 * 早退次数
	 * @param early
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setEarly(String early, ReportDaily reportDaily) {
		reportDaily.setEarly((
				(StringUtils.isNotEmpty(reportDaily.getEarly())?Double.parseDouble(reportDaily.getEarly()):0)+
				Double.parseDouble(early))+"");
		return reportDaily;
	}
	/**
	 * 早退时长
	 * @param earlyTime
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setEarlyTime(String earlyTime, ReportDaily reportDaily) {
		reportDaily.setEarlyTime((
				(StringUtils.isNotEmpty(reportDaily.getEarlyTime())?Double.parseDouble(reportDaily.getEarlyTime()):0)+
				Double.parseDouble(earlyTime))+"");
		return reportDaily;
	}
	/**
	 * 旷工次数
	 * @param absent
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setAbsent(String absent, ReportDaily reportDaily) {
		reportDaily.setAbsent((
				(StringUtils.isNotEmpty(reportDaily.getAbsent())?Double.parseDouble(reportDaily.getAbsent()):0)+
				Double.parseDouble(absent))+"");
		return reportDaily;
	}
	/**
	 * 旷工时长
	 * @param absentTime
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setAbsentTime(String absentTime, ReportDaily reportDaily) {
		reportDaily.setAbsentTime((
				(StringUtils.isNotEmpty(reportDaily.getAbsentTime())?Double.parseDouble(reportDaily.getAbsentTime()):0)+
				Double.parseDouble(absentTime))+"");
		return reportDaily;
	}
	/**
	 * 工作日加班（平日加班）
	 * @param normalOverWork
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setNormalOverWork(String normalOverWork, ReportDaily reportDaily) {
		reportDaily.setNormalOverWork((
				(StringUtils.isNotEmpty(reportDaily.getNormalOverWork())?Double.parseDouble(reportDaily.getNormalOverWork()):0)+
				Double.parseDouble(normalOverWork))+"");
		return reportDaily;
	}
	/**
	 * 休息日加班
	 * @param weekOverWork
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setWeekOverWork(String weekOverWork, ReportDaily reportDaily) {
		reportDaily.setWeekOverWork((
				(StringUtils.isNotEmpty(reportDaily.getWeekOverWork())?Double.parseDouble(reportDaily.getWeekOverWork()):0)+
				Double.parseDouble(weekOverWork))+"");
		return reportDaily;
	}
	/**
	 * 节假日加班
	 * @param holidayOverWork
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setHolidayOverWork(String holidayOverWork, ReportDaily reportDaily) {
		reportDaily.setHolidayOverWork((
				(StringUtils.isNotEmpty(reportDaily.getHolidayOverWork())?Double.parseDouble(reportDaily.getHolidayOverWork()):0)+
				Double.parseDouble(holidayOverWork))+"");
		return reportDaily;
	}
	/**
	 * 新增调休时长
	 * @param changeTime
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setChangeTime(String changeTime, ReportDaily reportDaily) {
		reportDaily.setChangeTime((
				(StringUtils.isNotEmpty(reportDaily.getChangeTime())?Double.parseDouble(reportDaily.getChangeTime()):0)+
				Double.parseDouble(changeTime))+"");
		return reportDaily;
	}
	
	
	/**
	 * 事假
	 * @param leaveAbsence
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setLeaveAbsence(String leaveAbsence, ReportDaily reportDaily) {
		reportDaily.setLeaveAbsence((
				(StringUtils.isNotEmpty(reportDaily.getLeaveAbsence())?Double.parseDouble(reportDaily.getLeaveAbsence()):0)+
				Double.parseDouble(leaveAbsence))+"");
		return reportDaily;
	}
	/**
	 * 调休假
	 * @param leaveDaysOff
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setLeaveDaysOff(String leaveDaysOff, ReportDaily reportDaily) {
		reportDaily.setLeaveDaysOff((
				(StringUtils.isNotEmpty(reportDaily.getLeaveDaysOff())?Double.parseDouble(reportDaily.getLeaveDaysOff()):0)+
				Double.parseDouble(leaveDaysOff))+"");
		return reportDaily;
	}
	/**
	 * 年假
	 * @param leaveAnnual
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setLeaveAnnual(String leaveAnnual, ReportDaily reportDaily) {
		reportDaily.setLeaveAnnual((
				(StringUtils.isNotEmpty(reportDaily.getLeaveAnnual())?Double.parseDouble(reportDaily.getLeaveAnnual()):0)+
				Double.parseDouble(leaveAnnual))+"");
		return reportDaily;
	}
	/**
	 * 病假
	 * @param leaveSick
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setLeaveSick(String leaveSick, ReportDaily reportDaily) {
		reportDaily.setLeaveSick((
				(StringUtils.isNotEmpty(reportDaily.getLeaveSick())?Double.parseDouble(reportDaily.getLeaveSick()):0)+
				Double.parseDouble(leaveSick))+"");
		return reportDaily;
	}
	/**
	 * 婚假
	 * @param leaveMarriage
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setLeaveMarriage(String leaveMarriage, ReportDaily reportDaily) {
		reportDaily.setLeaveMarriage((
				(StringUtils.isNotEmpty(reportDaily.getLeaveMarriage())?Double.parseDouble(reportDaily.getLeaveMarriage()):0)+
				Double.parseDouble(leaveMarriage))+"");
		return reportDaily;
	}
	/**
	 * 产假
	 * @param leaveMaternity
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setLeaveMaternity(String leaveMaternity, ReportDaily reportDaily) {
		reportDaily.setLeaveMaternity((
				(StringUtils.isNotEmpty(reportDaily.getLeaveMaternity())?Double.parseDouble(reportDaily.getLeaveMaternity()):0)+
				Double.parseDouble(leaveMaternity))+"");
		return reportDaily;
	}
	/**
	 * 产检假
	 * @param leaveMaternityCheck
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setLeaveMaternityCheck(String leaveMaternityCheck, ReportDaily reportDaily) {
		reportDaily.setLeaveMaternityCheck((
				(StringUtils.isNotEmpty(reportDaily.getLeaveMaternityCheck())?Double.parseDouble(reportDaily.getLeaveMaternityCheck()):0)+
				Double.parseDouble(leaveMaternityCheck))+"");
		return reportDaily;
	}
	/**
	 * 陪产假
	 * @param leavePaternity
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setLeavePaternity(String leavePaternity, ReportDaily reportDaily) {
		reportDaily.setLeavePaternity((
				(StringUtils.isNotEmpty(reportDaily.getLeavePaternity())?Double.parseDouble(reportDaily.getLeavePaternity()):0)+
				Double.parseDouble(leavePaternity))+"");
		return reportDaily;
	}
	/**
	 * 丧假
	 * @param leaveFuneral
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setLeaveFuneral(String leaveFuneral, ReportDaily reportDaily) {
		reportDaily.setLeaveFuneral((
				(StringUtils.isNotEmpty(reportDaily.getLeaveFuneral())?Double.parseDouble(reportDaily.getLeaveFuneral()):0)+
				Double.parseDouble(leaveFuneral))+"");
		return reportDaily;
	}
	/**
	 * 哺乳假
	 * @param leaveNursing
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setLeaveNursing(String leaveNursing, ReportDaily reportDaily) {
		reportDaily.setLeaveNursing((
				(StringUtils.isNotEmpty(reportDaily.getLeaveNursing())?Double.parseDouble(reportDaily.getLeaveNursing()):0)+
				Double.parseDouble(leaveNursing))+"");
		return reportDaily;
	}
	/**
	 * 福利日假
	 * @param leaveWelfare
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setLeaveWelfare(String leaveWelfare, ReportDaily reportDaily) {
		reportDaily.setLeaveWelfare((
				(StringUtils.isNotEmpty(reportDaily.getLeaveWelfare())?Double.parseDouble(reportDaily.getLeaveWelfare()):0)+
				Double.parseDouble(leaveWelfare))+"");
		return reportDaily;
	}
	/**
	 * 新增福利日额度
	 * @param newWelfare
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setNewWelfare(String newWelfare, ReportDaily reportDaily) {
		reportDaily.setNewWelfare((
				(StringUtils.isNotEmpty(reportDaily.getNewWelfare())?Double.parseDouble(reportDaily.getNewWelfare()):0)+
				Double.parseDouble(newWelfare))+"");
		return reportDaily;
	}
	/**
	 * 长病假
	 * @param leaveLongSick
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setLeaveLongSick(String leaveLongSick, ReportDaily reportDaily) {
		reportDaily.setLeaveLongSick((
				(StringUtils.isNotEmpty(reportDaily.getLeaveLongSick())?Double.parseDouble(reportDaily.getLeaveLongSick()):0)+
				Double.parseDouble(leaveLongSick))+"");
		return reportDaily;
	}
	/**
	 * 节假日外出
	 * @param outTimeHoliday
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setOutTimeHoliday(String outTimeHoliday, ReportDaily reportDaily) {
		reportDaily.setOutTimeHoliday((
				(StringUtils.isNotEmpty(reportDaily.getOutTimeHoliday())?Double.parseDouble(reportDaily.getOutTimeHoliday()):0)+
				Double.parseDouble(outTimeHoliday))+"");
		return reportDaily;
	}
	/**
	 * 休息日外出
	 * @param outTimeWeek
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setOutTimeWeek(String outTimeWeek, ReportDaily reportDaily) {
		reportDaily.setOutTimeWeek((
				(StringUtils.isNotEmpty(reportDaily.getOutTimeWeek())?Double.parseDouble(reportDaily.getOutTimeWeek()):0)+
				Double.parseDouble(outTimeWeek))+"");
		return reportDaily;
	}
	/**
	 * 工作日外出
	 * @param outTimeWork
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setOutTimeWork(String outTimeWork, ReportDaily reportDaily) {
		reportDaily.setOutTimeWork((
				(StringUtils.isNotEmpty(reportDaily.getOutTimeWork())?Double.parseDouble(reportDaily.getOutTimeWork()):0)+
				Double.parseDouble(outTimeWork))+"");
		return reportDaily;
	}
	/**
	 * 节假日出差
	 * @param evectionTimeHoliday
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setEvectionTimeHoliday(String evectionTimeHoliday, ReportDaily reportDaily) {
		reportDaily.setEvectionTimeHoliday((
				(StringUtils.isNotEmpty(reportDaily.getEvectionTimeHoliday())?Double.parseDouble(reportDaily.getEvectionTimeHoliday()):0)+
				Double.parseDouble(evectionTimeHoliday))+"");
		return reportDaily;
	}
	/**
	 * 休息日出差
	 * @param evectionTimeWeek
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setEvectionTimeWeek(String evectionTimeWeek, ReportDaily reportDaily) {
		reportDaily.setEvectionTimeWeek((
				(StringUtils.isNotEmpty(reportDaily.getEvectionTimeWeek())?Double.parseDouble(reportDaily.getEvectionTimeWeek()):0)+
				Double.parseDouble(evectionTimeWeek))+"");
		return reportDaily;
	}
	/**
	 * 工作日出差
	 * @param evectionTimeWork
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setEvectionTimeWork(String evectionTimeWork, ReportDaily reportDaily) {
		reportDaily.setEvectionTimeWork((
				(StringUtils.isNotEmpty(reportDaily.getEvectionTimeWork())?Double.parseDouble(reportDaily.getEvectionTimeWork()):0)+
				Double.parseDouble(evectionTimeWork))+"");
		return reportDaily;
	}
	
	/**
	 * 其他假
	 * @param leaveOther
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setLeaveOther(String leaveOther, ReportDaily reportDaily) {
		reportDaily.setLeaveOther((
				(StringUtils.isNotEmpty(reportDaily.getLeaveOther())?Double.parseDouble(reportDaily.getLeaveOther()):0)+
				Double.parseDouble(leaveOther))+"");
		return reportDaily;
	}
	/**
	 * 流产假
	 * @param leaveAmblosis
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setLeaveAmblosis(String leaveAmblosis, ReportDaily reportDaily) {
		reportDaily.setLeaveAmblosis((
				(StringUtils.isNotEmpty(reportDaily.getLeaveAmblosis())?Double.parseDouble(reportDaily.getLeaveAmblosis()):0)+
				Double.parseDouble(leaveAmblosis))+"");
		return reportDaily;
	}
	/**
	 * 孕期工间休息假
	 * @param leavePregnancy
	 * @param reportDaily
	 * @return
	 */
	public static ReportDaily setLeavePregnancy(String leavePregnancy, ReportDaily reportDaily) {
		reportDaily.setLeavePregnancy((
				(StringUtils.isNotEmpty(reportDaily.getLeavePregnancy())?Double.parseDouble(reportDaily.getLeavePregnancy()):0)+
				Double.parseDouble(leavePregnancy))+"");
		return reportDaily;
	}
	
	public static void main(String[] args) {
		/*AlgorithmResult result = new AlgorithmResult();
		ReportDaily reportDaily  = result.getReportDaily();
		AlgorithmReport.setLate("1", reportDaily);
		System.out.println(result.getReportDaily().getLate());*/
	}
}
