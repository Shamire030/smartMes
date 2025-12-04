package com.smartmes.mapper;

import com.smartmes.model.QualityInspection;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Date;

/**
 * 质量检查Mapper接口
 * 定义质量检查相关的数据访问方法
 */
@Mapper
public interface QualityInspectionMapper {
    
    /**
     * 根据ID查询质检记录
     * @param id 质检记录ID
     * @return 质量检查对象
     */
    QualityInspection selectById(Long id);
    
    /**
     * 根据质检单号查询质检记录
     * @param inspectionCode 质检单号
     * @return 质量检查对象
     */
    QualityInspection selectByInspectionCode(String inspectionCode);
    
    /**
     * 根据质检类型查询质检记录
     * @param inspectionType 质检类型
     * @return 质量检查列表
     */
    List<QualityInspection> selectByInspectionType(Integer inspectionType);
    
    /**
     * 根据关联ID查询质检记录
     * @param relatedId 关联ID
     * @param inspectionType 质检类型
     * @return 质量检查列表
     */
    List<QualityInspection> selectByRelatedId(Long relatedId, Integer inspectionType);
    
    /**
     * 根据产品ID查询质检记录
     * @param productId 产品ID
     * @return 质量检查列表
     */
    List<QualityInspection> selectByProductId(Long productId);
    
    /**
     * 根据批次号查询质检记录
     * @param batchNo 批次号
     * @return 质量检查列表
     */
    List<QualityInspection> selectByBatchNo(String batchNo);
    
    /**
     * 根据检验员ID查询质检记录
     * @param inspectorId 检验员ID
     * @return 质量检查列表
     */
    List<QualityInspection> selectByInspectorId(Long inspectorId);
    
    /**
     * 根据检验结果查询质检记录
     * @param inspectionResult 检验结果
     * @return 质量检查列表
     */
    List<QualityInspection> selectByInspectionResult(Integer inspectionResult);
    
    /**
     * 查询时间范围内的质检记录
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 质量检查列表
     */
    List<QualityInspection> selectByTimeRange(Date startTime, Date endTime);
    
    /**
     * 查询所有质检记录
     * @return 质量检查列表
     */
    List<QualityInspection> selectAll();
    
    /**
     * 插入质检记录
     * @param inspection 质量检查对象
     * @return 插入行数
     */
    int insert(QualityInspection inspection);
    
    /**
     * 更新质检记录
     * @param inspection 质量检查对象
     * @return 更新行数
     */
    int update(QualityInspection inspection);
    
    /**
     * 更新检验结果
     * @param id 质检记录ID
     * @param inspectionResult 检验结果
     * @return 更新行数
     */
    int updateInspectionResult(Long id, Integer inspectionResult);
    
    /**
     * 更新检验数量
     * @param id 质检记录ID
     * @param qualifiedQuantity 合格数量
     * @param unqualifiedQuantity 不合格数量
     * @param defectiveRate 不良品率
     * @return 更新行数
     */
    int updateInspectionQuantity(Long id, Integer qualifiedQuantity, Integer unqualifiedQuantity, Double defectiveRate);
    
    /**
     * 删除质检记录
     * @param id 质检记录ID
     * @return 删除行数
     */
    int deleteById(Long id);
}
