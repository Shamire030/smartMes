package com.smartmes.mapper;

import com.smartmes.model.EquipmentMaintenance;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Date;

/**
 * 设备维护保养Mapper接口
 * 定义设备维护保养相关的数据访问方法
 */
@Mapper
public interface EquipmentMaintenanceMapper {
    
    /**
     * 根据ID查询维护保养记录
     * @param id 维护保养记录ID
     * @return 维护保养记录对象
     */
    EquipmentMaintenance selectById(Long id);
    
    /**
     * 根据保养单号查询维护保养记录
     * @param maintenanceCode 保养单号
     * @return 维护保养记录对象
     */
    EquipmentMaintenance selectByMaintenanceCode(String maintenanceCode);
    
    /**
     * 根据设备ID查询维护保养记录
     * @param equipmentId 设备ID
     * @return 维护保养记录对象列表
     */
    List<EquipmentMaintenance> selectByEquipmentId(Long equipmentId);
    
    /**
     * 根据保养类型查询维护保养记录
     * @param maintenanceType 保养类型
     * @return 维护保养记录对象列表
     */
    List<EquipmentMaintenance> selectByMaintenanceType(String maintenanceType);
    
    /**
     * 根据保养状态查询维护保养记录
     * @param status 保养状态
     * @return 维护保养记录对象列表
     */
    List<EquipmentMaintenance> selectByStatus(Integer status);
    
    /**
     * 根据保养负责人查询维护保养记录
     * @param maintenancePersonId 保养负责人ID
     * @return 维护保养记录对象列表
     */
    List<EquipmentMaintenance> selectByMaintenancePersonId(Long maintenancePersonId);
    
    /**
     * 查询时间范围内的维护保养记录
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 维护保养记录对象列表
     */
    List<EquipmentMaintenance> selectByTimeRange(String startTime, String endTime);
    
    /**
     * 查询设备在指定时间范围内的维护保养记录
     * @param equipmentId 设备ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 维护保养记录对象列表
     */
    List<EquipmentMaintenance> selectByEquipmentIdAndTimeRange(Long equipmentId, String startTime, String endTime);
    
    /**
     * 分页查询维护保养记录
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 维护保养记录对象列表
     */
    List<EquipmentMaintenance> selectPage(Integer offset, Integer limit);
    
    /**
     * 查询维护保养记录总数
     * @return 维护保养记录总数
     */
    int selectCount();
    
    /**
     * 查询所有维护保养记录
     * @return 维护保养记录对象列表
     */
    List<EquipmentMaintenance> selectAll();
    
    /**
     * 插入维护保养记录
     * @param maintenance 维护保养记录对象
     * @return 插入行数
     */
    int insert(EquipmentMaintenance maintenance);
    
    /**
     * 更新维护保养记录
     * @param maintenance 维护保养记录对象
     * @return 更新行数
     */
    int update(EquipmentMaintenance maintenance);
    
    /**
     * 更新保养状态
     * @param id 维护保养记录ID
     * @param status 保养状态
     * @return 更新行数
     */
    int updateStatus(Long id, Integer status);
    
    /**
     * 更新实际开始时间
     * @param id 维护保养记录ID
     * @param actualStartTime 实际开始时间
     * @return 更新行数
     */
    int updateActualStartTime(Long id, String actualStartTime);
    
    /**
     * 更新实际结束时间
     * @param id 维护保养记录ID
     * @param actualEndTime 实际结束时间
     * @return 更新行数
     */
    int updateActualEndTime(Long id, String actualEndTime);
    
    /**
     * 更新保养结果
     * @param id 维护保养记录ID
     * @param maintenanceResult 保养结果
     * @param discoveredIssues 发现的问题
     * @param handlingMeasures 处理措施
     * @return 更新行数
     */
    int updateMaintenanceResult(Long id, String maintenanceResult, String discoveredIssues, String handlingMeasures);
    
    /**
     * 更新验收信息
     * @param id 维护保养记录ID
     * @param verifierId 验收人ID
     * @param verifierName 验收人姓名
     * @param verifyTime 验收时间
     * @return 更新行数
     */
    int updateVerification(Long id, Long verifierId, String verifierName, String verifyTime);
    
    /**
     * 删除维护保养记录
     * @param id 维护保养记录ID
     * @return 删除行数
     */
    int deleteById(Long id);
    
    /**
     * 按设备统计维护保养次数
     * @return 设备ID和保养次数的映射
     */
    List<Map<String, Object>> countByEquipment();
    
    /**
     * 按状态统计维护保养记录
     * @return 状态和记录数的映射
     */
    List<Map<String, Object>> countByStatus();
    
    /**
     * 按类型统计维护保养记录
     * @return 类型和记录数的映射
     */
    List<Map<String, Object>> countByType();
    
    /**
     * 根据参数查询维护保养记录
     * @param params 查询参数
     * @return 维护保养记录列表
     */
    List<EquipmentMaintenance> selectByParams(Map<String, Object> params);
    
    /**
     * 根据前缀查询最大保养单号
     * @param prefix 单号前缀
     * @return 最大保养单号
     */
    String selectMaxCodeByPrefix(String prefix);
}
