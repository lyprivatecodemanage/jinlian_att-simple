package com.xiangshangban.att_simple.service;

import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arjuna.ats.internal.jdbc.drivers.modifiers.list;
import com.xiangshangban.att_simple.bean.ClassesEmpInfo;
import com.xiangshangban.att_simple.bean.ClassesEmployee;
import com.xiangshangban.att_simple.bean.ClassesType;
import com.xiangshangban.att_simple.bean.Festival;
import com.xiangshangban.att_simple.bean.ReturnData;
import com.xiangshangban.att_simple.dao.ClassesEmployeeMapper;
import com.xiangshangban.att_simple.dao.ClassesTypeMapper;
import com.xiangshangban.att_simple.dao.FestivalMapper;
import com.xiangshangban.att_simple.utils.ExportRecordUtil;
import com.xiangshangban.att_simple.utils.FormatUtil;
import com.xiangshangban.att_simple.utils.TimeUtil;

@Service
@Transactional
public class ClassesServiceImpl implements ClassesService {

	@Autowired
	private ClassesTypeMapper classesTypeMapper;

	@Autowired
	private ClassesEmployeeMapper classesEmployeeMapper;

	@Autowired
	private FestivalMapper festivalMapper;

	// 定义访问次数标志
	private static int access_count = 0;
	
	/**
	 * 根据人员ID和公司ID获取人员名称
	 */
	@Override
	public String queryEmpNameById(String empId, String companyId) {
		return classesEmployeeMapper.selectEmpNameById(empId, companyId);
	}
	
	/**
	 * 创建公司的时候，给公司添加默认“常规班”
	 */
	@Override
	public boolean addCompanyDefaultClasses(String companyId) {
		//初始化返回的结果
		boolean result = false;
		ClassesType classesType = new ClassesType();
		classesType.setId(FormatUtil.createUuid());
		classesType.setClassesName("常规班次");
		classesType.setOnDutyTime("09:00");
		classesType.setOffDutyTime("18:00");
		classesType.setMorrowDutyTimeFlag("0");
		classesType.setRestTime("12:00-13:00");
		classesType.setRestDays("67");
		classesType.setFestivalRestFlag("1");
		classesType.setSignInRule("15");
		classesType.setSignOutRule("0");
		classesType.setOnPunchCardTime("180");
		classesType.setOffPunchCardTime("480");
		classesType.setAutoClassesFlag("1");
		classesType.setCompanyId(companyId.trim());
		classesType.setCreateTime(TimeUtil.getCurrentTime());
		classesType.setValidDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime()));
		classesType.setIsDefault("1");
		classesType.setMustAttendanceTime("480");
		int insertSelective = classesTypeMapper.insertSelective(classesType);
		if(insertSelective>0){
			result = true;
		}else{
			result = false;
		}
		return result;
	}
	
	/**
	 * 员工入职的时候，给人员排公司默认“常规班”
	 */
	@Override
	public boolean addDefaultEmpClasses(String companyId,JSONArray empArray) {
		//操作结果
		boolean result = false;
		//设置生效时间
		String validDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime());
		//查询当前公司默认的班次类型
		ClassesType classesType = classesTypeMapper.selectDefaultClassesType(companyId);
		if(classesType!=null){
			int operateResult = commonSchedulingOperate(validDate, empArray, classesType);
			if(operateResult>0){
				result = true;
			}else{
				result = false;
			}
		}
		return result;
	}
	
	/**
	 * 添加新的班次类型
	 */
	@Override
	public boolean addNewClassesType(String requestParam, String companyId) {

		// 处理请求参数
		JSONObject jsonObject = JSONObject.parseObject(requestParam);
		// 获取操作标志
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
		String validDate = (String)jsonObject.get("validDate");
		Object isDefault = jsonObject.get("isDefault");
		String mustAttendanceTime = jsonObject.getString("mustAttendanceTime");
		//自动排班开关
		Object autoScheduledSwitch = jsonObject.get("autoScheduledSwitch");
		//自动排班周期
		Object autoClassesFlag = jsonObject.get("autoClassesFlag");    

		// 声明要返回的数据
		boolean result = false;

		if (classesName != null && onDutyTime != null && offDutyTime != null && morrowFlag != null
				&& restStartTime != null && restEndTime != null && restDays != null && festivalRestFlag != null
				&& signInRule != null && signOutRule != null && onPunchCardRule != null && offPunchCardRule != null
				&& employeeIdList != null && autoClassesFlag != null && isDefault!=null && autoScheduledSwitch!=null) {
						// 获取要排班的人员列表
						JSONArray empArray = JSONArray.parseArray(JSONObject.toJSONString(employeeIdList));
						// 先删除，传递上来的班次类型和使用该班次类型的人员班次信息(删除从指定生效日往后的班次)
						Map<String, String> delParam = new HashMap<>();
						delParam.put("companyId", companyId.toString());
						// 判断是否有操作标志
						if (operateFlag != null && !operateFlag.toString().trim().equals("")) {
							delParam.put("classesTypeId", operateFlag.toString().trim());
							delParam.put("delDate","");
							// TODO 删除班次类型
							classesTypeMapper.removeAppointClassesType(delParam);
							//查询旧的班次类型名称
							ClassesType oldClassesType = classesTypeMapper.selectByPrimaryKey(operateFlag.toString().trim());
							if(oldClassesType!=null){
								if(oldClassesType.getClassesName().equals(classesName.toString().trim())){ //当没有更改班次类别名称的时候，在旧的班次名称后面添加（历史）字段
									//更新旧的班次类别名称
									classesTypeMapper.updateClassesTypeName(operateFlag.toString().trim(),oldClassesType.getClassesName()+"(历史)");
									//将原本使用旧班次类型的人员的班次列表名称进行更新
									classesEmployeeMapper.updateEmpClassesName(operateFlag.toString().trim(),oldClassesType.getClassesName()+"(历史)");
								}
							}
							//TODO 进行更新操作的时候，会出现取消某个人的情况，需要删除【取消该班次类别的人员】，从新的生效时间往后的班次
							//查询使用该班次类别的人员列表
							Map paramMap = new HashMap<>();
							paramMap.put("companyId", companyId);
							paramMap.put("classesTypeId",operateFlag.toString().trim());
							List<Map> selectPointClassesTypeEmp = classesEmployeeMapper.selectPointClassesTypeEmp(paramMap);
							if(selectPointClassesTypeEmp!=null && selectPointClassesTypeEmp.size()>0){
								if(empArray.size()>0){ 
									//判断取消了哪些人
									Object selectEmpId = null;
									//遍历该班次类别所有人员
									outter:
									for (Map map : selectPointClassesTypeEmp) {
										selectEmpId = map.get("emp_id");
										if(selectEmpId!=null && !selectEmpId.toString().trim().isEmpty()){
											for (int f = 0; f < empArray.size(); f++) {
												JSONObject upEmpObject = JSONObject.parseObject(empArray.get(f).toString());
												if(upEmpObject.get("empId").toString().trim().equals(selectEmpId.toString().trim())){ //相等，结束内层循环
													continue outter;
												}
											}
										}
										//删除该人员,新生效日之后所有的班次
										if (validDate != null && !validDate.toString().trim().equals("")) {
											delParam.put("offSetTime", validDate.toString().trim());
										} else {
											Calendar instance = Calendar.getInstance();
											instance.add(Calendar.DAY_OF_MONTH, 1);
											delParam.put("offSetTime", new SimpleDateFormat("yyyy-MM-dd").format(instance.getTime()));
										}
										delParam.put("empId",selectEmpId.toString().trim());
										// TODO 删除当前公司,指定人员,指定班次类别,指定日期之后的排班
										classesEmployeeMapper.deleteAppointClassesTypeEmp(delParam);
									}
								}else{ //取消所有人
									for (Map map : selectPointClassesTypeEmp) {
										Object object = map.get("emp_id");
										if(object!=null && !object.toString().trim().isEmpty()){
											//删除该人员,新生效日之后所有的班次
											if (validDate != null && !validDate.toString().trim().equals("")) {
												delParam.put("offSetTime", validDate.toString().trim());
											} else {
												Calendar instance = Calendar.getInstance();
												instance.add(Calendar.DAY_OF_MONTH, 1);
												delParam.put("offSetTime", new SimpleDateFormat("yyyy-MM-dd").format(instance.getTime()));
											}
											delParam.put("empId",object.toString().trim());
											// TODO 删除当前公司,指定人员,指定班次类别,指定日期之后的排班
											classesEmployeeMapper.deleteAppointClassesTypeEmp(delParam);
										}
									}
								}
							}
						}
						
						// 删除人员班次(删除当前设置的班次生效时间之后的所有班次)
						if (validDate != null && !validDate.toString().trim().equals("")) {
							delParam.put("offSetTime", validDate.toString().trim());
						} else {
							Calendar instance = Calendar.getInstance();
							instance.add(Calendar.DAY_OF_MONTH, 1);
							delParam.put("offSetTime", new SimpleDateFormat("yyyy-MM-dd").format(instance.getTime()));
						}
						//TODO 去除一个人一天多个排班的情况
						for(int g=0;g<empArray.size();g++){
							JSONObject parseObject = JSONObject.parseObject(empArray.get(g).toString());
							delParam.put("empId",parseObject.get("empId").toString().trim());
							delParam.put("classesTypeId",null);
							// TODO 删除当前公司,指定人员,指定日期之后的排班
							classesEmployeeMapper.deleteAppointClassesTypeEmp(delParam);
						}
						
						// TODO ①：添加班次类型
						ClassesType classesType = new ClassesType();
						// 班次类型的UUID
						String typeUUID = FormatUtil.createUuid();
						classesType.setId(typeUUID);
						classesType.setClassesName(classesName.toString().trim());
						classesType.setOnDutyTime(onDutyTime.toString().trim());
						classesType.setOffDutyTime(offDutyTime.toString().trim());
						classesType.setMorrowDutyTimeFlag(morrowFlag.toString().trim());
						classesType.setRestTime(restStartTime.toString().trim() + "-" + restEndTime.toString().trim());
						classesType.setRestDays(restDays.toString().trim());
						classesType.setFestivalRestFlag(festivalRestFlag.toString().trim());
						classesType.setSignInRule(signInRule.toString().trim());
						classesType.setSignOutRule(signOutRule.toString().trim());
						classesType.setOnPunchCardTime(onPunchCardRule.toString().trim());
						classesType.setOffPunchCardTime(offPunchCardRule.toString().trim());
						classesType.setAutoClassesFlag(autoClassesFlag.toString().trim());
						classesType.setCompanyId(companyId);
						classesType.setMustAttendanceTime(mustAttendanceTime);
						// 创建该班次类型的时间
						classesType.setCreateTime(TimeUtil.getCurrentTime());
						
						if(validDate != null && !validDate.toString().trim().equals("")){
							//该班次类型生效的时间
							classesType.setValidDate(validDate.toString().trim());
						}else{
							Calendar myCalendar = Calendar.getInstance();
							myCalendar.add(Calendar.DAY_OF_MONTH, 1);
							//该班次类型生效的时间
							classesType.setValidDate(new SimpleDateFormat("yyyy-MM-dd").format(myCalendar.getTime()));
						}
						classesType.setIsDefault(isDefault.toString().trim());
						classesType.setDelDate("");
						classesType.setAutoScheduledSwitch(autoScheduledSwitch.toString().trim());
						int addClassesType = classesTypeMapper.insertSelective(classesType);
						// TODO 添加班次类型成功====》添加人员班次
						if (addClassesType > 0) {
							int schedulingOperate = commonSchedulingOperate(validDate, empArray, classesType);
							// 添加：结果
							if (addClassesType > 0 && schedulingOperate > 0) {
								result = true;
							} else {
								result = false;
							}
						}
			}
		return result;
	}

	/**
	 * 查询当前公司所有的班次类型ID和Name
	 */
	@Override
	public List<ClassesType> queryAllClassesIdAndName(String companyId) {
		List<ClassesType> selectAllClassesIdAndName = classesTypeMapper.selectAllClassesIdAndName(companyId);
		return selectAllClassesIdAndName;
	}

	/**
	 * 查询指定班次类型详细信息（以及使用该班次的人员列表信息）
	 */
	@Override
	public Map queryPointClassesTypeInfo(String requestParam, String companyId) {
		// 解析请求参数
		JSONObject parseObject = JSONObject.parseObject(requestParam);
		Object classesTypeId = parseObject.get("classesTypeId");
		//定义返回的数据格式
		Map<String, Object> returnData = new HashMap<>();
		
		if (classesTypeId != null && !classesTypeId.toString().trim().isEmpty()) {
			// TODO 当前公司的班次类型
			ClassesType classesTypeInfo = classesTypeMapper.selectPointClassesTypeInfo(classesTypeId.toString().trim());
			if (classesTypeInfo != null) {
				// TODO 获取使用每个班次的人员
				// 查询使用当前班次的人员列表
				Map<String, String> param = new HashMap<String, String>();
				param.put("classesTypeId", classesTypeInfo.getId());
				param.put("companyId", companyId);

				List<Map> classesEmpList = classesEmployeeMapper.selectPointClassesTypeEmp(param);
				
				returnData.put("classesType", classesTypeInfo);
				returnData.put("classesEmp", classesEmpList);
			}
		}
		return returnData;
	}
	
	/**
	 * 查询指定班次类别信息
	 */
	@Override
	public ClassesType queryPointClassesTypeData(String classesTypeId) {
		return classesTypeMapper.selectPointClassesTypeInfo(classesTypeId);
	}

	/**
	 * 删除指定的班次类型
	 * 
	 * @param requestParam
	 * @param companyId
	 * @return
	 */
	@Override
	public boolean deleteAppointClassesType(String requestParam, String companyId) {

		JSONObject parseObject = JSONObject.parseObject(requestParam);
		Object object = parseObject.get("classesTypeId");
		// 初始化返回的结果
		boolean result = false;
		if (object != null) {
			Map<String, String> param = new HashMap<>();
			param.put("classesTypeId", object.toString().trim());
			param.put("companyId", companyId);
			param.put("delDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime()));
			//删除指定的班次类型
			int removeAppointClassesType = classesTypeMapper.removeAppointClassesType(param);
			param.put("offSetTime",new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime()));
			//删除使用该班次类型的人员班次(从指定的时间之后)
			int deleteAppointClassesTypeEmp = classesEmployeeMapper.deleteAppointClassesTypeEmp(param);
			
			if (removeAppointClassesType > 0 && deleteAppointClassesTypeEmp>0) {
				result = true;
			} else {
				result = false;
			}
		}
		return result;
	}

	/**
	 * 查询当前公司人员的班次信息
	 * 
	 * @param requestParam
	 * @param companyId
	 * @return
	 * {
		  "employeeId": null,
		  "data": [
		    {
		      "empId": "XFGCDSDSFSDFSDF46557",
		      "deptName": "测试部",
		      "classesList": [
		        {
		          "classesName": "常白班",
		          "classesId": "806FBB51326441FEB38EADE1F77737AF",
		          "week": "1",
		          "colorFlag": "1",
		          "theDate": "2018-01-22"
		        },
		        {
		          "classesName": "常白班",
		          "classesId": "35A65ACBFF8A43F19017F7463283FEA9",
		          "week": "2",
		          "colorFlag": "1",
		          "theDate": "2018-01-23"
		        },
		        {
		          "classesName": "常白班",
		          "classesId": "0CD0807A73F74A9296C843F4CC9D34D4",
		          "week": "3",
		          "colorFlag": "1",
		          "theDate": "2018-01-24"
		        },
		        {
		          "classesName": "常白班",
		          "classesId": "7B288683611A40A58B34F9F29AA4C6F0",
		          "week": "4",
		          "colorFlag": "1",
		          "theDate": "2018-01-25"
		        },
		        {
		          "classesName": "常白班",
		          "classesId": "91635142846A48F080B473F7C4A22DEB",
		          "week": "5",
		          "colorFlag": "1",
		          "theDate": "2018-01-26"
		        },
		        {
		          "classesName": "",
		          "classesId": "21C93F0C27F54E8CBE6F077214B0FD68",
		          "week": "6",
		          "colorFlag": "",
		          "theDate": "2018-01-27"
		        },
		        {
		          "classesName": "",
		          "classesId": "71E8DCE9171B44489948B0C8F7852C6B",
		          "week": "7",
		          "colorFlag": "",
		          "theDate": "2018-01-28"
		        }
		      ],
		      "empName": "测试unknown",

		      "postName": "测试人员"
		    }
		  ],
		  "totalPages": 0,
		  "message": "请求数据成功",
		  "returnCode": "3000",
		  "pagecountNum": 0,
		  "companyName": null,
		  "classesTopInfo": [
		    {
		      "totalnum": 50,
		      "classes_name": "常白班"
		    }
		  ]
		}
	 */
	@Override
	public ReturnData queryClassesInfo(String requestParam, String companyId) {
		// 解析请求参数
		JSONObject parseObject = JSONObject.parseObject(requestParam);

		Object classesTypeId = parseObject.get("classesTypeId");
		Object deptId = parseObject.get("deptId");
		Object empName = parseObject.get("empName");
		Object perviousWeek = parseObject.get("perviousWeek");
		Object thisWeek = parseObject.get("thisWeek");
		Object nextWeek = parseObject.get("nextWeek");
		Object page = parseObject.get("page");
		Object rows = parseObject.get("rows");

		// 定义返回的结果
		ReturnData resultInfo = new ReturnData();
		// 初始化(数据总行数/数据总页数)
		int totalRows = 0;
		int totalPage = 0;

		Map<String, String> param = new HashMap<>();

		param.put("companyId", companyId.trim());
		param.put("classesTypeId", (classesTypeId != null && !classesTypeId.toString().isEmpty())?classesTypeId.toString().trim() : null);
		param.put("deptId", (deptId != null && !deptId.toString().isEmpty()) ? deptId.toString().trim() : null);
		param.put("empName",(empName != null && !empName.toString().isEmpty()) ? "%" + empName.toString().trim() + "%" : null);

		// 获取当前时间
		Calendar calendar = Calendar.getInstance();
		if (perviousWeek != null && !perviousWeek.toString().trim().isEmpty()) { // 上周
			if (perviousWeek.toString().trim().equals("1")) {
				// 切换到上一周的今天
				calendar.add(Calendar.WEEK_OF_MONTH, -1);
				// 获取上一周周一和周日的日期
				Map<String, String> mondayAndWeekendDate = TimeUtil
						.getMondayAndWeekendDate(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
				param.put("startDate", mondayAndWeekendDate.get("monday"));
				param.put("endDate", mondayAndWeekendDate.get("weekend"));
			}
		}
		if (thisWeek != null && !thisWeek.toString().trim().isEmpty()) { // 本周
			if (thisWeek.toString().trim().equals("1")) {
				// 获取本周周一和周日的日期
				Map<String, String> mondayAndWeekendDate = TimeUtil
						.getMondayAndWeekendDate(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
				param.put("startDate", mondayAndWeekendDate.get("monday"));
				param.put("endDate", mondayAndWeekendDate.get("weekend"));
			}
		}
		if (nextWeek != null && !nextWeek.toString().trim().isEmpty()) { // 下周
			if (nextWeek.toString().trim().equals("1")) {
				// 切换到下一周的今天
				calendar.add(Calendar.WEEK_OF_MONTH, 1);
				// 获取下一周周一和周日的日期
				Map<String, String> mondayAndWeekendDate = TimeUtil
						.getMondayAndWeekendDate(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
				param.put("startDate", mondayAndWeekendDate.get("monday"));
				param.put("endDate", mondayAndWeekendDate.get("weekend"));
			}
		}

		List<Map> selectClassesInfo = classesEmployeeMapper.selectClassesInfo(param);

		if (selectClassesInfo != null && selectClassesInfo.size() > 0) {
			// 根据人员ID将数据进行分组处理
			Map<String, List<Map<String, Object>>> listMap = new HashMap<>();
			for (int i = 0; i < selectClassesInfo.size(); i++) {
				if (listMap.containsKey(selectClassesInfo.get(i).get("emp_id"))) {// 存在该map
					Map<String, Object> map = new HashMap();
					// 数据分组处理
					dataDiviceGroup(map, selectClassesInfo, i);
					// 获取到该list，向其中添加map数据
					listMap.get(selectClassesInfo.get(i).get("emp_id").toString()).add(map);
				} else {
					Map<String, Object> map = new HashMap();
					// 数据分组处理
					dataDiviceGroup(map, selectClassesInfo, i);
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					list.add(map);
					listMap.put(selectClassesInfo.get(i).get("emp_id").toString(), list);
				}
			}
			// 精简数据(改变数据结构)
			List<Map<String, Object>> realData = new ArrayList<>();
			for (String key : listMap.keySet()) {
				Map<String, Object> outterMap = new HashMap<>();
				outterMap.put("empId", listMap.get(key).get(0).get("empId"));
				outterMap.put("empName", listMap.get(key).get(0).get("empName"));
				outterMap.put("postName", listMap.get(key).get(0).get("postName"));
				outterMap.put("deptName", listMap.get(key).get(0).get("deptName"));
				outterMap.put("postName", listMap.get(key).get(0).get("postName"));

				// 初始化本周工时
				float theWeekhour = 0;

				List<Map<String, Object>> innerList = new ArrayList<>();
				for (int s = 0; s < listMap.get(key).size(); s++) {
					Map<String, Object> innerMap = new HashMap<>();
					innerMap.put("theDate", listMap.get(key).get(s).get("theDate"));
					innerMap.put("week", listMap.get(key).get(s).get("week"));
					innerMap.put("classesId", listMap.get(key).get(s).get("classesId"));
					innerMap.put("classesTypeId",listMap.get(key).get(s).get("classesTypeId"));
					innerMap.put("classesName", listMap.get(key).get(s).get("classesName"));
					innerMap.put("colorFlag", listMap.get(key).get(s).get("colorFlag"));
					
					if(listMap.get(key).get(s).get("onDutySchedulingDate")!=null 
							&& !listMap.get(key).get(s).get("onDutySchedulingDate").toString().trim().isEmpty()){
						innerMap.put("onDutyTime",listMap.get(key).get(s).get("onDutySchedulingDate").toString().split(" ")[1]);
						innerMap.put("offDutyTime",listMap.get(key).get(s).get("offDutySchedulingDate").toString().split(" ")[1]);
					}else{
						innerMap.put("onDutyTime","");
						innerMap.put("offDutyTime","");
					}
					// 计算当天的工时
					String onDutyTime = listMap.get(key).get(s).get("onDutySchedulingDate").toString();
					String offDutyTime = listMap.get(key).get(s).get("offDutySchedulingDate").toString();
					String restStartTime = listMap.get(key).get(s).get("restStartTime").toString();
					String restEndTime = listMap.get(key).get(s).get("restEndTime").toString();

					if (!onDutyTime.isEmpty() && !offDutyTime.isEmpty() && !restStartTime.isEmpty()
							&& !restEndTime.isEmpty()) {
						// 返回两个时间相差的分钟数
						float dutyTimeLength = Float.valueOf(
								String.valueOf(TimeUtil.minuteOfTime(offDutyTime + ":00", onDutyTime + ":00"))) / 60;

						float restTimtLength = Float.valueOf(
								String.valueOf(TimeUtil.minuteOfTime(restEndTime + ":00", restStartTime + ":00"))) / 60;

						theWeekhour += (dutyTimeLength - restTimtLength);
					}
					innerList.add(innerMap);
				}
				
				// 将数据完善为一整个周的数据,缺少哪天补哪天(避免有的班次没有排，造成前端列表展示困难)
				List<Map<String,String>> emptyDateClasses = new ArrayList<>();
				if (innerList.size() < 7) {
					outter:
					for(int b = 0;b<7;b++){
						Object selectTheDate = null;
						for (Map<String, Object> map : innerList) {
							selectTheDate = map.get("theDate");
							Object selectWeek = map.get("week");
							if(selectWeek.toString().trim().equals(""+(b+1))){
								continue outter;
							}
						}
						//保存没有数据的日期
						Map<String,String> innerMap = new HashMap<>();
						innerMap.put("theDate",selectTheDate.toString().trim());
						innerMap.put("theWeek",""+(b+1));
						
						emptyDateClasses.add(innerMap);
					}
				
					//给没有数据的星期补充数据
					for (Map<String, String> map : emptyDateClasses) {
						Map<String, Object> emptyMap = new HashMap<>();
						emptyMap.put("classesName", "");
						emptyMap.put("classesId", "");
						emptyMap.put("classesTypeId","");
						emptyMap.put("colorFlag", "");
						emptyMap.put("onDutyTime","");
						emptyMap.put("offDutyTime","");
						emptyMap.put("week",map.get("theWeek"));
						//设置当前周，指定星期对应日期
						emptyMap.put("theDate",TimeUtil.getPointWeekDate(map.get("theDate"),Integer.parseInt(map.get("theWeek"))));
						innerList.add(Integer.parseInt(map.get("theWeek"))-1,emptyMap);
					}
				}
				
				//遍历innerList,判断该天班次是不是历史班次(历史班次不可操作)
				for (Map<String, Object> map : innerList) {
					Object classesId = map.get("classesTypeId");
					if(classesId!=null && !classesId.toString().trim().isEmpty()){
						//根据班次类型id查询班次类别详细信息
						ClassesType selectByPrimaryKey = classesTypeMapper.selectByPrimaryKey(classesId.toString().trim());
						if(selectByPrimaryKey!=null){
							String delDate = selectByPrimaryKey.getDelDate();
							if(delDate!=null && !delDate.isEmpty()){ //该班次类别已经删除
								//判断当前班次的时间是不是位于班次类别删除日之前
								Object theDate = map.get("theDate");
								if(TimeUtil.compareTime(delDate+" "+"00:00:00",theDate.toString().trim()+" "+"00:00:00")){ //删除班次类别时间，大于当前班次日期（历史）
									map.put("isHistory","1");
								}else{
									map.put("isHistory","0");
								}
							}else{ //该班次类别没有进行删除
								map.put("isHistory","0");
							}
						}
					}else{
						map.put("isHistory","0");
					}
				}
				outterMap.put("classesList", innerList);
				// 添加本周工时到集合中   
				outterMap.put("thisWeekHours", String.valueOf(theWeekhour));
				realData.add(outterMap);
			}
			// 最终数据
			List<Map<String, Object>> finallyData = new ArrayList<>();
			if ((rows != null && !rows.toString().trim().isEmpty())
					&& (page != null && !page.toString().trim().isEmpty())) {
				if (realData != null && realData.size() > 0) {
					int pageIndex = Integer.parseInt(page.toString());
					int pageSize = Integer.parseInt(rows.toString());

					for (int i = ((pageIndex - 1) * pageSize); i < (pageSize * pageIndex); i++) {
						if (i == realData.size()) {
							break;
						}
						finallyData.add(realData.get(i));
					}
					// 获取数据的总行数
					totalRows = realData.size();
					// 设置总页数
					totalPage = totalRows % Integer.parseInt(rows.toString().trim()) == 0? totalRows / Integer.parseInt(rows.toString().trim()): totalRows / Integer.parseInt(rows.toString().trim()) + 1;
				}
				resultInfo.setData(commonSortForClassesEmpInfo(finallyData));
			} else {
				resultInfo.setData(commonSortForClassesEmpInfo(realData));
			}
		} else {
			//没有查询到数据，但是要返回日期
			Map<String,Object> returnMap = new HashMap<>();
			returnMap.put("empId","");
			returnMap.put("deptName","");
			returnMap.put("empName","");
			returnMap.put("postName","");
			returnMap.put("thisWeekHours","");
			//获取周一的日期
			String monday = param.get("startDate").toString();
			Calendar operateCal = Calendar.getInstance();
			try {
				operateCal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(monday));
				//时间减一天
				operateCal.add(Calendar.DAY_OF_MONTH, -1);
				List<Map<String,Object>> myListMap = new ArrayList<>();
				for(int x=0;x<7;x++){
					//日期增加一天
					operateCal.add(Calendar.DAY_OF_MONTH, +1);
					//获取星期
					String Myweek = "";
					// 获取星期数
					if (operateCal.get(Calendar.DAY_OF_WEEK) - 1 == 0) {
						Myweek = "7";
					} else {
						Myweek = String.valueOf(operateCal.get(Calendar.DAY_OF_WEEK) - 1);
					}
					//获取日期
					String myDate = new SimpleDateFormat("yyyy-MM-dd").format(operateCal.getTime());
					Map<String,Object> myInnerMap = new HashMap<>();
					myInnerMap.put("classesName", "");
					myInnerMap.put("classesId", "");
					myInnerMap.put("week",Myweek);
					myInnerMap.put("colorFlag", "");
					myInnerMap.put("theDate",myDate);
					myListMap.add(myInnerMap);
				}
				returnMap.put("classesList",myListMap);
				selectClassesInfo.add(returnMap);
				resultInfo.setData(commonSortForClassesEmpInfo(selectClassesInfo));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		// 查询班次类型使用人数排行榜前三名
		List<Map<String, String>> selectTopThreeClassesType = classesEmployeeMapper.selectTopThreeClassesType(companyId.trim());
		//测试班次使用人数排行榜信息
		/*for (Map<String, String> map : selectTopThreeClassesType) {
			System.out.println("=========="+map.get("classes_name")+"####"+map.get("totalnum").toString()+"==========");
		}*/
		resultInfo.setClassesTopInfo(selectTopThreeClassesType);
		resultInfo.setMessage("请求数据成功");
		resultInfo.setReturnCode("3000");
		resultInfo.setPagecountNum(String.valueOf(totalPage));
		resultInfo.setTotalPages(String.valueOf(totalRows));
		
		return resultInfo;
	}

	/**
	 * 一键排班
	 * 
	 * @param companyId
	 *            公司ID
	 * @return
	 */
	@Override
	public boolean oneButtonScheduling(String companyId) {
		// 查询当前公司已经排过班的所有人员信息(empId、lastDate<最后一次班次的时间>、)
		List<Map> selectAllClassesEmp = classesEmployeeMapper.selectAllClassesEmp(companyId);
		// 初始化要返回的结果
		int result = 0;
		if (selectAllClassesEmp != null && selectAllClassesEmp.size() > 0) {
			// 遍历人员信息
			for (int r = 0; r < selectAllClassesEmp.size(); r++) {
				Object empIdObj = selectAllClassesEmp.get(r).get("emp_id");
				Object lastDateObj = selectAllClassesEmp.get(r).get("last_date");
				Object classesIdObj = selectAllClassesEmp.get(r).get("classes_id");
				
				if((empIdObj!=null && StringUtils.isNotEmpty(empIdObj.toString().trim())) 
						&& (lastDateObj!=null && StringUtils.isNotEmpty(lastDateObj.toString().trim())) 
						&& (lastDateObj!=null && StringUtils.isNotEmpty(lastDateObj.toString().trim()))){
					String empId = empIdObj.toString().trim();
					String lastDate = (String)lastDateObj.toString().trim();
					// 最后的排班日期增加一为生效时间
					Date parse;
					try {
							String classesTypeId = classesIdObj.toString().trim();
							ClassesType classesType = new ClassesType();
							if(StringUtils.isNotEmpty(classesTypeId)){
								// 根据班次类型ID查询，班次类型详细信息
								classesType = classesTypeMapper.selectByPrimaryKey(classesTypeId);
								parse = new SimpleDateFormat("yyyy-MM-dd").parse(lastDate);
								Calendar calendar = Calendar.getInstance();
								calendar.setTime(parse);
								calendar.add(Calendar.DAY_OF_MONTH, +1);
								String validDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
								if (classesType != null) {
									Map<String, String> param = new HashMap<>();
									param.put("empId", empId);
									List<Map<String, String>> listMap = new ArrayList<>();
									listMap.add(param);
									JSONArray empArray = JSONArray.parseArray(JSONArray.toJSONString(listMap));
									// TODO 执行排班操作
									result = commonSchedulingOperate(validDate, empArray, classesType);
								}
							}
						} catch (ParseException e) {
							e.printStackTrace();
					    }
				}
			}
		}
		if(result>0){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 查询指定公司一周之内“一键排班”的次数
	 */
	@Override
	public int queryOneKeyAccessCount(String monday, String weekend, String companyId) {
		int accessCount = classesEmployeeMapper.selectOneKeyAccessCount(monday, weekend, companyId);
		return accessCount;
	}
	
	
	/**
	 * 给指定人员添加指定日期的班次（添加临时班次）
	 * 
	 * @param requestParam
	 * @param companyId
	 * @return
	 */
	@Override
	public boolean addEmpDutyTime(String requestParam, String companyId) {
		// 解析请求参数
		JSONObject parseObject = JSONObject.parseObject(requestParam);
		Object empId = parseObject.get("empId");
		Object onDutyTime = parseObject.get("onDutyTime");
		Object offDutyTime = parseObject.get("offDutyTime");
		Object restStartTime = parseObject.get("restStartTime");
		Object restEndTime = parseObject.get("restEndTime");
		Object signInRule = parseObject.get("signInRule");
		Object signOutRule = parseObject.get("signOutRule");
		Object onPunchCardRule = parseObject.get("onPunchCardRule");
		Object offPunchCardRule = parseObject.get("offPunchCardRule");

		Object empClassesId = parseObject.get("empClassesId");
		Object pointDate = parseObject.get("pointDate");
		Object pointWeek = parseObject.get("pointWeek");

		// 定义返回的结果
		boolean result = false;

		if (empId != null && pointDate != null && onDutyTime != null && offDutyTime != null && restStartTime != null
				&& restEndTime != null && signInRule != null && signOutRule != null && onPunchCardRule != null
				&& offPunchCardRule != null && pointWeek != null) {

			// 初始化执行结果
			int executeResult = 0;

			ClassesEmployee classesEmployee = new ClassesEmployee();

			classesEmployee.setClassesId(FormatUtil.createUuid());
			classesEmployee.setClassesName("临时班次");
			classesEmployee.setEmpId(empId.toString().trim());
			classesEmployee.setEmpCompanyId(companyId.trim());
			classesEmployee.setTheDate(pointDate.toString().trim());
			classesEmployee.setWeek(pointWeek.toString().trim());
			classesEmployee.setOnDutySchedulingDate(pointDate.toString().trim() + " " + onDutyTime.toString().trim());
			classesEmployee.setOffDutySchedulingDate(pointDate.toString().trim() + " " + offDutyTime.toString().trim());
			classesEmployee.setRestStartTime(pointDate.toString().trim() + " " + restStartTime.toString().trim());
			classesEmployee.setRestEndTime(pointDate.toString().trim() + " " + restEndTime.toString().trim());
			classesEmployee.setSignInRule(signInRule.toString().trim());
			classesEmployee.setSignOutRule(signOutRule.toString().trim());
			classesEmployee.setOnPunchCardRule(onPunchCardRule.toString().trim());
			classesEmployee.setOffPunchCardRule(offPunchCardRule.toString().trim());
			classesEmployee.setDivideColor("3");

			// 根据有无empClassesId（人员单天班次ID）判断，是休息日没有排上下班时间，还是压根就不存在该次排班
			if (empClassesId != null && !empClassesId.toString().trim().isEmpty()) {
				// 为班次添加上下班时间和休息时间
				classesEmployee.setId(empClassesId.toString().trim());
				executeResult = classesEmployeeMapper.updateAppointEmpDateClasses(classesEmployee);
			} else {
				// 添加新的班次
				classesEmployee.setId(FormatUtil.createUuid());
				executeResult = classesEmployeeMapper.insertSelective(classesEmployee);
			}

			if (executeResult > 0) {
				result = true;
			} else {
				result = true;
			}
		}
		return result;
	}

	/**
	 * 删除指定班次
	 * 
	 * @param requestParam
	 * @return
	 */
	@Override
	public boolean deleteEmpDutyTime(String requestParam) {
		// 解析请求参数
		JSONObject parseObject = JSONObject.parseObject(requestParam);
		Object empClassesId = parseObject.get("empClassesId");
		// 定义返回的结果
		boolean result = false;
		if (empClassesId != null) {
			int deleteAppointEmpDateClasses = classesEmployeeMapper.deleteAppointEmpDateClasses(empClassesId.toString().trim());
			if (deleteAppointEmpDateClasses > 0) {
				result = true;
			} else {
				result = true;
			}
		}
		return result;
	}

	/**
	 * 导出班次信息
	 * 
	 * @param requestParam
	 *            查询条件
	 * @param excelName
	 *            导出的excel表格名称
	 * @param out
	 *            返回给前端的输出流
	 * @param companyId
	 *            公司ID
	 */
	@Override
	public void exportRecordToExcel(String requestParam, String excelName, OutputStream out, String companyId) {
		// 解析请求参数
		com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(requestParam);
		Object flag = jsonObject.get("flag");
		if (flag != null && !flag.toString().trim().isEmpty()) {
			int value = Integer.parseInt(flag.toString().trim());
			switch (value) {
			case 0: // 条件查询公司人员班次
				ReturnData queryClassesInfo = queryClassesInfo(requestParam, companyId);
				List<Map> empClassesInfo = (List<Map>) queryClassesInfo.getData();
				// 定义表格头
				String[] empClassesHeaders = new String[11];
				empClassesHeaders[0] = "姓名";
				empClassesHeaders[1] = "岗位名称";
				empClassesHeaders[2] = "部门名称";
				empClassesHeaders[10] = "本周工时";
				if (empClassesInfo != null && empClassesInfo.size() > 0) {
					// 获取班次集合
					Object object = empClassesInfo.get(0).get("classesList");
					JSONArray parseArray = JSONArray.parseArray(JSONArray.toJSONString(object));
					String[] weekArr = { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" };
					for (int m = 0; m < parseArray.size(); m++) {
						JSONObject parseObject = JSONObject.parseObject(parseArray.get(m).toString());
						String theDate = parseObject.get("theDate").toString();
						empClassesHeaders[m + 3] = theDate + "(" + weekArr[m] + ")";
					}
				}
				// 导出操作日志
				ExportRecordUtil.exportAnyRecordToExcel(empClassesInfo, excelName, empClassesHeaders, out, value);
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 自动排班
	 * 
	 * @return
	 */
	@Override
	public boolean autoScheduling() {
		// 初始化要返回的结果
		int result = 0;
		//查询所有有班次类别的公司
		List<String> selectALLCompany = classesTypeMapper.selectALLCompany();
		if(selectALLCompany!=null && selectALLCompany.size()>0){
			for (String companyId : selectALLCompany) {
				if(companyId!=null && !companyId.isEmpty()){
					// 查询所有公司已经排班的人员信息
					List<Map> selectAllClassesEmp = classesEmployeeMapper.selectAllClassesEmp(companyId);
					if (selectAllClassesEmp != null && selectAllClassesEmp.size() > 0) {
						// 遍历人员信息
						for (int r = 0; r < selectAllClassesEmp.size(); r++) {
							// 人员ID
							String empId = selectAllClassesEmp.get(r).get("emp_id").toString().trim();
							// 方便调用公共排班方法，将人员ID转换为JSONArray对象
							Map<String, String> param = new HashMap<>();
							param.put("empId", empId);
							List<Map> listMap = new ArrayList<>();
							listMap.add(param);
							JSONArray empArray = JSONArray.parseArray(JSONArray.toJSONString(listMap));
							// 班次类型ID
							String classesTypeId = selectAllClassesEmp.get(r).get("classes_id").toString().trim();
							// 根据班次类型ID查询，班次类型详细信息
							ClassesType myclassesType = classesTypeMapper.selectByPrimaryKey(classesTypeId);
							if (myclassesType != null) {
								//判断当前班次类别是否开启自动排班
								String autoScheduledSwitch = myclassesType.getAutoScheduledSwitch();
								if(autoScheduledSwitch!=null && !autoScheduledSwitch.isEmpty() ){
									if(autoScheduledSwitch.equals("1")){ //开启自动排班
										// 获取该班次类别生效时间
										String validDate = myclassesType.getValidDate();
										//初始化时间间隔变量
										int dateInterval = 0;
										if (validDate != null && !validDate.isEmpty()) {
											// 判断班次类型生效时间到当前时间的时间间隔
											dateInterval = TimeUtil.dayOfDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime()), validDate+" "+"00:00:00");
										}
										// 获取班次类型自动更新的周期
										String classesCycle = myclassesType.getAutoClassesFlag();
										if(classesCycle!=null && !classesCycle.isEmpty()){
											if (dateInterval > 0) {
												if (classesCycle.equals("1")) { // 周期是一个月(30天),每隔30天执行一次
													// 判断周期是否到来
													if ((dateInterval % 30) == 0) {
														result = commonOperateForAutoScheduling(myclassesType, empArray);
													}
												} else if (classesCycle.equals("2")) { // 周期是一个季度(90天),每隔90天执行一次
													// 判断周期是否到来（生效时间为当天）
													if ((dateInterval % 90) == 0) {
														result = commonOperateForAutoScheduling(myclassesType, empArray);
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 查询指定公司人员，指定时间区间的班次信息
	 * 
	 * @param empId
	 * @param companyId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public List<ClassesEmployee> queryPointTimeClasses(String empId, String companyId, String startTime,
			String endTime) {
		List<ClassesEmployee> selectPointTimeClasses = new ArrayList<>();
		if (empId != null && !empId.isEmpty() && companyId != null && !companyId.isEmpty() && startTime != null
				&& !startTime.isEmpty()) {
			Map<String, String> param = new HashMap<>();
			param.put("empId", empId.trim());
			param.put("companyId", companyId.trim());
			param.put("startTime", startTime.trim());
			param.put("endTime", endTime.trim());

			selectPointTimeClasses = classesEmployeeMapper.selectPointTimeClasses(param);
		}
		return selectPointTimeClasses;
	}

	/**
	 * 查询当前公司指定人员指定日期的班次信息
	 * 
	 * @param empClassesId
	 *            人员单天班次ID
	 * @return
	 */
	@Override
	public Map queryPointEmpDateClasses(String requestParam) {
		// 解析请求参数
		JSONObject parse = JSONObject.parseObject(requestParam);
		Object empClassesId = parse.get("empClassesId");
		// 定义返回的结果
		Map<String, String> result = new HashMap<>();

		if (empClassesId != null && !empClassesId.toString().trim().isEmpty()) {

			ClassesEmployee selectPointEmpDateClasses = classesEmployeeMapper.selectPointEmpDateClasses(empClassesId.toString().trim());

			if (selectPointEmpDateClasses != null) {
				result.put("classesDate",(selectPointEmpDateClasses.getOnDutySchedulingDate() != null && !selectPointEmpDateClasses.getOnDutySchedulingDate().isEmpty())? selectPointEmpDateClasses.getOnDutySchedulingDate().split(" ")[0] : "");
				result.put("classesWeek",selectPointEmpDateClasses.getWeek() != null ? selectPointEmpDateClasses.getWeek() : "");
				result.put("onDutyTime",(selectPointEmpDateClasses.getOnDutySchedulingDate() != null && !selectPointEmpDateClasses.getOnDutySchedulingDate().isEmpty())? selectPointEmpDateClasses.getOnDutySchedulingDate().split(" ")[1] : "");
				result.put("offDutyTime",(selectPointEmpDateClasses.getOffDutySchedulingDate() != null && !selectPointEmpDateClasses.getOffDutySchedulingDate().isEmpty())? selectPointEmpDateClasses.getOffDutySchedulingDate().split(" ")[1] : "");
				result.put("restStartTime",(selectPointEmpDateClasses.getRestStartTime() != null && !selectPointEmpDateClasses.getRestStartTime().isEmpty())? selectPointEmpDateClasses.getRestStartTime().split(" ")[1] : "");
				result.put("restEndTime",(selectPointEmpDateClasses.getRestEndTime() != null&& !selectPointEmpDateClasses.getRestEndTime().isEmpty())? selectPointEmpDateClasses.getRestEndTime().split(" ")[1] : "");
			}
		}
		return result;
	}

	/********************************************************************************
	 * 
	 * =========================公共方法区===========================================
	 * ==
	 * 
	 ********************************************************************************/

	/**
	 * 计算排班天数
	 * 
	 * @param date
	 *            指定的班次生效时间（为null的时候，表示次日生效）
	 * @return
	 */
	public int getScheduleDays(Date date) {
		int count = 0;
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
			// 下方代码适用的是次日生效，而所以用户传入的时间就是成效时间，所以要减去一天，才能适用下方的代码
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		if (TimeUtil.getCurrentMaxDate() == dayOfMonth) { // 是本月的最后一天(则排完下一个整月========》获取下一个月的天数)

			calendar.add(Calendar.MONTH, 1);// 切换到下个月
			count = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);// 获取下个月的天数

		} else {// 不是本月的最后一天（先排完当前月，然后再排一个整月）

			int temp = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - dayOfMonth;// 本月剩余天数
			calendar.add(Calendar.MONTH, 1);// 切换到下个月
			count = temp + calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			// 重新切换回上个月
			calendar.add(Calendar.MONTH, -1);
		}
		return count;
	}

	/**
	 * 排班通用方法
	 * 
	 * @param validDate
	 *            生效时间 (传递null或者“”表示次日生效)
	 * @param empArray
	 *            要排班的人员集合
	 * @param classesType
	 *            班次类型bean
	 * @return
	 */
	public int commonSchedulingOperate(String validDate, JSONArray empArray, ClassesType classesType) {
		// 初始化添加班次结果变量
		int result = 0;
		// 根据访问次数，来确定排班修改的次数（每修改一次，改变一次颜色）
		access_count++;
		if (access_count == 3) {
			access_count = 1;
		}
		// 初始化需要排班的天数
		int scheduleDays = 0;

		if (validDate != null && !validDate.toString().trim().equals("")) {
			// --------------班次生效时间由用户决定(计算要排班的天数)-------------------
			try {
				// 解析用户传递的生效时间
				Date parseDate = new SimpleDateFormat("yyyy-MM-dd").parse(validDate.toString().trim());
				scheduleDays = getScheduleDays(parseDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			// -----------------默认班次次日生效(计算要排班的天数)-----------------------
			scheduleDays = getScheduleDays(null);
		}
		// TODO ============》添加人员班次
		String nextDayFlag = classesType.getMorrowDutyTimeFlag(); // 次日下班标志
		if(empArray.size()==0){
			//更新班次类别的时候，允许人员集合为null
			result = 1;
		}else{ //新增班次类别的时候不允许人员集合为null
			for (int i = 0; i < empArray.size(); i++) {
				// 创建Calendar对象
				Calendar cal = Calendar.getInstance();
				// 设置班次生效时间
				if (validDate != null && !validDate.toString().trim().equals("")) {
					try {
						Date parse = new SimpleDateFormat("yyyy-MM-dd").parse(validDate.toString().trim());
						cal.setTime(parse);
						if (!nextDayFlag.equals("1")) {
							cal.add(Calendar.DAY_OF_MONTH, -1);
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				JSONObject emp = JSONObject.parseObject(empArray.get(i).toString());
				// TODO 做x休x模式，获取工作天数和休息天数(限制for循环的执行次数)
				int workDaysCount = 0;
				int restDaysCount = 0;
				// 初始化工作天数限制变量、休息天数限制变量
				int workDaysLimit = 0;
				int restDaysLimit = 0;
				if (classesType.getRestDays().contains(",")) { // 做x休x模式
					// 获取工作天数
					workDaysCount = Integer.parseInt(classesType.getRestDays().trim().split(",")[0]);
					// 获取休息的天数
					restDaysCount = Integer.parseInt(classesType.getRestDays().trim().split(",")[1]);
				}
				for (int s = 0; s < scheduleDays; s++) {
					// 没有指定生效时间的时候，出现跨日的情况，第一次的排版要添加一天（从次日开始生效）、指定生效时间的时候，指定哪一天就是哪一天生效
					if (validDate == null || validDate.toString().trim().equals("")) {
						if (s == 0 && nextDayFlag.equals("1")) {
							cal.add(Calendar.DAY_OF_MONTH, 1);
						}
					}
					// 不跨天的时候，天数一天天递增
					if (!nextDayFlag.equals("1")) {
						cal.add(Calendar.DAY_OF_MONTH, 1);
					}

					ClassesEmployee classesEmployee = new ClassesEmployee();
					classesEmployee.setId(FormatUtil.createUuid());
					classesEmployee.setEmpId(emp.get("empId").toString());
					classesEmployee.setEmpCompanyId(classesType.getCompanyId());
					classesEmployee.setClassesId(classesType.getId());
					classesEmployee.setClassesName(classesType.getClassesName());
					// 设置人员班次上班打卡时间（人员班次从创建班次类型的次日开始排）
					classesEmployee.setOnDutySchedulingDate(
							new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()) + " " + classesType.getOnDutyTime());
					// 设置上班日期对应的星期
					if (cal.get(Calendar.DAY_OF_WEEK) - 1 == 0) {
						classesEmployee.setWeek("7");
					} else {
						classesEmployee.setWeek(String.valueOf(cal.get(Calendar.DAY_OF_WEEK) - 1));
					}

					// 下班时间为次日（需要再递增一天，以此来设置下班时间）
					if (nextDayFlag.equals("1")) {
						cal.add(Calendar.DAY_OF_MONTH, 1);
					}
					// 下班时间
					classesEmployee.setOffDutySchedulingDate(
							new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()) + " " + classesType.getOffDutyTime());
					String offset = classesEmployee.getOnDutySchedulingDate().split(" ")[0] + " "+ classesType.getRestTime().split("-")[0] + ":00";
					String last = classesEmployee.getOnDutySchedulingDate().split(" ")[0] + " "+ classesType.getRestTime().split("-")[1] + ":00";
					// 两个休息时间点，都是小于上班时间点的时候，表明两个休息时间点都是在第二天(休息时间段：下班的日期+时间点)
					if (TimeUtil.compareTime(classesEmployee.getOnDutySchedulingDate() + ":00", offset)
							&& TimeUtil.compareTime(classesEmployee.getOnDutySchedulingDate() + ":00", last)) {
						// 设置当天的休息时间段：开始时间
						classesEmployee.setRestStartTime(classesEmployee.getOffDutySchedulingDate().split(" ")[0] + " "+ classesType.getRestTime().split("-")[0]);
						classesEmployee.setRestEndTime(classesEmployee.getOffDutySchedulingDate().split(" ")[0] + " "+ classesType.getRestTime().split("-")[1]);
					} else if (TimeUtil.compareTime(offset, last)) { // 休息时间结束点<休息时间开始点
						// （表明时间跨天）
						classesEmployee.setRestStartTime(classesEmployee.getOnDutySchedulingDate().split(" ")[0] + " "+ classesType.getRestTime().split("-")[0]);
						classesEmployee.setRestEndTime(classesEmployee.getOffDutySchedulingDate().split(" ")[0] + " "+ classesType.getRestTime().split("-")[1]);
					} else {
						// 默认休息时间点在一天
						classesEmployee.setRestStartTime(classesEmployee.getOnDutySchedulingDate().split(" ")[0] + " "+ classesType.getRestTime().split("-")[0]);
						classesEmployee.setRestEndTime(classesEmployee.getOnDutySchedulingDate().split(" ")[0] + " "+ classesType.getRestTime().split("-")[1]);
					}
					// 设置签到签退规则
					classesEmployee.setSignInRule(classesType.getSignInRule());
					classesEmployee.setSignOutRule(classesType.getSignOutRule());
					// 设置打卡规则
					classesEmployee.setOnPunchCardRule(classesType.getOnPunchCardTime());
					classesEmployee.setOffPunchCardRule(classesType.getOffPunchCardTime());
					// 添加当前日期
					classesEmployee.setTheDate(classesEmployee.getOnDutySchedulingDate().split(" ")[0]);
					// 设置分隔颜色
					classesEmployee.setDivideColor(String.valueOf(access_count));
					
					//判断当前的班次类别是否开启“法定节假日休息的按钮”
					if(classesType.getFestivalRestFlag().equals("1")){ //开启法定节假日休息（要对：法定休息日，法定工作日，法定休息日进行限定）
						//TODO ##################################################################
						//		下方操作：对【一周的休息日、法定节假日、法定休息日、工作日】进行判断，限制班次的添加
						//TODO ##################################################################
						//查询所有的:法定节假日、法定休息日、工作日信息
						List<Festival> allFestivalInfo = festivalMapper.selectAllFestivalInfo();
						//初始化日期类型【法定节假日、法定休息日、工作日】变量
						String dateType = "";
						for (Festival festival : allFestivalInfo) {
							if (!classesEmployee.getOnDutySchedulingDate().equals("")) {
								if (classesEmployee.getOnDutySchedulingDate().split(" ")[0].equals(festival.getFestivalDate())) {
									//获取该排班日期的类型
									dateType = festival.getWorkType();
									break;
								}
							}
						}
						if(dateType.equals("")){ //=====>该排班日期，不是【法定节假日、法定休息日、工作日】
							if (classesType.getRestDays().contains(",")) { // 做x休x模式
								workDaysLimit++;
								if (workDaysLimit > workDaysCount) {
									restDaysLimit++;
									if (restDaysLimit <= restDaysCount) {
										// 设置休息日的班次
										setRestDaysClasses(classesEmployee);
									} else {
										// 本循环周期结束，重新设置循环次数
										// (workDaysLimit设置为0的时候将会，除第一轮外<第一轮是从workDaysLimit++后，也就是=1才开始的>，其它轮都会多一次)
										workDaysLimit = 1;
										restDaysLimit = 0;
									}
								}
							} else if (classesType.getRestDays().contains(classesEmployee.getWeek())) { // 周循环的模式(排除一周的休息日)
								// 设置休息日的班次
								setRestDaysClasses(classesEmployee);
							}
						}else if(dateType.equals("0")){ //==============>法定节假日（不进行排班）
							// 设置休息日的班次
							setRestDaysClasses(classesEmployee);
						}else if(dateType.equals("1")){ //==============>法定休息日（不进行排班）
							// 设置休息日的班次
							setRestDaysClasses(classesEmployee);
						}
					}else{ //未开启法定节假日休息（按照正常流程排班）
						if (classesType.getRestDays().contains(",")) { // 做x休x模式
							workDaysLimit++;
							if (workDaysLimit > workDaysCount) {
								restDaysLimit++;
								if (restDaysLimit <= restDaysCount) {
									// 设置休息日的班次
									setRestDaysClasses(classesEmployee);
								} else {
									// 本循环周期结束，重新设置循环次数
									// (workDaysLimit设置为0的时候将会，除第一轮外<第一轮是从workDaysLimit++后，也就是=1才开始的>，其它轮都会多一次)
									workDaysLimit = 1;
									restDaysLimit = 0;
								}
							}
						} else if (classesType.getRestDays().contains(classesEmployee.getWeek())) { // 周循环的模式(排除一周的休息日)
							// 设置休息日的班次
							setRestDaysClasses(classesEmployee);
						}
					}
					result = classesEmployeeMapper.insertSelective(classesEmployee);
				}
			}
		}
		return result;
	}
	
	/**
	 * 设置(休息日/节假日)的班次
	 */
	public void setRestDaysClasses(ClassesEmployee classesEmployee) {

		classesEmployee.setClassesId("");
		classesEmployee.setClassesName("");
		classesEmployee.setOnDutySchedulingDate("");
		classesEmployee.setOffDutySchedulingDate("");
		classesEmployee.setRestStartTime("");
		classesEmployee.setRestEndTime("");
		classesEmployee.setSignInRule("");
		classesEmployee.setSignOutRule("");
		classesEmployee.setOnPunchCardRule("");
		classesEmployee.setOffPunchCardRule("");
		classesEmployee.setDivideColor("");
	}

	/**
	 * 查询公司人员班次信息列表（抽取代码）,数据进行分组处理
	 * 
	 * @param map
	 * @param selectClassesInfo
	 * @param i
	 */
	public void dataDiviceGroup(Map<String, Object> map, List<Map> selectClassesInfo, int i) {
		map.put("empId", selectClassesInfo.get(i).get("emp_id"));
		map.put("empName", selectClassesInfo.get(i).get("employee_name"));
		map.put("postName", selectClassesInfo.get(i).get("post_name"));
		map.put("deptName", selectClassesInfo.get(i).get("department_name"));
		map.put("theDate", selectClassesInfo.get(i).get("the_date"));
		map.put("week", selectClassesInfo.get(i).get("week"));
		map.put("classesId", selectClassesInfo.get(i).get("id"));
		map.put("classesTypeId",selectClassesInfo.get(i).get("classes_id"));
		map.put("classesName", selectClassesInfo.get(i).get("classes_name"));
		map.put("onDutySchedulingDate", selectClassesInfo.get(i).get("on_duty_scheduling_date"));
		map.put("offDutySchedulingDate", selectClassesInfo.get(i).get("off_duty_scheduling_date"));
		map.put("restStartTime", selectClassesInfo.get(i).get("rest_start_time"));
		map.put("restEndTime", selectClassesInfo.get(i).get("rest_end_time"));
		map.put("colorFlag", selectClassesInfo.get(i).get("divide_color"));
	}

	/**
	 * 自动排班部分公共代码
	 */
	public int commonOperateForAutoScheduling(ClassesType classesType, JSONArray empArray) {
		// 初始化执行结果变量
		int result = 0;
		// TODO 执行排班操作 （生效时间为当前日期）
		String validDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime());
		// 先删除使用该班次类型的人员班次信息(删除从指定生效日往后的班次)
		Map<String, String> delParam = new HashMap<>();
		delParam.put("classesTypeId", classesType.getId());
		delParam.put("companyId", classesType.getCompanyId());
		delParam.put("offSetTime", validDate);
		JSONObject parseObject = JSONObject.parseObject(empArray.get(0).toString());
		if (parseObject != null) {
			delParam.put("empId", parseObject.get("empId").toString().trim());
		}
		// TODO 删除当前公司使用该班次类型,指定人员,指定日期之后的人员班次
		int deleteAppointClassesTypeEmp = classesEmployeeMapper.deleteAppointClassesTypeEmp(delParam);
		if (deleteAppointClassesTypeEmp > 0) {
			result = commonSchedulingOperate(validDate, empArray, classesType);
		}
		return result;
	}
	
	/**
	 * 获取公司人员班次信息【对封装后的数据进行排序操作：根据人员名称和班次内容排序】
	 */
	public List<ClassesEmpInfo> commonSortForClassesEmpInfo(List<?> listMap){
		//初始化要返回的数据
		List<ClassesEmpInfo> returnListMap = new ArrayList<>();
		//遍历参数ListMap
		for (Object object : listMap) {
			Map innerMap = (Map)object;
			ClassesEmpInfo classesEmpInfo = new ClassesEmpInfo();
			classesEmpInfo.setDeptName(innerMap.get("deptName").toString().trim());
			classesEmpInfo.setPostName(innerMap.get("postName").toString().trim());
			classesEmpInfo.setEmpId(innerMap.get("empId").toString().trim());
			classesEmpInfo.setEmpName(innerMap.get("empName").toString().trim());
			classesEmpInfo.setThisWeekHours(innerMap.get("thisWeekHours").toString().trim());
			classesEmpInfo.setClassesList((List<Map<String,String>>)innerMap.get("classesList"));
			
			returnListMap.add(classesEmpInfo);
		}
		
		//对集合中的数据进行排序操作
		Collections.sort(returnListMap, new Comparator<ClassesEmpInfo>() {
			@Override
            public int compare(ClassesEmpInfo o1, ClassesEmpInfo o2) {
			   //根据【人员名称+班次内容】进行【倒序】排序
               return (o2.getClassesList().toString()+o2.getEmpName()).compareTo(o1.getClassesList().toString()+o1.getEmpName());
            }
		});;
		return returnListMap;
	}
}
