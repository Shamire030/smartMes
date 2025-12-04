package com.smartmes.mapper;

import com.smartmes.model.EquipmentData;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

/**
 * 设备数据统计分析Mapper接口
 */
@Mapper
public interface EquipmentDataMapper {
    
    /**
     * 根据ID查询设备数据
     * @param id 主键ID
     * @return 设备数据对象
     */
    EquipmentData selectById(Integer id);
    
    /**
     * 根据数据类型和周期查询设备数据
     * @param dataType 数据类型
     * @param period 统计周期
     * @return 设备数据对象
     */
    EquipmentData selectByDataTypeAndPeriod(String dataType, String period);
    
    /**
     * 根据设备ID查询设备数据
     * @param equipmentId 设备ID
     * @return 设备数据列表
     */
    List<EquipmentData> selectByEquipmentId(Long equipmentId);
    
    /**
     * 根据设备类型查询设备数据
     * @param equipmentType 设备类型
     * @return 设备数据列表
     */
    List<EquipmentData> selectByEquipmentType(String equipmentType);
    
    /**
     * 根据生产线ID查询设备数据
     * @param productionLineId 生产线ID
     * @return 设备数据列表
     */
    List<EquipmentData> selectByProductionLineId(Integer productionLineId);
    
    /**
     * 根据工位ID查询设备数据
     * @param workStationId 工位ID
     * @return 设备数据列表
     */
    List<EquipmentData> selectByWorkStationId(Long workStationId);
    
    /**
     * 根据责任人ID查询设备数据
     * @param responsiblePersonId 责任人ID
     * @return 设备数据列表
     */
    List<EquipmentData> selectByResponsiblePersonId(Integer responsiblePersonId);
    
    /**
     * 根据设备状态查询设备数据
     * @param equipmentStatus 设备状态
     * @return 设备数据列表
     */
    List<EquipmentData> selectByEquipmentStatus(String equipmentStatus);
    
    /**
     * 根据时间范围查询设备数据
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 设备数据列表
     */
    List<EquipmentData> selectByTimeRange(String startTime, String endTime);
    
    /**
     * 根据设备ID和时间范围查询设备数据
     * @param equipmentId 设备ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 设备数据列表
     */
    List<EquipmentData> selectByEquipmentIdAndTimeRange(Long equipmentId, String startTime, String endTime);
    
    /**
     * 根据数据类型查询设备数据列表
     * @param dataType 数据类型
     * @return 设备数据列表
     */
    List<EquipmentData> selectByDataType(String dataType);
    
    /**
     * 分页查询设备数据
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 设备数据列表
     */
    List<EquipmentData> selectPage(Integer offset, Integer limit);
    
    /**
     * 查询设备数据总数
     * @return 数据总数
     */
    Integer selectCount();
    
    /**
     * 查询所有设备数据
     * @return 设备数据列表
     */
    List<EquipmentData> selectAll();
    
    /**
     * 根据条件查询设备数据
     * @param params 查询参数
     * @return 设备数据列表
     */
    List<EquipmentData> selectByParams(Map<String, Object> params);
    
    /**
     * 插入设备数据
     * @param equipmentData 设备数据对象
     * @return 影响行数
     */
    Integer insert(EquipmentData equipmentData);
    
    /**
     * 更新设备数据
     * @param equipmentData 设备数据对象
     * @return 影响行数
     */
    Integer update(EquipmentData equipmentData);
    
    /**
     * 根据ID删除设备数据
     * @param id 主键ID
     * @return 影响行数
     */
    Integer deleteById(Integer id);
    
    /**
     * 批量插入设备数据
     * @param list 设备数据列表
     * @return 影响行数
     */
    Integer batchInsert(List<EquipmentData> list);
    
    /**
     * 根据数据类型和设备统计设备数据
     * @param dataType 数据类型
     * @param equipmentId 设备ID
     * @return 统计结果
     */
    Map<String, Object> countEquipmentByDataTypeAndEquipment(String dataType, Long equipmentId);
    
    /**
     * 根据数据类型和设备统计设备数据
     * @param dataType 数据类型
     * @param equipmentId 设备ID
     * @return 统计结果
     */
    Map<String, Object> countEquipmentDataByDataTypeAndEquipment(String dataType, Long equipmentId);
    
    /**
     * 根据数据类型统计各设备运行数据
     * @param dataType 数据类型
     * @return 设备运行统计列表
     */
    List<Map<String, Object>> countEquipmentDataByEquipment(String dataType);
    
    /**
     * 根据数据类型统计各设备类型数据
     * @param dataType 数据类型
     * @return 设备类型统计列表
     */
    List<Map<String, Object>> countEquipmentByEquipmentType(String dataType);
    
    /**
     * 根据数据类型统计各设备类型运行数据
     * @param dataType 数据类型
     * @return 设备类型运行统计列表
     */
    List<Map<String, Object>> countEquipmentDataByEquipmentType(String dataType);
    
    /**
     * 根据数据类型统计各生产线设备数据
     * @param dataType 数据类型
     * @return 生产线设备统计列表
     */
    List<Map<String, Object>> countEquipmentByProductionLine(String dataType);
    
    /**
     * 根据数据类型统计各生产线设备运行数据
     * @param dataType 数据类型
     * @return 生产线设备运行统计列表
     */
    List<Map<String, Object>> countEquipmentDataByProductionLine(String dataType);
    
    /**
     * 获取设备利用率统计
     * @param dataType 数据类型
     * @return 设备利用率统计
     */
    Map<String, Object> getEquipmentUtilizationStatistics(String dataType);
    
    /**
     * 获取设备OEE统计
     * @param dataType 数据类型
     * @return 设备OEE统计
     */
    Map<String, Object> getEquipmentOEEStatistics(String dataType);
    
    /**
     * 获取设备故障率统计
     * @param dataType 数据类型
     * @return 设备故障率统计
     */
    Map<String, Object> getEquipmentFailureRateStatistics(String dataType);
    
    /**
     * 获取设备运行时间统计
     * @param dataType 数据类型
     * @return 设备运行时间统计
     */
    Map<String, Object> getEquipmentRunningTimeStatistics(String dataType);
    
    /**
     * 获取设备停机时间统计
     * @param dataType 数据类型
     * @return 设备停机时间统计
     */
    Map<String, Object> getEquipmentDowntimeStatistics(String dataType);
    
    /**
     * 获取设备维修次数统计
     * @param dataType 数据类型
     * @return 设备维修次数统计
     */
    Map<String, Object> getEquipmentMaintenanceCountStatistics(String dataType);
    
    /**
     * 获取设备利用率趋势分析
     * @param dataType 数据类型
     * @param limit 限制数量
     * @return 设备利用率趋势分析
     */
    List<Map<String, Object>> getEquipmentUtilizationTrend(String dataType, Integer limit);
    
    /**
     * 获取设备OEE趋势分析
     * @param dataType 数据类型
     * @param limit 限制数量
     * @return 设备OEE趋势分析
     */
    List<Map<String, Object>> getEquipmentOEETrend(String dataType, Integer limit);
    
    /**
     * 获取设备故障率趋势分析
     * @param dataType 数据类型
     * @param limit 限制数量
     * @return 设备故障率趋势分析
     */
    List<Map<String, Object>> getEquipmentFailureRateTrend(String dataType, Integer limit);
    
    /**
     * 获取设备运行时间趋势分析
     * @param dataType 数据类型
     * @param limit 限制数量
     * @return 设备运行时间趋势分析
     */
    List<Map<String, Object>> getEquipmentRunningTimeTrend(String dataType, Integer limit);
    
    /**
     * 根据数据类型统计设备利用率
     * @param dataType 数据类型
     * @return 设备利用率统计列表
     */
    List<Map<String, Object>> getEquipmentUtilizationRate(String dataType);
    
    /**
     * 根据数据类型统计设备OEE
     * @param dataType 数据类型
     * @return 设备OEE统计列表
     */
    List<Map<String, Object>> getEquipmentOEE(String dataType);
    
    /**
     * 根据数据类型统计设备故障率
     * @param dataType 数据类型
     * @return 设备故障率统计列表
     */
    List<Map<String, Object>> getEquipmentFailureRate(String dataType);
    
    /**
     * 根据数据类型统计设备运行时间趋势
     * @param dataType 数据类型
     * @param limit 限制数量
     * @return 设备运行时间趋势数据
     */
    List<Map<String, Object>> getRunningTimeTrend(String dataType, Integer limit);
    
    /**
     * 根据数据类型统计设备利用率趋势
     * @param dataType 数据类型
     * @param limit 限制数量
     * @return 设备利用率趋势数据
     */
    List<Map<String, Object>> getUtilizationRateTrend(String dataType, Integer limit);
    
    /**
     * 根据数据类型统计设备OEE趋势
     * @param dataType 数据类型
     * @param limit 限制数量
     * @return 设备OEE趋势数据
     */
    List<Map<String, Object>> getOEETrend(String dataType, Integer limit);
    
    /**
     * 根据设备ID和数据类型统计设备数据趋势
     * @param equipmentId 设备ID
     * @param dataType 数据类型
     * @param limit 限制数量
     * @return 设备数据趋势
     */
    List<Map<String, Object>> getEquipmentDataTrendByEquipment(Long equipmentId, String dataType, Integer limit);
    
    /**
     * 获取单设备利用率趋势分析
     * @param equipmentId 设备ID
     * @param dataType 数据类型
     * @param limit 限制数量
     * @return 单设备利用率趋势分析
     */
    List<Map<String, Object>> getSingleEquipmentUtilizationTrend(Long equipmentId, String dataType, Integer limit);
    
    /**
     * 获取单设备OEE趋势分析
     * @param equipmentId 设备ID
     * @param dataType 数据类型
     * @param limit 限制数量
     * @return 单设备OEE趋势分析
     */
    List<Map<String, Object>> getSingleEquipmentOEETrend(Long equipmentId, String dataType, Integer limit);
    
    /**
     * 获取单设备故障率趋势分析
     * @param equipmentId 设备ID
     * @param dataType 数据类型
     * @param limit 限制数量
     * @return 单设备故障率趋势分析
     */
    List<Map<String, Object>> getSingleEquipmentFailureRateTrend(Long equipmentId, String dataType, Integer limit);
    
    /**
     * 获取设备综合效率分析
     * @param equipmentId 设备ID
     * @param dataType 数据类型
     * @return 设备综合效率分析结果
     */
    Map<String, Object> getComprehensiveEfficiencyAnalysis(Long equipmentId, String dataType);
    
    /**
     * 获取设备类型效率对比
     * @param dataType 数据类型
     * @return 设备类型效率对比结果
     */
    List<Map<String, Object>> getEfficiencyComparisonByType(String dataType);
    
    /**
     * 获取生产线设备效率对比
     * @param dataType 数据类型
     * @return 生产线设备效率对比结果
     */
    List<Map<String, Object>> getEfficiencyComparisonByProductionLine(String dataType);
    
    /**
     * 统计设备利用率
     * @param dataType 数据类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 设备利用率统计结果
     */
    List<Map<String, Object>> countUtilization(String dataType, String startTime, String endTime);
    
    /**
     * 统计设备OEE
     * @param dataType 数据类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 设备OEE统计结果
     */
    List<Map<String, Object>> countOEE(String dataType, String startTime, String endTime);
    
    /**
     * 统计设备故障率
     * @param dataType 数据类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 设备故障率统计结果
     */
    List<Map<String, Object>> countFailureRate(String dataType, String startTime, String endTime);
    
    /**
     * 获取平均维修时间
     * @param dataType 数据类型
     * @return 平均维修时间统计结果
     */
    Map<String, Object> getAverageRepairTime(String dataType);
    
    /**
     * 获取设备OEE TOP10
     * @param dataType 数据类型
     * @param limit 限制数量
     * @return 设备OEE TOP10统计结果
     */
    List<Map<String, Object>> getTopOEE(String dataType, Integer limit);
    
    /**
     * 批量删除设备数据
     * @param ids ID列表
     * @return 影响行数
     */
    Integer batchDelete(List<Integer> ids);
    
    /**
     * 统计设备数据
     * @param params 查询参数
     * @return 设备数据统计结果
     */
    List<Map<String, Object>> exportEquipmentData(Map<String, Object> params);
    
    /**
     * 获取设备平均运行时间
     * @param dataType 数据类型
     * @return 设备平均运行时间统计结果
     */
    Map<String, Object> getAverageRunningTime(String dataType);
    
    /**
     * 获取设备平均故障次数
     * @param dataType 数据类型
     * @return 设备平均故障次数统计结果
     */
    Map<String, Object> getAverageFailureCount(String dataType);
    
    /**
     * 获取今日设备运行统计
     * @return 今日设备运行统计结果
     */
    Map<String, Object> getTodayEquipmentRunningStatistics();
    
    /**
     * 获取本周设备运行统计
     * @return 本周设备运行统计结果
     */
    Map<String, Object> getWeekEquipmentRunningStatistics();
    
    /**
     * 获取本月设备运行统计
     * @return 本月设备运行统计结果
     */
    Map<String, Object> getMonthEquipmentRunningStatistics();
    
    /**
     * 获取年度设备运行统计
     * @return 年度设备运行统计结果
     */
    Map<String, Object> getYearEquipmentRunningStatistics();
}
