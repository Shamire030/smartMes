package com.smartmes.service;

import com.smartmes.model.Equipment;
import java.util.List;
import java.util.Map;

/**
 * 设备管理服务接口
 */
public interface EquipmentService {
    
    /**
     * 根据ID查询设备信息
     * @param id 设备ID
     * @return 设备信息
     */
    Equipment selectById(Long id);
    
    /**
     * 根据编码查询设备信息
     * @param equipmentCode 设备编码
     * @return 设备信息
     */
    Equipment selectByEquipmentCode(String equipmentCode);
    
    /**
     * 根据名称查询设备信息
     * @param equipmentName 设备名称
     * @return 设备列表
     */
    List<Equipment> selectByEquipmentName(String equipmentName);
    
    /**
     * 根据类型查询设备信息
     * @param equipmentType 设备类型
     * @return 设备列表
     */
    List<Equipment> selectByEquipmentType(String equipmentType);
    
    /**
     * 根据状态查询设备信息
     * @param status 设备状态
     * @return 设备列表
     */
    List<Equipment> selectByStatus(Integer status);
    
    /**
     * 根据区域查询设备信息
     * @param location 设备区域
     * @return 设备列表
     */
    List<Equipment> selectByLocation(String location);
    
    /**
     * 根据负责人查询设备信息
     * @param responsiblePersonId 负责人ID
     * @return 设备列表
     */
    List<Equipment> selectByResponsiblePersonId(Long responsiblePersonId);
    
    /**
     * 根据在线状态查询设备信息
     * @param onlineStatus 在线状态
     * @return 设备列表
     */
    List<Equipment> selectByOnlineStatus(Integer onlineStatus);
    
    /**
     * 分页查询设备信息
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 设备列表
     */
    List<Equipment> selectPage(Integer pageNum, Integer pageSize);
    
    /**
     * 查询设备信息总数
     * @return 设备总数
     */
    Integer selectCount();
    
    /**
     * 查询所有设备信息
     * @return 设备列表
     */
    List<Equipment> selectAll();
    
    /**
     * 根据条件查询设备信息
     * @param params 查询参数
     * @return 设备列表
     */
    List<Equipment> selectByParams(Map<String, Object> params);
    
    /**
     * 插入设备信息
     * @param equipment 设备信息
     * @return 影响行数
     */
    Integer insert(Equipment equipment);
    
    /**
     * 更新设备信息
     * @param equipment 设备信息
     * @return 影响行数
     */
    Integer update(Equipment equipment);
    
    /**
     * 根据ID删除设备信息
     * @param id 设备ID
     * @return 影响行数
     */
    Integer deleteById(Long id);
    
    /**
     * 更新设备状态
     * @param id 设备ID
     * @param status 设备状态
     * @return 影响行数
     */
    Integer updateStatus(Long id, Integer status);
    
    /**
     * 更新设备在线状态
     * @param id 设备ID
     * @param onlineStatus 在线状态
     * @return 影响行数
     */
    Integer updateOnlineStatus(Long id, Integer onlineStatus);
    
    /**
     * 更新设备运行时间
     * @param id 设备ID
     * @param runtime 运行时间
     * @return 影响行数
     */
    Integer updateRuntime(Long id, Integer runtime);
    
    /**
     * 更新设备责任人
     * @param id 设备ID
     * @param responsiblePersonId 责任人ID
     * @param responsiblePersonName 责任人姓名
     * @return 影响行数
     */
    Integer updateResponsiblePerson(Long id, Long responsiblePersonId, String responsiblePersonName);
    
    /**
     * 生成设备编码
     * @return 设备编码
     */
    String generateEquipmentCode();
    
    /**
     * 设备状态统计
     * @return 状态统计数据
     */
    Map<String, Integer> getEquipmentStatusStatistics();
    
    /**
     * 设备类型统计
     * @return 类型统计数据
     */
    Map<String, Integer> getEquipmentTypeStatistics();
    
    /**
     * 设备区域统计
     * @return 区域统计数据
     */
    Map<String, Integer> getEquipmentLocationStatistics();
}
