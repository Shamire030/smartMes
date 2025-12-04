package com.smartmes.service.impl;

import com.smartmes.mapper.EquipmentDataMapper;
import com.smartmes.model.EquipmentData;
import com.smartmes.service.EquipmentDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 设备数据统计分析Service实现类
 */
@Service
@Transactional
public class EquipmentDataServiceImpl implements EquipmentDataService {
    
    @Autowired
    private EquipmentDataMapper equipmentDataMapper;
    
    @Override
    public EquipmentData selectById(Integer id) {
        return equipmentDataMapper.selectById(id);
    }
    
    @Override
    public EquipmentData selectByDataTypeAndPeriod(String dataType, String period) {
        return equipmentDataMapper.selectByDataTypeAndPeriod(dataType, period);
    }
    
    @Override
    public List<EquipmentData> selectByEquipmentId(Integer equipmentId) {
        return equipmentDataMapper.selectByEquipmentId(equipmentId);
    }
    
    @Override
    public List<EquipmentData> selectByEquipmentType(String equipmentType) {
        return equipmentDataMapper.selectByEquipmentType(equipmentType);
    }
    
    @Override
    public List<EquipmentData> selectByProductionLineId(Integer productionLineId) {
        return equipmentDataMapper.selectByProductionLineId(productionLineId);
    }
    
    @Override
    public List<EquipmentData> selectByWorkStationId(Integer workStationId) {
        return equipmentDataMapper.selectByWorkStationId(workStationId);
    }
    
    @Override
    public List<EquipmentData> selectByResponsiblePersonId(Integer responsiblePersonId) {
        return equipmentDataMapper.selectByResponsiblePersonId(responsiblePersonId);
    }
    
    @Override
    public List<EquipmentData> selectByEquipmentStatus(String equipmentStatus) {
        return equipmentDataMapper.selectByEquipmentStatus(equipmentStatus);
    }
    
    @Override
    public List<EquipmentData> selectByTimeRange(String startTime, String endTime) {
        return equipmentDataMapper.selectByTimeRange(startTime, endTime);
    }
    
    @Override
    public List<EquipmentData> selectByEquipmentIdAndTimeRange(Integer equipmentId, String startTime, String endTime) {
        return equipmentDataMapper.selectByEquipmentIdAndTimeRange(equipmentId, startTime, endTime);
    }
    
    @Override
    public List<EquipmentData> selectByDataType(String dataType) {
        return equipmentDataMapper.selectByDataType(dataType);
    }
    
    @Override
    public Map<String, Object> selectPage(Integer page, Integer limit) {
        int start = (page - 1) * limit;
        List<EquipmentData> list = equipmentDataMapper.selectPage(start, limit);
        int total = equipmentDataMapper.count();
        return Map.of("list", list, "total", total);
    }
    
    @Override
    public List<EquipmentData> selectAll() {
        return equipmentDataMapper.selectAll();
    }
    
    @Override
    public List<EquipmentData> selectByParams(Map<String, Object> params) {
        return equipmentDataMapper.selectByParams(params);
    }
    
    @Override
    public Integer insert(EquipmentData equipmentData) {
        // 生成数据编号
        if (equipmentData.getDataNo() == null || "".equals(equipmentData.getDataNo())) {
            equipmentData.setDataNo(generateDataNo());
        }
        // 设置默认值
        if (equipmentData.getUtilizationRate() == null && equipmentData.getTotalTime() != null && equipmentData.getTotalTime() > 0) {
            double utilizationRate = (double) equipmentData.getRunningTime() / equipmentData.getTotalTime() * 100;
            equipmentData.setUtilizationRate(Math.round(utilizationRate * 100) / 100.0);
        }
        if (equipmentData.getOee() == null && equipmentData.getPlanProductionTime() != null && equipmentData.getPlanProductionTime() > 0) {
            double oee = (double) equipmentData.getActualProductionTime() / equipmentData.getPlanProductionTime() * 100;
            equipmentData.setOee(Math.round(oee * 100) / 100.0);
        }
        return equipmentDataMapper.insert(equipmentData);
    }
    
    @Override
    public Integer update(EquipmentData equipmentData) {
        // 更新计算字段
        if (equipmentData.getTotalTime() != null && equipmentData.getTotalTime() > 0 && equipmentData.getRunningTime() != null) {
            double utilizationRate = (double) equipmentData.getRunningTime() / equipmentData.getTotalTime() * 100;
            equipmentData.setUtilizationRate(Math.round(utilizationRate * 100) / 100.0);
        }
        if (equipmentData.getPlanProductionTime() != null && equipmentData.getPlanProductionTime() > 0 && equipmentData.getActualProductionTime() != null) {
            double oee = (double) equipmentData.getActualProductionTime() / equipmentData.getPlanProductionTime() * 100;
            equipmentData.setOee(Math.round(oee * 100) / 100.0);
        }
        return equipmentDataMapper.update(equipmentData);
    }
    
    @Override
    public Integer deleteById(Integer id) {
        return equipmentDataMapper.deleteById(id);
    }
    
    @Override
    public Integer batchInsert(List<EquipmentData> list) {
        // 为每个数据生成编号
        for (EquipmentData data : list) {
            if (data.getDataNo() == null || "".equals(data.getDataNo())) {
                data.setDataNo(generateDataNo());
            }
            // 设置默认值
            if (data.getUtilizationRate() == null && data.getTotalTime() != null && data.getTotalTime() > 0) {
                double utilizationRate = (double) data.getRunningTime() / data.getTotalTime() * 100;
                data.setUtilizationRate(Math.round(utilizationRate * 100) / 100.0);
            }
            if (data.getOee() == null && data.getPlanProductionTime() != null && data.getPlanProductionTime() > 0) {
                double oee = (double) data.getActualProductionTime() / data.getPlanProductionTime() * 100;
                data.setOee(Math.round(oee * 100) / 100.0);
            }
        }
        return equipmentDataMapper.batchInsert(list);
    }
    
    @Override
    public Integer batchDelete(List<Integer> ids) {
        return equipmentDataMapper.batchDelete(ids);
    }
    
    @Override
    public String generateDataNo() {
        // 生成设备数据编号，格式：EQUIP_年月日_6位随机数
        String dateStr = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.BASIC_ISO_DATE);
        String randomStr = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return "EQUIP_" + dateStr + "_" + randomStr;
    }
    
    @Override
    public Map<String, Object> countEquipmentDataByDataTypeAndEquipment(String dataType, Integer equipmentId) {
        return equipmentDataMapper.countEquipmentDataByDataTypeAndEquipment(dataType, equipmentId);
    }
    
    @Override
    public List<Map<String, Object>> countEquipmentDataByEquipment(String dataType) {
        return equipmentDataMapper.countEquipmentDataByEquipment(dataType);
    }
    
    @Override
    public List<Map<String, Object>> countEquipmentDataByEquipmentType(String dataType) {
        return equipmentDataMapper.countEquipmentDataByEquipmentType(dataType);
    }
    
    @Override
    public List<Map<String, Object>> countEquipmentDataByProductionLine(String dataType) {
        return equipmentDataMapper.countEquipmentDataByProductionLine(dataType);
    }
    
    @Override
    public Map<String, Object> getEquipmentUtilizationStatistics(String dataType) {
        return equipmentDataMapper.getEquipmentUtilizationStatistics(dataType);
    }
    
    @Override
    public Map<String, Object> getEquipmentOEEStatistics(String dataType) {
        return equipmentDataMapper.getEquipmentOEEStatistics(dataType);
    }
    
    @Override
    public Map<String, Object> getEquipmentFailureRateStatistics(String dataType) {
        return equipmentDataMapper.getEquipmentFailureRateStatistics(dataType);
    }
    
    @Override
    public Map<String, Object> getEquipmentRunningTimeStatistics(String dataType) {
        return equipmentDataMapper.getEquipmentRunningTimeStatistics(dataType);
    }
    
    @Override
    public Map<String, Object> getEquipmentDowntimeStatistics(String dataType) {
        return equipmentDataMapper.getEquipmentDowntimeStatistics(dataType);
    }
    
    @Override
    public Map<String, Object> getEquipmentMaintenanceCountStatistics(String dataType) {
        return equipmentDataMapper.getEquipmentMaintenanceCountStatistics(dataType);
    }
    
    @Override
    public List<Map<String, Object>> getEquipmentUtilizationTrend(String dataType, Integer limit) {
        return equipmentDataMapper.getEquipmentUtilizationTrend(dataType, limit);
    }
    
    @Override
    public List<Map<String, Object>> getEquipmentOEETrend(String dataType, Integer limit) {
        return equipmentDataMapper.getEquipmentOEETrend(dataType, limit);
    }
    
    @Override
    public List<Map<String, Object>> getEquipmentFailureRateTrend(String dataType, Integer limit) {
        return equipmentDataMapper.getEquipmentFailureRateTrend(dataType, limit);
    }
    
    @Override
    public List<Map<String, Object>> getEquipmentRunningTimeTrend(String dataType, Integer limit) {
        return equipmentDataMapper.getEquipmentRunningTimeTrend(dataType, limit);
    }
    
    @Override
    public List<Map<String, Object>> getSingleEquipmentUtilizationTrend(Integer equipmentId, String dataType, Integer limit) {
        return equipmentDataMapper.getSingleEquipmentUtilizationTrend(equipmentId, dataType, limit);
    }
    
    @Override
    public List<Map<String, Object>> getSingleEquipmentOEETrend(Integer equipmentId, String dataType, Integer limit) {
        return equipmentDataMapper.getSingleEquipmentOEETrend(equipmentId, dataType, limit);
    }
    
    @Override
    public Map<String, Object> getTodayEquipmentRunningStatistics() {
        return equipmentDataMapper.getTodayEquipmentRunningStatistics();
    }
    
    @Override
    public Map<String, Object> getWeekEquipmentRunningStatistics() {
        return equipmentDataMapper.getWeekEquipmentRunningStatistics();
    }
    
    @Override
    public Map<String, Object> getMonthEquipmentRunningStatistics() {
        return equipmentDataMapper.getMonthEquipmentRunningStatistics();
    }
    
    @Override
    public Map<String, Object> getYearEquipmentRunningStatistics() {
        return equipmentDataMapper.getYearEquipmentRunningStatistics();
    }
    
    @Override
    public List<Map<String, Object>> exportEquipmentData(Map<String, Object> params) {
        return equipmentDataMapper.exportEquipmentData(params);
    }
}
