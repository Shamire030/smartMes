package com.smartmes.model;

import lombok.Data;
import java.util.Date;

/**
 * 设备数据统计分析实体类
 */
@Data
public class EquipmentData {
    private Integer id;                      // 主键ID
    private String dataType;                 // 数据类型：日/周/月/季度/年统计
    private String period;                   // 统计周期标识
    private Integer equipmentId;             // 设备ID
    private String equipmentCode;            // 设备编码
    private String equipmentName;            // 设备名称
    private String equipmentType;            // 设备类型
    private String location;                 // 设备位置
    private Integer totalRuntime;            // 总运行时间(分钟)
    private Integer totalStopTime;           // 总停机时间(分钟)
    private Integer productionRuntime;       // 生产运行时间(分钟)
    private Integer maintenanceRuntime;      // 保养维护时间(分钟)
    private Integer faultRuntime;            // 故障停机时间(分钟)
    private Double availabilityRate;         // 设备可用率
    private Double utilizationRate;          // 设备利用率
    private Double performanceRate;          // 设备性能率
    private Double oeeRate;                  // 设备综合效率OEE
    private Integer faultCount;              // 故障次数
    private Integer maintenanceCount;        // 维护次数
    private Integer startCount;              // 开机次数
    private Integer productionCount;         // 生产次数
    private Integer productQuantity;         // 生产数量
    private Double averageCycleTime;         // 平均周期时间(秒)
    private String mainFaultReason;          // 主要故障原因
    private Integer responsiblePersonId;     // 负责人ID
    private String responsiblePersonName;    // 负责人姓名
    private Date dataDate;                   // 数据日期
    private Date createTime;                 // 创建时间
    private Date updateTime;                 // 更新时间
}
