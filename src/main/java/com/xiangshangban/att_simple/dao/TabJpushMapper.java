package com.xiangshangban.att_simple.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.TabJpush;

@Mapper
public interface TabJpushMapper {
	Integer insertTabJpush(TabJpush tabJpush);
}
