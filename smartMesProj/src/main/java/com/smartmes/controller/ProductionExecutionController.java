package com.smartmes.controller;

import com.smartmes.model.ProductionExecution;
import com.smartmes.service.ProductionExecutionService;
import com.smartmes.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 生产执行控制器
 * 提供生产执行相关的REST API接口
 */
@RestController
@RequestMapping("/api/production-executions")
public class ProductionExecutionController {
    
    @Autowired
    private ProductionExecutionService productionExecutionService;
    
    /**
     * 根据ID获取生产执行记录
     * @param id 执行记录ID
     * @return 生产执行对象
     */
    @GetMapping("/{id}")
    public Result<ProductionExecution> getProductionExecutionById(@PathVariable Long id) {
        ProductionExecution execution = productionExecutionService.getProductionExecutionById(id);
        return Result.success(execution);
    }
    
    /**
     * 根据批次号获取生产执行记录
     * @param batchNo 批次号
     * @return 生产执行对象
     */
    @GetMapping("/batch/{batchNo}")
    public Result<ProductionExecution> getProductionExecutionByBatchNo(@PathVariable String batchNo) {
        ProductionExecution execution = productionExecutionService.getProductionExecutionByBatchNo(batchNo);
        return Result.success(execution);
    }
    
    /**
     * 根据计划ID获取生产执行记录列表
     * @param planId 计划ID
     * @return 生产执行列表
     */
    @GetMapping("/plan/{planId}")
    public Result<List<ProductionExecution>> getProductionExecutionsByPlanId(@PathVariable Long planId) {
        List<ProductionExecution> executions = productionExecutionService.getProductionExecutionsByPlanId(planId);
        return Result.success(executions);
    }
    
    /**
     * 根据设备ID获取生产执行记录列表
     * @param equipmentId 设备ID
     * @return 生产执行列表
     */
    @GetMapping("/equipment/{equipmentId}")
    public Result<List<ProductionExecution>> getProductionExecutionsByEquipmentId(@PathVariable Long equipmentId) {
        List<ProductionExecution> executions = productionExecutionService.getProductionExecutionsByEquipmentId(equipmentId);
        return Result.success(executions);
    }
    
    /**
     * 根据操作员ID获取生产执行记录列表
     * @param operatorId 操作员ID
     * @return 生产执行列表
     */
    @GetMapping("/operator/{operatorId}")
    public Result<List<ProductionExecution>> getProductionExecutionsByOperatorId(@PathVariable Long operatorId) {
        List<ProductionExecution> executions = productionExecutionService.getProductionExecutionsByOperatorId(operatorId);
        return Result.success(executions);
    }
    
    /**
     * 根据状态获取生产执行记录列表
     * @param status 执行状态
     * @return 生产执行列表
     */
    @GetMapping("/status/{status}")
    public Result<List<ProductionExecution>> getProductionExecutionsByStatus(@PathVariable Integer status) {
        List<ProductionExecution> executions = productionExecutionService.getProductionExecutionsByStatus(status);
        return Result.success(executions);
    }
    
    /**
     * 获取所有生产执行记录
     * @return 生产执行列表
     */
    @GetMapping
    public Result<List<ProductionExecution>> getAllProductionExecutions() {
        List<ProductionExecution> executions = productionExecutionService.getAllProductionExecutions();
        return Result.success(executions);
    }
    
    /**
     * 创建生产执行记录
     * @param execution 生产执行对象
     * @return 创建的生产执行对象
     */
    @PostMapping
    public Result<ProductionExecution> createProductionExecution(@RequestBody ProductionExecution execution) {
        ProductionExecution createdExecution = productionExecutionService.createProductionExecution(execution);
        return Result.success(createdExecution);
    }
    
    /**
     * 更新生产执行记录
     * @param id 执行记录ID
     * @param execution 生产执行对象
     * @return 更新后的生产执行对象
     */
    @PutMapping("/{id}")
    public Result<ProductionExecution> updateProductionExecution(@PathVariable Long id, @RequestBody ProductionExecution execution) {
        ProductionExecution updatedExecution = productionExecutionService.updateProductionExecution(id, execution);
        return Result.success(updatedExecution);
    }
    
    /**
     * 开始生产执行
     * @param id 执行记录ID
     * @return 更新后的生产执行对象
     */
    @PutMapping("/{id}/start")
    public Result<ProductionExecution> startProductionExecution(@PathVariable Long id) {
        ProductionExecution execution = productionExecutionService.startProductionExecution(id);
        return Result.success(execution);
    }
    
    /**
     * 结束生产执行
     * @param id 执行记录ID
     * @return 更新后的生产执行对象
     */
    @PutMapping("/{id}/end")
    public Result<ProductionExecution> endProductionExecution(@PathVariable Long id) {
        ProductionExecution execution = productionExecutionService.endProductionExecution(id);
        return Result.success(execution);
    }
    
    /**
     * 暂停生产执行
     * @param id 执行记录ID
     * @return 更新后的生产执行对象
     */
    @PutMapping("/{id}/pause")
    public Result<ProductionExecution> pauseProductionExecution(@PathVariable Long id) {
        ProductionExecution execution = productionExecutionService.pauseProductionExecution(id);
        return Result.success(execution);
    }
    
    /**
     * 恢复生产执行
     * @param id 执行记录ID
     * @return 更新后的生产执行对象
     */
    @PutMapping("/{id}/resume")
    public Result<ProductionExecution> resumeProductionExecution(@PathVariable Long id) {
        ProductionExecution execution = productionExecutionService.resumeProductionExecution(id);
        return Result.success(execution);
    }
    
    /**
     * 提交生产数量
     * @param id 执行记录ID
     * @param actualQuantity 实际数量
     * @param qualifiedQuantity 合格数量
     * @param unqualifiedQuantity 不合格数量
     * @return 更新后的生产执行对象
     */
    @PutMapping("/{id}/quantity")
    public Result<ProductionExecution> submitProductionQuantity(
            @PathVariable Long id,
            @RequestParam Integer actualQuantity,
            @RequestParam Integer qualifiedQuantity,
            @RequestParam Integer unqualifiedQuantity) {
        ProductionExecution execution = productionExecutionService.submitProductionQuantity(
                id, actualQuantity, qualifiedQuantity, unqualifiedQuantity);
        return Result.success(execution);
    }
    
    /**
     * 扫码开工
     * @param batchNo 批次号
     * @param operatorId 操作员ID
     * @return 更新后的生产执行对象
     */
    @PostMapping("/scan-to-start")
    public Result<ProductionExecution> scanToStart(
            @RequestParam String batchNo,
            @RequestParam Long operatorId) {
        ProductionExecution execution = productionExecutionService.scanToStart(batchNo, operatorId);
        return Result.success(execution);
    }
    
    /**
     * 上报异常
     * @param id 执行记录ID
     * @param errorMessage 错误信息
     * @return 更新后的生产执行对象
     */
    @PostMapping("/{id}/report-exception")
    public Result<ProductionExecution> reportException(
            @PathVariable Long id,
            @RequestParam String errorMessage) {
        ProductionExecution execution = productionExecutionService.reportException(id, errorMessage);
        return Result.success(execution);
    }
    
    /**
     * 删除生产执行记录
     * @param id 执行记录ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteProductionExecution(@PathVariable Long id) {
        productionExecutionService.deleteProductionExecution(id);
        return Result.success();
    }
}
