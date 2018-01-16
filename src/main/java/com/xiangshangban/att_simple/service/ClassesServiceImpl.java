package com.xiangshangban.att_simple.service;

import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.att_simple.bean.ClassesEmployee;
import com.xiangshangban.att_simple.bean.ClassesType;
import com.xiangshangban.att_simple.bean.Festival;
import com.xiangshangban.att_simple.dao.ClassesEmployeeMapper;
import com.xiangshangban.att_simple.dao.ClassesTypeMapper;
import com.xiangshangban.att_simple.dao.FestivalMapper;
import com.xiangshangban.att_simple.utils.FormatUtil;
import com.xiangshangban.att_simple.utils.TimeUtil;

@Service
@Transactional
public class ClassesServiceImpl implements ClassesService{
	
	@Autowired
	private ClassesTypeMapper classesTypeMapper;
	
	@Autowired
	private ClassesEmployeeMapper classesEmployeeMapper;
	
	@Autowired
	private FestivalMapper festivalMapper;
	
	/**
	 * 添加新的班次类型
	 */
	@Override
	public boolean addNewClassesType(String requestParam, String companyId) {
		//处理请求参数
		JSONObject jsonObject = JSONObject.parseObject(requestParam);
		//获取操作标志
		Object operateFlag = jsonObject.get("classesId");
		
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
		Object validDate = jsonObject.get("validDate");
		
		//声明要返回的数据
		boolean result = false;
		
		if(classesName!=null && onDutyTime!=null && offDutyTime!=null && morrowFlag!=null 
				&& restStartTime!=null && restEndTime!=null && restDays!=null && festivalRestFlag!=null 
				&& signInRule!=null && signOutRule!=null && onPunchCardRule!=null 
				&& offPunchCardRule!=null && employeeIdList!=null && autoClassesFlag!=null){
			
			//判断操作标志是否有数据
			if(operateFlag!=null && !operateFlag.toString().trim().equals("")){
				//先删除，传递上来的班次类型和使用该班次类型的人员班次信息(删除从指定生效日往后的班次)
				Map<String,String> delParam = new HashMap<>();
				delParam.put("classesTypeId",operateFlag.toString().trim());
				delParam.put("companyId", companyId.toString());
				//删除班次类型
				classesTypeMapper.removeAppointClassesType(delParam);
				//删除人员班次
				//设置班次生效时间
				if(validDate==null){
					//默认次日生效
					Calendar.getInstance().add(Calendar.DAY_OF_MONTH,1); 
				}else{
					
				}
			}
			
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
					
					//设置班次生效时间
					if(validDate==null){
						//默认次日生效
						cal.add(Calendar.DAY_OF_MONTH,1); 
					}else{
						try {
							Date parse = new SimpleDateFormat("yyyy-MM-dd").parse(validDate.toString().trim());
							cal.setTime(parse);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					
					
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
						//下班时间
						classesEmployee.setOffDutySchedulingDate(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime())+" "+offDutyTime.toString().trim());
						
						
						String offset = classesEmployee.getOnDutySchedulingDate().split(" ")[0]+" "+restStartTime.toString().trim()+":00";
						String last = classesEmployee.getOnDutySchedulingDate().split(" ")[0]+" "+restEndTime.toString().trim()+":00";
						
						//两个休息时间点，都是小于上班时间点的时候，表明两个休息时间点都是在第二天(休息时间段：下班的日期+时间点)
						if(TimeUtil.compareTime(classesEmployee.getOnDutySchedulingDate()+":00",offset) && 
								TimeUtil.compareTime(classesEmployee.getOnDutySchedulingDate()+":00",last)){
							//设置当天的休息时间段：开始时间
							classesEmployee.setRestStartTime(classesEmployee.getOffDutySchedulingDate().split(" ")[0]+" "+restStartTime.toString().trim());
							classesEmployee.setRestEndTime(classesEmployee.getOffDutySchedulingDate().split(" ")[0]+" "+restEndTime.toString().trim());
						}else if(TimeUtil.compareTime(offset,last)){  //休息时间结束点<休息时间开始点 （表明时间跨天）
							classesEmployee.setRestStartTime(classesEmployee.getOnDutySchedulingDate().split(" ")[0]+" "+restStartTime.toString().trim());
							classesEmployee.setRestEndTime(classesEmployee.getOffDutySchedulingDate().split(" ")[0]+" "+restEndTime.toString().trim());
						}else{
							//默认休息时间点在一天
							classesEmployee.setRestStartTime(classesEmployee.getOnDutySchedulingDate().split(" ")[0]+" "+restStartTime.toString().trim());
							classesEmployee.setRestEndTime(classesEmployee.getOnDutySchedulingDate().split(" ")[0]+" "+restEndTime.toString().trim());
						}
						
						//添加当前日期
						classesEmployee.setTheDate(classesEmployee.getOnDutySchedulingDate().split(" ")[0]);
						
						//TODO 排除休息日
						if(classesType.getRestDays().contains(classesEmployee.getWeek())){
							//当天没有安排班次
							classesEmployee.setClassesId("");
							classesEmployee.setOnDutySchedulingDate("");
							classesEmployee.setOffDutySchedulingDate("");
							classesEmployee.setRestStartTime("");
							classesEmployee.setRestEndTime("");
						}else{
							//TODO 排除法定节假日
							List<Festival> allFestivalInfo = festivalMapper.selectAllFestivalInfo();
							
							for (Festival festival : allFestivalInfo) {
								if(classesEmployee.getOnDutySchedulingDate().split(" ")[0].equals(festival.getFestivalDate())){
									classesEmployee.setClassesId("");
									classesEmployee.setOnDutySchedulingDate("");
									classesEmployee.setOffDutySchedulingDate("");
									classesEmployee.setRestStartTime("");
									classesEmployee.setRestEndTime("");
								}
							}
						}
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
	
	/**
	 * 查询当前公司所有的班次类型（以及使用该班次的人员列表信息）
	 * 以及该班次的详细信息
	 */
	@Override
	public List<Map> queryAllClassesTypeInfo(String companyId) {
		//TODO 当前公司的班次类型
		List<ClassesType> allClassesTypeInfo = classesTypeMapper.selectAllClassesTypeInfo(companyId);
		//定义返回的数据格式
		List<Map> classesTypeList = new ArrayList<>();
		//TODO 获取使用每个班次的人员
		for (ClassesType classesType : allClassesTypeInfo) {
			//查询使用当前班次的人员列表
			Map<String,String> param = new HashMap<String,String>();
			param.put("classesTypeId",classesType.getId());
			param.put("companyId",companyId);
			
			List<Map> classesEmpList = classesEmployeeMapper.selectPointClassesTypeEmp(param);
			
			Map<String,Object> returnData = new HashMap<>();
			returnData.put("classesType",classesType);
			returnData.put("classesEmp",classesEmpList);
			
			classesTypeList.add(returnData);
		}
		return classesTypeList;
	}

	@Override
	public boolean deleteAppointClassesType(String requestParam, String companyId) {
		
		JSONObject parseObject = JSONObject.parseObject(requestParam);
		Object object = parseObject.get("classesTypeId");
		//初始化返回的结果
		boolean result = false;
		if(object!=null){
			Map<String,String> param = new HashMap<>();
			param.put("classesTypeId",object.toString().trim());
			param.put("companyId", companyId);
			
			int removeAppointClassesType = classesTypeMapper.removeAppointClassesType(param);
			
			if(removeAppointClassesType>0){
				result = true;
			}else{
				result = false;
			}
		}
		return result;
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
	public boolean autoScheduling() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ClassesEmployee> queryPointTimeClasses(String empId, String companyId, String startTime,
			String endTime) {
		Map<String,String> param = new HashMap<>();
		param.put("empId", empId.trim());
		param.put("companyId", companyId.trim());
		param.put("startTime", startTime.trim());
		param.put("endTime",endTime.trim());
		
		List<ClassesEmployee> selectPointTimeClasses = classesEmployeeMapper.selectPointTimeClasses(param);
		return selectPointTimeClasses;
	}
}
