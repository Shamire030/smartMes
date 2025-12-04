package com.smartmes.model;

import lombok.Data;
import java.util.Date;

/**
 * 生产数据统计分析实体类
 */
@Data
public class ProductionData {
    private Integer id;                      // 主键ID
    private String dataType;                 // 数据类型：日/周/月/季度/年统计
    private String period;                   // 统计周期标识，如：2024-01-01, 2024-W01, 2024-01等
    private Integer planId;                  // 生产计划ID
    private String planCode;                 // 生产计划编码
    private Integer productId;               // 产品ID
    private String productCode;              // 产品编码
    private String productName;              // 产品名称
    private Integer workOrderId;             // 工单ID
    private String workOrderCode;            // 工单号
    private Integer plannedQuantity;         // 计划产量
    private Integer actualQuantity;          // 实际产量
    private Integer qualifiedQuantity;       // 合格数量
    private Integer defectiveQuantity;       // 不良数量
    private Double passRate;                 // 合格率
    private Double productivityRate;         // 生产效率
    private Integer totalManHours;           // 总工时
    private Integer equipmentRuntime;        // 设备运行时间(分钟)
    private Integer equipmentStopTime;       // 设备停机时间(分钟)
    private Integer startCount;              // 开机次数
    private Integer completedCount;          // 完成次数
    private Integer abnormalCount;           // 异常次数
    private Double averageCycleTime;         // 平均周期时间(秒)
    private String shift;                    // 班次
    private Date dataDate;                   // 数据日期
    private Date createTime;                 // 创建时间
    private Date updateTime;                 // 更新时间
}
