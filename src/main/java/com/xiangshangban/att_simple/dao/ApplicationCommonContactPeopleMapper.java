package com.xiangshangban.att_simple.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xiangshangban.att_simple.bean.Application;
import com.xiangshangban.att_simple.bean.ApplicationCommonContactPeople;
@Mapper
public interface ApplicationCommonContactPeopleMapper {
	/**
	 * 分页查询常用联系人
	 * @param employeeId
	 * @param companyId
	 * @param type
	 * @param page
	 * @param count
	 * @return
	 */
	List<ApplicationCommonContactPeople> commonContactPeopleList(@Param("employeeId")String employeeId,@Param("companyId")String companyId,
			@Param("type")String type,@Param("page")String page,@Param("count")String count);
	/**
	 * 查询employeId对应的常用联系人个数,条件参数:employeeId,companyId,type
	 * @param applicationCommonContactPeople
	 * @return
	 */
	Integer selectCountByEmployeeIdAndCompanyId(@Param("employeeId")String employeeId,@Param("companyId")String companyId,
			@Param("type")String type);
	/**
	 * 查询不同类型的常用联系人,条件:employeeId,companyId,type
	 * @param application
	 * @return
	 */
	List<ApplicationCommonContactPeople> selectByEmployeeIdAndCompanyIdAndType(Application application);
	/**
	 * 查询联系人是否已存在
	 * @param applicationCommonContactPeople
	 * @return
	 */
	Integer selectByAll(ApplicationCommonContactPeople applicationCommonContactPeople);
	/**
	 * 删除常用联系人,条件:employeeId,companyId,type
	 * @param application
	 * @return
	 */
	int deleteByEmployeeIdAndCompanyIdAndType(@Param("employeeId")String employeeId,@Param("companyId")String companyId,@Param("type")String type);
	
	/** 系统  **/
    int deleteByPrimaryKey(String id);

    int insert(ApplicationCommonContactPeople record);

    int insertSelective(ApplicationCommonContactPeople record);

    ApplicationCommonContactPeople selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ApplicationCommonContactPeople record);

    int updateByPrimaryKey(ApplicationCommonContactPeople record);
}