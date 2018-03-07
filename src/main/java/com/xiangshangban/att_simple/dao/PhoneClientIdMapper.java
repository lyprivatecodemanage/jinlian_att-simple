package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xiangshangban.att_simple.bean.PhoneClientId;

@Mapper
public interface PhoneClientIdMapper {
	
	PhoneClientId selectByPhone(String phone);
	
	PhoneClientId selectDefaultCompanyByUserId(String userId);
	
	PhoneClientId selectByClientId(String clientId);
	
	Integer insertPhoneClientId(PhoneClientId phoneClientId);
	
	Integer deletePhoneClientIdByPhone(String phone);
	
	Integer deletePhoneClientIdByClientId(@Param("clientId")String clientId);
	
}
