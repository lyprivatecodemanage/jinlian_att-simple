package com.xiangshangban.att_simple.bean;

public class VacationQuota {
    private String id;

    private String employeeId;

    private String departmentId;

    private String companyId;

    private String yearVacationTotal;

    private String surplusYearVacation;

    private String transferRestTotal;

    private String surplusTransferRest;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getYearVacationTotal() {
        return yearVacationTotal;
    }

    public void setYearVacationTotal(String yearVacationTotal) {
        this.yearVacationTotal = yearVacationTotal;
    }

    public String getSurplusYearVacation() {
        return surplusYearVacation;
    }

    public void setSurplusYearVacation(String surplusYearVacation) {
        this.surplusYearVacation = surplusYearVacation;
    }

    public String getTransferRestTotal() {
        return transferRestTotal;
    }

    public void setTransferRestTotal(String transferRestTotal) {
        this.transferRestTotal = transferRestTotal;
    }

    public String getSurplusTransferRest() {
        return surplusTransferRest;
    }

    public void setSurplusTransferRest(String surplusTransferRest) {
        this.surplusTransferRest = surplusTransferRest;
    }
}