package com.smartmes.model;

import java.util.Date;

/**
 * 设备数据统计分析实体类
 */
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

    // Getter and Setter methods
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getDataType() { return dataType; }
    public void setDataType(String dataType) { this.dataType = dataType; }
    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
    public Integer getEquipmentId() { return equipmentId; }
    public void setEquipmentId(Integer equipmentId) { this.equipmentId = equipmentId; }
    public String getEquipmentCode() { return equipmentCode; }
    public void setEquipmentCode(String equipmentCode) { this.equipmentCode = equipmentCode; }
    public String getEquipmentName() { return equipmentName; }
    public void setEquipmentName(String equipmentName) { this.equipmentName = equipmentName; }
    public String getEquipmentType() { return equipmentType; }
    public void setEquipmentType(String equipmentType) { this.equipmentType = equipmentType; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public Integer getTotalRuntime() { return totalRuntime; }
    public void setTotalRuntime(Integer totalRuntime) { this.totalRuntime = totalRuntime; }
    public Integer getTotalStopTime() { return totalStopTime; }
    public void setTotalStopTime(Integer totalStopTime) { this.totalStopTime = totalStopTime; }
    public Integer getProductionRuntime() { return productionRuntime; }
    public void setProductionRuntime(Integer productionRuntime) { this.productionRuntime = productionRuntime; }
    public Integer getMaintenanceRuntime() { return maintenanceRuntime; }
    public void setMaintenanceRuntime(Integer maintenanceRuntime) { this.maintenanceRuntime = maintenanceRuntime; }
    public Integer getFaultRuntime() { return faultRuntime; }
    public void setFaultRuntime(Integer faultRuntime) { this.faultRuntime = faultRuntime; }
    public Double getAvailabilityRate() { return availabilityRate; }
    public void setAvailabilityRate(Double availabilityRate) { this.availabilityRate = availabilityRate; }
    public Double getUtilizationRate() { return utilizationRate; }
    public void setUtilizationRate(Double utilizationRate) { this.utilizationRate = utilizationRate; }
    public Double getPerformanceRate() { return performanceRate; }
    public void setPerformanceRate(Double performanceRate) { this.performanceRate = performanceRate; }
    public Double getOeeRate() { return oeeRate; }
    public void setOeeRate(Double oeeRate) { this.oeeRate = oeeRate; }
    public Integer getFaultCount() { return faultCount; }
    public void setFaultCount(Integer faultCount) { this.faultCount = faultCount; }
    public Integer getMaintenanceCount() { return maintenanceCount; }
    public void setMaintenanceCount(Integer maintenanceCount) { this.maintenanceCount = maintenanceCount; }
    public Integer getStartCount() { return startCount; }
    public void setStartCount(Integer startCount) { this.startCount = startCount; }
    public Integer getProductionCount() { return productionCount; }
    public void setProductionCount(Integer productionCount) { this.productionCount = productionCount; }
    public Integer getProductQuantity() { return productQuantity; }
    public void setProductQuantity(Integer productQuantity) { this.productQuantity = productQuantity; }
    public Double getAverageCycleTime() { return averageCycleTime; }
    public void setAverageCycleTime(Double averageCycleTime) { this.averageCycleTime = averageCycleTime; }
    public String getMainFaultReason() { return mainFaultReason; }
    public void setMainFaultReason(String mainFaultReason) { this.mainFaultReason = mainFaultReason; }
    public Integer getResponsiblePersonId() { return responsiblePersonId; }
    public void setResponsiblePersonId(Integer responsiblePersonId) { this.responsiblePersonId = responsiblePersonId; }
    public String getResponsiblePersonName() { return responsiblePersonName; }
    public void setResponsiblePersonName(String responsiblePersonName) { this.responsiblePersonName = responsiblePersonName; }
    public Date getDataDate() { return dataDate; }
    public void setDataDate(Date dataDate) { this.dataDate = dataDate; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
    public Date getUpdateTime() { return updateTime; }
    public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }
}
