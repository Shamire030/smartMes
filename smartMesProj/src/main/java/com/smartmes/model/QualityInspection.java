package com.smartmes.model;

import lombok.Data;
import java.util.Date;

/**
 * 质量检查实体类
 * 对应第四组（质量管理服务）的核心业务模型
 * 支持IQC/IPQC/FQC质检流程
 */
@Data
public class QualityInspection {
    
    /**
     * 质检记录ID
     */
    private Long id;
    
    /**
     * 质检单号
     */
    private String inspectionCode;
    
    /**
     * 质检类型：1-IQC（来料检验） 2-IPQC（过程检验） 3-FQC（成品检验）
     */
    private Integer inspectionType;
    
    /**
     * 关联ID（根据质检类型关联不同表）
     */
    private Long relatedId;
    
    /**
     * 产品ID
     */
    private Long productId;
    
    /**
     * 批次号
     */
    private String batchNo;
    
    /**
     * 检验数量
     */
    private Integer inspectionQuantity;
    
    /**
     * 合格数量
     */
    private Integer qualifiedQuantity;
    
    /**
     * 不合格数量
     */
    private Integer unqualifiedQuantity;
    
    /**
     * 不良品率
     */
    private Double defectiveRate;
    
    /**
     * 检验结果：0-待检验 1-合格 2-不合格 3-让步接收
     */
    private Integer inspectionResult;
    
    /**
     * 检验标准ID
     */
    private Long inspectionStandardId;
    
    /**
     * 检验员ID
     */
    private Long inspectorId;
    
    /**
     * 检验开始时间
     */
    private Date startTime;
    
    /**
     * 检验结束时间
     */
    private Date endTime;
    
    /**
     * 不合格原因分析
     */
    private String unqualifiedReason;
    
    /**
     * 处理建议
     */
    private String processingSuggestion;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
}
