package com.xiangshangban.att_simple.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.att_simple.bean.ReturnData;
import com.xiangshangban.att_simple.bean.Vacation;
import com.xiangshangban.att_simple.bean.VacationDetails;
import com.xiangshangban.att_simple.bean.AnnualLeaveJob;
import com.xiangshangban.att_simple.bean.Employee;
import com.xiangshangban.att_simple.bean.OperateLog;
import com.xiangshangban.att_simple.bean.Paging;
import com.xiangshangban.att_simple.dao.AnnualLeaveJobMapper;
import com.xiangshangban.att_simple.dao.EmployeeDao;
import com.xiangshangban.att_simple.dao.VacationDetailsMapper;
import com.xiangshangban.att_simple.dao.VacationMapper;
import com.xiangshangban.att_simple.utils.FormatUtil;
import com.xiangshangban.att_simple.utils.HttpRequestFactory;
import com.xiangshangban.att_simple.utils.computeVacation;

@Service("vacationService")
public class VacationServiceImpl implements VacationService {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	SimpleDateFormat sdate = new SimpleDateFormat("yyyy-MM-dd");
	
	computeVacation cv = new computeVacation();

	@Value("${sendUrl}")
	String sendUrl;
	
	@Autowired
	AnnualLeaveJobMapper annualLeaveJobMapper;
	
	@Autowired
	VacationMapper vacationMapper;
	
	@Autowired
	VacationDetailsMapper vacationDetailsMapper;
	
	@Autowired
	EmployeeDao employeeDao;

	/**
	 * 假期模糊分页查询
	 */
	@Override
	public ReturnData SelectFuzzyPagel(Paging paging) {
		// TODO Auto-generated method stub
		ReturnData returndata = new ReturnData();
		
		if(StringUtils.isEmpty(paging.getPageNum()) || StringUtils.isEmpty(paging.getVarPageNo()) || StringUtils.isEmpty(paging.getYear())){
			returndata.setReturnCode("3006");
			returndata.setMessage("参数为空");
            return returndata;
		}

		List<Vacation> list = vacationMapper.SelectFuzzyPagel(paging);
		
		if(list.size()>0){
			//查询出条件筛选后的总条数
			int count = vacationMapper.SelectTotalNumber(paging.getCompanyId(), paging.getDepartmentId(), paging.getEmployeeName(),paging.getYear());
			
			int pageNo = count%Integer.parseInt(paging.getPageNum())==0?count/Integer.parseInt(paging.getPageNum()):count/Integer.parseInt(paging.getPageNum())+1;
			
			returndata.setTotalPages(count);
			returndata.setPagecountNum(pageNo);
			returndata.setData(JSONObject.toJSON(list));
			returndata.setReturnCode("3000");
			returndata.setMessage("数据请求成功");
	        return returndata;
		}
		
		returndata.setReturnCode("3000");
		returndata.setMessage("数据请求成功");
        return returndata;
	}

	/**
	 * 年假微调
	 */
	@Override 
	public ReturnData AnnualLeaveAdjustment(String vacationId, String vacationMold, String annualLeave,
			String adjustingInstruction,String auditorEmployeeId,String year) {
		// TODO Auto-generated method stub
		ReturnData returndata = new ReturnData();
		double limitChange = 0;
		String changingReason;
		if (StringUtils.isEmpty(vacationId) || StringUtils.isEmpty(vacationMold) || StringUtils.isEmpty(annualLeave) || StringUtils.isEmpty(year)){
			returndata.setReturnCode("3006");
			returndata.setMessage("参数为空");
            return returndata;
		}
		
		//判断调整增减
		if(vacationMold.equals("0")){
			limitChange = Double.parseDouble(annualLeave);
		}
		if(vacationMold.equals("1")){
			limitChange = Double.parseDouble("-"+annualLeave);
		}
		
		//查询年假假期详情最后一次修改的值
		VacationDetails vacationDetails = vacationDetailsMapper.SelectVacationIdByEndResult(vacationId,"0",year);
		
		//若员工没有任何假期操作
		if(vacationDetails == null){
			//新增微调为第一个假期操作
			VacationDetails vd = new VacationDetails();
			vd.setVacationDetailsId(FormatUtil.createUuid());
			vd.setVacationId(vacationId);
			vd.setVacationType("0");
			vd.setVacationMold(vacationMold);
			vd.setLimitChange(annualLeave);
			vd.setVacationTotal(String.valueOf(limitChange));
			vd.setVacationBalance(String.valueOf(limitChange));
			
			if("0".equals(auditorEmployeeId)){
				changingReason = vd.Tweaks;
			}else{
				changingReason = vd.manualAdjustment;
			}
			
			vd.setChangingReason(changingReason);
			vd.setAdjustingInstruction(adjustingInstruction);
			vd.setAuditorEmployeeId(auditorEmployeeId);
			vd.setChangeingDate(sdf.format(new Date()));
			vd.setYear(year);
			
			int num = vacationMapper.UpdateAnnualLeave(vacationId, String.valueOf(limitChange), String.valueOf(limitChange),year);
			
			if(num > 0){
				vacationDetailsMapper.insertSelective(vd);
				
				returndata.setReturnCode("3000");
				returndata.setMessage("数据请求成功");
		        return returndata;
			}
		}else{
			//使用查询出来最后一条结果的总额和余额  加上调整的值
			double i = Double.parseDouble(vacationDetails.getVacationTotal())+limitChange;
			double o = Double.parseDouble(vacationDetails.getVacationBalance())+limitChange;
			
			VacationDetails vd = new VacationDetails();
			vd.setVacationDetailsId(FormatUtil.createUuid());
			vd.setVacationId(vacationId);
			vd.setVacationType("0");
			vd.setVacationMold(vacationMold);
			vd.setLimitChange(annualLeave);
			vd.setVacationTotal(String.valueOf(i));
			vd.setVacationBalance(String.valueOf(o));
			
			if("0".equals(auditorEmployeeId)){
				changingReason = vd.Tweaks;
			}else{
				changingReason = vd.manualAdjustment;
			}
			
			vd.setChangingReason(changingReason);
			vd.setAdjustingInstruction(adjustingInstruction);
			vd.setAuditorEmployeeId(auditorEmployeeId);
			vd.setChangeingDate(sdf.format(new Date()));
			vd.setYear(year);
			
			int num = vacationMapper.UpdateAnnualLeave(vacationId,String.valueOf(i),String.valueOf(o),year);
			
			if(num > 0){
				vacationDetailsMapper.insertSelective(vd);
				
				returndata.setReturnCode("3000");
				returndata.setMessage("数据请求成功");
		        return returndata;
			}
		}
		
		returndata.setReturnCode("3001");
		returndata.setMessage("服务器错误");
        return returndata;
	}

	/**
	 * 调休微调
	 */
	@Override
	public ReturnData AdjustRestAdjustment(String vacationId, String vacationMold, String adjustRest,
			String adjustingInstruction, String auditorEmployeeId,String year) {
		// TODO Auto-generated method stub
		ReturnData returndata = new ReturnData();
		Double limitChange = 0.0;
		String changingReason;
		
		if (StringUtils.isEmpty(vacationId) || StringUtils.isEmpty(vacationMold) || StringUtils.isEmpty(adjustRest)){
			returndata.setReturnCode("3006");
			returndata.setMessage("参数为空");
            return returndata;
		}
		
		//判断调整增减
		if(vacationMold.equals("0")){
			limitChange = Double.parseDouble(adjustRest);
		}
		if(vacationMold.equals("1")){
			limitChange = Double.parseDouble("-"+adjustRest);
		}
				
		//查询调休假期详情最后一次修改的值
		VacationDetails vacationDetails = vacationDetailsMapper.SelectVacationIdByEndResult(vacationId,"1",null);
		
		//若员工没有任何假期操作
		if(vacationDetails == null){
			//新增微调为第一个假期操作
			VacationDetails vd = new VacationDetails();
			vd.setVacationDetailsId(FormatUtil.createUuid());
			vd.setVacationId(vacationId);
			vd.setVacationType("1");
			vd.setVacationMold(vacationMold);
			vd.setLimitChange(adjustRest);
			vd.setVacationTotal(String.valueOf(limitChange));
			vd.setVacationBalance(String.valueOf(limitChange));
			vd.setAdjustingInstruction(adjustingInstruction);
			
			if("0".equals(auditorEmployeeId)){
				changingReason = vd.Tweaks;
			}else{
				changingReason = vd.manualAdjustment;
			}
			
			vd.setChangingReason(changingReason);
			vd.setAuditorEmployeeId(auditorEmployeeId);
			vd.setChangeingDate(sdf.format(new Date()));
			vd.setYear(year);
			
			int num = vacationDetailsMapper.insertSelective(vd);
			
			if(num > 0){
				vacationMapper.UpdateAdjustRest(vacationId, String.valueOf(limitChange), String.valueOf(limitChange));
				
				returndata.setReturnCode("3000");
				returndata.setMessage("数据请求成功");
		        return returndata;
			}
		}else{
			//使用查询出来最后一条结果的总额和余额  加上调整的值
			double i = Double.parseDouble(vacationDetails.getVacationTotal())+limitChange;
			double o = Double.parseDouble(vacationDetails.getVacationBalance())+limitChange;
			
			VacationDetails vd = new VacationDetails();
			vd.setVacationDetailsId(FormatUtil.createUuid());
			vd.setVacationId(vacationId);
			vd.setVacationType("1");
			vd.setVacationMold(vacationMold);
			vd.setLimitChange(adjustRest);
			vd.setVacationTotal(String.valueOf(i));
			vd.setVacationBalance(String.valueOf(o));
			vd.setAdjustingInstruction(adjustingInstruction);
			
			if("0".equals(auditorEmployeeId)){
				changingReason = vd.Tweaks;
			}else{
				changingReason = vd.manualAdjustment;
			}
			
			vd.setChangingReason(changingReason);
			vd.setAuditorEmployeeId(auditorEmployeeId);
			vd.setChangeingDate(sdf.format(new Date()));
			vd.setYear(year);
			
			int num = vacationDetailsMapper.insertSelective(vd);
			
			if(num > 0){
				vacationMapper.UpdateAdjustRest(vacationId,String.valueOf(i),String.valueOf(o));
				
				returndata.setReturnCode("3000");
				returndata.setMessage("数据请求成功");
		        return returndata;
			}
		}
		
		returndata.setReturnCode("3000");
		returndata.setMessage("服务器错误");
        return returndata;
	}

	/**
	 * 查询年假必填资料不完善人员信息
	 */
	@Override
	public ReturnData IncompleteData(String companyId){
		ReturnData returndata = new ReturnData();
		List<Employee> list = employeeDao.findAllEmployeeByCompanyId(companyId);
		List<Employee> result = new ArrayList<>();
		
		for (Employee employee : list) {
			if(StringUtils.isEmpty(employee.getSeniority()) || StringUtils.isEmpty(employee.getProbationaryExpired()) || StringUtils.isEmpty(employee.getEntryTime())){
				result.add(employee);
			}
		}
		returndata.setData(JSON.toJSON(result));
		returndata.setReturnCode("3000");
		returndata.setMessage("数据请求成功");
        return returndata;
	}
	
	/**
	 * 年假一键清零
	 */
	@Override
	public ReturnData ResetAnnualLeave(String companyId,String year,String auditorEmployeeId) {
		// TODO Auto-generated method stub
		ReturnData returndata = new ReturnData();
		
		List<Vacation> list = vacationMapper.selectResetAnnualLeave(companyId, year);
			
		for (Vacation vacation : list) {
			 resetAnnualLeave(vacation.getVacationId(),"1", vacation.getAnnualLeaveBalance(),"年假一键清零", auditorEmployeeId, year);
		}
		
		returndata.setReturnCode("3000");
		returndata.setMessage("数据请求成功");
		return returndata;
	}

	/**
	 * 年假一键生成
	 */
	@Override
	public ReturnData AnnualLeaveGenerate(String companyId,String year,String auditorEmployeeId) {
		// TODO Auto-generated method stub
		ReturnData returndata = new ReturnData();
		try{
		
			//查询该公司所有人员
			List<Employee> list = employeeDao.findAllEmployeeByCompanyId(companyId);
			
			for (Employee employee : list) {
				//判断员工工龄字段不能为空   试用期到期时间    入职时间
				if(StringUtils.isNotEmpty(employee.getSeniority()) && StringUtils.isNotEmpty(employee.getProbationaryExpired()) && StringUtils.isNotEmpty(employee.getEntryTime())){
					
					//年假天数
					double AVday = 0.0;
					
					try {
						AVday = cv.ABCAnnualFormula(employee.getSeniority(), 1, employee.getEntryTime(), 0, 0);
						//计算入职时间到现在时间的年假  累加入职前年假
						AVday += cv.computeAnnualVacation(sdate.parse(employee.getEntryTime()));
						
						if(AVday>15){
							AVday = 15.0;
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(AVday>-1){
						Vacation vacation = vacationMapper.SelectEmployeeVacation(companyId, null, employee.getEmployeeId(),year);
						
						if(vacation != null ){
							if(!vacation.getYear().equals(year)){
								Vacation v = new Vacation();
								v.setVacationId(vacation.getVacationId());
								v.setCompanyId(employee.getCompanyId());
								v.setDepartmentId(employee.getDepartmentId());
								v.setEmployeeId(employee.getEmployeeId());
								v.setAnnualLeaveTotal(String.valueOf(AVday));
								v.setAnnualLeaveBalance(String.valueOf(AVday));
								v.setAdjustRestTotal(vacation.getAdjustRestTotal());
								v.setAdjustRestBalance(vacation.getAdjustRestBalance());
								v.setYear(year);
								
								int num = vacationMapper.insertSelective(v);
								
								if(num>0){
									AnnualLeaveAdjustment(vacation.getVacationId(),"0",String.valueOf(AVday),"年假一键生成", auditorEmployeeId, year);
								}
							}
						}else{
							Vacation v = new Vacation();
							v.setVacationId(FormatUtil.createUuid());
							v.setCompanyId(employee.getCompanyId());
							v.setDepartmentId(employee.getDepartmentId());
							v.setEmployeeId(employee.getEmployeeId());
							v.setAnnualLeaveTotal(String.valueOf(AVday));
							v.setAnnualLeaveBalance(String.valueOf(AVday));
							v.setAdjustRestTotal("0");
							v.setAdjustRestBalance("0");
							v.setYear(year);
							
							int num = vacationMapper.insertSelective(v);
							
							if(num>0){
								AnnualLeaveAdjustment(v.getVacationId(),"0",String.valueOf(AVday),"年假一键生成", auditorEmployeeId, year);
							}
						}
					}
				}
			}
			returndata.setReturnCode("3000");
			returndata.setMessage("数据请求成功");
			return returndata;
		}catch(Exception e){
			returndata.setReturnCode("3001");
			returndata.setMessage("服务器错误");
			return returndata;
		}
	}

	/**
	 * 清除余额
	 * @param vacationId
	 * @param vacationMold
	 * @param annualLeave
	 * @param adjustingInstruction
	 * @param auditorEmployeeId
	 * @param year
	 * @return
	 */
	public ReturnData resetAnnualLeave(String vacationId, String vacationMold, String annualLeave,
			String adjustingInstruction,String auditorEmployeeId,String year) {
		// TODO Auto-generated method stub
		ReturnData returndata = new ReturnData();
		double limitChange = 0;
		String changingReason;
		if (StringUtils.isEmpty(vacationId) || StringUtils.isEmpty(vacationMold) || StringUtils.isEmpty(annualLeave) || StringUtils.isEmpty(year)){
			returndata.setReturnCode("3006");
			returndata.setMessage("参数为空");
            return returndata;
		}
		
		//判断调整增减
		if(vacationMold.equals("0")){
			limitChange = Double.parseDouble(annualLeave);
		}
		if(vacationMold.equals("1")){
			limitChange = Double.parseDouble("-"+annualLeave);
		}
		
		//查询年假假期详情最后一次修改的值
		VacationDetails vacationDetails = vacationDetailsMapper.SelectVacationIdByEndResult(vacationId,"0",year);
		
		//若员工没有任何假期操作
		if(vacationDetails == null){
			//新增微调为第一个假期操作
			VacationDetails vd = new VacationDetails();
			vd.setVacationDetailsId(FormatUtil.createUuid());
			vd.setVacationId(vacationId);
			vd.setVacationType("0");
			vd.setVacationMold(vacationMold);
			vd.setLimitChange(annualLeave);
			vd.setVacationTotal(String.valueOf(limitChange));
			vd.setVacationBalance(String.valueOf(limitChange));
			
			if("0".equals(auditorEmployeeId)){
				changingReason = vd.Tweaks;
			}else{
				changingReason = vd.manualAdjustment;
			}
			
			vd.setChangingReason(changingReason);
			vd.setAdjustingInstruction(adjustingInstruction);
			vd.setAuditorEmployeeId(auditorEmployeeId);
			vd.setChangeingDate(sdf.format(new Date()));
			vd.setYear(year);
			
			int num = vacationMapper.UpdateAnnualLeave(vacationId, String.valueOf(limitChange), String.valueOf(limitChange),year);
			
			if(num > 0){
				vacationDetailsMapper.insertSelective(vd);
				
				returndata.setReturnCode("3000");
				returndata.setMessage("数据请求成功");
		        return returndata;
			}
		}else{
			//使用查询出来最后一条结果的总额和余额  加上调整的值
			double o = Double.parseDouble(vacationDetails.getVacationBalance())+limitChange;
			
			VacationDetails vd = new VacationDetails();
			vd.setVacationDetailsId(FormatUtil.createUuid());
			vd.setVacationId(vacationId);
			vd.setVacationType("0");
			vd.setVacationMold(vacationMold);
			vd.setLimitChange(annualLeave);
			vd.setVacationTotal(vacationDetails.getVacationTotal());
			vd.setVacationBalance(String.valueOf(o));
			
			if("0".equals(auditorEmployeeId)){
				changingReason = vd.Tweaks;
			}else{
				changingReason = vd.manualAdjustment;
			}
			
			vd.setChangingReason(changingReason);
			vd.setAdjustingInstruction(adjustingInstruction);
			vd.setAuditorEmployeeId(auditorEmployeeId);
			vd.setChangeingDate(sdf.format(new Date()));
			vd.setYear(year);
			
			int num = vacationMapper.UpdateAnnualLeave(vacationId,vacationDetails.getVacationTotal(),String.valueOf(o),year);
			
			if(num > 0){
				vacationDetailsMapper.insertSelective(vd);
				
				returndata.setReturnCode("3000");
				returndata.setMessage("数据请求成功");
		        return returndata;
			}
		}
		
		returndata.setReturnCode("3001");
		returndata.setMessage("服务器错误");
        return returndata;
	}
	
}
