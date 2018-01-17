package com.xiangshangban.att_simple.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.att_simple.bean.ReturnData;
import com.xiangshangban.att_simple.service.ClassesService;
import com.xiangshangban.att_simple.service.NotClockingInEmpService;

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
	
	/**
	 * 新增/修改班次类型
	 * @param requestParam
	 {
  		 "classesId":"",(通过有无班次ID<没有的时候传递null>,来判断用户进行的是新增的操作还是更新的操作)
         "classesName":"常白班",（班次名称）
         "on_duty_time":"09:00",(上班时间)
         "off_duty_time":"18:00",（下班时间）
         "morrowFlag":"0/1",(是否是次日的这个时间下班 0:不是 1:是)
         "restStartTime":"12:00",每天的休息时间段:开始时间
         "restEndTime":"13:00",每天的休息时间段:结束时间
         "restDays":"67",一周的休息日（6、7表示周六和周日休息）
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
	 	 "validDate":"2018-01-16"(当前班次生效的时间，没有的时候不传为null)
	 }
	 * @param request
	 * @return
	 */
	@PostMapping("/addClassesType")
	public ReturnData addClassesType(@RequestBody String requestParam,HttpServletRequest request){
		//初始化返回内容
		ReturnData returnData = new ReturnData();
		String companyId = request.getHeader("companyId");
		if(companyId!=null && !companyId.isEmpty()){
			boolean addNewClassesType = classesService.addNewClassesType(requestParam, companyId.trim());
			if(addNewClassesType){
				returnData.setReturnCode("3000");
				returnData.setMessage("添加成功");
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
	 * 查询当前公司所有班次类型详细信息（设置班次的时候默认显示数据）
	 * 返回数据结构：
	 * {
		  "employeeId": null,
		  "data": [
		    {
		      "classesType": {
		        "id": "AB7130D9D6A94A0F82E159C234A7AA0C",
		        "classesName": "常白班",
		        "onDutyTime": "20:00",
		        "offDutyTime": "07:00",
		        "morrowDutyTimeFlag": "1",
		        "restTime": "23:00-1:00",
		        "restDays": "67",
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
		    }
		  ],
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
	@PostMapping("/getAllClassesType")
	public ReturnData getAllClassesTypeInfo(HttpServletRequest request){
		//获取公司ID
		String companyId = request.getHeader("companyId");
		//初始化返回的数据
		ReturnData returnData = new ReturnData();
		if(companyId!=null && !companyId.isEmpty()){
			List<Map> allClassesTypeInfo = classesService.queryAllClassesTypeInfo(companyId.trim());
			
			returnData.setData(allClassesTypeInfo);
			returnData.setReturnCode("3000");
			returnData.setMessage("请求数据成功");
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
		//初始化返回的数据
		ReturnData returnData = new ReturnData();
		if(companyId!=null && !companyId.isEmpty()){
			boolean deleteAppointClassesType = classesService.deleteAppointClassesType(requestParam,companyId.trim());
			if(deleteAppointClassesType){
				returnData.setReturnCode("3000");
				returnData.setMessage("删除成功");
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
	 * 根据条件查询当前公司人员的班次排列，以及人数最多的三个班次（班次类型有几个显	示几个）的人数
	 * @param requestParam
	 * 	{
			“classesType”:”常白班”（班次类型）
			“empDept”:”研发部”（人员部门）
			“empName”:”小青”（人员名称）
			“perviousWeek”:”0/1”（是否查看上周的数据0：没有该搜索条件 1：有该搜索条件）
			“thisWeek”：“0/1”(是否查看本周班次 0：不查看 1：查看)
			“nextWeek”:”0/1”(是否查看下一周的班次 0：不查看 1：查看)
			“page”:”1”(当前页码)
			“rows”:”5”（每一页要显示的行数）
			“currentDate”:”2018-01-09”（隐藏条件<后台自己准备>，根据当前时间划定一周时间）
		}
	 * @param request
	 * @return
	 */
	public Map<String,Object> getClassesInfo(@RequestBody String requestParam,HttpServletRequest request){
		return null;
	}
	
	/**
	 * 一键排班
	 * @param requestParam
	 * {
			“employeeIdList”:”[ (需要进行一键排班的人员)
				{“empId”,”CSDCSCFSDIFHSDK56”},
				{“empId”:”ADKJASDBKASDHASD67”}
			]”
	   }
	 * @param request
	 * @return
	 */
	public Map<String,Object> oneKeyScheduling(@RequestBody String requestParam,HttpServletRequest request){
		
		return null;
	}
	
	/**
	 * 给指定的人员添加指定日期的排班(班次类型不变，允许微调上下班时间)
	 * 请求参数：
	 * 	{
			“empId”:”CASCASCA”--------->人员ID
			“dateInfo”:”2018-01-10”----->指定的排班日期
			“on_duty_time”:”08:00”-------->该班次微调后的上班时间
			“off_dutyy_time”:”18:00”------>该班次微调后的下班时间
		}
	 * @param reuestParam
	 * @param request
	 * @return
	 */
	@PostMapping("/addOneDateClasses")
	public Map<String,Object> addEmpOneDateClasses(@RequestBody String reuestParam,HttpServletRequest request){
		
		return null;
	}
	
	/**
	 * 删除指定人员指定日期的排班
	 * @param reuestParam
	 * {
			“empId”:”CASCASCA”--------->人员ID
			“dateInfo”:”2018-01-10”----->指定的排班日期
	   }
	 * @param request
	 * @return
	 */
	public Map<String,Object> deleteEmpOneDateClasses(@RequestBody String reuestParam,HttpServletRequest request){
		
		return null;
	}
	
	/**
	 * 根据条件导出人员班次信息
	 * @param requestParam
	 * {
			“classesType”:”常白班”（班次类型）
			“empDept”:”研发部”（人员部门）
			“empName”:”小青”（人员名称）
			“perviousWeek”:”0/1”（是否查看上周的数据0：没有该搜索条件 1：有该搜索条件）
			“thisWeek”：“0/1”(是否查看本周班次 0：不查看 1：查看)
			“nextWeek”:”0/1”(是否查看下一周的班次 0：不查看 1：查看)
			“currentDate”:”2018-01-09”（隐藏条件<后台自己准备>，根据当前时间划定一周时间）
		}
	 * @param request
	 * @param response
	 */
	public void exportScheduling(@RequestBody String requestParam, HttpServletRequest request, HttpServletResponse response){
		
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
		//初始化返回的数据
		ReturnData returnData = new ReturnData();
		if(companyId!=null && !companyId.isEmpty()){
			boolean result = notClockingInEmpService.addNotClockingInEmp(requestParam, companyId.trim());
			if(result){
				returnData.setReturnCode("3000");
				returnData.setMessage("添加成功");
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
	 * 获取当前公司无需考勤人员信息
	 * 返回数据格式：
	 * {
		  "employeeId": null,
		  "data": [
		    {
		      "department_id": "wooUnknown",
		      "emp_id": "XFGCDSDSFSDFSDF13213"
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
		//初始化返回的数据
		ReturnData returnData = new ReturnData();
		if(companyId!=null && !companyId.isEmpty()){
			List<Map> queryNotClockingInEmp = notClockingInEmpService.queryNotClockingInEmp(companyId.trim());
			
			returnData.setData(queryNotClockingInEmp);
			returnData.setReturnCode("3000");
			returnData.setMessage("请求数据成功");
		}else{
			returnData.setReturnCode("3013");
			returnData.setMessage("请求头参数缺失【未知的登录人（公司）ID】");
		}
		return returnData;
	}
}
