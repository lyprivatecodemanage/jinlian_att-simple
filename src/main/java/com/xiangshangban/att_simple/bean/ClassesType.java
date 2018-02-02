package com.xiangshangban.att_simple.bean;

public class ClassesType {
    private String id;//主键（班次ID）

    private String classesName;//班次名称

    private String onDutyTime;//上班时间

    private String offDutyTime;//下班时间

    private String morrowDutyTimeFlag;//（标志位）是否是次日当前时刻下班

    private String restTime;//一天的休息时间段（12:00-13:00）

    private String restDays;//一周的哪几天休息

    private String festivalRestFlag;//节假日是否放假标志位（0:不放假 1:放假）

    private String signInRule;//签到迟到xx分钟不算迟到

    private String signOutRule;//签退提早xx分钟不算早退

    private String onPunchCardTime;//上班允许提前xx分钟打卡

    private String offPunchCardTime;//下班允许推迟xx分钟打卡

    private String autoClassesFlag;//自动排班标志位（1：一个月进行一次排班 2：一个季度进行一次排班）
    
    private String autoScheduledSwitch;//自动排班开关（1：启用自动排班 0：不启用自动排班）

    private String createTime;//该班次类型生成的时间

    private String companyId;//公司ID
    
    private String validDate;//班次类型生效的时间
    
    private String isDefault;//标志位（0：不是公司默认的班次 1：是公司默认的班次）
    
    private String delDate;//班次类别删除时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassesName() {
        return classesName;
    }

    public void setClassesName(String classesName) {
        this.classesName = classesName;
    }

    public String getOnDutyTime() {
        return onDutyTime;
    }

    public void setOnDutyTime(String onDutyTime) {
        this.onDutyTime = onDutyTime;
    }

    public String getOffDutyTime() {
        return offDutyTime;
    }

    public void setOffDutyTime(String offDutyTime) {
        this.offDutyTime = offDutyTime;
    }

    public String getMorrowDutyTimeFlag() {
        return morrowDutyTimeFlag;
    }

    public void setMorrowDutyTimeFlag(String morrowDutyTimeFlag) {
        this.morrowDutyTimeFlag = morrowDutyTimeFlag;
    }

    public String getRestTime() {
        return restTime;
    }

    public void setRestTime(String restTime) {
        this.restTime = restTime;
    }

    public String getRestDays() {
        return restDays;
    }

    public void setRestDays(String restDays) {
        this.restDays = restDays;
    }

    public String getFestivalRestFlag() {
        return festivalRestFlag;
    }

    public void setFestivalRestFlag(String festivalRestFlag) {
        this.festivalRestFlag = festivalRestFlag;
    }

    public String getSignInRule() {
        return signInRule;
    }

    public void setSignInRule(String signInRule) {
        this.signInRule = signInRule;
    }

    public String getSignOutRule() {
        return signOutRule;
    }

    public void setSignOutRule(String signOutRule) {
        this.signOutRule = signOutRule;
    }

    public String getOnPunchCardTime() {
        return onPunchCardTime;
    }

    public void setOnPunchCardTime(String onPunchCardTime) {
        this.onPunchCardTime = onPunchCardTime;
    }

    public String getOffPunchCardTime() {
        return offPunchCardTime;
    }

    public void setOffPunchCardTime(String offPunchCardTime) {
        this.offPunchCardTime = offPunchCardTime;
    }

    public String getAutoClassesFlag() {
        return autoClassesFlag;
    }

    public void setAutoClassesFlag(String autoClassesFlag) {
        this.autoClassesFlag = autoClassesFlag;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getDelDate() {
		return delDate;
	}

	public void setDelDate(String delDate) {
		this.delDate = delDate;
	}

	public String getAutoScheduledSwitch() {
		return autoScheduledSwitch;
	}

	public void setAutoScheduledSwitch(String autoScheduledSwitch) {
		this.autoScheduledSwitch = autoScheduledSwitch;
	}
}