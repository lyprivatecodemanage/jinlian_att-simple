package com.xiangshangban.att_simple.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xiangshangban.att_simple.bean.VacationDetails;

@Mapper
public interface VacationDetailsMapper {

	int deleteByPrimaryKey(String vacationDetailsId);

    int insert(VacationDetails record);

    int insertSelective(VacationDetails record);

    VacationDetails selectByPrimaryKey(String vacationDetailsId);

    int updateByPrimaryKeySelective(VacationDetails record);

    int updateByPrimaryKey(VacationDetails record);
    
    /*-------------------------------------华丽的分割线-----------------------------------------*/
    
    /**
     * 查询某个员工的最后一次年假调整的对象信息
     * @param vacationId
     * @return
     */
    VacationDetails SelectVacationIdByEndResult(@Param("vacationId")String vacationId,@Param("vacationType")String vacationType,@Param("year")String year);
    
    /**
     * 查询假期详情表 某位员工的记录条数
     * @return
     */
    int SelectTotalNum(@Param("vacationId")String vacationId,@Param("vacationType")String vacationType,
    		@Param("changingReason")String changingReason,@Param("year")String year);
    
    /**
     * 查询某个员工的假期详情里的所有记录并进行模糊分页处理
     * @param vacationId
     * @param vacationType
     * @param changingReason
     * @param changeingDateRank
     * @param pageExcludeNumber
     * @param pageNum
     * @return
     */
    List<VacationDetails> SelectVacationDetails(@Param("vacationId")String vacationId,@Param("vacationType")String vacationType,
    		@Param("changingReason")String changingReason,@Param("changeingDateRank")String changeingDateRank,@Param("pageExcludeNumber")String pageExcludeNumber,@Param("pageNum")String pageNum,@Param("year")String year);

    
}