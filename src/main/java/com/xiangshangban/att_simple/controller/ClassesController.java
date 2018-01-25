package com.xiangshangban.att_simple.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arjuna.ats.internal.jdbc.drivers.modifiers.list;
import com.xiangshangban.att_simple.AttSimple;
import com.xiangshangban.att_simple.bean.ClassesType;
import com.xiangshangban.att_simple.bean.OperateLog;
import com.xiangshangban.att_simple.bean.ReturnData;
import com.xiangshangban.att_simple.service.ClassesService;
import com.xiangshangban.att_simple.service.NotClockingInEmpService;
import com.xiangshangban.att_simple.utils.HttpRequestFactory;

/**
 * @author 王勇辉
 * TODO 排班管理控制器
 */
@RestController
@RequestMapping("/classes")
public class ClassesController {
	
	@Autowired
	private ClassesService classesService;
	
	@Autowired
	private NotClockingInEmpService notClockingInEmpService;
	
	public static Logger logger = Logger.getLogger(ClassesController.class);
	
	/***************
	 * 操作日志访问路径
	 **************/
	@Value("${sendUrl}")
	private String sendUrl;
	
	/**
	 * 添加公司默认常规班
	 * @param companyId
	 * @return
	 */
	@PostMapping("/addDefaultClassesType")
	public String addDefaultClassesType(@RequestParam String companyId){
		//初始化返回结果
		String result = "";
		if(companyId!=null && !companyId.trim().isEmpty()){
			boolean addCompanyDefaultClasses = classesService.addCompanyDefaultClasses(companyId);
			if(addCompanyDefaultClasses){
				result = "添加成功";
			}else{
				result = "添加失败";
			}
		}else{
			result = "参数【公司ID】异常";
		}
		return result;
	}
	
	/**
	 * 人员入职的时候给这些人员排“公司默认常规班次”
	 * @param requestParam
	 * {
	 * 	  "companyId":"CAJKCLA",
	 * 	  "empIdList":[ 
			 {"empId":"XFGCDSDSFSDFSDF13213"},
			 {"empId":"XFGCDSDSFSDFSDF46557"}
		  ]
	 * }
	 * @return
	 */
	@PostMapping("/addDefaultEmpClasses")
	public String addDefaultEmpClasses(@RequestBody String requestParam){
		//解析请求数据
		JSONObject parseObject = JSONObject.parseObject(requestParam);
		Object companyId = parseObject.get("companyId");
		Object empIdList = parseObject.get("empIdList");
		JSONArray parseArray = JSONArray.parseArray(JSONArray.toJSONString(empIdList));
		//初始化返回结果
		String result = "";
		if((companyId!=null && !companyId.toString().trim().isEmpty()) && (parseArray!=null && parseArray.size()>0)){
			boolean addDefaultEmpClasses = classesService.addDefaultEmpClasses(companyId.toString().trim(),parseArray);
			if(addDefaultEmpClasses){
				result = "排班成功";
			}else{
				result = "排班失败";
			}
		}else{
			result = "参数异常";
		}
		return result;
	}
	
	/**
	 * 新增/修改班次类型
	 * @param requestParam
	 {
  		 "classesId":"",(通过有无班次ID,来判断用户进行的是新增的操作还是更新的操作)
         "classesName":"常白班",（班次名称）
         "on_duty_time":"09:00",(上班时间)
         "off_duty_time":"18:00",（下班时间）
         "morrowFlag":"0/1",(是否是次日的这个时间下班 0:不是 1:是)
         "restStartTime":"12:00",每天的休息时间段:开始时间
         "restEndTime":"13:00",每天的休息时间段:结束时间
         "restDays":"67",(传递的是67的时候，表示一周的周六和周日休息、传递的是"5,2"表示做5休2)
         "festivalRestFlag":"0/1",(法定假日是否休息标志位0:不休息 1:休息)
		 "signInRule":"20",（签到晚20分钟不算迟到）
		 "signOutRule":"20",(签退早20分钟不算早退)
 		 "onPunchCardRule":"20", (上班打卡限制，允许提前20分钟)
		 "offPunchCardRule":"20", (下班打卡限制，允许推迟20分钟)
		 "employeeIdList":[  (使用改排班的人员列表)
			 {"empId":"XFGCDSDSFSDFSDF13213"},
			 {"empId":"XFGCDSDSFSDFSDF46557"}
		  ],
	 	 "autoClassesFlag":"1/2",(自动排班周期 1:月  2:季度)
	 	 "validDate":"2018-01-16"(当前班次生效的时间，没有的时候不传)
	 }
	 * @param request
	 * @return
	 */
	@PostMapping("/addClassesType")
	public ReturnData addClassesType(@RequestBody String requestParam,HttpServletRequest request){
		//初始化返回内容
		ReturnData returnData = new ReturnData();
		String companyId = request.getHeader("companyId");
		String accessUserId = request.getHeader("accessUserId");
		if((companyId!=null && !companyId.isEmpty()) && (accessUserId!=null && !accessUserId.isEmpty())){
			
			//查询当前公司已经存在的班次类别ID和Name
			List<ClassesType> queryAllClassesIdAndName = classesService.queryAllClassesIdAndName(companyId);
			boolean addNewClassesType = false;
			if(queryAllClassesIdAndName!=null && queryAllClassesIdAndName.size()>5){
				returnData.setReturnCode("3001");
				returnData.setMessage("班次类别最多存在5个,当前不能进行新增操作");
			}else{
				addNewClassesType = classesService.addNewClassesType(requestParam, companyId.trim());
			}
			if(addNewClassesType){
				returnData.setReturnCode("3000");
				returnData.setMessage("添加成功");
				//增加操作日志:记录web端的操作
				String addOperateLog = addOperateLog(accessUserId,companyId,"在班次设置界面(新增/更新)班次设置");
				logger.info("【(新增/修改)班次设置】------>操作日志"+addOperateLog);
			}else{
				returnData.setReturnCode("3001");
				returnData.setMessage("添加失败");
			}
		}else{
			returnData.setReturnCode("3013");
			returnData.setMessage("请求头参数缺失【未知的登录人（公司）ID】");
		}
		return returnData;
	}
	
	/**
	 * 查询当前公司所有班次类型ID和名称
	 * @param request
	 * @return
	 */
	@PostMapping("/getAllClassesIdAndName")
	public ReturnData getAllClassesIdAndName(HttpServletRequest request){
		//获取公司ID
		String companyId = request.getHeader("companyId");
		//获取访问人员ID
		String accessUserId = request.getHeader("accessUserId");
		//初始化返回的数据
		ReturnData returnData = new ReturnData();
		if((companyId!=null && !companyId.isEmpty()) && (accessUserId!=null && !accessUserId.isEmpty())){
			List<ClassesType> allClassesIdAndName = classesService.queryAllClassesIdAndName(companyId.trim());
			//精简返回的数据
			List<Map> listMap = new ArrayList<>();
			if(allClassesIdAndName!=null && allClassesIdAndName.size()>0){
				for (ClassesType classesType : allClassesIdAndName) {
					Map innerMap = new HashMap();
					innerMap.put("id",classesType.getId());
					innerMap.put("classes_name", classesType.getClassesName());
					innerMap.put("is_default",classesType.getIsDefault());
					listMap.add(innerMap);
				}
				returnData.setData(listMap);
			}else{
				returnData.setData(allClassesIdAndName);
			}
			returnData.setReturnCode("3000");
			returnData.setMessage("请求数据成功");
			//增加操作日志:记录web端的操作
			/*String addOperateLog = addOperateLog(accessUserId,companyId,"在班次设置界面查看当前公司所有的班次类别名称");
			logger.info("【查询当前公司所有班次类别】------>操作日志"+addOperateLog);*/
		}else{
			returnData.setReturnCode("3013");
			returnData.setMessage("请求头参数缺失【未知的登录人（公司）ID】");
		}
		return returnData;
	}
	
	/**
	 * 查询指定班次类型详细信息（设置班次的时候默认显示数据）
	 * {
	 * 	 "classesTypeId":"XASJBXAKSJXBNNN" ------------>班次类型ID
	 * }
	 * 返回数据结构：
	 * {
		  "employeeId": null,
		  "data": 
		    {
		      "classesType": {
		        "id": "AB7130D9D6A94A0F82E159C234A7AA0C",
		        "classesName": "常白班",
		        "onDutyTime": "20:00",
		        "offDutyTime": "07:00",
		        "morrowDutyTimeFlag": "1",
		        "restTime": "23:00-1:00",
		        "restDays": "67",（返回67表明按照周为周期进行排班、返回“6,2”表示按照做6休2的周期进行排班）
		        "festivalRestFlag": "1",
		        "signInRule": "20",
		        "signOutRule": "20",
		        "onPunchCardTime": "20",
		        "offPunchCardTime": "20",
		        "autoClassesFlag": "1",
		        "createTime": null,
		        "companyId": null
		      },
		      "classesEmp": [
		        {
		          "department_id": "wooUnknown",
		          "emp_id": "XFGCDSDSFSDFSDF13213"
		        },
		        {
		          "department_id": "",
		          "emp_id": "XFGCDSDSFSDFSDF46557"
		        }
		      ]
		    },
		  "totalPages": null,
		  "message": "请求数据成功",
		  "returnCode": "3000",
		  "pagecountNum": null,
		  "companyName": null
		}
	 *
	 * @param request
	 * @return
	 */
	@PostMapping("/getPointClassesType")
	public ReturnData getPointClassesTypeInfo(@RequestBody String requestParam,HttpServletRequest request){
		//获取公司ID
		String companyId = request.getHeader("companyId");
		//获取访问人员ID
		String accessUserId = request.getHeader("accessUserId");
		//初始化返回的数据
		ReturnData returnData = new ReturnData();
		if((companyId!=null && !companyId.isEmpty()) && (accessUserId!=null && !accessUserId.isEmpty())){
			Map allClassesTypeInfo = classesService.queryPointClassesTypeInfo(requestParam,companyId.trim());
			returnData.setData(allClassesTypeInfo);
			returnData.setReturnCode("3000");
			returnData.setMessage("请求数据成功");
			//增加操作日志:记录web端的操作
			/*String addOperateLog = addOperateLog(accessUserId,companyId,"在班次设置界面查看指定班次类别详细信息");
			logger.info("【查询指定班次类别详细信息】------>操作日志"+addOperateLog);*/
		}else{
			returnData.setReturnCode("3013");
			returnData.setMessage("请求头参数缺失【未知的登录人（公司）ID】");
		}
		return returnData;
	}
	
	/**
	 * 删除指定的班次类型
	 * @param requestParam
	 * 	{
			“classesTypeId”:”CBASKJCHAB123”-------->班次类型ID
		}
	 * @param request
	 * @return
	 */
	@PostMapping("/delClassesType")
	public ReturnData deleteClassesType(@RequestBody String requestParam,HttpServletRequest request){
		//获取公司ID
		String companyId = request.getHeader("companyId");
		//获取访问人员ID
		String accessUserId = request.getHeader("accessUserId");
		//初始化返回的数据
		ReturnData returnData = new ReturnData();
		if((companyId!=null && !companyId.isEmpty()) && (accessUserId!=null && !accessUserId.isEmpty())){
			boolean deleteAppointClassesType = classesService.deleteAppointClassesType(requestParam,companyId.trim());
			if(deleteAppointClassesType){
				returnData.setReturnCode("3000");
				returnData.setMessage("删除成功");
				//查询班次类型名称
				JSONObject parseObject = JSONObject.parseObject(requestParam);
				Object classesTypeId = parseObject.get("classesTypeId");
				ClassesType queryPointClassesTypeData = classesService.queryPointClassesTypeData(classesTypeId.toString().trim());
				String classesName = queryPointClassesTypeData.getClassesName();
				//增加操作日志:记录web端的操作
				String addOperateLog = addOperateLog(accessUserId,companyId,"在班次设置界面删除【"+classesName+"】班次");
				logger.info("【删除指定班次类别】------>操作日志"+addOperateLog);
			}else{
				returnData.setReturnCode("3001");
				returnData.setMessage("删除失败");
			}
		}else{
			returnData.setReturnCode("3013");
			returnData.setMessage("请求头参数缺失【未知的登录人（公司）ID】");
		}
		return returnData;
	}
	
	/**
	 * 根据条件查询当前公司人员的班次排列，以及人数最多的三个班次（班次类型有几个显示几个）的人数
	 * @param requestParam
	 * 	{
			"classesTypeId":"XASXLASKMX"（班次编号）
			"deptId":"dasdasdas”（人员部门编号）
			"empName":"小青"（人员名称）
			"perviousWeek":"0/1"（是否查看上周的数据0：没有该搜索条件 1：有该搜索条件）
			"thisWeek":"0/1"(是否查看本周班次 0：不查看 1：查看)------->默认显示本周的
			"nextWeek":"0/1"(是否查看下一周的班次 0：不查看 1：查看)
			"page":"1"(当前页码)
			"rows":"5"（每一页要显示的行数）
		}
	 * @param request
	 * @return
	 */
	@PostMapping("/getEmpClassesInfo")
	public ReturnData getEmpClassesInfo(@RequestBody String requestParam,HttpServletRequest request){
		//获取公司ID
		String companyId = request.getHeader("companyId");
		//获取访问人员ID
		String accessUserId = request.getHeader("accessUserId");
		//初始化返回的数据
		ReturnData returnData = new ReturnData();
		if((companyId!=null && !companyId.isEmpty()) && (accessUserId!=null && !accessUserId.isEmpty())){
			returnData = classesService.queryClassesInfo(requestParam,companyId.trim());
			//增加操作日志:记录web端的操作
			/*String addOperateLog = addOperateLog(accessUserId,companyId,"在班次管理界面查看公司所有人班次信息");
			logger.info("【查询所有人员班次信息】------>操作日志"+addOperateLog);*/
		}else{
			returnData.setReturnCode("3013");
			returnData.setMessage("请求头参数缺失【未知的登录人（公司）ID】");
		}
		return returnData;
	}
	
	/**
	 * 根据条件导出人员班次信息
	 * @param requestParam
	 * {
			"classesTypeId":"常白班"（班次编号）
			"deptId":"研发部”（人员部门编号）
			"empName":"小青"（人员名称）
			"perviousWeek":"0/1"（是否查看上周的数据0：没有该搜索条件 1：有该搜索条件）
			"thisWeek":"0/1"(是否查看本周班次 0：不查看 1：查看)------->默认显示本周的
			"nextWeek":"0/1"(是否查看下一周的班次 0：不查看 1：查看)
			"flag":"0"（导出记录标志位：0表示导出人员班次信息）
	   }
	 * @param request
	 * @param response
	 */
	@PostMapping(value="export/scheduling",produces="application/json;charset=UTF-8")
	public void exportScheduling(@RequestBody String requestParam, HttpServletRequest request, HttpServletResponse response){
		try {
            response.setContentType("application/octet-stream ");
            String agent = request.getHeader("USER-AGENT");
            String excelName = "unknown.xls";
            //解析请求的数据
            JSONObject jsonObject = JSONObject.parseObject(requestParam);
            //获取请求的标志
            Object flag = jsonObject.get("flag");

            if (flag != null && !flag.toString().trim().isEmpty()) {
                String status = flag.toString().trim();
                if (status.equals("0")) {
                    excelName = "empClassesRecord.xls";
                }
            }
            if (agent != null && agent.indexOf("MSIE") == -1 && agent.indexOf("rv:11") == -1 &&
                    agent.indexOf("Edge") == -1 && agent.indexOf("Apache-HttpClient") == -1) {//非IE
                excelName = new String(excelName.getBytes("UTF-8"), "ISO-8859-1");
                response.addHeader("Content-Disposition", "attachment;filename=" + excelName);
            } else {
                response.addHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(excelName, "UTF-8"));
            }
            response.addHeader("excelName", java.net.URLEncoder.encode(excelName, "UTF-8"));
            //获取输出流
            OutputStream out = response.getOutputStream();
            // 获取公司ID
            String companyId = request.getHeader("companyId");
            //获取操作人ID
            String accessUserId = request.getHeader("accessUserId");
            if ((companyId!=null && !companyId.isEmpty()) && (accessUserId!=null && !accessUserId.isEmpty())) {
            	classesService.exportRecordToExcel(requestParam, excelName, out, companyId);
                out.flush();
                //增加操作日志:记录web端的操作
    			String addOperateLog = addOperateLog(accessUserId,companyId,"在班次管理界面导出公司人员班次信息");
    			logger.info("【导出公司人员班次信息】------>操作日志"+addOperateLog);
            } else {
                System.out.println("未知的登录人（公司）ID");
            }
        } catch (IOException e) {
            System.out.println("导出文件输出流出错了！" + e);
        }
	}
	
	/**
	 * 一键排班(一次排一个周期)
	 * @param request
	 * @return
	 */
	@PostMapping("/oneKeyScheduling")
	public ReturnData oneKeyScheduling(HttpServletRequest request){
		//获取公司ID
		String companyId = request.getHeader("companyId");
		//获取操作人ID
        String accessUserId = request.getHeader("accessUserId");
		//初始化返回的数据
		ReturnData returnData = new ReturnData();
		if((companyId!=null && !companyId.isEmpty()) && (accessUserId!=null && !accessUserId.isEmpty())){
			boolean deleteAppointClassesType = classesService.oneButtonScheduling(companyId.trim());
			if(deleteAppointClassesType){
				returnData.setReturnCode("3000");
				returnData.setMessage("排班成功");
				 //增加操作日志:记录web端的操作
    			String addOperateLog = addOperateLog(accessUserId,companyId,"在班次管理界面为公司所有已排班人员执行一键排班操作");
    			logger.info("【一键排班】------>操作日志"+addOperateLog);
			}else{
				returnData.setReturnCode("3001");
				returnData.setMessage("排班失败");
			}
		}else{
			returnData.setReturnCode("3013");
			returnData.setMessage("请求头参数缺失【未知的登录人（公司）ID】");
		}
		return returnData;
	}
	
	/**
	 * 给指定的人员添加指定日期的排班(临时班次)
	 * 请求参数：
	 * 	{
	  		"empClassesId":""----------->人员班次ID
	  		（传递的时候表示，该天的班次是因为休息日原因没有安排上下班时间<该条记录是在数据库中存在的>/不传递的时候表示，该天就没有排班次<数据库中没有该条记录>）
			"empId":"CASCASCA"--------->人员ID
			"pointDate":"2018-01-10"----->指定的排班日期
			"pointWeek":"4"------------->指定的星期
			"onDutyTime":"08:00"-------->上班时间
			"offDutyTime":"18:00"------>下班时间(添加单天的班次的时候不允许跨日)
			"restStartTime":"12:00"------->开始休息时间点
			"restEndTime":"13:00"--------->结束休息时间点
			"signInRule":"15",----------->签到迟到15分钟不算迟到
			"signOutRule":"15"----------->签退提前15分钟不算早退
			"onPunchCardRule":"20",------>上班打卡允许提前20分钟
			"offPunchCardRule":"20"------>下班允许推迟20分钟打卡
		}
	 * @param reuestParam 
	 * @param request
	 * @return
	 */
	@PostMapping("/addOneDateClasses")
	public ReturnData addEmpOneDateClasses(@RequestBody String requestParam,HttpServletRequest request){
		//获取公司ID
		String companyId = request.getHeader("companyId");
		//获取操作人ID
        String accessUserId = request.getHeader("accessUserId");
		//初始化返回的数据
		ReturnData returnData = new ReturnData();
		if((companyId!=null && !companyId.isEmpty()) && (accessUserId!=null && !accessUserId.isEmpty())){
			boolean deleteAppointClassesType = classesService.addEmpDutyTime(requestParam,companyId.trim());
			if(deleteAppointClassesType){
				returnData.setReturnCode("3000");
				returnData.setMessage("添加成功");
				//获取人员ID和指定日期
				JSONObject parseObject = JSONObject.parseObject(requestParam);
				Object pointDate = parseObject.get("pointDate");
				Object empId = parseObject.get("empId");
				//根据人员ID获取人员名称
				String empName = classesService.queryEmpNameById(empId.toString(), companyId);
				//增加操作日志:记录web端的操作
    			String addOperateLog = addOperateLog(accessUserId,companyId,"在班次管理界面为【"+empName+"】添加"+pointDate.toString()+"日的排班");
    			logger.info("【给指定人员添加指定日期的排班】------>操作日志"+addOperateLog);
			}else{
				returnData.setReturnCode("3001");
				returnData.setMessage("添加失败");
			}
		}else{
			returnData.setReturnCode("3013");
			returnData.setMessage("请求头参数缺失【未知的登录人（公司）ID】");
		}
		return returnData;
	}
	
	/**
	 * 删除人员指定日期的班次
	 * @param reuestParam
	 * {
	 * 		"empId":"CAJSKCASCJASK",
	 * 		"pointDate":"2018-01-10"
			"empClassesId":"82FC223D03C34A4E8EEF49EC129F1C9C" ------>人员单天班次ID
	   }
	 * @param request
	 * @return
	 */
	@PostMapping("/delPointEmpDateClasses")
	public ReturnData deleteEmpOneDateClasses(@RequestBody String requestParam,HttpServletRequest request){
		//获取公司ID
		String companyId = request.getHeader("companyId");
		//获取操作人ID
        String accessUserId = request.getHeader("accessUserId");
		//初始化返回的数据
		ReturnData returnData = new ReturnData();
		if((companyId!=null && !companyId.isEmpty()) && (accessUserId!=null && !accessUserId.isEmpty())){
			boolean deleteEmpDutyTime = classesService.deleteEmpDutyTime(requestParam);
			if(deleteEmpDutyTime){
				returnData.setReturnCode("3000");
				returnData.setMessage("删除成功");
				//获取人员ID和指定日期
				JSONObject parseObject = JSONObject.parseObject(requestParam);
				Object empId = parseObject.get("empId");
				Object pointDate = parseObject.get("pointDate");
				//根据人员ID获取人员名称
				String empName = classesService.queryEmpNameById(empId.toString(), companyId);
				//增加操作日志:记录web端的操作
    			String addOperateLog = addOperateLog(accessUserId,companyId,"在班次管理界面删除【"+empName+"】"+pointDate.toString()+"日的排班");
    			logger.info("【删除指定人员指定日期的排班】------>操作日志"+addOperateLog);
			}else{
				returnData.setReturnCode("3001");
				returnData.setMessage("删除失败");
			}
		}else{
			returnData.setReturnCode("3013");
			returnData.setMessage("请求头参数缺失【未知的登录人（公司）ID】");
		}
		return returnData;
	}
	
	/**
	 * 查询指定人员指定日期的班次信息
	 * @param requestParam
	 * {
	 * 		"empId":"AXSXASXASXASXASA",
	 * 		"pointDate":"2018-01-21",
			"empClassesId":"82FC223D03C34A4E8EEF49EC129F1C9C" ------>人员单天班次ID
	   }
	 * @param request 获取头信息
	 * @return
	 * {
		  "employeeId": null,
		  "data": {
		    "restStartTime": "24:00",
		    "restEndTime": "1:00",
		    "classesDate": "2018-01-18",
		    "onDutyTime": "20:00",
		    "offDutyTime": "07:00",
		    "classesWeek": "4"
		  },
		  "totalPages": null,
		  "message": "请求数据成功",
		  "returnCode": "3000",
		  "pagecountNum": null,
		  "companyName": null
	   }
	 */
	@PostMapping("/getPointEmpDateClasses")
	public ReturnData getPointEmpDateClasses(@RequestBody String requestParam,HttpServletRequest request){
		//获取公司ID
		String companyId = request.getHeader("companyId");
		//获取操作人ID
        String accessUserId = request.getHeader("accessUserId");
		//初始化返回的数据
		ReturnData returnData = new ReturnData();
		
		if((companyId!=null && !companyId.isEmpty()) && (accessUserId!=null && !accessUserId.isEmpty())){
			Map queryPointEmpDateClasses = classesService.queryPointEmpDateClasses(requestParam);
			returnData.setData(queryPointEmpDateClasses);
			returnData.setReturnCode("3000");
			returnData.setMessage("请求数据成功");
			//获取人员ID和指定日期
			JSONObject parseObject = JSONObject.parseObject(requestParam);
			Object empId = parseObject.get("empId");
			Object pointDate = parseObject.get("pointDate");
			//根据人员ID获取人员名称
			String empName = classesService.queryEmpNameById(empId.toString(), companyId);
			//增加操作日志:记录web端的操作
		/*	String addOperateLog = addOperateLog(accessUserId,companyId,"在班次管理界面查看【"+empName+"】"+pointDate.toString()+"日的排班");
			logger.info("【查看指定人员指定日期的排班】------>操作日志"+addOperateLog);*/
		}else{
			returnData.setReturnCode("3013");
			returnData.setMessage("请求头参数缺失【未知的登录人（公司）ID】");
		}
		return returnData;
	}
	
	/**
	 * (添加/更新)无需考勤人员信息
	 * @param requestParam
	 * {
	  	 "flag":"0/1",(0:新增无需考勤人员 1:更新无需考勤人员)
         "employeeIdList":[
			{"empId":"newCBAJKSCBJAKSA12111"},
			{"empId":"newCAHBCJKSHADCBA5646"}
		 ]
	   }
	 * @param request
	 * @return
	 */
	@PostMapping("/addNotClockingInEmp")
	public ReturnData addNotClockingInEmp(@RequestBody String requestParam,HttpServletRequest request){
		//获取公司ID
		String companyId = request.getHeader("companyId");
		//获取操作人ID
        String accessUserId = request.getHeader("accessUserId");
		//初始化返回的数据
		ReturnData returnData = new ReturnData();
		if((companyId!=null && !companyId.isEmpty()) && (accessUserId!=null && !accessUserId.isEmpty())){
			boolean result = notClockingInEmpService.addNotClockingInEmp(requestParam, companyId.trim());
			if(result){
				returnData.setReturnCode("3000");
				returnData.setMessage("操作成功");
				//增加操作日志:记录web端的操作
    			String addOperateLog = addOperateLog(accessUserId,companyId,"在班次设置界面(添加/更新)无需考勤人员");
    			logger.info("【(添加/更新)无需考勤人员】------>操作日志"+addOperateLog);
			}else{
				returnData.setReturnCode("3001");
				returnData.setMessage("操作失败");
			}
		}else{
			returnData.setReturnCode("3013");
			returnData.setMessage("请求头参数缺失【未知的登录人（公司）ID】");
		}
		return returnData;
	}
	
	/**
	 * 获取当前公司无需考勤人员信息
	 * 返回数据格式：
	 * {
		  "employeeId": null,
		  "data": [
		    {
		      "department_id": "wooUnknown",
		      "emp_id": "XFGCDSDSFSDFSDF13213",
		      "employee_name":"小兰"
		    }
		  ],
		  "totalPages": null,
		  "message": "请求数据成功",
		  "returnCode": "3000",
		  "pagecountNum": null,
		  "companyName": null
	   }
	 * @param request
	 * @return
	 */
	@PostMapping("/getNotClockingInEmp")
	public ReturnData getNotClockingInEmp(HttpServletRequest request){
		//获取公司ID
		String companyId = request.getHeader("companyId");
		//获取操作人ID
        String accessUserId = request.getHeader("accessUserId");
		//初始化返回的数据
		ReturnData returnData = new ReturnData();
		if((companyId!=null && !companyId.isEmpty()) && (accessUserId!=null && !accessUserId.isEmpty())){
			List<Map> queryNotClockingInEmp = notClockingInEmpService.queryNotClockingInEmp(companyId.trim());
			
			returnData.setData(queryNotClockingInEmp);
			returnData.setReturnCode("3000");
			returnData.setMessage("请求数据成功");
			//增加操作日志:记录web端的操作
			/*String addOperateLog = addOperateLog(accessUserId,companyId,"在班次设置界面查看无需考勤人员");
			logger.info("【查看无需考勤人员】------>操作日志"+addOperateLog);*/
		}else{
			returnData.setReturnCode("3013");
			returnData.setMessage("请求头参数缺失【未知的登录人（公司）ID】");
		}
		return returnData;
	}
	
	/**
	 * 测试自动排班
	 */
	@PostMapping("/autoScheduling")
	public ReturnData testAutoScheduling(){
		boolean autoScheduling = classesService.autoScheduling();
		ReturnData returnData = new ReturnData();
		if(autoScheduling){
			returnData.setMessage("自动排班成功");
		}else{
			returnData.setMessage("自动排班失败");
		}
		return returnData;
	}
	
	
	/****************************************************************
	 * 公共方法区 
	 ****************************************************************/
	
	
	/**
	 * 添加操作日志公共方法
	 * @param accessUserId 访问用户ID
	 * @param companyId 访问用户公司ID
	 * @return
	 */
	public String addOperateLog(String accessUserId,String companyId,String content){
		OperateLog operateLog = new OperateLog();
		operateLog.setOperateEmpId(accessUserId.trim());
		operateLog.setOperateEmpCompanyId(companyId.trim());
		operateLog.setOperateType("3");
		operateLog.setOperateContent(content);
		String sendRequet = HttpRequestFactory.sendRequet(sendUrl, operateLog);
		return sendRequet;
	}
}
