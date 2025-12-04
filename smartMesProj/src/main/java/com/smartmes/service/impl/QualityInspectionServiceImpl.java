package com.smartmes.service.impl;

import com.smartmes.mapper.QualityInspectionMapper;
import com.smartmes.model.QualityInspection;
import com.smartmes.service.QualityInspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 质量管理服务实现类
 * 实现质量管理相关的业务逻辑
 */
@Service
public class QualityInspectionServiceImpl implements QualityInspectionService {
    
    @Autowired
    private QualityInspectionMapper qualityInspectionMapper;
    
    @Override
    public QualityInspection getById(Long id) {
        return qualityInspectionMapper.selectById(id);
    }
    
    @Override
    public QualityInspection getByInspectionCode(String inspectionCode) {
        return qualityInspectionMapper.selectByInspectionCode(inspectionCode);
    }
    
    @Override
    public List<QualityInspection> getByInspectionType(Integer inspectionType) {
        return qualityInspectionMapper.selectByInspectionType(inspectionType);
    }
    
    @Override
    public List<QualityInspection> getByRelatedId(Long relatedId, Integer inspectionType) {
        return qualityInspectionMapper.selectByRelatedId(relatedId, inspectionType);
    }
    
    @Override
    public List<QualityInspection> getByProductId(Long productId) {
        return qualityInspectionMapper.selectByProductId(productId);
    }
    
    @Override
    public List<QualityInspection> getByBatchNo(String batchNo) {
        return qualityInspectionMapper.selectByBatchNo(batchNo);
    }
    
    @Override
    public List<QualityInspection> getByTimeRange(Date startTime, Date endTime) {
        return qualityInspectionMapper.selectByTimeRange(startTime, endTime);
    }
    
    @Transactional
    @Override
    public QualityInspection createQualityInspection(QualityInspection inspection) {
        // 生成质检单号
        if (inspection.getInspectionCode() == null) {
            inspection.setInspectionCode(generateInspectionCode(inspection.getInspectionType()));
        }
        
        // 设置初始状态
        if (inspection.getInspectionResult() == null) {
            inspection.setInspectionResult(0); // 待检验
        }
        
        // 设置时间
        Date now = new Date();
        inspection.setCreateTime(now);
        inspection.setUpdateTime(now);
        
        // 插入数据库
        qualityInspectionMapper.insert(inspection);
        return inspection;
    }
    
    @Transactional
    @Override
    public QualityInspection updateQualityInspection(QualityInspection inspection) {
        // 更新时间
        inspection.setUpdateTime(new Date());
        
        // 更新数据库
        qualityInspectionMapper.update(inspection);
        return inspection;
    }
    
    @Transactional
    @Override
    public boolean deleteQualityInspection(Long id) {
        // 检查是否存在
        QualityInspection inspection = getById(id);
        if (inspection == null) {
            return false;
        }
        
        // 只有待检验的记录可以删除
        if (inspection.getInspectionResult() != 0) {
            throw new RuntimeException("只有待检验的记录可以删除");
        }
        
        // 删除记录
        return qualityInspectionMapper.deleteById(id) > 0;
    }
    
    @Transactional
    @Override
    public QualityInspection startInspection(Long id) {
        QualityInspection inspection = getById(id);
        if (inspection == null) {
            throw new RuntimeException("质检记录不存在");
        }
        
        // 检查状态
        if (inspection.getInspectionResult() != 0) {
            throw new RuntimeException("该质检记录已经开始或已完成");
        }
        
        // 设置开始时间
        inspection.setStartTime(new Date());
        inspection.setUpdateTime(new Date());
        
        // 更新数据库
        qualityInspectionMapper.update(inspection);
        return inspection;
    }
    
    @Transactional
    @Override
    public QualityInspection completeInspection(Long id, Integer qualifiedQuantity, Integer unqualifiedQuantity,
                                               Integer inspectionResult, String unqualifiedReason) {
        QualityInspection inspection = getById(id);
        if (inspection == null) {
            throw new RuntimeException("质检记录不存在");
        }
        
        // 检查状态
        if (inspection.getInspectionResult() != 0 && inspection.getInspectionResult() != 1) {
            throw new RuntimeException("该质检记录已经完成");
        }
        
        // 计算总数量和不良品率
        Integer totalQuantity = qualifiedQuantity + unqualifiedQuantity;
        Double defectiveRate = totalQuantity > 0 ? 
                (double) unqualifiedQuantity / totalQuantity * 100 : 0.0;
        
        // 更新数据
        inspection.setQualifiedQuantity(qualifiedQuantity);
        inspection.setUnqualifiedQuantity(unqualifiedQuantity);
        inspection.setDefectiveRate(defectiveRate);
        inspection.setInspectionResult(inspectionResult);
        inspection.setUnqualifiedReason(unqualifiedReason);
        inspection.setEndTime(new Date());
        inspection.setUpdateTime(new Date());
        
        // 更新数据库
        qualityInspectionMapper.update(inspection);
        return inspection;
    }
    
    @Override
    public String generateInspectionCode(Integer inspectionType) {
        // 根据质检类型生成前缀
        String prefix = "IQ";
        switch (inspectionType) {
            case 1: // IQC
                prefix = "IQ";
                break;
            case 2: // IPQC
                prefix = "PQ";
                break;
            case 3: // FQC
                prefix = "FQ";
                break;
            default:
                prefix = "QC";
        }
        
        // 生成时间戳和随机数
        String timestamp = String.format("%tY%tm%td%tH%tM", new Date());
        String random = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        
        return prefix + timestamp + random;
    }
    
    @Override
    public QualityStatisticsDTO getQualityStatistics(Long productId, Date startTime, Date endTime) {
        // 获取时间范围内的质检记录
        List<QualityInspection> inspections = qualityInspectionMapper.selectByTimeRange(startTime, endTime);
        
        // 初始化统计数据
        QualityStatisticsDTO statistics = new QualityStatisticsDTO();
        Long totalInspectionCount = (long) inspections.size();
        Long qualifiedCount = 0L;
        Long unqualifiedCount = 0L;
        Long totalInspectionQuantity = 0L;
        Long totalQualifiedQuantity = 0L;
        Long totalUnqualifiedQuantity = 0L;
        Double totalDefectiveRate = 0.0;
        int validDefectiveRateCount = 0;
        
        // 统计数据
        for (QualityInspection inspection : inspections) {
            // 如果指定了产品ID，只统计该产品的数据
            if (productId != null && !productId.equals(inspection.getProductId())) {
                continue;
            }
            
            // 统计检验次数
            if (inspection.getInspectionResult() == 1) {
                qualifiedCount++;
            } else if (inspection.getInspectionResult() == 2) {
                unqualifiedCount++;
            }
            
            // 统计数量
            if (inspection.getInspectionQuantity() != null) {
                totalInspectionQuantity += inspection.getInspectionQuantity();
            }
            if (inspection.getQualifiedQuantity() != null) {
                totalQualifiedQuantity += inspection.getQualifiedQuantity();
            }
            if (inspection.getUnqualifiedQuantity() != null) {
                totalUnqualifiedQuantity += inspection.getUnqualifiedQuantity();
            }
            
            // 统计不良品率
            if (inspection.getDefectiveRate() != null) {
                totalDefectiveRate += inspection.getDefectiveRate();
                validDefectiveRateCount++;
            }
        }
        
        // 设置统计结果
        statistics.setTotalInspectionCount(totalInspectionCount);
        statistics.setQualifiedCount(qualifiedCount);
        statistics.setUnqualifiedCount(unqualifiedCount);
        
        // 计算合格率
        Double passRate = totalInspectionCount > 0 ? 
                (double) qualifiedCount / totalInspectionCount * 100 : 0.0;
        statistics.setPassRate(passRate);
        
        // 计算平均不良品率
        Double averageDefectiveRate = validDefectiveRateCount > 0 ? 
                totalDefectiveRate / validDefectiveRateCount : 0.0;
        statistics.setAverageDefectiveRate(averageDefectiveRate);
        
        statistics.setTotalInspectionQuantity(totalInspectionQuantity);
        statistics.setTotalQualifiedQuantity(totalQualifiedQuantity);
        statistics.setTotalUnqualifiedQuantity(totalUnqualifiedQuantity);
        
        return statistics;
    }
}
