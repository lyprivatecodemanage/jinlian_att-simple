package com.xiangshangban.att_simple.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.att_simple.bean.ReturnData;
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
	public ReturnData SelectVacationDetails(String vacationId, String vacationType, String changingReason,
			String changeingDateRank, String pageExcludeNumber, String pageNum) {
		// TODO Auto-generated method stub
		ReturnData returndata = new ReturnData();
		
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
				
				Map<String, Object> map = new HashMap<>();
				map.put("ketData",JSONObject.toJSON(v));
				map.put("listData",JSONObject.toJSON(list));
				
				returndata.setData(map);
				returndata.setTotalPages(count);
				returndata.setPagecountNum(pageNo);
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
