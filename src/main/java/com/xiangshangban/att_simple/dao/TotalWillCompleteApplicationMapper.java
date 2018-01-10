package com.xiangshangban.att_simple.dao;

import com.xiangshangban.att_simple.bean.TotalWillCompleteApplication;

public interface TotalWillCompleteApplicationMapper {
    int deleteByPrimaryKey(String applcationNo);

    int insert(TotalWillCompleteApplication record);

    int insertSelective(TotalWillCompleteApplication record);

    TotalWillCompleteApplication selectByPrimaryKey(String applcationNo);

    int updateByPrimaryKeySelective(TotalWillCompleteApplication record);

    int updateByPrimaryKey(TotalWillCompleteApplication record);
}