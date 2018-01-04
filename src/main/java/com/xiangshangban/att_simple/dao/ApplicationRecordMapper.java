package com.xiangshangban.att_simple.dao;

import com.xiangshangban.att_simple.bean.ApplicationRecord;

public interface ApplicationRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(ApplicationRecord record);

    int insertSelective(ApplicationRecord record);

    ApplicationRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ApplicationRecord record);

    int updateByPrimaryKey(ApplicationRecord record);
}