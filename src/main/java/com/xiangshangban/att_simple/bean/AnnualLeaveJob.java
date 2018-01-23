package com.xiangshangban.att_simple.bean;

public class AnnualLeaveJob {
    private String jobId;

    private String companyId;

    private String auditorEmployeeId;

    private String year;

    private String executeDate;

    private String createJobDate;

    private String jobType;

    private String jobStatus;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getAuditorEmployeeId() {
        return auditorEmployeeId;
    }

    public void setAuditorEmployeeId(String auditorEmployeeId) {
        this.auditorEmployeeId = auditorEmployeeId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getExecuteDate() {
        return executeDate;
    }

    public void setExecuteDate(String executeDate) {
        this.executeDate = executeDate;
    }

    public String getCreateJobDate() {
        return createJobDate;
    }

    public void setCreateJobDate(String createJobDate) {
        this.createJobDate = createJobDate;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

	public AnnualLeaveJob(String jobId, String companyId, String auditorEmployeeId, String year, String executeDate,
			String createJobDate, String jobType, String jobStatus) {
		super();
		this.jobId = jobId;
		this.companyId = companyId;
		this.auditorEmployeeId = auditorEmployeeId;
		this.year = year;
		this.executeDate = executeDate;
		this.createJobDate = createJobDate;
		this.jobType = jobType;
		this.jobStatus = jobStatus;
	}

	public AnnualLeaveJob() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}