package com.xiangshangban.att_simple.dao;

import com.xiangshangban.att_simple.bean.checkingIn;

public interface checkingInMapper {
    int deleteByPrimaryKey(String checkinginId);

    int insert(checkingIn record);

    int insertSelective(checkingIn record);

    checkingIn selectByPrimaryKey(String checkinginId);

    int updateByPrimaryKeySelective(checkingIn record);

    int updateByPrimaryKey(checkingIn record);
}