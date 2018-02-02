package com.xiangshangban.att_simple.bean;
/**
 * 签到签退(辅助计算用)
 * @author 韦友弟
 *
 */
public class AlgorithmSign {
	private String signInTime;//签到
	private String signOutTime;//签退
	String checkBeginTime = "";//实际上班时间，检查迟到异常的开始时间
	String checkEndTime = "";//实际下班时间。检查早退异常的开始时间
	private boolean hasLeaveSignIn = false;//有左请假
	private boolean hasLeaveSignOut = false;//有右请假
	private boolean hasOutSignIn1 = false;//有左出差
	private boolean hasOutSignOut1 = false;//有右出差
	private boolean hasOutSignIn2 = false;//有左外出
	private boolean hasOutSignOut2 = false;//有右外出
	private boolean hasOutSignIn3 = false;//有左派工
	private boolean hasOutSignOut3 = false;//有右派工
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
	public String getCheckBeginTime() {
		return checkBeginTime;
	}
	public void setCheckBeginTime(String checkBeginTime) {
		this.checkBeginTime = checkBeginTime;
	}
	public String getCheckEndTime() {
		return checkEndTime;
	}
	public void setCheckEndTime(String checkEndTime) {
		this.checkEndTime = checkEndTime;
	}
	public boolean isHasLeaveSignIn() {
		return hasLeaveSignIn;
	}
	public void setHasLeaveSignIn(boolean hasLeaveSignIn) {
		this.hasLeaveSignIn = hasLeaveSignIn;
	}
	public boolean isHasLeaveSignOut() {
		return hasLeaveSignOut;
	}
	public void setHasLeaveSignOut(boolean hasLeaveSignOut) {
		this.hasLeaveSignOut = hasLeaveSignOut;
	}
	public boolean isHasOutSignIn1() {
		return hasOutSignIn1;
	}
	public void setHasOutSignIn1(boolean hasOutSignIn1) {
		this.hasOutSignIn1 = hasOutSignIn1;
	}
	public boolean isHasOutSignOut1() {
		return hasOutSignOut1;
	}
	public void setHasOutSignOut1(boolean hasOutSignOut1) {
		this.hasOutSignOut1 = hasOutSignOut1;
	}
	public boolean isHasOutSignIn2() {
		return hasOutSignIn2;
	}
	public void setHasOutSignIn2(boolean hasOutSignIn2) {
		this.hasOutSignIn2 = hasOutSignIn2;
	}
	public boolean isHasOutSignOut2() {
		return hasOutSignOut2;
	}
	public void setHasOutSignOut2(boolean hasOutSignOut2) {
		this.hasOutSignOut2 = hasOutSignOut2;
	}
	public boolean isHasOutSignIn3() {
		return hasOutSignIn3;
	}
	public void setHasOutSignIn3(boolean hasOutSignIn3) {
		this.hasOutSignIn3 = hasOutSignIn3;
	}
	public boolean isHasOutSignOut3() {
		return hasOutSignOut3;
	}
	public void setHasOutSignOut3(boolean hasOutSignOut3) {
		this.hasOutSignOut3 = hasOutSignOut3;
	}
	
	
	
}
