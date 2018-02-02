package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.OSSFile;

@Mapper
public interface OSSFileDao {

	public void addOSSFile(OSSFile oSSFile);
}
