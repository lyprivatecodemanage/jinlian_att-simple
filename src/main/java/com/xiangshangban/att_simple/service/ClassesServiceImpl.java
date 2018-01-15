package com.xiangshangban.att_simple.service;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.att_simple.bean.ClassesEmployee;
import com.xiangshangban.att_simple.bean.ClassesType;
import com.xiangshangban.att_simple.dao.ClassesEmployeeMapper;
import com.xiangshangban.att_simple.dao.ClassesTypeMapper;
import com.xiangshangban.att_simple.utils.FormatUtil;
import com.xiangshangban.att_simple.utils.TimeUtil;

@Service
@Transactional
public class ClassesServiceImpl implements ClassesService{
	
	@Autowired
	private ClassesTypeMapper classesTypeMapper;
	
	@Autowired
	private ClassesEmployeeMapper classesEmployeeMapper;
	
	@Override
	public boolean addNewClassesType(String requestParam, String companyId) {
		//处理请求参数
		JSONObject jsonObject = JSONObject.parseObject(requestParam);
		Object classesName = jsonObject.get("classesName");
		Object onDutyTime = jsonObject.get("on_duty_time");
		Object offDutyTime = jsonObject.get("off_duty_time");
		Object morrowFlag = jsonObject.get("morrowFlag");
		Object restStartTime = jsonObject.get("restStartTime");
		Object restEndTime = jsonObject.get("restEndTime");
		Object restDays = jsonObject.get("restDays");
		Object festivalRestFlag = jsonObject.get("festivalRestFlag");
		Object signInRule = jsonObject.get("signInRule");
		Object signOutRule = jsonObject.get("signOutRule");
		Object onPunchCardRule = jsonObject.get("onPunchCardRule");
		Object offPunchCardRule = jsonObject.get("offPunchCardRule");
		Object employeeIdList = jsonObject.get("employeeIdList");
		Object autoClassesFlag = jsonObject.get("autoClassesFlag");
		
		//声明要返回的数据
		boolean result = false;
		
		if(classesName!=null && onDutyTime!=null && offDutyTime!=null && morrowFlag!=null 
				&& restStartTime!=null && restEndTime!=null && restDays!=null && festivalRestFlag!=null 
				&& signInRule!=null && signOutRule!=null && onPunchCardRule!=null 
				&& offPunchCardRule!=null && employeeIdList!=null && autoClassesFlag!=null){
			//获取要排班的人员列表
			JSONArray empArray = JSONArray.parseArray(JSONObject.toJSONString(employeeIdList));
			//TODO ①：添加班次类型
			ClassesType classesType = new ClassesType();
			//班次类型的UUID
			String typeUUID = FormatUtil.createUuid();
			classesType.setId(typeUUID);
			classesType.setClassesName(classesName.toString().trim());
			classesType.setOnDutyTime(onDutyTime.toString().trim());
			classesType.setOffDutyTime(offDutyTime.toString().trim());
			classesType.setMorrowDutyTimeFlag(morrowFlag.toString().trim());
			classesType.setRestTime(restStartTime.toString().trim()+"-"+restEndTime.toString().trim());
			classesType.setRestDays(restDays.toString().trim());
			classesType.setFestivalRestFlag(festivalRestFlag.toString().trim());
			classesType.setSignInRule(signInRule.toString().trim());
			classesType.setSignOutRule(signOutRule.toString().trim());
			classesType.setOnPunchCardTime(onPunchCardRule.toString().trim());
			classesType.setOffPunchCardTime(offPunchCardRule.toString().trim());
			classesType.setAutoClassesFlag(autoClassesFlag.toString().trim());
			classesType.setCompanyId(companyId);
			//创建该班次类型的时间
			classesType.setCreateTime(TimeUtil.getCurrentTime());
			
			int addClassesType = classesTypeMapper.insertSelective(classesType);
			
			//TODO 添加班次类型成功====》添加人员班次
			if(addClassesType>0){
				//判断当前时间是不是本月的最后一天
				Calendar calendar = Calendar.getInstance();
				int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
				//初始化需要排班的天数
				int scheduleDays = 0;
				if(TimeUtil.getCurrentMaxDate()==dayOfMonth){
					//是本月的最后一天(则排完下一个整月========》获取下一个月的天数)
					calendar.add(Calendar.MONTH, 1);//切换到下个月
					scheduleDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);//获取下个月的天数
				}else{
					//不是本月的最后一天（先排完当前月，然后再排一个整月）
					int temp = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)-dayOfMonth;//本月剩余天数
					calendar.add(Calendar.MONTH, 1);//切换到下个月
					scheduleDays = temp+calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
					//重新切换回上个月
					calendar.add(Calendar.MONTH, -1);
				}
				
				//TODO ============》添加人员班次
				String nextDayFlag = morrowFlag.toString().trim(); //次日下班标志
				//定义添加班次后的结果
				int addClassesEmp = 0;
				for(int i = 0;i<empArray.size();i++){
					
					//创建Calendar对象
					Calendar cal = Calendar.getInstance();
					
					//班次生效时间为次日生效
					cal.add(Calendar.DAY_OF_MONTH,1); 
					
					JSONObject emp = JSONObject.parseObject(empArray.get(i).toString());
					
					for(int s=0;s<scheduleDays;s++){
						
						//次日下班的时候，上班日期是前一次排班下班的日期
						if(!nextDayFlag.equals("1")){
							cal.add(Calendar.DAY_OF_MONTH,1); 
						}
						
						ClassesEmployee classesEmployee = new ClassesEmployee();
						
						classesEmployee.setId(FormatUtil.createUuid());
						classesEmployee.setEmpId(emp.get("empId").toString());
						classesEmployee.setEmpCompanyId(companyId);
						classesEmployee.setClassesId(typeUUID);
						//设置人员班次上班打卡时间（人员班次从创建班次类型的次日开始排）
						classesEmployee.setOnDutySchedulingDate(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime())+" "+onDutyTime.toString().trim());
						//设置当天的休息时间段：开始时间
						classesEmployee.setRestStartTime(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime())+restStartTime);
						
						//设置上班日期对应的星期
						if(cal.get(Calendar.DAY_OF_WEEK)-1==0){
							classesEmployee.setWeek("7");
						}else{
							classesEmployee.setWeek(String.valueOf(cal.get(Calendar.DAY_OF_WEEK)-1));
						}
						
						//判断是否设置下班时间为次日
						if(nextDayFlag.equals("1")){
							cal.add(Calendar.DAY_OF_MONTH,1);
						}
						//判断休息时间是否跨日
						/*if(){
							
						}*/
						//下班时间
						classesEmployee.setOffDutySchedulingDate(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime())+" "+offDutyTime.toString().trim());
						//
						/*classesEmployee.setRestEndTime(restEndTime);*/
						
						addClassesEmp = classesEmployeeMapper.insertSelective(classesEmployee);
					}
				}
				
				//添加：结果
				if(addClassesType>0 && addClassesEmp>0){
					result = true;
				}else{
					result = false;
				}
			}
			
		}
		return result;
	}

	@Override
	public List<Map> queryAllClassesTypeInfo(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteAppointClassesType(String requestParam, String companyId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Map> queryClassesInfo(String requestParam, String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean oneButtonScheduling(String requestParam, String companyId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addEmpDutyTime(String requestParam, String companyId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteEmpDutyTime(String requestParam, String companyId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void exportRecordToExcel(String requestParam, String excelName, OutputStream out, String companyId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean addNotClockingInEmpInfo(String requestParam, String companyId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean autoScheduling() {
		// TODO Auto-generated method stub
		return false;
	}
}
