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
    
    //年假总额排序
    private String annualLeaveTotalRank;
    //年假余额排序
    private String annualLeaveBalanceRank;
    //调休总额排序
    private String adjustRestTotalRank;
    //调休余额排序
    private String adjustRestBalanceRank;
    //分页数据总数
    private Integer pageTotalNumber;
    //每页显示数据条数
    private Integer pageNum;
    
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

	public String getAnnualLeaveTotalRank() {
		return annualLeaveTotalRank;
	}

	public void setAnnualLeaveTotalRank(String annualLeaveTotalRank) {
		this.annualLeaveTotalRank = annualLeaveTotalRank;
	}

	public String getAnnualLeaveBalanceRank() {
		return annualLeaveBalanceRank;
	}

	public void setAnnualLeaveBalanceRank(String annualLeaveBalanceRank) {
		this.annualLeaveBalanceRank = annualLeaveBalanceRank;
	}

	public String getAdjustRestTotalRank() {
		return adjustRestTotalRank;
	}

	public void setAdjustRestTotalRank(String adjustRestTotalRank) {
		this.adjustRestTotalRank = adjustRestTotalRank;
	}

	public String getAdjustRestBalanceRank() {
		return adjustRestBalanceRank;
	}

	public void setAdjustRestBalanceRank(String adjustRestBalanceRank) {
		this.adjustRestBalanceRank = adjustRestBalanceRank;
	}

	public Integer getPageTotalNumber() {
		return pageTotalNumber;
	}

	public void setPageTotalNumber(Integer pageTotalNumber) {
		this.pageTotalNumber = pageTotalNumber;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
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