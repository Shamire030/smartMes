package com.smartmes.service.impl;

import com.smartmes.model.ProductionPlan;
import com.smartmes.mapper.ProductionPlanMapper;
import com.smartmes.service.ProductionPlanService;
import com.smartmes.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Date;

/**
 * 生产计划服务实现类
 * 实现生产计划相关的业务逻辑
 */
@Service
public class ProductionPlanServiceImpl implements ProductionPlanService {
    
    @Autowired
    private ProductionPlanMapper productionPlanMapper;
    
    @Override
    public ProductionPlan getProductionPlanById(Long id) {
        ProductionPlan plan = productionPlanMapper.findById(id);
        if (plan == null) {
            throw new BusinessException("生产计划不存在");
        }
        return plan;
    }
    
    @Override
    public ProductionPlan getProductionPlanByCode(String planCode) {
        ProductionPlan plan = productionPlanMapper.findByPlanCode(planCode);
        if (plan == null) {
            throw new BusinessException("生产计划不存在");
        }
        return plan;
    }
    
    @Override
    public List<ProductionPlan> getAllProductionPlans() {
        return productionPlanMapper.findAll();
    }
    
    @Override
    public List<ProductionPlan> getProductionPlansByStatus(Integer status) {
        return productionPlanMapper.findByStatus(status);
    }
    
    @Override
    public List<ProductionPlan> getProductionPlansByTimeRange(Date startTime, Date endTime) {
        return productionPlanMapper.findByPlanStartTimeBetween(startTime, endTime);
    }
    
    @Override
    @Transactional
    public ProductionPlan createProductionPlan(ProductionPlan productionPlan) {
        // 检查计划编号是否已存在
        if (productionPlanMapper.findByPlanCode(productionPlan.getPlanCode()) != null) {
            throw new BusinessException("计划编号已存在");
        }
        
        // 设置默认值
        Date now = new Date();
        productionPlan.setCreateTime(now);
        productionPlan.setUpdateTime(now);
        productionPlan.setCompletedQuantity(0);
        productionPlan.setStatus(0); // 初始状态：未开始
        
        productionPlanMapper.insert(productionPlan);
        return productionPlan;
    }
    
    @Override
    @Transactional
    public ProductionPlan updateProductionPlan(Long id, ProductionPlan productionPlan) {
        // 检查计划是否存在
        ProductionPlan existingPlan = productionPlanMapper.findById(id);
        if (existingPlan == null) {
            throw new BusinessException("生产计划不存在");
        }
        
        // 检查计划编号是否被其他记录使用
        ProductionPlan planWithSameCode = productionPlanMapper.findByPlanCode(productionPlan.getPlanCode());
        if (planWithSameCode != null && !planWithSameCode.getId().equals(id)) {
            throw new BusinessException("计划编号已存在");
        }
        
        // 更新字段
        productionPlan.setId(id);
        productionPlan.setUpdateTime(new Date());
        
        productionPlanMapper.update(productionPlan);
        return productionPlanMapper.findById(id);
    }
    
    @Override
    @Transactional
    public ProductionPlan updatePlanStatus(Long id, Integer status) {
        // 检查计划是否存在
        ProductionPlan productionPlan = productionPlanMapper.findById(id);
        if (productionPlan == null) {
            throw new BusinessException("生产计划不存在");
        }
        
        // 更新状态
        Date now = new Date();
        productionPlanMapper.updateStatus(id, status, now);
        
        // 重新获取更新后的计划
        productionPlan = productionPlanMapper.findById(id);
        
        // 如果状态变更为进行中，设置实际开始时间
        if (status == 1 && productionPlan.getActualStartTime() == null) {
            productionPlan.setActualStartTime(now);
            productionPlan.setUpdateTime(now);
            productionPlanMapper.update(productionPlan);
        }
        
        // 如果状态变更为已完成，设置实际结束时间
        if (status == 2 && productionPlan.getActualEndTime() == null) {
            productionPlan.setActualEndTime(now);
            productionPlan.setUpdateTime(now);
            productionPlanMapper.update(productionPlan);
        }
        
        return productionPlanMapper.findById(id);
    }
    
    @Override
    @Transactional
    public void deleteProductionPlan(Long id) {
        // 检查计划是否存在
        ProductionPlan existingPlan = productionPlanMapper.findById(id);
        if (existingPlan == null) {
            throw new BusinessException("生产计划不存在");
        }
        
        // 只有未开始的计划可以删除
        if (existingPlan.getStatus() != 0) {
            throw new BusinessException("只有未开始的计划可以删除");
        }
        
        productionPlanMapper.deleteById(id);
    }
    
    @Override
    @Transactional
    public ProductionPlan syncFromErpOrder(String erpOrderCode) {
        // 检查是否已存在该ERP订单对应的计划
        ProductionPlan existingPlan = productionPlanMapper.findByErpOrderCode(erpOrderCode);
        if (existingPlan != null) {
            throw new BusinessException("该ERP订单已同步过");
        }
        
        // 模拟从ERP同步数据的逻辑
        // 在实际应用中，这里应该调用ERP系统的接口获取数据
        ProductionPlan productionPlan = new ProductionPlan();
        productionPlan.setPlanCode("PLAN_" + erpOrderCode + "_" + System.currentTimeMillis());
        productionPlan.setPlanName("ERP订单生成的计划 - " + erpOrderCode);
        productionPlan.setProductId(1L); // 模拟数据
        productionPlan.setPlanQuantity(100); // 模拟数据
        
        // 设置时间
        Date now = new Date();
        productionPlan.setCreateTime(now);
        productionPlan.setUpdateTime(now);
        productionPlan.setPlanStartTime(now);
        
        // 设置结束时间为3天后
        Date endTime = new Date();
        endTime.setTime(endTime.getTime() + 3 * 24 * 60 * 60 * 1000);
        productionPlan.setPlanEndTime(endTime);
        
        // 设置默认值
        productionPlan.setCompletedQuantity(0);
        productionPlan.setStatus(0);
        productionPlan.setErpOrderCode(erpOrderCode);
        
        productionPlanMapper.insert(productionPlan);
        return productionPlan;
    }
}
