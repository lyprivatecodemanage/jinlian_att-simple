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
	
	/**
	 * 查询该日期段的日报信息
	 * @param companyId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<ReportDaily> selectDateRangeReportDaily(@Param("companyId")String companyId,@Param("beginDate")String beginDate,@Param("endDate")String endDate,@Param("attDate")String attDate);
	
	/**
	 * 查询某个月该员工是否存在考勤异常
	 * @param employeeId
	 * @return
	 */
	int selectMonthEcxeptionEmployee(@Param("companyId")String companyId,@Param("employeeId")String employeeId,@Param("attDate")String attDate,@Param("dateTime")String dateTime);
	
	/**
	 * 查询一个员工在某月存在几次请假
	 * @param companyId
	 * @param employeeId
	 * @return
	 */
	int selectLeaveCount(@Param("companyId")String companyId,@Param("employeeId")String employeeId,@Param("attDate")String attDate,@Param("dateTime")String dateTime);
	
	/**
	 * 月报数据统计
	 * @param paging
	 * @return
	 */
	List<ReportDaily> selectMonthReportFuzzy(Paging paging);
	
	/**
	 * 月报数据统总数
	 * @param paging
	 * @return
	 */
	int selectMonthReportFuzzyTotalNumber(Paging paging);
	
	/**
	 * 月报报表
	 * @param companyId
	 * @param attDate
	 * @return
	 */
	List<ReportDaily> MonthReportExcel(@Param("companyId")String companyId,@Param("attDate")String attDate,@Param("dateTime")String dateTime);
	/**
	 * 修改应出天数和时长
	 * @param reportDaily
	 * @return
	 */
	int updateWorkTime(ReportDaily reportDaily);
}