package com.smartmes.service.impl;

import com.smartmes.mapper.ProductionDataMapper;
import com.smartmes.model.ProductionData;
import com.smartmes.service.ProductionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 生产数据统计分析Service实现类
 */
@Service
public class ProductionDataServiceImpl implements ProductionDataService {

    @Autowired
    private ProductionDataMapper productionDataMapper;

    @Override
    public ProductionData selectById(Integer id) {
        return productionDataMapper.selectById(id);
    }

    @Override
    public ProductionData selectByDataTypeAndPeriod(String dataType, String period) {
        return productionDataMapper.selectByDataTypeAndPeriod(dataType, period);
    }

    @Override
    public List<ProductionData> selectByProductId(Integer productId) {
        return productionDataMapper.selectByProductId(productId);
    }

    @Override
    public List<ProductionData> selectByWorkOrderId(Integer workOrderId) {
        return productionDataMapper.selectByWorkOrderId(workOrderId);
    }

    @Override
    public List<ProductionData> selectByShift(String shift) {
        return productionDataMapper.selectByShift(shift);
    }

    @Override
    public List<ProductionData> selectByTimeRange(String startTime, String endTime) {
        return productionDataMapper.selectByTimeRange(startTime, endTime);
    }

    @Override
    public List<ProductionData> selectByProductIdAndTimeRange(Integer productId, String startTime, String endTime) {
        return productionDataMapper.selectByProductIdAndTimeRange(productId, startTime, endTime);
    }

    @Override
    public List<ProductionData> selectByDataType(String dataType) {
        return productionDataMapper.selectByDataType(dataType);
    }

    @Override
    public Map<String, Object> selectPage(Integer page, Integer limit) {
        return productionDataMapper.selectPage(page, limit);
    }

    @Override
    public List<ProductionData> selectAll() {
        return productionDataMapper.selectAll();
    }

    @Override
    public List<ProductionData> selectByParams(Map<String, Object> params) {
        return productionDataMapper.selectByParams(params);
    }

    @Override
    public Integer insert(ProductionData productionData) {
        return productionDataMapper.insert(productionData);
    }

    @Override
    public Integer update(ProductionData productionData) {
        return productionDataMapper.update(productionData);
    }

    @Override
    public Integer deleteById(Integer id) {
        return productionDataMapper.deleteById(id);
    }

    @Override
    public Integer batchInsert(List<ProductionData> list) {
        return productionDataMapper.batchInsert(list);
    }

    @Override
    public Integer batchDelete(List<Integer> ids) {
        return productionDataMapper.batchDelete(ids);
    }

    @Override
    public String generateDataCode() {
        // 生成生产数据统计编码
        String prefix = "PROD_";
        String timestamp = String.format("%tY%tm%td", new Date());
        String random = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return prefix + timestamp + random;
    }

    @Override
    public Map<String, Object> countQuantityByDataTypeAndProduct(String dataType, Integer productId) {
        return productionDataMapper.countQuantityByDataTypeAndProduct(dataType, productId);
    }

    @Override
    public List<Map<String, Object>> countQuantityByProduct(String dataType) {
        return productionDataMapper.countQuantityByProduct(dataType);
    }

    @Override
    public List<Map<String, Object>> countQuantityByWorkOrder(String dataType) {
        return productionDataMapper.countQuantityByWorkOrder(dataType);
    }

    @Override
    public List<Map<String, Object>> countQuantityByShift(String dataType) {
        return productionDataMapper.countQuantityByShift(dataType);
    }

    @Override
    public Map<String, Object> getProductionLineStatistics(Integer productionLineId, String dataType) {
        return productionDataMapper.getProductionLineStatistics(productionLineId, dataType);
    }

    @Override
    public Map<String, Object> getEquipmentStatistics(Integer equipmentId, String dataType) {
        return productionDataMapper.getEquipmentStatistics(equipmentId, dataType);
    }

    @Override
    public Map<String, Object> getWorkOrderCompletionRate(Integer workOrderId) {
        return productionDataMapper.getWorkOrderCompletionRate(workOrderId);
    }

    @Override
    public List<Map<String, Object>> getPassRateTrend(String dataType, Integer limit) {
        return productionDataMapper.getPassRateTrend(dataType, limit);
    }

    @Override
    public List<Map<String, Object>> getProductivityRateTrend(String dataType, Integer limit) {
        return productionDataMapper.getProductivityRateTrend(dataType, limit);
    }

    @Override
    public List<Map<String, Object>> getQuantityTrendByProduct(Integer productId, String dataType, Integer limit) {
        return productionDataMapper.getQuantityTrendByProduct(productId, dataType, limit);
    }

    @Override
    public Map<String, Object> getTodayProductionStatistics() {
        return productionDataMapper.getTodayProductionStatistics();
    }

    @Override
    public Map<String, Object> getWeekProductionStatistics() {
        return productionDataMapper.getWeekProductionStatistics();
    }

    @Override
    public Map<String, Object> getMonthProductionStatistics() {
        return productionDataMapper.getMonthProductionStatistics();
    }

    @Override
    public Map<String, Object> getYearProductionStatistics() {
        return productionDataMapper.getYearProductionStatistics();
    }

    @Override
    public List<Map<String, Object>> exportProductionData(Map<String, Object> params) {
        return productionDataMapper.exportProductionData(params);
    }

    // 以下是在Controller中调用但在接口中缺失的方法

    @Override
    public Map<String, Object> countProductionByDataTypeAndProduct(String dataType, Integer productId) {
        return productionDataMapper.countProductionByDataTypeAndProduct(dataType, productId);
    }

    @Override
    public List<Map<String, Object>> countProductionByProduct(String dataType) {
        return productionDataMapper.countProductionByProduct(dataType);
    }

    @Override
    public List<Map<String, Object>> countProductionByEquipment(String dataType) {
        return productionDataMapper.countProductionByEquipment(dataType);
    }

    @Override
    public List<Map<String, Object>> countProductionByProductionLine(String dataType) {
        return productionDataMapper.countProductionByProductionLine(dataType);
    }

    @Override
    public List<Map<String, Object>> getProductionTrend(String dataType, Integer limit) {
        return productionDataMapper.getProductionTrend(dataType, limit);
    }

    @Override
    public List<Map<String, Object>> getProductionEfficiencyTrend(String dataType, Integer limit) {
        return productionDataMapper.getProductionEfficiencyTrend(dataType, limit);
    }

    @Override
    public List<Map<String, Object>> getProductYieldTrend(Integer productId, String dataType, Integer limit) {
        return productionDataMapper.getProductYieldTrend(productId, dataType, limit);
    }

    @Override
    public List<Map<String, Object>> getEquipmentProductionTrend(Long equipmentId, String dataType, Integer limit) {
        return productionDataMapper.getEquipmentProductionTrend(equipmentId, dataType, limit);
    }

    @Override
    public List<ProductionData> selectByEquipmentId(Long equipmentId) {
        return productionDataMapper.selectByEquipmentId(equipmentId);
    }

    @Override
    public List<ProductionData> selectByProductionLineId(Integer productionLineId) {
        return productionDataMapper.selectByProductionLineId(productionLineId);
    }

    @Override
    public List<ProductionData> selectByWorkStationId(Integer workStationId) {
        return productionDataMapper.selectByWorkStationId(workStationId);
    }
}
