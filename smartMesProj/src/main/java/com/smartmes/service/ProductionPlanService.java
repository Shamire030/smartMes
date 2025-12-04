package com.smartmes.service;

import com.smartmes.model.ProductionPlan;
import java.util.List;
import java.util.Date;

/**
 * 生产计划服务接口
 * 定义生产计划相关的业务逻辑方法
 */
public interface ProductionPlanService {
    
    /**
     * 根据ID获取生产计划
     * @param id 计划ID
     * @return 生产计划对象
     */
    ProductionPlan getProductionPlanById(Long id);
    
    /**
     * 根据计划编号获取生产计划
     * @param planCode 计划编号
     * @return 生产计划对象
     */
    ProductionPlan getProductionPlanByCode(String planCode);
    
    /**
     * 获取所有生产计划
     * @return 生产计划列表
     */
    List<ProductionPlan> getAllProductionPlans();
    
    /**
     * 根据状态获取生产计划
     * @param status 计划状态
     * @return 生产计划列表
     */
    List<ProductionPlan> getProductionPlansByStatus(Integer status);
    
    /**
     * 根据时间范围获取生产计划
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 生产计划列表
     */
    List<ProductionPlan> getProductionPlansByTimeRange(Date startTime, Date endTime);
    
    /**
     * 创建生产计划
     * @param productionPlan 生产计划对象
     * @return 创建的生产计划对象
     */
    ProductionPlan createProductionPlan(ProductionPlan productionPlan);
    
    /**
     * 更新生产计划
     * @param id 计划ID
     * @param productionPlan 生产计划对象
     * @return 更新后的生产计划对象
     */
    ProductionPlan updateProductionPlan(Long id, ProductionPlan productionPlan);
    
    /**
     * 更新计划状态
     * @param id 计划ID
     * @param status 新状态
     * @return 更新后的生产计划对象
     */
    ProductionPlan updatePlanStatus(Long id, Integer status);
    
    /**
     * 删除生产计划
     * @param id 计划ID
     */
    void deleteProductionPlan(Long id);
    
    /**
     * 从ERP同步订单到生产计划
     * @param erpOrderCode ERP订单号
     * @return 创建的生产计划对象
     */
    ProductionPlan syncFromErpOrder(String erpOrderCode);
}
