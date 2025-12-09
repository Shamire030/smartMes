package com.smartmes.model;

import lombok.Data;
import java.util.Date;

/**
 * 质量数据统计分析实体类
 */
@Data
public class QualityData {
    private Integer id;                      // 主键ID
    private String dataType;                 // 数据类型：日/周/月/季度/年统计
    private String period;                   // 统计周期标识
    private Integer inspectionId;            // 质检记录ID
    private String inspectionCode;           // 质检单号
    private Integer productId;               // 产品ID
    private String productCode;              // 产品编码
    private String productName;              // 产品名称
    private String inspectionType;           // 质检类型：IQC/IPQC/FQC
    private Integer inspectedQuantity;       // 检验数量
    private Integer qualifiedQuantity;       // 合格数量
    private Integer defectiveQuantity;       // 不合格数量
    private Double passRate;                 // 合格率
    private Integer minorDefectCount;        // 轻微缺陷数量
    private Integer majorDefectCount;        // 严重缺陷数量
    private Integer criticalDefectCount;     // 致命缺陷数量
    private String defectReason;             // 主要缺陷原因
    private Integer equipmentId;             // 设备ID
    private String equipmentCode;            // 设备编码
    private Integer workStationId;           // 工位ID
    private String workStationCode;          // 工位编码
    private Integer operatorId;              // 操作人ID
    private String operatorName;             // 操作人姓名
    private Integer inspectPersonId;         // 检验人员ID
    private String inspectPersonName;        // 检验人员姓名
    private String lotNumber;                // 批次号
    private String supplierCode;             // 供应商编码
    private Date dataDate;                   // 数据日期
    private Date createTime;                 // 创建时间
    private Date updateTime;                 // 更新时间
}
