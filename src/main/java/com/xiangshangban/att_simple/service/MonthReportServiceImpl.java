package com.xiangshangban.att_simple.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xiangshangban.att_simple.bean.Employee;
import com.xiangshangban.att_simple.bean.Paging;
import com.xiangshangban.att_simple.bean.ReturnData;
import com.xiangshangban.att_simple.dao.EmployeeDao;
import com.xiangshangban.att_simple.dao.ReportDailyMapper;

@Service("monthReportService")
public class MonthReportServiceImpl implements MonthReportService {

	@Autowired
	EmployeeDao employeeDao;
	
	@Autowired
	ReportDailyMapper reportDailyMapper;
	
	@Override
	public ReturnData monthReportKeyData(String companyId,String year,String month) {
		// TODO Auto-generated method stub
		ReturnData returndata = new ReturnData();
		Map<String,Object> map = new HashMap<>();
		List<Employee> list = new ArrayList<>();
		int leaveCount = 0;
		
		try{
			/**
			 * 获得公司人员数
			 */
			int employeeNum = employeeDao.findAllEmployeeByCompanyId(companyId).size();
			
			/**
			 * 当月全勤
			 */
			//获取所有在职人员信息
			List<Employee> empList = employeeDao.findAllEmployeeByCompanyId(companyId);
			
			
			//循环人员数据查询整月有无考勤异常
			for (Employee e : empList) {
				String attDate = year+"-"+month;
				
				int num = reportDailyMapper.selectMonthEcxeptionEmployee(companyId, e.getEmployeeId(),attDate);
				
				//计算公司某月总请假次数
				leaveCount += reportDailyMapper.selectLeaveCount(companyId,e.getEmployeeId(), attDate);
				
				if(num==0){
					list.add(e);
				}
			}
			//全勤人数
			int checkingNum = list.size();
			
			map.put("employeeNum",employeeNum);
			map.put("checkingNum",checkingNum);
			map.put("leaveCount",leaveCount);
			
			returndata.setData(JSON.toJSON(map));
			returndata.setReturnCode("3000");
			returndata.setMessage("数据请求成功");
			return returndata;
		}catch(Exception e){
			e.printStackTrace();
			returndata.setReturnCode("3001");
			returndata.setMessage("服务器错误");
			return returndata;
		}
	}

	@Override
	public ReturnData SelectMonthReportFuzzy(Paging paging) {
		// TODO Auto-generated method stub
		
		/**
		 * 月报模糊分页查询
		 */
		
		return null;
	}

}
