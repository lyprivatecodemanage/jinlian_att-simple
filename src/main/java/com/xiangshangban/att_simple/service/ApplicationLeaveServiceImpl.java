package com.xiangshangban.att_simple.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.att_simple.bean.ApplicationLeave;
import com.xiangshangban.att_simple.bean.Paging;
import com.xiangshangban.att_simple.bean.ReturnData;
import com.xiangshangban.att_simple.dao.ApplicationLeaveMapper;

@Service("applicationLeaveService")
public class ApplicationLeaveServiceImpl implements ApplicationLeaveService {

	SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM");
	
	@Autowired
	ApplicationLeaveMapper applicationLeaveMapper;
	
	@Override
	public ReturnData selectCompleteLeave(Paging paging) {
		// TODO Auto-generated method stub
		ReturnData returndata = new ReturnData();
		
		List<ApplicationLeave> list = applicationLeaveMapper.selectCompleteLeave(paging);
		
		if(list != null){
			returndata.setData(JSONObject.toJSON(list));
			//总条数
			int pageTotalNum = applicationLeaveMapper.selectTotalNum(paging);
			
			if(pageTotalNum%Integer.parseInt(paging.getPageNum())==0){
				returndata.setPagecountNum(pageTotalNum/Integer.parseInt(paging.getPageNum()));
			}else{
				returndata.setPagecountNum(pageTotalNum/Integer.parseInt(paging.getPageNum())+1);
			}
			
			returndata.setTotalPages(pageTotalNum);
			returndata.setReturnCode("3000");
			returndata.setMessage("数据请求成功");
			return returndata;
		}
		
		returndata.setReturnCode("3001");
		returndata.setMessage("服务器错误");
		return returndata;
	}

	@Override
	public ReturnData selectLeaveKeyData(String companyId) {
		// TODO Auto-generated method stub
		ReturnData data = new ReturnData();
		
		String applicationTime = sdf.format(new Date())+"%";
		
		List<ApplicationLeave> list = applicationLeaveMapper.selectLeaveKeyData(companyId,applicationTime);
		
		if(list!=null){
			data.setData(JSONObject.toJSON(list));
			data.setReturnCode("3000");
			data.setMessage("数据请求成功");
			return data;
		}
		data.setReturnCode("3001");
		data.setMessage("服务器错误");
		return data;
	}

}
