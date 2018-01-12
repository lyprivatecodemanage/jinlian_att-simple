package com.xiangshangban.att_simple.bean;

public class ClassesEmployee {
    private String id;

    private String empId;

    private String empCompanyId;

    private String classesId;

    private String onDutySchedulingDate;

    private String offDutySchedulingDate;

    private String week;

    private ClassesType classesType;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpCompanyId() {
        return empCompanyId;
    }

    public void setEmpCompanyId(String empCompanyId) {
        this.empCompanyId = empCompanyId;
    }

    public String getClassesId() {
        return classesId;
    }

    public void setClassesId(String classesId) {
        this.classesId = classesId;
    }

    public String getOnDutySchedulingDate() {
        return onDutySchedulingDate;
    }

    public void setOnDutySchedulingDate(String onDutySchedulingDate) {
        this.onDutySchedulingDate = onDutySchedulingDate;
    }

    public String getOffDutySchedulingDate() {
        return offDutySchedulingDate;
    }

    public void setOffDutySchedulingDate(String offDutySchedulingDate) {
        this.offDutySchedulingDate = offDutySchedulingDate;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

	public ClassesType getClassesType() {
		return classesType;
	}

	public void setClassesType(ClassesType classesType) {
		this.classesType = classesType;
	}
}