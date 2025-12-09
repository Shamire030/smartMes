package com.smartmes.mapper;

import com.smartmes.model.QualityData;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

/**
 * 质量数据统计分析Mapper接口
 */
@Mapper
public interface QualityDataMapper {
    
    /**
     * 根据ID查询质量数据
     * @param id 主键ID
     * @return 质量数据对象
     */
    QualityData selectById(Integer id);
    
    /**
     * 根据数据类型和周期查询质量数据
     * @param dataType 数据类型
     * @param period 统计周期
     * @return 质量数据对象
     */
    QualityData selectByDataTypeAndPeriod(String dataType, String period);
    
    /**
     * 根据产品ID查询质量数据
     * @param productId 产品ID
     * @return 质量数据列表
     */
    List<QualityData> selectByProductId(Integer productId);
    
    /**
     * 根据质检类型查询质量数据
     * @param inspectionType 质检类型
     * @return 质量数据列表
     */
    List<QualityData> selectByInspectionType(String inspectionType);
    
    /**
     * 根据设备ID查询质量数据
     * @param equipmentId 设备ID
     * @return 质量数据列表
     */
    List<QualityData> selectByEquipmentId(Integer equipmentId);
    
    /**
     * 根据工位ID查询质量数据
     * @param workStationId 工位ID
     * @return 质量数据列表
     */
    List<QualityData> selectByWorkStationId(Integer workStationId);
    
    /**
     * 根据操作人ID查询质量数据
     * @param operatorId 操作人ID
     * @return 质量数据列表
     */
    List<QualityData> selectByOperatorId(Integer operatorId);
    
    /**
     * 根据时间范围查询质量数据
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 质量数据列表
     */
    List<QualityData> selectByTimeRange(String startTime, String endTime);
    
    /**
     * 根据产品ID和时间范围查询质量数据
     * @param productId 产品ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 质量数据列表
     */
    List<QualityData> selectByProductIdAndTimeRange(Integer productId, String startTime, String endTime);
    
    /**
     * 根据数据类型查询质量数据列表
     * @param dataType 数据类型
     * @return 质量数据列表
     */
    List<QualityData> selectByDataType(String dataType);
    
    /**
     * 分页查询质量数据
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 质量数据列表
     */
    List<QualityData> selectPage(Integer offset, Integer limit);
    
    /**
     * 查询质量数据总数
     * @return 数据总数
     */
    Integer selectCount();
    
    /**
     * 查询所有质量数据
     * @return 质量数据列表
     */
    List<QualityData> selectAll();
    
    /**
     * 根据条件查询质量数据
     * @param params 查询参数
     * @return 质量数据列表
     */
    List<QualityData> selectByParams(Map<String, Object> params);
    
    /**
     * 插入质量数据
     * @param qualityData 质量数据对象
     * @return 影响行数
     */
    Integer insert(QualityData qualityData);
    
    /**
     * 更新质量数据
     * @param qualityData 质量数据对象
     * @return 影响行数
     */
    Integer update(QualityData qualityData);
    
    /**
     * 根据ID删除质量数据
     * @param id 主键ID
     * @return 影响行数
     */
    Integer deleteById(Integer id);
    
    /**
     * 批量插入质量数据
     * @param list 质量数据列表
     * @return 影响行数
     */
    Integer batchInsert(List<QualityData> list);
    
    /**
     * 根据数据类型和产品统计质量数据
     * @param dataType 数据类型
     * @param productId 产品ID
     * @return 统计结果
     */
    Map<String, Object> countQualityByDataTypeAndProduct(String dataType, Integer productId);
    
    /**
     * 根据数据类型统计各产品质量数据
     * @param dataType 数据类型
     * @return 产品质量统计列表
     */
    List<Map<String, Object>> countQualityByProduct(String dataType);
    
    /**
     * 根据数据类型统计各质检类型质量数据
     * @param dataType 数据类型
     * @return 质检类型质量统计列表
     */
    List<Map<String, Object>> countQualityByInspectionType(String dataType);
    
    /**
     * 根据数据类型统计各设备质量数据
     * @param dataType 数据类型
     * @return 设备质量统计列表
     */
    List<Map<String, Object>> countQualityByEquipment(String dataType);
    
    /**
     * 根据数据类型统计各工位质量数据
     * @param dataType 数据类型
     * @return 工位质量统计列表
     */
    List<Map<String, Object>> countQualityByWorkStation(String dataType);
    
    /**
     * 根据数据类型统计合格率趋势
     * @param dataType 数据类型
     * @param limit 限制数量
     * @return 合格率趋势数据
     */
    List<Map<String, Object>> getPassRateTrend(String dataType, Integer limit);
    
    /**
     * 根据数据类型统计缺陷类型分布
     * @param dataType 数据类型
     * @return 缺陷类型分布数据
     */
    List<Map<String, Object>> getDefectTypeDistribution(String dataType);
    
    /**
     * 根据数据类型统计主要缺陷原因分布
     * @param dataType 数据类型
     * @param limit 限制数量
     * @return 主要缺陷原因分布数据
     */
    List<Map<String, Object>> getMainDefectReasonDistribution(String dataType, Integer limit);
    
    /**
     * 根据产品ID和数据类型统计质量趋势
     * @param productId 产品ID
     * @param dataType 数据类型
     * @param limit 限制数量
     * @return 质量趋势数据
     */
    List<Map<String, Object>> getQualityTrendByProduct(Integer productId, String dataType, Integer limit);
}
