package com.smartmes.controller;

import com.smartmes.model.ProductionPlan;
import com.smartmes.service.ProductionPlanService;
import com.smartmes.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Date;

/**
 * 生产计划控制器
 * 提供生产计划相关的REST API接口
 */
@RestController
@RequestMapping("/api/production-plans")
public class ProductionPlanController {
    
    @Autowired
    private ProductionPlanService productionPlanService;
    
    /**
     * 根据ID获取生产计划
     * @param id 计划ID
     * @return 生产计划对象
     */
    @GetMapping("/{id}")
    public Result<ProductionPlan> getProductionPlanById(@PathVariable Long id) {
        ProductionPlan productionPlan = productionPlanService.getProductionPlanById(id);
        return Result.success(productionPlan);
    }
    
    /**
     * 根据计划编号获取生产计划
     * @param planCode 计划编号
     * @return 生产计划对象
     */
    @GetMapping("/code/{planCode}")
    public Result<ProductionPlan> getProductionPlanByCode(@PathVariable String planCode) {
        ProductionPlan productionPlan = productionPlanService.getProductionPlanByCode(planCode);
        return Result.success(productionPlan);
    }
    
    /**
     * 获取所有生产计划
     * @return 生产计划列表
     */
    @GetMapping
    public Result<List<ProductionPlan>> getAllProductionPlans() {
        List<ProductionPlan> productionPlans = productionPlanService.getAllProductionPlans();
        return Result.success(productionPlans);
    }
    
    /**
     * 根据状态获取生产计划
     * @param status 计划状态
     * @return 生产计划列表
     */
    @GetMapping("/status/{status}")
    public Result<List<ProductionPlan>> getProductionPlansByStatus(@PathVariable Integer status) {
        List<ProductionPlan> productionPlans = productionPlanService.getProductionPlansByStatus(status);
        return Result.success(productionPlans);
    }
    
    /**
     * 根据时间范围获取生产计划
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 生产计划列表
     */
    @GetMapping("/time-range")
    public Result<List<ProductionPlan>> getProductionPlansByTimeRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        List<ProductionPlan> productionPlans = productionPlanService.getProductionPlansByTimeRange(startTime, endTime);
        return Result.success(productionPlans);
    }
    
    /**
     * 创建生产计划
     * @param productionPlan 生产计划对象
     * @return 创建的生产计划对象
     */
    @PostMapping
    public Result<ProductionPlan> createProductionPlan(@RequestBody ProductionPlan productionPlan) {
        ProductionPlan createdPlan = productionPlanService.createProductionPlan(productionPlan);
        return Result.success(createdPlan);
    }
    
    /**
     * 更新生产计划
     * @param id 计划ID
     * @param productionPlan 生产计划对象
     * @return 更新后的生产计划对象
     */
    @PutMapping("/{id}")
    public Result<ProductionPlan> updateProductionPlan(@PathVariable Long id, @RequestBody ProductionPlan productionPlan) {
        ProductionPlan updatedPlan = productionPlanService.updateProductionPlan(id, productionPlan);
        return Result.success(updatedPlan);
    }
    
    /**
     * 更新计划状态
     * @param id 计划ID
     * @param status 新状态
     * @return 更新后的生产计划对象
     */
    @PutMapping("/{id}/status")
    public Result<ProductionPlan> updatePlanStatus(@PathVariable Long id, @RequestParam Integer status) {
        ProductionPlan updatedPlan = productionPlanService.updatePlanStatus(id, status);
        return Result.success(updatedPlan);
    }
    
    /**
     * 删除生产计划
     * @param id 计划ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteProductionPlan(@PathVariable Long id) {
        productionPlanService.deleteProductionPlan(id);
        return Result.success();
    }
    
    /**
     * 从ERP同步订单到生产计划
     * @param erpOrderCode ERP订单号
     * @return 创建的生产计划对象
     */
    @PostMapping("/sync-from-erp")
    public Result<ProductionPlan> syncFromErpOrder(@RequestParam String erpOrderCode) {
        ProductionPlan productionPlan = productionPlanService.syncFromErpOrder(erpOrderCode);
        return Result.success(productionPlan);
    }
}
