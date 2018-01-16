package com.xiangshangban.att_simple.bean;

/**
 * 假期表
 * @author mian
 */
public class Vacation {
	//假期表ID
	private String vacationId;
	//公司ID
    private String companyId;
    //部门ID
    private String departmentId;
    //人员ID
    private String employeeId;
    //年假总额
    private String annualLeaveTotal;
    //年假余额
    private String annualLeaveBalance;
    //调休总额
    private String adjustRestTotal;
    //调休余额
    private String adjustRestBalance;
    
    /*----------------------------------扩展属性------------------------------------*/
    
    //部门名称
    private String departmentName;
    //姓名
    private String employeeName;
    
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getVacationId() {
        return vacationId;
    }

    public void setVacationId(String vacationId) {
        this.vacationId = vacationId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getAnnualLeaveTotal() {
        return annualLeaveTotal;
    }

    public void setAnnualLeaveTotal(String annualLeaveTotal) {
        this.annualLeaveTotal = annualLeaveTotal;
    }

    public String getAnnualLeaveBalance() {
        return annualLeaveBalance;
    }

    public void setAnnualLeaveBalance(String annualLeaveBalance) {
        this.annualLeaveBalance = annualLeaveBalance;
    }

    public String getAdjustRestTotal() {
        return adjustRestTotal;
    }

    public void setAdjustRestTotal(String adjustRestTotal) {
        this.adjustRestTotal = adjustRestTotal;
    }

    public String getAdjustRestBalance() {
        return adjustRestBalance;
    }

    public void setAdjustRestBalance(String adjustRestBalance) {
        this.adjustRestBalance = adjustRestBalance;
    }

	public Vacation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Vacation(String vacationId, String companyId, String departmentId, String employeeId,
			String annualLeaveTotal, String annualLeaveBalance, String adjustRestTotal, String adjustRestBalance) {
		super();
		this.vacationId = vacationId;
		this.companyId = companyId;
		this.departmentId = departmentId;
		this.employeeId = employeeId;
		this.annualLeaveTotal = annualLeaveTotal;
		this.annualLeaveBalance = annualLeaveBalance;
		this.adjustRestTotal = adjustRestTotal;
		this.adjustRestBalance = adjustRestBalance;
	}
    
}