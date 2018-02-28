package com.xiangshangban.att_simple.bean;

public class ReportDaily extends ReportDailyKey {
    private String reportId;
    private String deptId;//部门ID
    private String paibanRemark;//排班描述
    private String checkInfo;//打卡有效时段描述
    private String workTime;//应该工作时间，理论
    private String realWorkTime;//实际工作时间，实际
    private String realAttendanceTime;//实际考勤时间，实际
    private String late;//迟到次数
    private String lateTime;//迟到时长
    private String early;//早退次数
    private String earlyTime;//早退次数
    private String absent;//旷工次数
    private String absentTime;//旷工时长
    private String normalOverWork;//平日加班时长
    private String weekOverWork;//周末加班时长
    private String holidayOverWork;//节日加班时长
    private String changeTime;//新增调休时间
    private String leaveAbsence;//事假
    private String leaveDaysOff;//调休假
    private String leaveAnnual;//年假
    private String leaveSick;//病假
    private String leaveMarriage;//婚假
    private String leaveMaternity;//产假
    private String leaveMaternityCheck;//产检假
    private String leavePaternity;//陪产假
    private String leaveFuneral;//丧假
    private String leaveNursing;//哺乳假
    private String leaveWelfare;//福利日使用额度
    private String newWelfare;//福利日生成额度
    private String leaveLongSick;//长病假
    private String outTimeHoliday;//节假日外出时间
    private String outTimeWeek;//周末外出时间
    private String outTimeWork;//工作日外出时间
    private String evectionTimeHoliday;//节假日出差时间
    private String evectionTimeWeek;//周末出差时间
    private String evectionTimeWork;//工作日出差时间
    private String deptName;//部门名称
    private String beginTime1;//上班时间
    private String endTime1;//下班时间
    private String leaveAmblosis;//流产假
    private String leavePregnancy;//孕期工间休息假
    private String leaveOther;//其他假
    private String workDay;//应出天数
    private String signInTime;//签到
    private String signOutTime;//签退
    private String hasException;//是否有异常待处理，0：否，1：是
    private String exceptionMark;//异常描述
    private String departmentName;//部门名称
    private String exceptionMarkName;//异常名称
    private String employeeName;//人员名称
    private String isProcess;//异常处理状态，0：未处理，1：处理。默认已处理。不存在异常时，该状态也为未处理。当确认过异常，则日报数据不会再计算更新
    private String leaveDate;//请假时间
    private String matterLeave;//事假时间（除年假调休假以外所有假）
    //计数
    private String count;

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getMatterLeave() {
		return matterLeave;
	}

	public void setMatterLeave(String matterLeave) {
		this.matterLeave = matterLeave;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getExceptionMarkName() {
		return exceptionMarkName;
	}

	public void setExceptionMarkName(String exceptionMarkName) {
		this.exceptionMarkName = exceptionMarkName;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getHasException() {
		return hasException;
	}

	public void setHasException(String hasException) {
		this.hasException = hasException;
	}

	public String getExceptionMark() {
		return exceptionMark;
	}

	public void setExceptionMark(String exceptionMark) {
		this.exceptionMark = exceptionMark;
	}

	public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getPaibanRemark() {
        return paibanRemark;
    }

    public void setPaibanRemark(String paibanRemark) {
        this.paibanRemark = paibanRemark;
    }

    public String getCheckInfo() {
        return checkInfo;
    }

    public void setCheckInfo(String checkInfo) {
        this.checkInfo = checkInfo;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getRealWorkTime() {
        return realWorkTime;
    }

    public void setRealWorkTime(String realWorkTime) {
        this.realWorkTime = realWorkTime;
    }

    public String getRealAttendanceTime() {
        return realAttendanceTime;
    }

    public void setRealAttendanceTime(String realAttendanceTime) {
        this.realAttendanceTime = realAttendanceTime;
    }

    public String getLate() {
        return late;
    }

    public void setLate(String late) {
        this.late = late;
    }

    public String getLateTime() {
        return lateTime;
    }

    public void setLateTime(String lateTime) {
        this.lateTime = lateTime;
    }

    public String getEarly() {
        return early;
    }

    public void setEarly(String early) {
        this.early = early;
    }

    public String getEarlyTime() {
        return earlyTime;
    }

    public void setEarlyTime(String earlyTime) {
        this.earlyTime = earlyTime;
    }

    public String getAbsent() {
        return absent;
    }

    public void setAbsent(String absent) {
        this.absent = absent;
    }

    public String getAbsentTime() {
        return absentTime;
    }

    public void setAbsentTime(String absentTime) {
        this.absentTime = absentTime;
    }

    public String getNormalOverWork() {
        return normalOverWork;
    }

    public void setNormalOverWork(String normalOverWork) {
        this.normalOverWork = normalOverWork;
    }

    public String getWeekOverWork() {
        return weekOverWork;
    }

    public void setWeekOverWork(String weekOverWork) {
        this.weekOverWork = weekOverWork;
    }

    public String getHolidayOverWork() {
        return holidayOverWork;
    }

    public void setHolidayOverWork(String holidayOverWork) {
        this.holidayOverWork = holidayOverWork;
    }

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    public String getLeaveAbsence() {
        return leaveAbsence;
    }

    public void setLeaveAbsence(String leaveAbsence) {
        this.leaveAbsence = leaveAbsence;
    }

    public String getLeaveDaysOff() {
        return leaveDaysOff;
    }

    public void setLeaveDaysOff(String leaveDaysOff) {
        this.leaveDaysOff = leaveDaysOff;
    }

    public String getLeaveAnnual() {
        return leaveAnnual;
    }

    public void setLeaveAnnual(String leaveAnnual) {
        this.leaveAnnual = leaveAnnual;
    }

    public String getLeaveSick() {
        return leaveSick;
    }

    public void setLeaveSick(String leaveSick) {
        this.leaveSick = leaveSick;
    }

    public String getLeaveMarriage() {
        return leaveMarriage;
    }

    public void setLeaveMarriage(String leaveMarriage) {
        this.leaveMarriage = leaveMarriage;
    }

    public String getLeaveMaternity() {
        return leaveMaternity;
    }

    public void setLeaveMaternity(String leaveMaternity) {
        this.leaveMaternity = leaveMaternity;
    }

    public String getLeaveMaternityCheck() {
        return leaveMaternityCheck;
    }

    public void setLeaveMaternityCheck(String leaveMaternityCheck) {
        this.leaveMaternityCheck = leaveMaternityCheck;
    }

    public String getLeavePaternity() {
        return leavePaternity;
    }

    public void setLeavePaternity(String leavePaternity) {
        this.leavePaternity = leavePaternity;
    }

    public String getLeaveFuneral() {
        return leaveFuneral;
    }

    public void setLeaveFuneral(String leaveFuneral) {
        this.leaveFuneral = leaveFuneral;
    }

    public String getLeaveNursing() {
        return leaveNursing;
    }

    public void setLeaveNursing(String leaveNursing) {
        this.leaveNursing = leaveNursing;
    }

    public String getLeaveWelfare() {
        return leaveWelfare;
    }

    public void setLeaveWelfare(String leaveWelfare) {
        this.leaveWelfare = leaveWelfare;
    }

    public String getNewWelfare() {
        return newWelfare;
    }

    public void setNewWelfare(String newWelfare) {
        this.newWelfare = newWelfare;
    }

    public String getLeaveLongSick() {
        return leaveLongSick;
    }

    public void setLeaveLongSick(String leaveLongSick) {
        this.leaveLongSick = leaveLongSick;
    }

    public String getOutTimeHoliday() {
        return outTimeHoliday;
    }

    public void setOutTimeHoliday(String outTimeHoliday) {
        this.outTimeHoliday = outTimeHoliday;
    }

    public String getOutTimeWeek() {
        return outTimeWeek;
    }

    public void setOutTimeWeek(String outTimeWeek) {
        this.outTimeWeek = outTimeWeek;
    }

    public String getOutTimeWork() {
        return outTimeWork;
    }

    public void setOutTimeWork(String outTimeWork) {
        this.outTimeWork = outTimeWork;
    }

    public String getEvectionTimeHoliday() {
        return evectionTimeHoliday;
    }

    public void setEvectionTimeHoliday(String evectionTimeHoliday) {
        this.evectionTimeHoliday = evectionTimeHoliday;
    }

    public String getEvectionTimeWeek() {
        return evectionTimeWeek;
    }

    public void setEvectionTimeWeek(String evectionTimeWeek) {
        this.evectionTimeWeek = evectionTimeWeek;
    }

    public String getEvectionTimeWork() {
        return evectionTimeWork;
    }

    public void setEvectionTimeWork(String evectionTimeWork) {
        this.evectionTimeWork = evectionTimeWork;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getBeginTime1() {
        return beginTime1;
    }

    public void setBeginTime1(String beginTime1) {
        this.beginTime1 = beginTime1;
    }

    public String getEndTime1() {
        return endTime1;
    }

    public void setEndTime1(String endTime1) {
        this.endTime1 = endTime1;
    }

    public String getLeaveOther() {
        return leaveOther;
    }

    public void setLeaveOther(String leaveOther) {
        this.leaveOther = leaveOther;
    }

    public String getLeaveAmblosis() {
        return leaveAmblosis;
    }

    public void setLeaveAmblosis(String leaveAmblosis) {
        this.leaveAmblosis = leaveAmblosis;
    }

    public String getLeavePregnancy() {
        return leavePregnancy;
    }

    public void setLeavePregnancy(String leavePregnancy) {
        this.leavePregnancy = leavePregnancy;
    }

    public String getWorkDay() {
        return workDay;
    }

    public void setWorkDay(String workDay) {
        this.workDay = workDay;
    }

	public String getSignInTime() {
		return signInTime;
	}

	public void setSignInTime(String signInTime) {
		this.signInTime = signInTime;
	}

	public String getSignOutTime() {
		return signOutTime;
	}

	public void setSignOutTime(String signOutTime) {
		this.signOutTime = signOutTime;
	}

	public String getIsProcess() {
		return isProcess;
	}

	public void setIsProcess(String isProcess) {
		this.isProcess = isProcess;
	}
}