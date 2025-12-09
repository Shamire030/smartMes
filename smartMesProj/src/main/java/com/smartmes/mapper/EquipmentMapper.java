package com.smartmes.mapper;

import com.smartmes.model.Equipment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Date;

/**
 * 设备Mapper接口
 * 定义设备信息相关的数据访问方法
 */
@Mapper
public interface EquipmentMapper {
    
    /**
     * 根据ID查询设备
     * @param id 设备ID
     * @return 设备对象
     */
    Equipment selectById(Long id);
    
    /**
     * 根据设备编码查询设备
     * @param equipmentCode 设备编码
     * @return 设备对象
     */
    Equipment selectByEquipmentCode(String equipmentCode);
    
    /**
     * 根据设备名称查询设备
     * @param equipmentName 设备名称
     * @return 设备对象列表
     */
    List<Equipment> selectByEquipmentName(String equipmentName);
    
    /**
     * 根据设备类型查询设备
     * @param equipmentTypeId 设备类型ID
     * @return 设备对象列表
     */
    List<Equipment> selectByEquipmentTypeId(Long equipmentTypeId);
    
    /**
     * 根据设备类型名称查询设备
     * @param equipmentType 设备类型名称
     * @return 设备对象列表
     */
    List<Equipment> selectByEquipmentType(String equipmentType);
    
    /**
     * 根据设备区域查询设备
     * @param location 设备区域
     * @return 设备对象列表
     */
    List<Equipment> selectByLocation(String location);
    
    /**
     * 根据状态查询设备
     * @param status 设备状态
     * @return 设备对象列表
     */
    List<Equipment> selectByStatus(Integer status);
    
    /**
     * 根据在线状态查询设备
     * @param onlineStatus 在线状态
     * @return 设备对象列表
     */
    List<Equipment> selectByOnlineStatus(Integer onlineStatus);
    
    /**
     * 根据所在车间查询设备
     * @param workshopId 车间ID
     * @return 设备对象列表
     */
    List<Equipment> selectByWorkshopId(Long workshopId);
    
    /**
     * 根据所在产线查询设备
     * @param productionLineId 产线ID
     * @return 设备对象列表
     */
    List<Equipment> selectByProductionLineId(Long productionLineId);
    
    /**
     * 根据负责人ID查询设备
     * @param responsiblePersonId 负责人ID
     * @return 设备对象列表
     */
    List<Equipment> selectByResponsiblePersonId(Long responsiblePersonId);
    
    /**
     * 分页查询设备列表
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 设备对象列表
     */
    List<Equipment> selectPage(Integer offset, Integer limit);
    
    /**
     * 查询设备总数
     * @return 设备总数
     */
    int selectCount();
    
    /**
     * 查询所有设备
     * @return 设备对象列表
     */
    List<Equipment> selectAll();
    
    /**
     * 插入设备
     * @param equipment 设备对象
     * @return 插入行数
     */
    int insert(Equipment equipment);
    
    /**
     * 更新设备
     * @param equipment 设备对象
     * @return 更新行数
     */
    int update(Equipment equipment);
    
    /**
     * 更新设备状态
     * @param id 设备ID
     * @param status 设备状态
     * @return 更新行数
     */
    int updateStatus(Long id, Integer status);
    
    /**
     * 更新设备在线状态
     * @param id 设备ID
     * @param onlineStatus 在线状态
     * @return 更新行数
     */
    int updateOnlineStatus(Long id, Integer onlineStatus);
    
    /**
     * 更新设备运行时间
     * @param id 设备ID
     * @param additionalHours 新增运行小时数
     * @param lastOnlineTime 最后在线时间
     * @return 更新行数
     */
    int updateRunningHours(Long id, Double additionalHours, Date lastOnlineTime);
    
    /**
     * 更新设备运行时间（简单版本）
     * @param id 设备ID
     * @param runtime 运行时间
     * @return 更新行数
     */
    int updateRuntime(Long id, Integer runtime);
    
    /**
     * 更新设备责任人
     * @param id 设备ID
     * @param responsiblePersonId 责任人ID
     * @param responsiblePersonName 责任人姓名
     * @return 更新行数
     */
    int updateResponsiblePerson(Long id, Long responsiblePersonId, String responsiblePersonName);
    
    /**
     * 根据参数查询设备
     * @param params 查询参数
     * @return 设备对象列表
     */
    List<Equipment> selectByParams(Map<String, Object> params);
    
    /**
     * 根据前缀查询最大设备编码
     * @param prefix 编码前缀
     * @return 最大设备编码
     */
    String selectMaxCodeByPrefix(String prefix);
    
    /**
     * 按状态统计设备数量
     * @return 状态和数量的映射
     */
    List<Map<String, Object>> countByStatus();
    
    /**
     * 按类型统计设备数量
     * @return 类型和数量的映射
     */
    List<Map<String, Object>> countByType();
    
    /**
     * 按区域统计设备数量
     * @return 区域和数量的映射
     */
    List<Map<String, Object>> countByLocation();
    
    /**
     * 删除设备
     * @param id 设备ID
     * @return 删除行数
     */
    int deleteById(Long id);
}
