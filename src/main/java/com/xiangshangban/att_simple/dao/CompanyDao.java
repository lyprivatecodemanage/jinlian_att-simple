package com.xiangshangban.att_simple.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.att_simple.bean.Company;

@Mapper
public interface CompanyDao {
    
   
    Company selectByCompany(String companyId);

    
    List<Company> fingdByAllCompany();
    
    Company  findBycompanyNo(String companyNo);
    //查询一个人加入了哪些公司
    List<Company>selectByUserCompany(String Account);
}    