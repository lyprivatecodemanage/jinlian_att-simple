package com.xiangshangban.att_simple.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.att_simple.bean.Vacation;
import com.xiangshangban.att_simple.bean.VacationDetails;
import com.xiangshangban.att_simple.dao.VacationDetailsMapper;
import com.xiangshangban.att_simple.dao.VacationMapper;

@Service("vacationDetailsService")
public class VacationDetailsServiceImpl implements VacationDetailsService {

	@Autowired
	VacationDetailsMapper vacationDetailsMapper;
	
	@Autowired
	VacationMapper vacationMapper;
	
	@Override
	public Map<String,Object> SelectVacationDetails(String vacationId, String vacationType, String changingReason,
			String changeingDateRank, String pageExcludeNumber, String pageNum) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<>();
		
		List<VacationDetails> list = vacationDetailsMapper.SelectVacationDetails(vacationId, vacationType, changingReason, changeingDateRank, pageExcludeNumber, pageNum);
		
		if(list != null){
			Vacation v = vacationMapper.selectByPrimaryKey(vacationId);
			if(v!=null){
				int count = vacationDetailsMapper.SelectTotalNum(vacationId, vacationType, changingReason);
				int pageNo = 0;
				if(count%Integer.parseInt(pageNum)==0){
					pageNo = count/Integer.parseInt(pageNum);
				}else{
					pageNo = count/Integer.parseInt(pageNum)+1;
				}
				
				
				map.put("ketData",JSONObject.toJSON(v));
				map.put("listData",JSONObject.toJSON(list));
				map.put("returnCode", "3000");
				map.put("message", "数据请求成功");
				map.put("totalNum", count);
				map.put("pageNo",pageNo);
		        return map;
			}
		}
		map.put("returnCode", "3001");
		map.put("message", "服务器错误");
        return map;		
	}

}
