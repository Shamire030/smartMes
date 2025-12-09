package com.smartmes.service;

import com.smartmes.model.EquipmentMaintenance;
import java.util.List;
import java.util.Map;
import java.util.Date;

/**
 * 设备维护保养服务接口
 */
public interface EquipmentMaintenanceService {
    
    /**
     * 根据ID查询维护保养记录
     * @param id 维护保养记录ID
     * @return 维护保养记录
     */
    EquipmentMaintenance selectById(Long id);
    
    /**
     * 根据保养单号查询维护保养记录
     * @param maintenanceCode 保养单号
     * @return 维护保养记录
     */
    EquipmentMaintenance selectByMaintenanceCode(String maintenanceCode);
    
    /**
     * 根据设备ID查询维护保养记录
     * @param equipmentId 设备ID
     * @return 维护保养记录列表
     */
    List<EquipmentMaintenance> selectByEquipmentId(Long equipmentId);
    
    /**
     * 根据保养类型查询维护保养记录
     * @param maintenanceType 保养类型
     * @return 维护保养记录列表
     */
    List<EquipmentMaintenance> selectByMaintenanceType(String maintenanceType);
    
    /**
     * 根据保养状态查询维护保养记录
     * @param status 保养状态
     * @return 维护保养记录列表
     */
    List<EquipmentMaintenance> selectByStatus(Integer status);
    
    /**
     * 根据保养负责人查询维护保养记录
     * @param maintenancePersonId 保养负责人ID
     * @return 维护保养记录列表
     */
    List<EquipmentMaintenance> selectByMaintenancePersonId(Long maintenancePersonId);
    
    /**
     * 查询时间范围内的维护保养记录
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 维护保养记录列表
     */
    List<EquipmentMaintenance> selectByTimeRange(String startTime, String endTime);
    
    /**
     * 查询设备在指定时间范围内的维护保养记录
     * @param equipmentId 设备ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 维护保养记录列表
     */
    List<EquipmentMaintenance> selectByEquipmentIdAndTimeRange(Long equipmentId, String startTime, String endTime);
    
    /**
     * 分页查询维护保养记录
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 维护保养记录列表
     */
    List<EquipmentMaintenance> selectPage(Integer pageNum, Integer pageSize);
    
    /**
     * 查询维护保养记录总数
     * @return 维护保养记录总数
     */
    Integer selectCount();
    
    /**
     * 查询所有维护保养记录
     * @return 维护保养记录列表
     */
    List<EquipmentMaintenance> selectAll();
    
    /**
     * 根据条件查询维护保养记录
     * @param params 查询参数
     * @return 维护保养记录列表
     */
    List<EquipmentMaintenance> selectByParams(Map<String, Object> params);
    
    /**
     * 插入维护保养记录
     * @param equipmentMaintenance 维护保养记录
     * @return 影响行数
     */
    Integer insert(EquipmentMaintenance equipmentMaintenance);
    
    /**
     * 更新维护保养记录
     * @param equipmentMaintenance 维护保养记录
     * @return 影响行数
     */
    Integer update(EquipmentMaintenance equipmentMaintenance);
    
    /**
     * 根据ID删除维护保养记录
     * @param id 维护保养记录ID
     * @return 影响行数
     */
    Integer deleteById(Long id);
    
    /**
     * 更新保养状态
     * @param id 维护保养记录ID
     * @param status 保养状态
     * @return 影响行数
     */
    Integer updateStatus(Long id, Integer status);
    
    /**
     * 更新实际开始时间
     * @param id 维护保养记录ID
     * @param actualStartTime 实际开始时间
     * @return 影响行数
     */
    Integer updateActualStartTime(Long id, String actualStartTime);
    
    /**
     * 更新实际结束时间
     * @param id 维护保养记录ID
     * @param actualEndTime 实际结束时间
     * @return 影响行数
     */
    Integer updateActualEndTime(Long id, String actualEndTime);
    
    /**
     * 更新保养结果
     * @param id 维护保养记录ID
     * @param maintenanceResult 保养结果
     * @param discoveredIssues 发现问题
     * @param handlingMeasures 处理措施
     * @return 影响行数
     */
    Integer updateMaintenanceResult(Long id, String maintenanceResult, String discoveredIssues, String handlingMeasures);
    
    /**
     * 更新验收信息
     * @param id 维护保养记录ID
     * @param verifierId 验收人ID
     * @param verifierName 验收人姓名
     * @param verifyTime 验收时间
     * @return 影响行数
     */
    Integer updateVerification(Long id, Long verifierId, String verifierName, String verifyTime);
    
    /**
     * 生成保养单号
     * @return 保养单号
     */
    String generateMaintenanceCode();
    
    /**
     * 保养状态统计
     * @return 状态统计数据
     */
    Map<String, Integer> getMaintenanceStatusStatistics();
    
    /**
     * 保养类型统计
     * @return 类型统计数据
     */
    Map<String, Integer> getMaintenanceTypeStatistics();
    
    /**
     * 设备保养频次统计
     * @return 设备保养频次统计数据
     */
    Map<Long, Integer> getEquipmentMaintenanceFrequencyStatistics();
    
    /**
     * 开始保养
     * @param id 维护保养记录ID
     * @param operatorId 操作人ID
     * @param operatorName 操作人姓名
     * @return 影响行数
     */
    Integer startMaintenance(Long id, Long operatorId, String operatorName);
    
    /**
     * 完成保养
     * @param id 维护保养记录ID
     * @param maintenanceResult 保养结果
     * @param discoveredIssues 发现问题
     * @param handlingMeasures 处理措施
     * @return 影响行数
     */
    Integer completeMaintenance(Long id, String maintenanceResult, String discoveredIssues, String handlingMeasures);
    
    /**
     * 验收保养
     * @param id 维护保养记录ID
     * @param verifierId 验收人ID
     * @param verifierName 验收人姓名
     * @return 影响行数
     */
    Integer verifyMaintenance(Long id, Long verifierId, String verifierName);
}
