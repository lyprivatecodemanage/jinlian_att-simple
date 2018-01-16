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
import com.xiangshangban.att_simple.bean.Vacation;
import com.xiangshangban.att_simple.bean.VacationDetails;
import com.xiangshangban.att_simple.bean.paging;
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
	public Map<String,Object> SelectFuzzyPagel(paging paging) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<>();
		
		if(StringUtils.isEmpty(paging.getPageNum()) || StringUtils.isEmpty(paging.getVarPageNo())){
			map.put("returnCode", "3006");
			map.put("message", "参数为空");
            return map;
		}

		List<Vacation> list = vacationMapper.SelectFuzzyPagel(paging);
		
		if(list != null){
			//查询出条件筛选后的总条数
			int count = vacationMapper.SelectTotalNumber(paging.getCompanyId(), paging.getDepartmentId(), paging.getEmployeeName());
			
			int pageNo = count%Integer.parseInt(paging.getPageNum())==0?count/Integer.parseInt(paging.getPageNum()):count/Integer.parseInt(paging.getPageNum())+1;
			
			map.put("totalNum", count);
			map.put("pageNo", pageNo);
			map.put("data",JSONObject.toJSON(list));
			map.put("returnCode", "3000");
			map.put("message", "数据请求成功");
	        return map;
		}
		
        map.put("returnCode", "3001");
		map.put("message", "服务器错误");
        return map;
	}

	@Override
	public Map<String, Object> AnnualLeaveAdjustment(String vacationId, String vacationMold, String annualLeave,
			String adjustingInstruction,String auditorEmployeeId) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		int limitChange = 0;
		
		if (StringUtils.isEmpty(vacationId) || StringUtils.isEmpty(vacationMold) || StringUtils.isEmpty(annualLeave)){
			map.put("returnCode", "3006");
			map.put("message", "参数为空");
            return map;
		}
		
		//判断调整增减
		if(vacationMold.equals("0")){
			limitChange = Integer.parseInt(annualLeave);
		}
		if(vacationMold.equals("1")){
			limitChange = Integer.parseInt("-"+annualLeave);
		}
		
		//查询年假假期详情最后一次修改的值
		VacationDetails vacationDetails = vacationDetailsMapper.SelectVacationIdByEndResult(vacationId,"0");
		
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
			
			int num = vacationDetailsMapper.insertSelective(vd);
			
			if(num > 0){
				vacationMapper.UpdateAnnualLeave(vacationId, String.valueOf(limitChange), String.valueOf(limitChange));
				
				map.put("returnCode", "3000");
				map.put("message", "数据请求成功");
		        return map;
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
			
			int num = vacationDetailsMapper.insertSelective(vd);
			
			if(num > 0){
				vacationMapper.UpdateAnnualLeave(vacationId,String.valueOf(i),String.valueOf(o));
				
				map.put("returnCode", "3000");
				map.put("message", "数据请求成功");
		        return map;
			}
		}
		
		map.put("returnCode", "3001");
		map.put("message", "服务器错误");
        return map;
	}

	@Override
	public Map<String, Object> AdjustRestAdjustment(String vacationId, String vacationMold, String adjustRest,
			String adjustingInstruction, String auditorEmployeeId) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		int limitChange = 0;
		
		if (StringUtils.isEmpty(vacationId) || StringUtils.isEmpty(vacationMold) || StringUtils.isEmpty(adjustRest)){
			map.put("returnCode", "3006");
			map.put("message", "参数为空");
            return map;
		}
		
		//判断调整增减
		if(vacationMold.equals("0")){
			limitChange = Integer.parseInt(adjustRest);
		}
		if(vacationMold.equals("1")){
			limitChange = Integer.parseInt("-"+adjustRest);
		}
		
		//查询调休假期详情最后一次修改的值
		VacationDetails vacationDetails = vacationDetailsMapper.SelectVacationIdByEndResult(vacationId,"1");
		
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
			
			int num = vacationDetailsMapper.insertSelective(vd);
			
			if(num > 0){
				vacationMapper.UpdateAdjustRest(vacationId, String.valueOf(limitChange), String.valueOf(limitChange));
				
				map.put("returnCode", "3000");
				map.put("message", "数据请求成功");
		        return map;
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
			
			int num = vacationDetailsMapper.insertSelective(vd);
			
			if(num > 0){
				vacationMapper.UpdateAdjustRest(vacationId,String.valueOf(i),String.valueOf(o));
				
				map.put("returnCode", "3000");
				map.put("message", "数据请求成功");
		        return map;
			}
		}
		
		map.put("returnCode", "3001");
		map.put("message", "服务器错误");
        return map;
	}

}
