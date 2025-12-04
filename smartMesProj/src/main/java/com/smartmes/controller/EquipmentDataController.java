package com.smartmes.controller;

import com.smartmes.model.EquipmentData;
import com.smartmes.service.EquipmentDataService;
import com.smartmes.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 设备数据统计分析Controller
 */
@RestController
@RequestMapping("/api/equipment-data")
public class EquipmentDataController {
    
    @Autowired
    private EquipmentDataService equipmentDataService;
    
    /**
     * 根据ID查询设备数据
     */
    @GetMapping("/{id}")
    public Result<EquipmentData> selectById(@PathVariable Integer id) {
        try {
            EquipmentData data = equipmentDataService.selectById(id);
            return Result.success(data);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据数据类型和周期查询设备数据
     */
    @GetMapping("/by-type-period")
    public Result<EquipmentData> selectByDataTypeAndPeriod(@RequestParam String dataType, @RequestParam String period) {
        try {
            EquipmentData data = equipmentDataService.selectByDataTypeAndPeriod(dataType, period);
            return Result.success(data);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据设备ID查询设备数据
     */
    @GetMapping("/by-equipment/{equipmentId}")
    public Result<List<EquipmentData>> selectByEquipmentId(@PathVariable Long equipmentId) {
        try {
            List<EquipmentData> list = equipmentDataService.selectByEquipmentId(equipmentId);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据设备类型查询设备数据
     */
    @GetMapping("/by-equipment-type/{equipmentType}")
    public Result<List<EquipmentData>> selectByEquipmentType(@PathVariable String equipmentType) {
        try {
            List<EquipmentData> list = equipmentDataService.selectByEquipmentType(equipmentType);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据工位ID查询设备数据
     */
    @GetMapping("/by-workstation/{workStationId}")
    public Result<List<EquipmentData>> selectByWorkStationId(@PathVariable Integer workStationId) {
        try {
            List<EquipmentData> list = equipmentDataService.selectByWorkStationId(workStationId);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据生产线ID查询设备数据
     */
    @GetMapping("/by-production-line/{productionLineId}")
    public Result<List<EquipmentData>> selectByProductionLineId(@PathVariable Integer productionLineId) {
        try {
            List<EquipmentData> list = equipmentDataService.selectByProductionLineId(productionLineId);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据时间范围查询设备数据
     */
    @GetMapping("/by-time-range")
    public Result<List<EquipmentData>> selectByTimeRange(@RequestParam String startTime, @RequestParam String endTime) {
        try {
            List<EquipmentData> list = equipmentDataService.selectByTimeRange(startTime, endTime);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据设备ID和时间范围查询设备数据
     */
    @GetMapping("/by-equipment-time-range")
    public Result<List<EquipmentData>> selectByEquipmentIdAndTimeRange(@RequestParam Long equipmentId, @RequestParam String startTime, @RequestParam String endTime) {
        try {
            List<EquipmentData> list = equipmentDataService.selectByEquipmentIdAndTimeRange(equipmentId, startTime, endTime);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据数据类型查询设备数据列表
     */
    @GetMapping("/by-type/{dataType}")
    public Result<List<EquipmentData>> selectByDataType(@PathVariable String dataType) {
        try {
            List<EquipmentData> list = equipmentDataService.selectByDataType(dataType);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 分页查询设备数据
     */
    @GetMapping("/page")
    public Result<Map<String, Object>> selectPage(@RequestParam Integer page, @RequestParam Integer limit) {
        try {
            Map<String, Object> result = equipmentDataService.selectPage(page, limit);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 查询所有设备数据
     */
    @GetMapping("/all")
    public Result<List<EquipmentData>> selectAll() {
        try {
            List<EquipmentData> list = equipmentDataService.selectAll();
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据条件查询设备数据
     */
    @PostMapping("/by-params")
    public Result<List<EquipmentData>> selectByParams(@RequestBody Map<String, Object> params) {
        try {
            List<EquipmentData> list = equipmentDataService.selectByParams(params);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 插入设备数据
     */
    @PostMapping
    public Result<Integer> insert(@RequestBody EquipmentData equipmentData) {
        try {
            Integer result = equipmentDataService.insert(equipmentData);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 更新设备数据
     */
    @PutMapping
    public Result<Integer> update(@RequestBody EquipmentData equipmentData) {
        try {
            Integer result = equipmentDataService.update(equipmentData);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据ID删除设备数据
     */
    @DeleteMapping("/{id}")
    public Result<Integer> deleteById(@PathVariable Integer id) {
        try {
            Integer result = equipmentDataService.deleteById(id);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 批量插入设备数据
     */
    @PostMapping("/batch-insert")
    public Result<Integer> batchInsert(@RequestBody List<EquipmentData> list) {
        try {
            Integer result = equipmentDataService.batchInsert(list);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 批量删除设备数据
     */
    @PostMapping("/batch-delete")
    public Result<Integer> batchDelete(@RequestBody List<Integer> ids) {
        try {
            Integer result = equipmentDataService.batchDelete(ids);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 生成数据编号
     */
    @GetMapping("/generate-data-no")
    public Result<String> generateDataNo() {
        try {
            String dataNo = equipmentDataService.generateDataNo();
            return Result.success(dataNo);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 统计设备利用率
     */
    @GetMapping("/count-utilization")
    public Result<List<Map<String, Object>>> countUtilization(@RequestParam String dataType, @RequestParam String startTime, @RequestParam String endTime) {
        try {
            List<Map<String, Object>> list = equipmentDataService.countUtilization(dataType, startTime, endTime);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 统计设备OEE
     */
    @GetMapping("/count-oee")
    public Result<List<Map<String, Object>>> countOEE(@RequestParam String dataType, @RequestParam String startTime, @RequestParam String endTime) {
        try {
            List<Map<String, Object>> list = equipmentDataService.countOEE(dataType, startTime, endTime);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 统计设备故障率
     */
    @GetMapping("/count-failure-rate")
    public Result<List<Map<String, Object>>> countFailureRate(@RequestParam String dataType, @RequestParam String startTime, @RequestParam String endTime) {
        try {
            List<Map<String, Object>> list = equipmentDataService.countFailureRate(dataType, startTime, endTime);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取设备利用率趋势
     */
    @GetMapping("/utilization-trend")
    public Result<List<Map<String, Object>>> getUtilizationTrend(@RequestParam Long equipmentId, @RequestParam String dataType, @RequestParam Integer limit) {
        try {
            List<Map<String, Object>> list = equipmentDataService.getSingleEquipmentUtilizationTrend(equipmentId, dataType, limit);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取设备OEE趋势
     */
    @GetMapping("/oee-trend")
    public Result<List<Map<String, Object>>> getOEEtrend(@RequestParam Long equipmentId, @RequestParam String dataType, @RequestParam Integer limit) {
        try {
            List<Map<String, Object>> list = equipmentDataService.getSingleEquipmentOEETrend(equipmentId, dataType, limit);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取设备故障率趋势
     */
    @GetMapping("/failure-rate-trend")
    public Result<List<Map<String, Object>>> getFailureRateTrend(@RequestParam Long equipmentId, @RequestParam String dataType, @RequestParam Integer limit) {
        try {
            List<Map<String, Object>> list = equipmentDataService.getSingleEquipmentFailureRateTrend(equipmentId, dataType, limit);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取设备平均运行时间
     */
    @GetMapping("/average-running-time")
    public Result<Map<String, Object>> getAverageRunningTime(@RequestParam String dataType) {
        try {
            Map<String, Object> result = equipmentDataService.getAverageRunningTime(dataType);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取设备平均故障次数
     */
    @GetMapping("/average-failure-count")
    public Result<Map<String, Object>> getAverageFailureCount(@RequestParam String dataType) {
        try {
            Map<String, Object> result = equipmentDataService.getAverageFailureCount(dataType);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取设备平均维修时间
     */
    @GetMapping("/average-repair-time")
    public Result<Map<String, Object>> getAverageRepairTime(@RequestParam String dataType) {
        try {
            Map<String, Object> result = equipmentDataService.getAverageRepairTime(dataType);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取设备利用率TOP10
     */
    @GetMapping("/top-utilization")
    public Result<List<Map<String, Object>>> getTopUtilization(@RequestParam String dataType, @RequestParam Integer limit) {
        try {
            List<Map<String, Object>> list = equipmentDataService.getTopUtilization(dataType, limit);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取设备OEE TOP10
     */
    @GetMapping("/top-oee")
    public Result<List<Map<String, Object>>> getTopOEE(@RequestParam String dataType, @RequestParam Integer limit) {
        try {
            List<Map<String, Object>> list = equipmentDataService.getTopOEE(dataType, limit);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取设备故障率TOP10
     */
    @GetMapping("/top-failure-rate")
    public Result<List<Map<String, Object>>> getTopFailureRate(@RequestParam String dataType, @RequestParam Integer limit) {
        try {
            List<Map<String, Object>> list = equipmentDataService.getTopFailureRate(dataType, limit);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取设备综合效率分析
     */
    @GetMapping("/comprehensive-efficiency-analysis")
    public Result<Map<String, Object>> getComprehensiveEfficiencyAnalysis(@RequestParam Long equipmentId, @RequestParam String dataType) {
        try {
            Map<String, Object> result = equipmentDataService.getComprehensiveEfficiencyAnalysis(equipmentId, dataType);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取设备类型效率对比
     */
    @GetMapping("/efficiency-comparison-by-type")
    public Result<List<Map<String, Object>>> getEfficiencyComparisonByType(@RequestParam String dataType) {
        try {
            List<Map<String, Object>> list = equipmentDataService.getEfficiencyComparisonByType(dataType);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取生产线设备效率对比
     */
    @GetMapping("/efficiency-comparison-by-production-line")
    public Result<List<Map<String, Object>>> getEfficiencyComparisonByProductionLine(@RequestParam String dataType) {
        try {
            List<Map<String, Object>> list = equipmentDataService.getEfficiencyComparisonByProductionLine(dataType);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取今日设备统计
     */
    @GetMapping("/today-statistics")
    public Result<Map<String, Object>> getTodayEquipmentStatistics() {
        try {
            Map<String, Object> result = equipmentDataService.getTodayEquipmentStatistics();
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取本周设备统计
     */
    @GetMapping("/week-statistics")
    public Result<Map<String, Object>> getWeekEquipmentStatistics() {
        try {
            Map<String, Object> result = equipmentDataService.getWeekEquipmentStatistics();
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取本月设备统计
     */
    @GetMapping("/month-statistics")
    public Result<Map<String, Object>> getMonthEquipmentStatistics() {
        try {
            Map<String, Object> result = equipmentDataService.getMonthEquipmentStatistics();
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取年度设备统计
     */
    @GetMapping("/year-statistics")
    public Result<Map<String, Object>> getYearEquipmentStatistics() {
        try {
            Map<String, Object> result = equipmentDataService.getYearEquipmentStatistics();
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 导出设备数据
     */
    @PostMapping("/export")
    public Result<List<Map<String, Object>>> exportEquipmentData(@RequestBody Map<String, Object> params) {
        try {
            List<Map<String, Object>> list = equipmentDataService.exportEquipmentData(params);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
