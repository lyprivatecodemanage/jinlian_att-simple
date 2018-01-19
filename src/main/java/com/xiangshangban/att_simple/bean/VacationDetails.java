package com.xiangshangban.att_simple.bean;

/**
 * 假期详情表
 * @author mian
 */
public class VacationDetails {
	//假期详情表ID
	private String vacationDetailsId;
	//假期表ID
    private String vacationId;
    //假期类型(0:年假,1:调休)
    private String vacationType;
    //调整状态(0:收入,1:支出)
    private String vacationMold;
    //额度改变
    private String limitChange;
    //假期总额
    private String vacationTotal;
    //假期余额
    private String vacationBalance;
    //增减原因(1.系统调整2.人工调整)
    private String changingReason;
    //操作人ID
    private String auditorEmployeeId;
    //调整时间
    private String changeingDate;
    //调整说明
    private String adjustingInstruction;
    //年份
    private String year;
    /*----------------------------------扩展属性------------------------------------*/
    
    public static String Tweaks = "系统调整";
    
    public static String manualAdjustment = "人工调整";
    
    private String vacationTypeName;
    
    private String employeeName;
    
    public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getVacationTypeName() {
		return vacationTypeName;
	}

	public void setVacationTypeName(String vacationTypeName) {
		this.vacationTypeName = vacationTypeName;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getVacationDetailsId() {
        return vacationDetailsId;
    }

    public void setVacationDetailsId(String vacationDetailsId) {
        this.vacationDetailsId = vacationDetailsId;
    }

    public String getVacationId() {
        return vacationId;
    }

    public void setVacationId(String vacationId) {
        this.vacationId = vacationId;
    }

    public String getVacationType() {
        return vacationType;
    }

    public void setVacationType(String vacationType) {
        this.vacationType = vacationType;
    }

    public String getVacationMold() {
        return vacationMold;
    }

    public void setVacationMold(String vacationMold) {
        this.vacationMold = vacationMold;
    }

    public String getLimitChange() {
        return limitChange;
    }

    public void setLimitChange(String limitChange) {
        this.limitChange = limitChange;
    }

    public String getVacationTotal() {
        return vacationTotal;
    }

    public void setVacationTotal(String vacationTotal) {
        this.vacationTotal = vacationTotal;
    }

    public String getVacationBalance() {
        return vacationBalance;
    }

    public void setVacationBalance(String vacationBalance) {
        this.vacationBalance = vacationBalance;
    }

    public String getChangingReason() {
        return changingReason;
    }

    public void setChangingReason(String changingReason) {
        this.changingReason = changingReason;
    }

    public String getAuditorEmployeeId() {
        return auditorEmployeeId;
    }

    public void setAuditorEmployeeId(String auditorEmployeeId) {
        this.auditorEmployeeId = auditorEmployeeId;
    }

    public String getChangeingDate() {
        return changeingDate;
    }

    public void setChangeingDate(String changeingDate) {
        this.changeingDate = changeingDate;
    }

	public String getAdjustingInstruction() {
		return adjustingInstruction;
	}

	public void setAdjustingInstruction(String adjustingInstruction) {
		this.adjustingInstruction = adjustingInstruction;
	}
    
}