package com.smartmes.mapper;

import com.smartmes.model.ProductionPlan;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Date;

/**
 * 生产计划Mapper接口
 * 使用MyBatis实现数据访问层
 */
@Mapper
public interface ProductionPlanMapper {
    
    /**
     * 根据ID获取生产计划
     * @param id 计划ID
     * @return 生产计划对象
     */
    ProductionPlan findById(Long id);
    
    /**
     * 根据计划编号查询计划
     * @param planCode 计划编号
     * @return 生产计划对象
     */
    ProductionPlan findByPlanCode(String planCode);
    
    /**
     * 获取所有生产计划
     * @return 生产计划列表
     */
    List<ProductionPlan> findAll();
    
    /**
     * 根据产品ID查询计划列表
     * @param productId 产品ID
     * @return 生产计划列表
     */
    List<ProductionPlan> findByProductId(Long productId);
    
    /**
     * 根据状态查询计划列表
     * @param status 计划状态
     * @return 生产计划列表
     */
    List<ProductionPlan> findByStatus(Integer status);
    
    /**
     * 根据时间范围查询计划列表
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 生产计划列表
     */
    List<ProductionPlan> findByPlanStartTimeBetween(Date startTime, Date endTime);
    
    /**
     * 根据ERP订单号查询计划
     * @param erpOrderCode ERP订单号
     * @return 生产计划对象
     */
    ProductionPlan findByErpOrderCode(String erpOrderCode);
    
    /**
     * 新增生产计划
     * @param productionPlan 生产计划对象
     * @return 影响行数
     */
    int insert(ProductionPlan productionPlan);
    
    /**
     * 更新生产计划
     * @param productionPlan 生产计划对象
     * @return 影响行数
     */
    int update(ProductionPlan productionPlan);
    
    /**
     * 更新计划状态
     * @param id 计划ID
     * @param status 新状态
     * @param updateTime 更新时间
     * @return 影响行数
     */
    int updateStatus(Long id, Integer status, Date updateTime);
    
    /**
     * 删除生产计划
     * @param id 计划ID
     * @return 影响行数
     */
    int deleteById(Long id);
}