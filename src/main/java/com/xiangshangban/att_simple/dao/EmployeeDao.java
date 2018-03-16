package com.xiangshangban.att_simple.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xiangshangban.att_simple.bean.Employee;
import com.xiangshangban.att_simple.bean.Uusers;

@Mapper
public interface EmployeeDao {

	Employee selectEmployeeByCompanyIdAndEmployeeId(@Param("employeeId")String employeeId,@Param("companyId")String companyId);
	
	/**
	 * 通过公司id查询公司所有的在职员工
	 * @param companyId
	 * @return
	 */
	List<Employee> selectAllEmployeeByCompanyId(String companyId);
	
	// 查询单条员信息
	Employee selectByEmployee(@Param("employeeId") String employeeId, @Param("companyId") String companyId);


	List<Employee> findByAllEmployee(Map<String, String> map);
	
	List<Employee> findAllEmployeeByCompanyId(String companyId);

	List<Employee> findByLiZhiemployee(String companyId);

	List<Employee> findBydeptemployee(@Param("departmentId") String departmentId, @Param("companyId") String companyId);

	List<Employee> findByMoreEmployee(Map<String, String> map);

	// 分页查询员工信息
	List<Employee> selectByAllFnyeEmployee(@Param("companyId")String companyId,@Param("numPage") String numPage,
			@Param("numRecordCount") String numRecordCount, @Param("employeeName") String employeeName,
			@Param("employeeSex") String employeeSex, @Param("departmentName") String departmentName,
			@Param("postName") String postName, @Param("employeeStatus") String employeeStatus,
			@Param("departmentId")String departmentId,@Param("type")String type);
	
	int selectCountEmployeeFromCompany(@Param("companyId")String companyId,/*@Param("numPage") String numPage,
			@Param("numRecordCount") String numRecordCount,*/ @Param("employeeName") String employeeName,
			@Param("employeeSex") String employeeSex, @Param("departmentName") String departmentName,
			@Param("postName") String postName, @Param("employeeStatus") String employeeStatus,
			@Param("departmentId")String departmentId,@Param("type")String type);


	Employee findByemploginName(String loginName);

	Employee findByemployeeNo(@Param("employeeNo") String employeeNo, @Param("companyId") String companyId);


	List<Employee> findByposcounttemp(@Param("postId") String postId, @Param("companyId") String companyId);

	List<Employee> findByruzhiempinfo(String companyId);

	List<Employee> selectByAllEmployee(String companyId);

	// 根据人员姓名，所属部门，主岗位动态查询所有在职人员以及所属部门和主岗位
	List<Employee> findBydynamicempadmin(Map<String, String> map);

	// 查询所有在职人员以及所属部门和主岗位
	List<Employee> findByempadmin(Map<String, String> map);

	List<Employee> findByempadmins(Map<String, String> map);

	/**
	 * 根据登录名查询是否用户Id
	 * 
	 * @param loginName
	 *            登录名
	 * @return
	 */
	String getUserIdByLoginName(String loginName);

	List<Employee> findEmployeeByDepartmentId(@Param("companyId") String companyId,
			@Param("departmentId") String departmentId);
	
	Employee selectByEmployeeFromApp(@Param("companyId")String companyId,@Param("userId")String userId,@Param("type")String type);
	
	Employee selectEmployeeByLoginNameAndCompanyId(@Param("loginName")String loginName,@Param("companyId")String companyId);
	
	/**
	 * 查询导出人员
	 * @param companyId 
	 * @return
	 */
	List<Employee> findExport(@Param("companyId")String companyId);

	
	Employee selectDirectPersonLoginName(@Param("employeeId")String employeeId,@Param("companyId")String companyId);
	/**
	 * @author 李业   查询公司首页管理员展示信息
	 * @param employeeId
	 * @param companyId
	 * @return
	 */
	Employee selectAdminEmployeeDetails(@Param("employeeId")String employeeId,@Param("companyId")String companyId);
	/**
	 * 常用联系人分页
	 * @param employeeId
	 * @param companyId
	 * @param type
	 * @param page
	 * @param count
	 * @return
	 */
	List<Employee> selectCommonContactPeoplePage(@Param("employeeId")String employeeId,@Param("companyId")String companyId,@Param("page")String page,@Param("count")String count);
	/**
	 * 根据人员id,部门id,公司id查询对应的名称
	 * @param employeeId
	 * @param departmentId
	 * @param companyId
	 * @return
	 */
	Employee selectNameByEmployeeIdAndDepartmentIdAndCompanyId(@Param("employeeId")String employeeId,@Param("departmentId")String departmentId,@Param("companyId")String companyId);
	/**
	 * 根据员工id查询员工的手机号 
	 * @param userid
	 * @return
	 */
	Uusers selectPhoneByEmployeeIdFromUUsers(String userid);
}
