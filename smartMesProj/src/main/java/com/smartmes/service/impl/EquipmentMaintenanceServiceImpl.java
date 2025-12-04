package com.smartmes.service.impl;

import com.smartmes.mapper.EquipmentMaintenanceMapper;
import com.smartmes.model.EquipmentMaintenance;
import com.smartmes.service.EquipmentMaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 设备维护保养服务实现类
 */
@Service
public class EquipmentMaintenanceServiceImpl implements EquipmentMaintenanceService {
    
    @Autowired
    private EquipmentMaintenanceMapper equipmentMaintenanceMapper;
    
    @Override
    public EquipmentMaintenance selectById(Long id) {
        return equipmentMaintenanceMapper.selectById(id);
    }
    
    @Override
    public EquipmentMaintenance selectByMaintenanceCode(String maintenanceCode) {
        return equipmentMaintenanceMapper.selectByMaintenanceCode(maintenanceCode);
    }
    
    @Override
    public List<EquipmentMaintenance> selectByEquipmentId(Long equipmentId) {
        return equipmentMaintenanceMapper.selectByEquipmentId(equipmentId);
    }
    
    @Override
    public List<EquipmentMaintenance> selectByMaintenanceType(String maintenanceType) {
        return equipmentMaintenanceMapper.selectByMaintenanceType(maintenanceType);
    }
    
    @Override
    public List<EquipmentMaintenance> selectByStatus(Integer status) {
        return equipmentMaintenanceMapper.selectByStatus(status);
    }
    
    @Override
    public List<EquipmentMaintenance> selectByMaintenancePersonId(Long maintenancePersonId) {
        return equipmentMaintenanceMapper.selectByMaintenancePersonId(maintenancePersonId);
    }
    
    @Override
    public List<EquipmentMaintenance> selectByTimeRange(String startTime, String endTime) {
        return equipmentMaintenanceMapper.selectByTimeRange(startTime, endTime);
    }
    
    @Override
    public List<EquipmentMaintenance> selectByEquipmentIdAndTimeRange(Long equipmentId, String startTime, String endTime) {
        return equipmentMaintenanceMapper.selectByEquipmentIdAndTimeRange(equipmentId, startTime, endTime);
    }
    
    @Override
    public List<EquipmentMaintenance> selectPage(Integer pageNum, Integer pageSize) {
        int offset = (pageNum - 1) * pageSize;
        return equipmentMaintenanceMapper.selectPage(offset, pageSize);
    }
    
    @Override
    public Integer selectCount() {
        return equipmentMaintenanceMapper.selectCount();
    }
    
    @Override
    public List<EquipmentMaintenance> selectAll() {
        return equipmentMaintenanceMapper.selectAll();
    }
    
    @Override
    public List<EquipmentMaintenance> selectByParams(Map<String, Object> params) {
        return equipmentMaintenanceMapper.selectByParams(params);
    }
    
    @Transactional
    @Override
    public Integer insert(EquipmentMaintenance equipmentMaintenance) {
        // 生成保养单号
        equipmentMaintenance.setMaintenanceCode(generateMaintenanceCode());
        // 设置创建和更新时间
        Date now = new Date();
        equipmentMaintenance.setCreateTime(now);
        equipmentMaintenance.setUpdateTime(now);
        // 设置默认状态
        if (equipmentMaintenance.getStatus() == null) {
            equipmentMaintenance.setStatus(0); // 0: 待执行
        }
        return equipmentMaintenanceMapper.insert(equipmentMaintenance);
    }
    
    @Transactional
    @Override
    public Integer update(EquipmentMaintenance equipmentMaintenance) {
        // 更新时间
        equipmentMaintenance.setUpdateTime(new Date());
        return equipmentMaintenanceMapper.update(equipmentMaintenance);
    }
    
    @Transactional
    @Override
    public Integer deleteById(Long id) {
        // 检查保养记录状态，只有待执行状态可以删除
        EquipmentMaintenance maintenance = equipmentMaintenanceMapper.selectById(id);
        if (maintenance != null && maintenance.getStatus() != 0) {
            return 0; // 非待执行状态不允许删除
        }
        return equipmentMaintenanceMapper.deleteById(id);
    }
    
    @Transactional
    @Override
    public Integer updateStatus(Long id, Integer status) {
        // 状态流转控制
        EquipmentMaintenance maintenance = equipmentMaintenanceMapper.selectById(id);
        if (maintenance == null) {
            return 0;
        }
        // 这里可以添加状态流转的业务逻辑验证
        return equipmentMaintenanceMapper.updateStatus(id, status);
    }
    
    @Transactional
    @Override
    public Integer updateActualStartTime(Long id, String actualStartTime) {
        return equipmentMaintenanceMapper.updateActualStartTime(id, actualStartTime);
    }
    
    @Transactional
    @Override
    public Integer updateActualEndTime(Long id, String actualEndTime) {
        return equipmentMaintenanceMapper.updateActualEndTime(id, actualEndTime);
    }
    
    @Transactional
    @Override
    public Integer updateMaintenanceResult(Long id, String maintenanceResult, String discoveredIssues, String handlingMeasures) {
        return equipmentMaintenanceMapper.updateMaintenanceResult(id, maintenanceResult, discoveredIssues, handlingMeasures);
    }
    
    @Transactional
    @Override
    public Integer updateVerification(Long id, Long verifierId, String verifierName, String verifyTime) {
        return equipmentMaintenanceMapper.updateVerification(id, verifierId, verifierName, verifyTime);
    }
    
    @Override
    public String generateMaintenanceCode() {
        // 生成保养单号: EM + 年月日 + 4位序号
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = sdf.format(new Date());
        String prefix = "EM" + dateStr;
        
        // 查询当日最大序号
        String maxCode = equipmentMaintenanceMapper.selectMaxCodeByPrefix(prefix);
        Long sequence = 1L;
        
        if (maxCode != null && maxCode.length() >= 14) {
            try {
                sequence = Long.parseLong(maxCode.substring(10)) + 1;
            } catch (NumberFormatException e) {
                // 异常处理，默认使用1
            }
        }
        
        // 格式化为4位序号
        return prefix + String.format("%04d", sequence);
    }
    
    @Override
    public Map<String, Integer> getMaintenanceStatusStatistics() {
        Map<String, Integer> statistics = new HashMap<>();
        // 统计各状态保养记录数量
        List<Map<String, Object>> list = equipmentMaintenanceMapper.countByStatus();
        for (Map<String, Object> map : list) {
            Integer status = ((Number) map.get("status")).intValue();
            Integer count = ((Number) map.get("count")).intValue();
            statistics.put("status_" + status, count);
        }
        return statistics;
    }
    
    @Override
    public Map<String, Integer> getMaintenanceTypeStatistics() {
        Map<String, Integer> statistics = new HashMap<>();
        // 统计各类型保养记录数量
        List<Map<String, Object>> list = equipmentMaintenanceMapper.countByType();
        for (Map<String, Object> map : list) {
            String type = (String) map.get("maintenance_type");
            Integer count = ((Number) map.get("count")).intValue();
            statistics.put(type, count);
        }
        return statistics;
    }
    
    @Override
    public Map<Long, Integer> getEquipmentMaintenanceFrequencyStatistics() {
        Map<Long, Integer> statistics = new HashMap<>();
        // 统计各设备保养频次
        List<Map<String, Object>> list = equipmentMaintenanceMapper.countByEquipment();
        for (Map<String, Object> map : list) {
            Long equipmentId = ((Number) map.get("equipment_id")).longValue();
            Integer count = ((Number) map.get("count")).intValue();
            statistics.put(equipmentId, count);
        }
        return statistics;
    }
    
    @Transactional
    @Override
    public Integer startMaintenance(Long id, Long operatorId, String operatorName) {
        // 检查保养记录状态
        EquipmentMaintenance maintenance = equipmentMaintenanceMapper.selectById(id);
        if (maintenance == null || maintenance.getStatus() != 0) {
            return 0; // 只有待执行状态可以开始
        }
        
        // 更新实际开始时间和状态
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return equipmentMaintenanceMapper.updateActualStartTime(id, now);
    }
    
    @Transactional
    @Override
    public Integer completeMaintenance(Long id, String maintenanceResult, String discoveredIssues, String handlingMeasures) {
        // 检查保养记录状态
        EquipmentMaintenance maintenance = equipmentMaintenanceMapper.selectById(id);
        if (maintenance == null) {
            return 0; // 记录不存在
        }
        
        // 更新保养结果
        equipmentMaintenanceMapper.updateMaintenanceResult(id, maintenanceResult, discoveredIssues, handlingMeasures);
        
        // 更新实际结束时间和状态
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return equipmentMaintenanceMapper.updateActualEndTime(id, now);
    }
    
    @Transactional
    @Override
    public Integer verifyMaintenance(Long id, Long verifierId, String verifierName) {
        // 检查保养记录状态
        EquipmentMaintenance maintenance = equipmentMaintenanceMapper.selectById(id);
        if (maintenance == null) {
            return 0; // 记录不存在
        }
        
        // 更新验收信息
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return equipmentMaintenanceMapper.updateVerification(id, verifierId, verifierName, now);
    }
}
