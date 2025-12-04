package com.smartmes.model;

import java.util.Date;

/**
 * 生产计划实体类
 * 对应第二组（生产计划服务）的核心业务模型
 */
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
    
    // Getter and Setter methods
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getPlanCode() {
        return planCode;
    }
    
    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }
    
    public String getPlanName() {
        return planName;
    }
    
    public void setPlanName(String planName) {
        this.planName = planName;
    }
    
    public Long getProductId() {
        return productId;
    }
    
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    
    public Integer getPlanQuantity() {
        return planQuantity;
    }
    
    public void setPlanQuantity(Integer planQuantity) {
        this.planQuantity = planQuantity;
    }
    
    public Integer getCompletedQuantity() {
        return completedQuantity;
    }
    
    public void setCompletedQuantity(Integer completedQuantity) {
        this.completedQuantity = completedQuantity;
    }
    
    public Date getPlanStartTime() {
        return planStartTime;
    }
    
    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }
    
    public Date getPlanEndTime() {
        return planEndTime;
    }
    
    public void setPlanEndTime(Date planEndTime) {
        this.planEndTime = planEndTime;
    }
    
    public Date getActualStartTime() {
        return actualStartTime;
    }
    
    public void setActualStartTime(Date actualStartTime) {
        this.actualStartTime = actualStartTime;
    }
    
    public Date getActualEndTime() {
        return actualEndTime;
    }
    
    public void setActualEndTime(Date actualEndTime) {
        this.actualEndTime = actualEndTime;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public String getResponsiblePerson() {
        return responsiblePerson;
    }
    
    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public Date getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    public String getErpOrderCode() {
        return erpOrderCode;
    }
    
    public void setErpOrderCode(String erpOrderCode) {
        this.erpOrderCode = erpOrderCode;
    }
}
