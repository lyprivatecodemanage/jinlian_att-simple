package com.xiangshangban.att_simple.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiangshangban.att_simple.bean.ReturnData;
import com.xiangshangban.att_simple.service.ClassesService;

/**
 * @author 王勇辉
 * TODO 排班管理控制器
 */
@RestController
@RequestMapping("/classes")
public class ClassesController {
	
	@Autowired
	private ClassesService classesService;
	
	/**
	 * 新增班次类型
	 * @param requestParam
	 * {
	         "classesName":"常白班",（班次名称）
	         "on_duty_time":"09:00",(上班时间)
	         "off_duty_time":"18:00",（下班时间）
	         "morrowFlag":"0/1",(是否是次日的这个时间下班 0:不是 1:是)
	         "restTime":"12:00-13:00",每天的休息时间段
	         "restDays":"67"，一周的休息日（6、7表示周六和周日休息）
	         "festivalRestFlag":"0/1"(法定假日是否休息标志位0:不休息 1:休息)
			 “signInRule”:”20”（签到晚20分钟不算迟到）
			 “signOutRule”：“20”(签退早20分钟不算早退)
	 		 “onPunchCardRule”：“20”(上班打卡限制,允许提前20分钟)
			 “offPunchCardRule”:”20”(下班打卡限制，允许推迟20分钟) 
			 “employeeIdList”:”[  (排班人员列表)
			 {“empId”:”XFGCDSDSFSDFSDF13213”},
			 {“empId”:”XFGCDSDSFSDFSDF46557”}
			]”
		 	“autoClassesFlag”：“1/2”(自动排班周期类型 1：月 2：周)
		}
	 * @param request
	 * @return
	 */
	@PostMapping("/addClassesType")
	public ReturnData addClassesType(@RequestBody String requestParam,HttpServletRequest request){
		//初始化返回内容
		ReturnData result = new ReturnData();
		String companyId = request.getHeader("companyId");
		if(companyId!=null && !companyId.isEmpty()){
			boolean addNewClassesType = classesService.addNewClassesType(requestParam, companyId);
			if(addNewClassesType){
				result.setReturnCode("3000");
				result.setMessage("添加成功");
			}else{
				result.setReturnCode("3001");
				result.setMessage("添加失败");
			}
		}else{
			result.setReturnCode("3013");
			result.setMessage("请求头参数缺失【未知的登录人（公司）ID】");
		}
		return result;
	}
	
	/**
	 * 查询当前公司所有班次类型详细信息（设置班次的时候默认显示数据）
	 *
	 * @param request
	 * @return
	 */
	@PostMapping("/getAllClassesType")
	public Map<String,Object> getAllClassesTypeInfo(HttpServletRequest request){
		
		return null;
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
	public Map<String,Object> deleteClassesType(@RequestBody String requestParam,HttpServletRequest request){
		return null;
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
	 * 添加无需考勤人员信息
	 * @param requestParam
	 * {
         "employeeIdList":"[ (不需要考勤的人员列表)
			{“empId”:”CBAJKSCBJAKSA12111”},
			{“empId”:”CAHBCJKSHADCBA5646”}
		 ]",（班次名称）
	   }
	 * @param request
	 * @return
	 */
	public Map<String,Object> addNotClockingInEmp(@RequestBody String requestParam,HttpServletRequest request){
		return null;
	}
	
}
