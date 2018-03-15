package com.xiangshangban.att_simple.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiangshangban.att_simple.bean.ApplicationFillCard;
import com.xiangshangban.att_simple.bean.Paging;
import com.xiangshangban.att_simple.bean.ReturnData;
import com.xiangshangban.att_simple.dao.ApplicationFillCardMapper;

@Service("applicationFillCardService")
public class ApplicationFillCardServiceImpl implements ApplicationFillCardService {

	@Autowired
	ApplicationFillCardMapper applicationFillCardMapper;

	@Override
	public ReturnData SelectFuzzyPagel(Paging paging) {
		// TODO Auto-generated method stub
		ReturnData data = new ReturnData();
		
		List<ApplicationFillCard> list = applicationFillCardMapper.SelectFuzzyPagel(paging);
		
		int num = applicationFillCardMapper.FindAllNumber(paging);
		
		data.setData(list);
		data.setReturnCode("3000");
		data.setMessage("数据请求成功");
		data.setTotalPages(num);
		
		return data;
	}

}
