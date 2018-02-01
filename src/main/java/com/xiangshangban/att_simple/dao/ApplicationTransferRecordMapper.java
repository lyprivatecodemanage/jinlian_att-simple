package com.xiangshangban.att_simple.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.ApplicationTransferRecord;
@Mapper
public interface ApplicationTransferRecordMapper {
	/**
	 * 根据单号查询转移记录
	 * @param applicationNo
	 * @return
	 */
	List<ApplicationTransferRecord> selectTransferByApplicationNo(String applicationNo);
	
	
	//系统----------------------------------------------------------------
    int deleteByPrimaryKey(String id);

    int insert(ApplicationTransferRecord record);

    int insertSelective(ApplicationTransferRecord record);

    ApplicationTransferRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ApplicationTransferRecord record);

    int updateByPrimaryKey(ApplicationTransferRecord record);
}