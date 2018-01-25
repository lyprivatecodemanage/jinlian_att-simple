package com.xiangshangban.att_simple.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xiangshangban.att_simple.bean.Paging;
import com.xiangshangban.att_simple.bean.ReportDaily;
import com.xiangshangban.att_simple.bean.ReportDailyKey;
@Mapper
public interface ReportDailyMapper {
    int deleteByPrimaryKey(ReportDailyKey key);

    int insert(ReportDaily record);

    int insertSelective(ReportDaily record);

    ReportDaily selectByPrimaryKey(ReportDailyKey key);

    int updateByPrimaryKeySelective(ReportDaily record);

    int updateByPrimaryKey(ReportDaily record);
    
	int deleteByDate(@Param("companyId")String companyId, 
			@Param("employeeId")String employeeId, @Param("attDate")String attDate);

	ReportDaily selectByDate(@Param("companyId")String companyId, 
			@Param("employeeId")String employeeId, @Param("attDate")String attDate);
	
	/**
	 * 查询昨日请假人数
	 * @param companyId
	 * @param attDate
	 * @return
	 */
	int selectYesterdayLeaveNumber(@Param("companyId")String companyId,@Param("attDate")String attDate);
	
	/**
	 * 查询昨日考勤异常人数
	 * @param companyId
	 * @param attDate
	 * @return
	 */
	int selectYesterdayExcption(@Param("companyId")String companyId,@Param("attDate")String attDate);
	
	/**
	 * 使用唯一列查询日报信息
	 * @param rid
	 * @return
	 */
	ReportDaily selectById(String rid);
	
	/**
	 * 考勤日报模糊分页查询
	 * @param paging
	 * @return
	 */
	List<ReportDaily> selectReportDaily(Paging paging);
	
	/**
	 * 考勤日报模糊总数查询
	 * @param paging
	 * @return
	 */
	int selectReportDailyTotalNumber(Paging paging);
}