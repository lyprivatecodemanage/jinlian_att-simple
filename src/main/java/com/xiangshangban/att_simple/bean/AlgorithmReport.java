package com.xiangshangban.att_simple.bean;
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
				Long.parseLong(reportDaily.getWorkTime())+
				Long.parseLong(workTime))+"");
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
				Long.parseLong(reportDaily.getRealWorkTime())+
				Long.parseLong(realWorkTime))+"");
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
				Long.parseLong(reportDaily.getRealAttendanceTime())+
				Long.parseLong(realAttendanceTime))+"");
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
				Long.parseLong(reportDaily.getLate())+
				Long.parseLong(late))+"");
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
				Long.parseLong(reportDaily.getLateTime())+
				Long.parseLong(lateTime))+"");
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
				Long.parseLong(reportDaily.getEarly())+
				Long.parseLong(early))+"");
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
				Long.parseLong(reportDaily.getEarlyTime())+
				Long.parseLong(earlyTime))+"");
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
				Long.parseLong(reportDaily.getAbsent())+
				Long.parseLong(absent))+"");
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
				Long.parseLong(reportDaily.getAbsentTime())+
				Long.parseLong(absentTime))+"");
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
				Long.parseLong(reportDaily.getNormalOverWork())+
				Long.parseLong(normalOverWork))+"");
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
				Long.parseLong(reportDaily.getWeekOverWork())+
				Long.parseLong(weekOverWork))+"");
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
				Long.parseLong(reportDaily.getHolidayOverWork())+
				Long.parseLong(holidayOverWork))+"");
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
				Long.parseLong(reportDaily.getChangeTime())+
				Long.parseLong(changeTime))+"");
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
				Long.parseLong(reportDaily.getLeaveAbsence())+
				Long.parseLong(leaveAbsence))+"");
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
				Long.parseLong(reportDaily.getLeaveDaysOff())+
				Long.parseLong(leaveDaysOff))+"");
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
				Long.parseLong(reportDaily.getLeaveAnnual())+
				Long.parseLong(leaveAnnual))+"");
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
				Long.parseLong(reportDaily.getLeaveSick())+
				Long.parseLong(leaveSick))+"");
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
				Long.parseLong(reportDaily.getLeaveMarriage())+
				Long.parseLong(leaveMarriage))+"");
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
				Long.parseLong(reportDaily.getLeaveMaternity())+
				Long.parseLong(leaveMaternity))+"");
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
				Long.parseLong(reportDaily.getLeaveMaternityCheck())+
				Long.parseLong(leaveMaternityCheck))+"");
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
				Long.parseLong(reportDaily.getLeavePaternity())+
				Long.parseLong(leavePaternity))+"");
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
				Long.parseLong(reportDaily.getLeaveFuneral())+
				Long.parseLong(leaveFuneral))+"");
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
				Long.parseLong(reportDaily.getLeaveNursing())+
				Long.parseLong(leaveNursing))+"");
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
				Long.parseLong(reportDaily.getLeaveWelfare())+
				Long.parseLong(leaveWelfare))+"");
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
				Long.parseLong(reportDaily.getNewWelfare())+
				Long.parseLong(newWelfare))+"");
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
				Long.parseLong(reportDaily.getLeaveLongSick())+
				Long.parseLong(leaveLongSick))+"");
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
				Long.parseLong(reportDaily.getOutTimeHoliday())+
				Long.parseLong(outTimeHoliday))+"");
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
				Long.parseLong(reportDaily.getOutTimeWeek())+
				Long.parseLong(outTimeWeek))+"");
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
				Long.parseLong(reportDaily.getOutTimeWork())+
				Long.parseLong(outTimeWork))+"");
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
				Long.parseLong(reportDaily.getEvectionTimeHoliday())+
				Long.parseLong(evectionTimeHoliday))+"");
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
				Long.parseLong(reportDaily.getEvectionTimeWeek())+
				Long.parseLong(evectionTimeWeek))+"");
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
				Long.parseLong(reportDaily.getEvectionTimeWork())+
				Long.parseLong(evectionTimeWork))+"");
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
				Long.parseLong(reportDaily.getLeaveOther())+
				Long.parseLong(leaveOther))+"");
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
				Long.parseLong(reportDaily.getLeaveAmblosis())+
				Long.parseLong(leaveAmblosis))+"");
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
				Long.parseLong(reportDaily.getLeavePregnancy())+
				Long.parseLong(leavePregnancy))+"");
		return reportDaily;
	}
	
	public static void main(String[] args) {
		/*AlgorithmResult result = new AlgorithmResult();
		ReportDaily reportDaily  = result.getReportDaily();
		AlgorithmReport.setLate("1", reportDaily);
		System.out.println(result.getReportDaily().getLate());*/
	}
}
