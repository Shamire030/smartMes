package com.smartmes.service;

import com.smartmes.model.ProductionData;
import java.util.List;
import java.util.Map;

/**
 * 生产数据统计分析Service接口
 */
public interface ProductionDataService {
    
    /**
     * 根据ID查询生产数据
     * @param id 主键ID
     * @return 生产数据对象
     */
    ProductionData selectById(Integer id);
    
    /**
     * 根据数据类型和周期查询生产数据
     * @param dataType 数据类型
     * @param period 统计周期
     * @return 生产数据对象
     */
    ProductionData selectByDataTypeAndPeriod(String dataType, String period);
    
    /**
     * 根据产品ID查询生产数据
     * @param productId 产品ID
     * @return 生产数据列表
     */
    List<ProductionData> selectByProductId(Integer productId);
    
    /**
     * 根据工单ID查询生产数据
     * @param workOrderId 工单ID
     * @return 生产数据列表
     */
    List<ProductionData> selectByWorkOrderId(Integer workOrderId);
    
    /**
     * 根据班次查询生产数据
     * @param shift 班次
     * @return 生产数据列表
     */
    List<ProductionData> selectByShift(String shift);
    
    /**
     * 根据时间范围查询生产数据
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 生产数据列表
     */
    List<ProductionData> selectByTimeRange(String startTime, String endTime);
    
    /**
     * 根据产品ID和时间范围查询生产数据
     * @param productId 产品ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 生产数据列表
     */
    List<ProductionData> selectByProductIdAndTimeRange(Integer productId, String startTime, String endTime);
    
    /**
     * 根据数据类型查询生产数据列表
     * @param dataType 数据类型
     * @return 生产数据列表
     */
    List<ProductionData> selectByDataType(String dataType);
    
    /**
     * 分页查询生产数据
     * @param page 页码
     * @param limit 每页数量
     * @return Map包含数据列表和总数
     */
    Map<String, Object> selectPage(Integer page, Integer limit);
    
    /**
     * 查询所有生产数据
     * @return 生产数据列表
     */
    List<ProductionData> selectAll();
    
    /**
     * 根据条件查询生产数据
     * @param params 查询参数
     * @return 生产数据列表
     */
    List<ProductionData> selectByParams(Map<String, Object> params);
    
    /**
     * 插入生产数据
     * @param productionData 生产数据对象
     * @return 影响行数
     */
    Integer insert(ProductionData productionData);
    
    /**
     * 更新生产数据
     * @param productionData 生产数据对象
     * @return 影响行数
     */
    Integer update(ProductionData productionData);
    
    /**
     * 根据ID删除生产数据
     * @param id 主键ID
     * @return 影响行数
     */
    Integer deleteById(Integer id);
    
    /**
     * 批量插入生产数据
     * @param list 生产数据列表
     * @return 影响行数
     */
    Integer batchInsert(List<ProductionData> list);
    
    /**
     * 批量删除生产数据
     * @param ids ID列表
     * @return 影响行数
     */
    Integer batchDelete(List<Integer> ids);
    
    /**
     * 生成生产数据统计编码
     * @return 统计编码
     */
    String generateDataCode();
    
    /**
     * 根据数据类型和产品统计产量
     * @param dataType 数据类型
     * @param productId 产品ID
     * @return 统计结果
     */
    Map<String, Object> countQuantityByDataTypeAndProduct(String dataType, Integer productId);
    
    /**
     * 根据数据类型统计各产品产量
     * @param dataType 数据类型
     * @return 产品产量统计列表
     */
    List<Map<String, Object>> countQuantityByProduct(String dataType);
    
    /**
     * 根据数据类型统计各工单产量
     * @param dataType 数据类型
     * @return 工单产量统计列表
     */
    List<Map<String, Object>> countQuantityByWorkOrder(String dataType);
    
    /**
     * 根据数据类型统计各班次产量
     * @param dataType 数据类型
     * @return 班次产量统计列表
     */
    List<Map<String, Object>> countQuantityByShift(String dataType);
    
    /**
     * 获取生产线产量统计
     * @param productionLineId 生产线ID
     * @param dataType 数据类型
     * @return 生产线产量统计
     */
    Map<String, Object> getProductionLineStatistics(Integer productionLineId, String dataType);
    
    /**
     * 获取设备产量统计
     * @param equipmentId 设备ID
     * @param dataType 数据类型
     * @return 设备产量统计
     */
    Map<String, Object> getEquipmentStatistics(Integer equipmentId, String dataType);
    
    /**
     * 获取工单完成率统计
     * @param workOrderId 工单ID
     * @return 工单完成率统计
     */
    Map<String, Object> getWorkOrderCompletionRate(Integer workOrderId);
    
    /**
     * 根据数据类型统计合格率趋势
     * @param dataType 数据类型
     * @param limit 限制数量
     * @return 合格率趋势数据
     */
    List<Map<String, Object>> getPassRateTrend(String dataType, Integer limit);
    
    /**
     * 根据数据类型统计生产效率趋势
     * @param dataType 数据类型
     * @param limit 限制数量
     * @return 生产效率趋势数据
     */
    List<Map<String, Object>> getProductivityRateTrend(String dataType, Integer limit);
    
    /**
     * 根据产品ID和数据类型统计产量趋势
     * @param productId 产品ID
     * @param dataType 数据类型
     * @param limit 限制数量
     * @return 产量趋势数据
     */
    List<Map<String, Object>> getQuantityTrendByProduct(Integer productId, String dataType, Integer limit);
    
    /**
     * 获取今日生产统计
     * @return 今日生产统计
     */
    Map<String, Object> getTodayProductionStatistics();
    
    /**
     * 获取本周生产统计
     * @return 本周生产统计
     */
    Map<String, Object> getWeekProductionStatistics();
    
    /**
     * 获取本月生产统计
     * @return 本月生产统计
     */
    Map<String, Object> getMonthProductionStatistics();
    
    /**
     * 获取年度生产统计
     * @return 年度生产统计
     */
    Map<String, Object> getYearProductionStatistics();
    
    /**
     * 导出生产数据
     * @param params 查询参数
     * @return 导出数据
     */
    List<Map<String, Object>> exportProductionData(Map<String, Object> params);
    
    /**
     * 根据数据类型和产品统计生产数据
     * @param dataType 数据类型
     * @param productId 产品ID
     * @return 统计结果
     */
    Map<String, Object> countProductionByDataTypeAndProduct(String dataType, Integer productId);
    
    /**
     * 根据数据类型统计各产品生产数据
     * @param dataType 数据类型
     * @return 产品生产数据统计列表
     */
    List<Map<String, Object>> countProductionByProduct(String dataType);
    
    /**
     * 根据数据类型统计各设备生产数据
     * @param dataType 数据类型
     * @return 设备生产数据统计列表
     */
    List<Map<String, Object>> countProductionByEquipment(String dataType);
    
    /**
     * 根据数据类型统计各生产线生产数据
     * @param dataType 数据类型
     * @return 生产线生产数据统计列表
     */
    List<Map<String, Object>> countProductionByProductionLine(String dataType);
    
    /**
     * 获取产量趋势
     * @param dataType 数据类型
     * @param limit 限制数量
     * @return 产量趋势数据
     */
    List<Map<String, Object>> getProductionTrend(String dataType, Integer limit);
    
    /**
     * 获取生产效率趋势
     * @param dataType 数据类型
     * @param limit 限制数量
     * @return 生产效率趋势数据
     */
    List<Map<String, Object>> getProductionEfficiencyTrend(String dataType, Integer limit);
    
    /**
     * 获取产品产量趋势
     * @param productId 产品ID
     * @param dataType 数据类型
     * @param limit 限制数量
     * @return 产品产量趋势数据
     */
    List<Map<String, Object>> getProductYieldTrend(Integer productId, String dataType, Integer limit);
    
    /**
     * 获取设备产量趋势
     * @param equipmentId 设备ID
     * @param dataType 数据类型
     * @param limit 限制数量
     * @return 设备产量趋势数据
     */
    List<Map<String, Object>> getEquipmentProductionTrend(Long equipmentId, String dataType, Integer limit);
    
    /**
     * 根据设备ID查询生产数据
     * @param equipmentId 设备ID
     * @return 生产数据列表
     */
    List<ProductionData> selectByEquipmentId(Long equipmentId);
    
    /**
     * 根据生产线ID查询生产数据
     * @param productionLineId 生产线ID
     * @return 生产数据列表
     */
    List<ProductionData> selectByProductionLineId(Integer productionLineId);
    
    /**
     * 根据工位ID查询生产数据
     * @param workStationId 工位ID
     * @return 生产数据列表
     */
    List<ProductionData> selectByWorkStationId(Integer workStationId);
}
