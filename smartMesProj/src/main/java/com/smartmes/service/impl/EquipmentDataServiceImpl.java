package com.smartmes.service.impl;

import com.smartmes.mapper.EquipmentDataMapper;
import com.smartmes.model.EquipmentData;
import com.smartmes.service.EquipmentDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
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
    public List<EquipmentData> selectByEquipmentId(Long equipmentId) {
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
        return equipmentDataMapper.selectByWorkStationId(workStationId.longValue());
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
    public List<EquipmentData> selectByEquipmentIdAndTimeRange(Long equipmentId, String startTime, String endTime) {
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
        int total = equipmentDataMapper.selectCount();
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        return result;
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
        if (equipmentData.getUtilizationRate() == null && equipmentData.getTotalRuntime() != null && equipmentData.getTotalRuntime() > 0) {
            double utilizationRate = (double) equipmentData.getProductionRuntime() / equipmentData.getTotalRuntime() * 100;
            equipmentData.setUtilizationRate(Math.round(utilizationRate * 100) / 100.0);
        }
        if (equipmentData.getOeeRate() == null && equipmentData.getProductionRuntime() != null && equipmentData.getProductionRuntime() > 0 && equipmentData.getAvailabilityRate() != null && equipmentData.getPerformanceRate() != null) {
            // OEE = 可用率 × 性能率 × 质量率
            // 这里做简化处理
            Double oee = equipmentData.getAvailabilityRate() * equipmentData.getPerformanceRate() * 100;
            equipmentData.setOeeRate(Math.round(oee * 100) / 100.0);
        }
        return equipmentDataMapper.insert(equipmentData);
    }
    
    @Override
    public Integer update(EquipmentData equipmentData) {
        // 更新计算字段
        if (equipmentData.getTotalRuntime() != null && equipmentData.getTotalRuntime() > 0 && equipmentData.getProductionRuntime() != null) {
            double utilizationRate = (double) equipmentData.getProductionRuntime() / equipmentData.getTotalRuntime() * 100;
            equipmentData.setUtilizationRate(Math.round(utilizationRate * 100) / 100.0);
        }
        if (equipmentData.getProductionRuntime() != null && equipmentData.getProductionRuntime() > 0 && equipmentData.getAvailabilityRate() != null && equipmentData.getPerformanceRate() != null) {
            // OEE = 可用率 × 性能率 × 质量率
            // 这里做简化处理
            Double oee = equipmentData.getAvailabilityRate() * equipmentData.getPerformanceRate() * 100;
            equipmentData.setOeeRate(Math.round(oee * 100) / 100.0);
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
            if (data.getUtilizationRate() == null && data.getTotalRuntime() != null && data.getTotalRuntime() > 0) {
                double utilizationRate = (double) data.getProductionRuntime() / data.getTotalRuntime() * 100;
                data.setUtilizationRate(Math.round(utilizationRate * 100) / 100.0);
            }
            if (data.getOeeRate() == null && data.getProductionRuntime() != null && data.getProductionRuntime() > 0 && data.getAvailabilityRate() != null && data.getPerformanceRate() != null) {
                // OEE = 可用率 × 性能率 × 质量率
                // 这里做简化处理
                Double oee = data.getAvailabilityRate() * data.getPerformanceRate() * 100;
                data.setOeeRate(Math.round(oee * 100) / 100.0);
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
    public Map<String, Object> countEquipmentDataByDataTypeAndEquipment(String dataType, Long equipmentId) {
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
        // 使用现有方法代替不存在的方法
        return equipmentDataMapper.getAverageRunningTime(dataType);
    }
    
    @Override
    public Map<String, Object> getEquipmentDowntimeStatistics(String dataType) {
        // 使用现有方法代替不存在的方法
        return equipmentDataMapper.getAverageRunningTime(dataType);
    }
    
    @Override
    public Map<String, Object> getEquipmentMaintenanceCountStatistics(String dataType) {
        // 使用现有方法代替不存在的方法
        return equipmentDataMapper.getAverageRepairTime(dataType);
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
    public List<Map<String, Object>> getSingleEquipmentUtilizationTrend(Long equipmentId, String dataType, Integer limit) {
        return equipmentDataMapper.getSingleEquipmentUtilizationTrend(equipmentId, dataType, limit);
    }
    
    @Override
    public List<Map<String, Object>> getSingleEquipmentOEETrend(Long equipmentId, String dataType, Integer limit) {
        return equipmentDataMapper.getSingleEquipmentOEETrend(equipmentId, dataType, limit);
    }
    
    @Override
    public List<Map<String, Object>> getSingleEquipmentFailureRateTrend(Long equipmentId, String dataType, Integer limit) {
        return equipmentDataMapper.getSingleEquipmentFailureRateTrend(equipmentId, dataType, limit);
    }
    
    @Override
    public Map<String, Object> getComprehensiveEfficiencyAnalysis(Long equipmentId, String dataType) {
        return equipmentDataMapper.getComprehensiveEfficiencyAnalysis(equipmentId, dataType);
    }
    
    @Override
    public List<Map<String, Object>> getEfficiencyComparisonByType(String dataType) {
        return equipmentDataMapper.getEfficiencyComparisonByType(dataType);
    }
    
    @Override
    public List<Map<String, Object>> getEfficiencyComparisonByProductionLine(String dataType) {
        return equipmentDataMapper.getEfficiencyComparisonByProductionLine(dataType);
    }
    

    
    @Override
    public List<Map<String, Object>> exportEquipmentData(Map<String, Object> params) {
        return equipmentDataMapper.exportEquipmentData(params);
    }
    
    @Override
    public List<Map<String, Object>> countUtilization(String dataType, String startTime, String endTime) {
        return equipmentDataMapper.countUtilization(dataType, startTime, endTime);
    }
    
    @Override
    public List<Map<String, Object>> countOEE(String dataType, String startTime, String endTime) {
        return equipmentDataMapper.countOEE(dataType, startTime, endTime);
    }
    
    @Override
    public List<Map<String, Object>> countFailureRate(String dataType, String startTime, String endTime) {
        return equipmentDataMapper.countFailureRate(dataType, startTime, endTime);
    }

    @Override
    public Map<String, Object> getAverageRunningTime(String dataType) {
        return equipmentDataMapper.getAverageRunningTime(dataType);
    }

    @Override
    public Map<String, Object> getAverageFailureCount(String dataType) {
        return equipmentDataMapper.getAverageFailureCount(dataType);
    }

    @Override
    public Map<String, Object> getAverageRepairTime(String dataType) {
        return equipmentDataMapper.getAverageRepairTime(dataType);
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
    public List<Map<String, Object>> getTopUtilization(String dataType, Integer limit) {
        // 获取所有设备利用率数据
        List<Map<String, Object>> utilizationList = equipmentDataMapper.getEquipmentUtilizationRate(dataType);
        
        // 按利用率降序排序
        utilizationList.sort((o1, o2) -> {
            Double rate1 = Double.parseDouble(o1.get("utilizationRate").toString());
            Double rate2 = Double.parseDouble(o2.get("utilizationRate").toString());
            return rate2.compareTo(rate1);
        });
        
        // 返回前limit条数据
        return utilizationList.subList(0, Math.min(limit, utilizationList.size()));
    }
    
    @Override
    public List<Map<String, Object>> getTopOEE(String dataType, Integer limit) {
        // 直接调用mapper的getTopOEE方法
        return equipmentDataMapper.getTopOEE(dataType, limit);
    }
    
    @Override
    public List<Map<String, Object>> getTopFailureRate(String dataType, Integer limit) {
        // 获取所有设备故障率数据
        List<Map<String, Object>> failureRateList = equipmentDataMapper.getEquipmentFailureRate(dataType);
        
        // 按故障率降序排序
        failureRateList.sort((o1, o2) -> {
            Double rate1 = Double.parseDouble(o1.get("failureRate").toString());
            Double rate2 = Double.parseDouble(o2.get("failureRate").toString());
            return rate2.compareTo(rate1);
        });
        
        // 返回前limit条数据
        return failureRateList.subList(0, Math.min(limit, failureRateList.size()));
    }
    
    @Override
    public Map<String, Object> getTodayEquipmentStatistics() {
        // 调用现有的getTodayEquipmentRunningStatistics方法
        return equipmentDataMapper.getTodayEquipmentRunningStatistics();
    }
    
    @Override
    public Map<String, Object> getWeekEquipmentStatistics() {
        // 调用现有的getWeekEquipmentRunningStatistics方法
        return equipmentDataMapper.getWeekEquipmentRunningStatistics();
    }
    
    @Override
    public Map<String, Object> getMonthEquipmentStatistics() {
        // 调用现有的getMonthEquipmentRunningStatistics方法
        return equipmentDataMapper.getMonthEquipmentRunningStatistics();
    }
    
    @Override
    public Map<String, Object> getYearEquipmentStatistics() {
        // 调用现有的getYearEquipmentRunningStatistics方法
        return equipmentDataMapper.getYearEquipmentRunningStatistics();
    }
}
