package com.xiangshangban.att_simple.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.att_simple.bean.NotClockingInEmp;
import com.xiangshangban.att_simple.dao.NotClockingInEmpMapper;
import com.xiangshangban.att_simple.utils.FormatUtil;

/**
 * 无需考勤人员业务实现类
 * @author Administrator
 *
 */
@Service
@Transactional
public class NotClockingInEmpServiceImpl implements NotClockingInEmpService{
	
	@Autowired
	private NotClockingInEmpMapper notClockingInEmpMapper;
	
	/**
	 * （添加/更新/删除）无需考勤人员
	 */
	@Override
	public boolean addNotClockingInEmp(String requestParam, String companyId) {
		
		JSONObject jsonObject = JSONObject.parseObject(requestParam);
		//获取操作标志
		String operateFlag = jsonObject.get("flag").toString().trim();
		Object object = jsonObject.get("employeeIdList");
		//定义执行结果
		boolean result = false;
		
		if(object!=null){
			JSONArray empArray = JSONArray.parseArray(JSONArray.toJSONString(object));
			if(operateFlag.equals("1")){
				//执行删除当前公司无需考勤人员操作
				notClockingInEmpMapper.removeNotClockingInEmp(companyId.trim());
				if(empArray.size()==0){ //仅仅执行删除公司人员操作
					result = true;
				}
			}
			if(empArray.size()>0){
				result = commonAddNotClockingInEmp(empArray,companyId);
			}
		}
		return result;
	}
	
	/**
	 * 查询无需考勤人员
	 */
	@Override
	public List<Map> queryNotClockingInEmp(String companyId) {
		List<Map> selectNotClockingInEmp = notClockingInEmpMapper.selectNotClockingInEmp(companyId);
		return selectNotClockingInEmp;
	}
	
	/*********************************************************
	 * 添加无需考勤人员公共操作
	 *********************************************************/
	
	public boolean commonAddNotClockingInEmp(JSONArray empArray,String companyId){
		//初始化返回结果
		boolean result = false;
		for(int i=0;i<empArray.size();i++){
			
			JSONObject emp = JSONObject.parseObject(JSONArray.toJSONString(empArray.get(i)));
			
			NotClockingInEmp notClockingInEmp = new NotClockingInEmp();
			notClockingInEmp.setId(FormatUtil.createUuid());
			notClockingInEmp.setEmpId(emp.getString("empId"));
			notClockingInEmp.setEmpCompanyId(companyId);
			
			int insertResult = notClockingInEmpMapper.insertNotClockingInEmp(notClockingInEmp);
			
			if(insertResult>0){
				result = true;
			}else{
				result = false;
			}
		}
		return result;
	}
}
