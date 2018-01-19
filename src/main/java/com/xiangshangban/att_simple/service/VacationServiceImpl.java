package com.xiangshangban.att_simple.service;

import java.io.ObjectOutputStream.PutField;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.att_simple.bean.ReturnData;
import com.xiangshangban.att_simple.bean.Vacation;
import com.xiangshangban.att_simple.bean.VacationDetails;
import com.xiangshangban.att_simple.bean.Paging;
import com.xiangshangban.att_simple.dao.VacationDetailsMapper;
import com.xiangshangban.att_simple.dao.VacationMapper;
import com.xiangshangban.att_simple.utils.FormatUtil;

@Service("vacationService")
public class VacationServiceImpl implements VacationService {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	VacationMapper vacationMapper;
	
	@Autowired
	VacationDetailsMapper vacationDetailsMapper;

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
		
		if(list != null){
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
		
		returndata.setReturnCode("3001");
		returndata.setMessage("服务器错误");
        return returndata;
	}

	@Override
	public ReturnData AnnualLeaveAdjustment(String vacationId, String vacationMold, String annualLeave,
			String adjustingInstruction,String auditorEmployeeId,String year) {
		// TODO Auto-generated method stub
		ReturnData returndata = new ReturnData();
		int limitChange = 0;
		
		if (StringUtils.isEmpty(vacationId) || StringUtils.isEmpty(vacationMold) || StringUtils.isEmpty(annualLeave) || StringUtils.isEmpty(year)){
			returndata.setReturnCode("3006");
			returndata.setMessage("参数为空");
            return returndata;
		}
		
		//判断调整增减
		if(vacationMold.equals("0")){
			limitChange = Integer.parseInt(annualLeave);
		}
		if(vacationMold.equals("1")){
			limitChange = Integer.parseInt("-"+annualLeave);
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
			vd.setChangingReason(vd.manualAdjustment);
			vd.setAdjustingInstruction(adjustingInstruction);
			vd.setAuditorEmployeeId(auditorEmployeeId);
			vd.setChangeingDate(sdf.format(new Date()));
			vd.setYear(year);
			
			int num = vacationDetailsMapper.insertSelective(vd);
			
			if(num > 0){
				vacationMapper.UpdateAnnualLeave(vacationId, String.valueOf(limitChange), String.valueOf(limitChange),year);
				
				returndata.setReturnCode("3000");
				returndata.setMessage("数据请求成功");
		        return returndata;
			}
		}else{
			//使用查询出来最后一条结果的总额和余额  加上调整的值
			int i = Integer.parseInt(vacationDetails.getVacationTotal())+limitChange;
			int o = Integer.parseInt(vacationDetails.getVacationBalance())+limitChange;
			
			VacationDetails vd = new VacationDetails();
			vd.setVacationDetailsId(FormatUtil.createUuid());
			vd.setVacationId(vacationId);
			vd.setVacationType("0");
			vd.setVacationMold(vacationMold);
			vd.setLimitChange(annualLeave);
			vd.setVacationTotal(String.valueOf(i));
			vd.setVacationBalance(String.valueOf(o));
			vd.setChangingReason(vd.manualAdjustment);
			vd.setAdjustingInstruction(adjustingInstruction);
			vd.setAuditorEmployeeId(auditorEmployeeId);
			vd.setChangeingDate(sdf.format(new Date()));
			vd.setYear(year);
			
			int num = vacationDetailsMapper.insertSelective(vd);
			
			if(num > 0){
				vacationMapper.UpdateAnnualLeave(vacationId,String.valueOf(i),String.valueOf(o),year);
				
				returndata.setReturnCode("3000");
				returndata.setMessage("数据请求成功");
		        return returndata;
			}
		}
		
		returndata.setReturnCode("3001");
		returndata.setMessage("服务器错误");
        return returndata;
	}

	@Override
	public ReturnData AdjustRestAdjustment(String vacationId, String vacationMold, String adjustRest,
			String adjustingInstruction, String auditorEmployeeId,String year) {
		// TODO Auto-generated method stub
		ReturnData returndata = new ReturnData();
		int limitChange = 0;
		
		if (StringUtils.isEmpty(vacationId) || StringUtils.isEmpty(vacationMold) || StringUtils.isEmpty(adjustRest)){
			returndata.setReturnCode("3006");
			returndata.setMessage("参数为空");
            return returndata;
		}
		
		//判断调整增减
		if(vacationMold.equals("0")){
			limitChange = Integer.parseInt(adjustRest);
		}
		if(vacationMold.equals("1")){
			limitChange = Integer.parseInt("-"+adjustRest);
		}
		
		//查询调休假期详情最后一次修改的值
		VacationDetails vacationDetails = vacationDetailsMapper.SelectVacationIdByEndResult(vacationId,"1",year);
		
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
			vd.setChangingReason(vd.manualAdjustment);
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
			int i = Integer.parseInt(vacationDetails.getVacationTotal())+limitChange;
			int o = Integer.parseInt(vacationDetails.getVacationBalance())+limitChange;
			
			VacationDetails vd = new VacationDetails();
			vd.setVacationDetailsId(FormatUtil.createUuid());
			vd.setVacationId(vacationId);
			vd.setVacationType("1");
			vd.setVacationMold(vacationMold);
			vd.setLimitChange(adjustRest);
			vd.setVacationTotal(String.valueOf(i));
			vd.setVacationBalance(String.valueOf(o));
			vd.setAdjustingInstruction(adjustingInstruction);
			vd.setChangingReason(vd.manualAdjustment);
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

}
