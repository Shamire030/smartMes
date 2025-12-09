package com.smartmes.model;

import lombok.Data;
import java.util.Date;

/**
 * 设备维护保养记录实体类
 * 对应第四组（设备管理服务）的设备维护保养业务模型
 * 支持预防维修和定期保养管理
 */
@Data
public class EquipmentMaintenance {
    
    /**
     * 保养记录ID
     */
    private Long id;
    
    /**
     * 保养单号
     */
    private String maintenanceCode;
    
    /**
     * 设备ID
     */
    private Long equipmentId;
    
    /**
     * 设备编码
     */
    private String equipmentCode;
    
    /**
     * 设备名称
     */
    private String equipmentName;
    
    /**
     * 保养类型：1-定期保养 2-预防性保养 3-故障后保养 4-专项保养
     */
    private Integer maintenanceType;
    
    /**
     * 保养计划ID（如果有）
     */
    private Long maintenancePlanId;
    
    /**
     * 计划开始时间
     */
    private Date planStartTime;
    
    /**
     * 计划结束时间
     */
    private Date planEndTime;
    
    /**
     * 实际开始时间
     */
    private Date actualStartTime;
    
    /**
     * 实际结束时间
     */
    private Date actualEndTime;
    
    /**
     * 保养状态：0-未开始 1-进行中 2-已完成 3-已取消
     */
    private Integer status;
    
    /**
     * 保养内容描述
     */
    private String maintenanceContent;
    
    /**
     * 保养结果：1-良好 2-一般 3-较差
     */
    private Integer maintenanceResult;
    
    /**
     * 发现的问题
     */
    private String discoveredIssues;
    
    /**
     * 处理措施
     */
    private String handlingMeasures;
    
    /**
     * 保养负责人ID
     */
    private Long maintenancePersonId;
    
    /**
     * 保养负责人姓名
     */
    private String maintenancePersonName;
    
    /**
     * 验收人ID
     */
    private Long verifierId;
    
    /**
     * 验收人姓名
     */
    private String verifierName;
    
    /**
     * 验收时间
     */
    private Date verifyTime;
    
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
