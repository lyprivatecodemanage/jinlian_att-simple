package com.xiangshangban.att_simple.bean;

/**
 * 计算调休时长情况表
 * @author mian
 */
public class AdjustRestDateCalculate {
	//UUID
    private String adjustRestId;
    //申请表ID
	private String applicationFormId;
	//影响调休时长类型(0:加班申请通过,1:管理员系统微调)
	private String adjustRestType;
	//调整时长
	private String adjustRestDuration;
	//调整状态(0:收入,1:支出)
	private String adjustMold;
	//调整时间
	private String adjustDate;
	//调整原因
	private String adjustReason;
	//调休总额
	private String adjustTotal;
	//调休余额
	private String adjustBalance;

	public String getAdjustRestId() {
		return adjustRestId;
	}

	public void setAdjustRestId(String adjustRestId) {
		this.adjustRestId = adjustRestId;
	}

	public String getApplicationFormId() {
		return applicationFormId;
	}

	public void setApplicationFormId(String applicationFormId) {
		this.applicationFormId = applicationFormId;
	}

	public String getAdjustRestType() {
		return adjustRestType;
	}

	public void setAdjustRestType(String adjustRestType) {
		this.adjustRestType = adjustRestType;
	}

	public String getAdjustRestDuration() {
		return adjustRestDuration;
	}

	public void setAdjustRestDuration(String adjustRestDuration) {
		this.adjustRestDuration = adjustRestDuration;
	}

	public String getAdjustMold() {
		return adjustMold;
	}

	public void setAdjustMold(String adjustMold) {
		this.adjustMold = adjustMold;
	}

	public String getAdjustDate() {
		return adjustDate;
	}

	public void setAdjustDate(String adjustDate) {
		this.adjustDate = adjustDate;
	}

	public String getAdjustReason() {
		return adjustReason;
	}

	public void setAdjustReason(String adjustReason) {
		this.adjustReason = adjustReason;
	}

	public String getAdjustTotal() {
		return adjustTotal;
	}

	public void setAdjustTotal(String adjustTotal) {
		this.adjustTotal = adjustTotal;
	}

	public String getAdjustBalance() {
		return adjustBalance;
	}

	public void setAdjustBalance(String adjustBalance) {
		this.adjustBalance = adjustBalance;
	}

	public AdjustRestDateCalculate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AdjustRestDateCalculate(String adjustRestId, String applicationFormId, String adjustRestType,
			String adjustRestDuration, String adjustMold, String adjustDate, String adjustReason, String adjustTotal,
			String adjustBalance) {
		super();
		this.adjustRestId = adjustRestId;
		this.applicationFormId = applicationFormId;
		this.adjustRestType = adjustRestType;
		this.adjustRestDuration = adjustRestDuration;
		this.adjustMold = adjustMold;
		this.adjustDate = adjustDate;
		this.adjustReason = adjustReason;
		this.adjustTotal = adjustTotal;
		this.adjustBalance = adjustBalance;
	}
	
}