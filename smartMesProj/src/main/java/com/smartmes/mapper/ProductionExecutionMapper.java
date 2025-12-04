package com.smartmes.mapper;

import com.smartmes.model.ProductionExecution;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Date;

/**
 * 生产执行Mapper接口
 * 定义生产执行相关的数据访问方法
 */
@Mapper
public interface ProductionExecutionMapper {
    
    /**
     * 根据ID查询生产执行记录
     * @param id 执行记录ID
     * @return 生产执行对象
     */
    ProductionExecution selectById(Long id);
    
    /**
     * 根据计划ID查询生产执行记录
     * @param planId 计划ID
     * @return 生产执行列表
     */
    List<ProductionExecution> selectByPlanId(Long planId);
    
    /**
     * 根据批次号查询生产执行记录
     * @param batchNo 批次号
     * @return 生产执行对象
     */
    ProductionExecution selectByBatchNo(String batchNo);
    
    /**
     * 根据设备ID查询生产执行记录
     * @param equipmentId 设备ID
     * @return 生产执行列表
     */
    List<ProductionExecution> selectByEquipmentId(Long equipmentId);
    
    /**
     * 根据操作员ID查询生产执行记录
     * @param operatorId 操作员ID
     * @return 生产执行列表
     */
    List<ProductionExecution> selectByOperatorId(Long operatorId);
    
    /**
     * 根据状态查询生产执行记录
     * @param status 执行状态
     * @return 生产执行列表
     */
    List<ProductionExecution> selectByStatus(Integer status);
    
    /**
     * 查询时间范围内的生产执行记录
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 生产执行列表
     */
    List<ProductionExecution> selectByTimeRange(Date startTime, Date endTime);
    
    /**
     * 查询所有生产执行记录
     * @return 生产执行列表
     */
    List<ProductionExecution> selectAll();
    
    /**
     * 插入生产执行记录
     * @param execution 生产执行对象
     * @return 插入行数
     */
    int insert(ProductionExecution execution);
    
    /**
     * 更新生产执行记录
     * @param execution 生产执行对象
     * @return 更新行数
     */
    int update(ProductionExecution execution);
    
    /**
     * 更新执行状态
     * @param id 执行记录ID
     * @param status 新状态
     * @return 更新行数
     */
    int updateStatus(Long id, Integer status);
    
    /**
     * 更新产量信息
     * @param id 执行记录ID
     * @param actualQuantity 实际数量
     * @param qualifiedQuantity 合格数量
     * @param unqualifiedQuantity 不合格数量
     * @return 更新行数
     */
    int updateProductionQuantity(Long id, Integer actualQuantity, Integer qualifiedQuantity, Integer unqualifiedQuantity);
    
    /**
     * 删除生产执行记录
     * @param id 执行记录ID
     * @return 删除行数
     */
    int deleteById(Long id);
}
