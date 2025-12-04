package com.smartmes.service.impl;

import com.smartmes.mapper.EquipmentMapper;
import com.smartmes.model.Equipment;
import com.smartmes.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 设备管理服务实现类
 */
@Service
public class EquipmentServiceImpl implements EquipmentService {
    
    @Autowired
    private EquipmentMapper equipmentMapper;
    
    @Override
    public Equipment selectById(Long id) {
        return equipmentMapper.selectById(id);
    }
    
    @Override
    public Equipment selectByEquipmentCode(String equipmentCode) {
        return equipmentMapper.selectByEquipmentCode(equipmentCode);
    }
    
    @Override
    public List<Equipment> selectByEquipmentName(String equipmentName) {
        return equipmentMapper.selectByEquipmentName(equipmentName);
    }
    
    @Override
    public List<Equipment> selectByEquipmentType(String equipmentType) {
        return equipmentMapper.selectByEquipmentType(equipmentType);
    }
    
    @Override
    public List<Equipment> selectByStatus(Integer status) {
        return equipmentMapper.selectByStatus(status);
    }
    
    @Override
    public List<Equipment> selectByLocation(String location) {
        return equipmentMapper.selectByLocation(location);
    }
    
    @Override
    public List<Equipment> selectByResponsiblePersonId(Long responsiblePersonId) {
        return equipmentMapper.selectByResponsiblePersonId(responsiblePersonId);
    }
    
    @Override
    public List<Equipment> selectByOnlineStatus(Integer onlineStatus) {
        return equipmentMapper.selectByOnlineStatus(onlineStatus);
    }
    
    @Override
    public List<Equipment> selectPage(Integer pageNum, Integer pageSize) {
        int offset = (pageNum - 1) * pageSize;
        return equipmentMapper.selectPage(offset, pageSize);
    }
    
    @Override
    public Integer selectCount() {
        return equipmentMapper.selectCount();
    }
    
    @Override
    public List<Equipment> selectAll() {
        return equipmentMapper.selectAll();
    }
    
    @Override
    public List<Equipment> selectByParams(Map<String, Object> params) {
        return equipmentMapper.selectByParams(params);
    }
    
    @Transactional
    @Override
    public Integer insert(Equipment equipment) {
        // 生成设备编码
        equipment.setEquipmentCode(generateEquipmentCode());
        // 设置创建和更新时间
        Date now = new Date();
        equipment.setCreateTime(now);
        equipment.setUpdateTime(now);
        // 设置默认状态
        if (equipment.getStatus() == null) {
            equipment.setStatus(0); // 0: 待机
        }
        if (equipment.getOnlineStatus() == null) {
            equipment.setOnlineStatus(0); // 0: 离线
        }
        if (equipment.getTotalRunningHours() == null) {
            equipment.setTotalRunningHours(0.0);
        }
        return equipmentMapper.insert(equipment);
    }
    
    @Transactional
    @Override
    public Integer update(Equipment equipment) {
        // 更新时间
        equipment.setUpdateTime(new Date());
        return equipmentMapper.update(equipment);
    }
    
    @Transactional
    @Override
    public Integer deleteById(Long id) {
        // 检查设备是否有关联的维护保养记录或生产记录
        // 这里可以添加业务逻辑验证
        return equipmentMapper.deleteById(id);
    }
    
    @Transactional
    @Override
    public Integer updateStatus(Long id, Integer status) {
        // 状态流转控制
        Equipment equipment = equipmentMapper.selectById(id);
        if (equipment == null) {
            return 0;
        }
        // 这里可以添加状态流转的业务逻辑验证
        return equipmentMapper.updateStatus(id, status);
    }
    
    @Transactional
    @Override
    public Integer updateOnlineStatus(Long id, Integer onlineStatus) {
        return equipmentMapper.updateOnlineStatus(id, onlineStatus);
    }
    
    @Transactional
    @Override
    public Integer updateRuntime(Long id, Integer runtime) {
        return equipmentMapper.updateRuntime(id, runtime);
    }
    
    @Transactional
    @Override
    public Integer updateResponsiblePerson(Long id, Long responsiblePersonId, String responsiblePersonName) {
        return equipmentMapper.updateResponsiblePerson(id, responsiblePersonId, responsiblePersonName);
    }
    
    @Override
    public String generateEquipmentCode() {
        // 生成设备编码: EQ + 年月日 + 4位序号
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = sdf.format(new Date());
        String prefix = "EQ" + dateStr;
        
        // 查询当日最大序号
        String maxCode = equipmentMapper.selectMaxCodeByPrefix(prefix);
        Integer sequence = 1;
        
        if (maxCode != null && maxCode.length() >= 14) {
            try {
                sequence = Integer.parseInt(maxCode.substring(10)) + 1;
            } catch (NumberFormatException e) {
                // 异常处理，默认使用1
            }
        }
        
        // 格式化为4位序号
        return prefix + String.format("%04d", sequence);
    }
    
    @Override
    public Map<String, Integer> getEquipmentStatusStatistics() {
        Map<String, Integer> statistics = new HashMap<>();
        // 统计各状态设备数量
        List<Map<String, Object>> list = equipmentMapper.countByStatus();
        for (Map<String, Object> map : list) {
            Integer status = ((Number) map.get("status")).intValue();
            Integer count = ((Number) map.get("count")).intValue();
            statistics.put("status_" + status, count);
        }
        return statistics;
    }
    
    @Override
    public Map<String, Integer> getEquipmentTypeStatistics() {
        Map<String, Integer> statistics = new HashMap<>();
        // 统计各类型设备数量
        List<Map<String, Object>> list = equipmentMapper.countByType();
        for (Map<String, Object> map : list) {
            String type = (String) map.get("equipment_type");
            Integer count = ((Number) map.get("count")).intValue();
            statistics.put(type, count);
        }
        return statistics;
    }
    
    @Override
    public Map<String, Integer> getEquipmentLocationStatistics() {
        Map<String, Integer> statistics = new HashMap<>();
        // 统计各区域设备数量
        List<Map<String, Object>> list = equipmentMapper.countByLocation();
        for (Map<String, Object> map : list) {
            String location = (String) map.get("location");
            Integer count = ((Number) map.get("count")).intValue();
            statistics.put(location, count);
        }
        return statistics;
    }
}
