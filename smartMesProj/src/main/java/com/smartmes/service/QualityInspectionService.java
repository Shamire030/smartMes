package com.smartmes.service;

import com.smartmes.model.QualityInspection;

import java.util.List;
import java.util.Date;

/**
 * 质量管理服务接口
 * 定义质量检查相关的业务逻辑方法
 */
public interface QualityInspectionService {
    
    /**
     * 根据ID查询质检记录
     * @param id 质检记录ID
     * @return 质量检查对象
     */
    QualityInspection getById(Long id);
    
    /**
     * 根据质检单号查询质检记录
     * @param inspectionCode 质检单号
     * @return 质量检查对象
     */
    QualityInspection getByInspectionCode(String inspectionCode);
    
    /**
     * 根据质检类型查询质检记录
     * @param inspectionType 质检类型
     * @return 质量检查列表
     */
    List<QualityInspection> getByInspectionType(Integer inspectionType);
    
    /**
     * 根据关联ID查询质检记录
     * @param relatedId 关联ID
     * @param inspectionType 质检类型
     * @return 质量检查列表
     */
    List<QualityInspection> getByRelatedId(Long relatedId, Integer inspectionType);
    
    /**
     * 根据产品ID查询质检记录
     * @param productId 产品ID
     * @return 质量检查列表
     */
    List<QualityInspection> getByProductId(Long productId);
    
    /**
     * 根据批次号查询质检记录
     * @param batchNo 批次号
     * @return 质量检查列表
     */
    List<QualityInspection> getByBatchNo(String batchNo);
    
    /**
     * 查询时间范围内的质检记录
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 质量检查列表
     */
    List<QualityInspection> getByTimeRange(Date startTime, Date endTime);
    
    /**
     * 创建质检记录
     * @param inspection 质量检查对象
     * @return 创建后的质量检查对象
     */
    QualityInspection createQualityInspection(QualityInspection inspection);
    
    /**
     * 更新质检记录
     * @param inspection 质量检查对象
     * @return 更新后的质量检查对象
     */
    QualityInspection updateQualityInspection(QualityInspection inspection);
    
    /**
     * 删除质检记录
     * @param id 质检记录ID
     * @return 是否删除成功
     */
    boolean deleteQualityInspection(Long id);
    
    /**
     * 开始质检
     * @param id 质检记录ID
     * @return 更新后的质量检查对象
     */
    QualityInspection startInspection(Long id);
    
    /**
     * 完成质检
     * @param id 质检记录ID
     * @param qualifiedQuantity 合格数量
     * @param unqualifiedQuantity 不合格数量
     * @param inspectionResult 检验结果
     * @param unqualifiedReason 不合格原因
     * @return 更新后的质量检查对象
     */
    QualityInspection completeInspection(Long id, Integer qualifiedQuantity, Integer unqualifiedQuantity, 
                                         Integer inspectionResult, String unqualifiedReason);
    
    /**
     * 生成质检单号
     * @param inspectionType 质检类型
     * @return 生成的质检单号
     */
    String generateInspectionCode(Integer inspectionType);
    
    /**
     * 统计质量数据（如合格率、不良品率等）
     * @param productId 产品ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 质量统计数据
     */
    QualityStatisticsDTO getQualityStatistics(Long productId, Date startTime, Date endTime);
    
    /**
     * 质量统计数据传输对象
     */
    class QualityStatisticsDTO {
        private Long totalInspectionCount;      // 总检验次数
        private Long qualifiedCount;            // 合格次数
        private Long unqualifiedCount;          // 不合格次数
        private Double passRate;                // 合格率
        private Double averageDefectiveRate;    // 平均不良品率
        private Long totalInspectionQuantity;   // 总检验数量
        private Long totalQualifiedQuantity;    // 总合格数量
        private Long totalUnqualifiedQuantity;  // 总不合格数量
        
        // Getters and Setters
        public Long getTotalInspectionCount() { return totalInspectionCount; }
        public void setTotalInspectionCount(Long totalInspectionCount) { this.totalInspectionCount = totalInspectionCount; }
        public Long getQualifiedCount() { return qualifiedCount; }
        public void setQualifiedCount(Long qualifiedCount) { this.qualifiedCount = qualifiedCount; }
        public Long getUnqualifiedCount() { return unqualifiedCount; }
        public void setUnqualifiedCount(Long unqualifiedCount) { this.unqualifiedCount = unqualifiedCount; }
        public Double getPassRate() { return passRate; }
        public void setPassRate(Double passRate) { this.passRate = passRate; }
        public Double getAverageDefectiveRate() { return averageDefectiveRate; }
        public void setAverageDefectiveRate(Double averageDefectiveRate) { this.averageDefectiveRate = averageDefectiveRate; }
        public Long getTotalInspectionQuantity() { return totalInspectionQuantity; }
        public void setTotalInspectionQuantity(Long totalInspectionQuantity) { this.totalInspectionQuantity = totalInspectionQuantity; }
        public Long getTotalQualifiedQuantity() { return totalQualifiedQuantity; }
        public void setTotalQualifiedQuantity(Long totalQualifiedQuantity) { this.totalQualifiedQuantity = totalQualifiedQuantity; }
        public Long getTotalUnqualifiedQuantity() { return totalUnqualifiedQuantity; }
        public void setTotalUnqualifiedQuantity(Long totalUnqualifiedQuantity) { this.totalUnqualifiedQuantity = totalUnqualifiedQuantity; }
    }
}
