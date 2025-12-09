package com.smartmes.model;

import lombok.Data;
import java.util.Date;

/**
 * 生产执行实体类
 * 对应第三组（生产执行服务）的核心业务模型
 */
@Data
public class ProductionExecution {
    
    /**
     * 执行记录ID
     */
    private Long id;
    
    /**
     * 计划ID
     */
    private Long planId;
    
    /**
     * 产品ID
     */
    private Long productId;
    
    /**
     * 批次号
     */
    private String batchNo;
    
    /**
     * 设备ID
     */
    private Long equipmentId;
    
    /**
     * 操作员ID
     */
    private Long operatorId;
    
    /**
     * 开工时间
     */
    private Date startTime;
    
    /**
     * 完工时间
     */
    private Date endTime;
    
    /**
     * 计划数量
     */
    private Integer planQuantity;
    
    /**
     * 实际产出数量
     */
    private Integer actualQuantity;
    
    /**
     * 合格数量
     */
    private Integer qualifiedQuantity;
    
    /**
     * 不合格数量
     */
    private Integer unqualifiedQuantity;
    
    /**
     * 执行状态：0-未开始 1-进行中 2-已完成 3-已暂停
     */
    private Integer status;
    
    /**
     * 工单状态：0-正常 1-异常
     */
    private Integer workOrderStatus;
    
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
