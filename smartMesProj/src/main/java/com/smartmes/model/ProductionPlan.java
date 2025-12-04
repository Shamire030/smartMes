package com.smartmes.model;

import lombok.Data;
import java.util.Date;

/**
 * 生产计划实体类
 * 对应第二组（生产计划服务）的核心业务模型
 */
@Data
public class ProductionPlan {
    
    /**
     * 计划ID
     */
    private Long id;
    
    /**
     * 计划编号
     */
    private String planCode;
    
    /**
     * 计划名称
     */
    private String planName;
    
    /**
     * 产品ID
     */
    private Long productId;
    
    /**
     * 计划数量
     */
    private Integer planQuantity;
    
    /**
     * 已完成数量
     */
    private Integer completedQuantity;
    
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
     * 状态：0-未开始 1-进行中 2-已完成 3-已暂停 4-已取消
     */
    private Integer status;
    /**
     * 负责人
     */
    private String responsiblePerson;
    
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
    
    /**
     * ERP订单号
     */
    private String erpOrderCode;
}
