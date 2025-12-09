package com.smartmes.service;

import com.smartmes.model.ProductionExecution;
import java.util.List;
import java.util.Date;

/**
 * 生产执行服务接口
 * 定义生产执行相关的业务逻辑方法
 */
public interface ProductionExecutionService {
    
    /**
     * 根据ID获取生产执行记录
     * @param id 执行记录ID
     * @return 生产执行对象
     */
    ProductionExecution getProductionExecutionById(Long id);
    
    /**
     * 根据批次号获取生产执行记录
     * @param batchNo 批次号
     * @return 生产执行对象
     */
    ProductionExecution getProductionExecutionByBatchNo(String batchNo);
    
    /**
     * 根据计划ID获取生产执行记录列表
     * @param planId 计划ID
     * @return 生产执行列表
     */
    List<ProductionExecution> getProductionExecutionsByPlanId(Long planId);
    
    /**
     * 根据设备ID获取生产执行记录列表
     * @param equipmentId 设备ID
     * @return 生产执行列表
     */
    List<ProductionExecution> getProductionExecutionsByEquipmentId(Long equipmentId);
    
    /**
     * 根据操作员ID获取生产执行记录列表
     * @param operatorId 操作员ID
     * @return 生产执行列表
     */
    List<ProductionExecution> getProductionExecutionsByOperatorId(Long operatorId);
    
    /**
     * 根据状态获取生产执行记录列表
     * @param status 执行状态
     * @return 生产执行列表
     */
    List<ProductionExecution> getProductionExecutionsByStatus(Integer status);
    
    /**
     * 获取所有生产执行记录
     * @return 生产执行列表
     */
    List<ProductionExecution> getAllProductionExecutions();
    
    /**
     * 创建生产执行记录
     * @param execution 生产执行对象
     * @return 创建的生产执行对象
     */
    ProductionExecution createProductionExecution(ProductionExecution execution);
    
    /**
     * 更新生产执行记录
     * @param id 执行记录ID
     * @param execution 生产执行对象
     * @return 更新后的生产执行对象
     */
    ProductionExecution updateProductionExecution(Long id, ProductionExecution execution);
    
    /**
     * 开始生产执行
     * @param id 执行记录ID
     * @return 更新后的生产执行对象
     */
    ProductionExecution startProductionExecution(Long id);
    
    /**
     * 结束生产执行
     * @param id 执行记录ID
     * @return 更新后的生产执行对象
     */
    ProductionExecution endProductionExecution(Long id);
    
    /**
     * 暂停生产执行
     * @param id 执行记录ID
     * @return 更新后的生产执行对象
     */
    ProductionExecution pauseProductionExecution(Long id);
    
    /**
     * 恢复生产执行
     * @param id 执行记录ID
     * @return 更新后的生产执行对象
     */
    ProductionExecution resumeProductionExecution(Long id);
    
    /**
     * 提交生产数量
     * @param id 执行记录ID
     * @param actualQuantity 实际数量
     * @param qualifiedQuantity 合格数量
     * @param unqualifiedQuantity 不合格数量
     * @return 更新后的生产执行对象
     */
    ProductionExecution submitProductionQuantity(Long id, Integer actualQuantity, Integer qualifiedQuantity, Integer unqualifiedQuantity);
    
    /**
     * 扫码开工
     * @param batchNo 批次号
     * @param operatorId 操作员ID
     * @return 更新后的生产执行对象
     */
    ProductionExecution scanToStart(String batchNo, Long operatorId);
    
    /**
     * 上报异常
     * @param id 执行记录ID
     * @param errorMessage 错误信息
     * @return 更新后的生产执行对象
     */
    ProductionExecution reportException(Long id, String errorMessage);
    
    /**
     * 删除生产执行记录
     * @param id 执行记录ID
     */
    void deleteProductionExecution(Long id);
}
