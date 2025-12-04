package com.smartmes.controller;

import com.smartmes.model.ProductionData;
import com.smartmes.service.ProductionDataService;
import com.smartmes.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 生产数据统计分析Controller
 */
@RestController
@RequestMapping("/api/production-data")
public class ProductionDataController {
    
    @Autowired
    private ProductionDataService productionDataService;
    
    /**
     * 根据ID查询生产数据
     */
    @GetMapping("/{id}")
    public Result<ProductionData> selectById(@PathVariable Integer id) {
        try {
            ProductionData data = productionDataService.selectById(id);
            return Result.success(data);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据数据类型和周期查询生产数据
     */
    @GetMapping("/by-type-period")
    public Result<ProductionData> selectByDataTypeAndPeriod(@RequestParam String dataType, @RequestParam String period) {
        try {
            ProductionData data = productionDataService.selectByDataTypeAndPeriod(dataType, period);
            return Result.success(data);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据产品ID查询生产数据
     */
    @GetMapping("/by-product/{productId}")
    public Result<List<ProductionData>> selectByProductId(@PathVariable Integer productId) {
        try {
            List<ProductionData> list = productionDataService.selectByProductId(productId);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据设备ID查询生产数据
     */
    @GetMapping("/by-equipment/{equipmentId}")
    public Result<List<ProductionData>> selectByEquipmentId(@PathVariable Integer equipmentId) {
        try {
            List<ProductionData> list = productionDataService.selectByEquipmentId(equipmentId);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据生产线ID查询生产数据
     */
    @GetMapping("/by-production-line/{productionLineId}")
    public Result<List<ProductionData>> selectByProductionLineId(@PathVariable Integer productionLineId) {
        try {
            List<ProductionData> list = productionDataService.selectByProductionLineId(productionLineId);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据工位ID查询生产数据
     */
    @GetMapping("/by-workstation/{workStationId}")
    public Result<List<ProductionData>> selectByWorkStationId(@PathVariable Integer workStationId) {
        try {
            List<ProductionData> list = productionDataService.selectByWorkStationId(workStationId);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据时间范围查询生产数据
     */
    @GetMapping("/by-time-range")
    public Result<List<ProductionData>> selectByTimeRange(@RequestParam String startTime, @RequestParam String endTime) {
        try {
            List<ProductionData> list = productionDataService.selectByTimeRange(startTime, endTime);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据产品ID和时间范围查询生产数据
     */
    @GetMapping("/by-product-time-range")
    public Result<List<ProductionData>> selectByProductIdAndTimeRange(@RequestParam Integer productId, @RequestParam String startTime, @RequestParam String endTime) {
        try {
            List<ProductionData> list = productionDataService.selectByProductIdAndTimeRange(productId, startTime, endTime);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据数据类型查询生产数据列表
     */
    @GetMapping("/by-type/{dataType}")
    public Result<List<ProductionData>> selectByDataType(@PathVariable String dataType) {
        try {
            List<ProductionData> list = productionDataService.selectByDataType(dataType);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 分页查询生产数据
     */
    @GetMapping("/page")
    public Result<Map<String, Object>> selectPage(@RequestParam Integer page, @RequestParam Integer limit) {
        try {
            Map<String, Object> result = productionDataService.selectPage(page, limit);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 查询所有生产数据
     */
    @GetMapping("/all")
    public Result<List<ProductionData>> selectAll() {
        try {
            List<ProductionData> list = productionDataService.selectAll();
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据条件查询生产数据
     */
    @PostMapping("/by-params")
    public Result<List<ProductionData>> selectByParams(@RequestBody Map<String, Object> params) {
        try {
            List<ProductionData> list = productionDataService.selectByParams(params);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 插入生产数据
     */
    @PostMapping
    public Result<Integer> insert(@RequestBody ProductionData productionData) {
        try {
            Integer result = productionDataService.insert(productionData);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 更新生产数据
     */
    @PutMapping
    public Result<Integer> update(@RequestBody ProductionData productionData) {
        try {
            Integer result = productionDataService.update(productionData);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据ID删除生产数据
     */
    @DeleteMapping("/{id}")
    public Result<Integer> deleteById(@PathVariable Integer id) {
        try {
            Integer result = productionDataService.deleteById(id);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 批量插入生产数据
     */
    @PostMapping("/batch-insert")
    public Result<Integer> batchInsert(@RequestBody List<ProductionData> list) {
        try {
            Integer result = productionDataService.batchInsert(list);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 批量删除生产数据
     */
    @PostMapping("/batch-delete")
    public Result<Integer> batchDelete(@RequestBody List<Integer> ids) {
        try {
            Integer result = productionDataService.batchDelete(ids);
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
            String dataNo = productionDataService.generateDataNo();
            return Result.success(dataNo);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据数据类型和产品统计生产数据
     */
    @GetMapping("/count-by-type-product")
    public Result<Map<String, Object>> countProductionByDataTypeAndProduct(@RequestParam String dataType, @RequestParam Integer productId) {
        try {
            Map<String, Object> result = productionDataService.countProductionByDataTypeAndProduct(dataType, productId);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据数据类型统计各产品生产数据
     */
    @GetMapping("/count-by-product")
    public Result<List<Map<String, Object>>> countProductionByProduct(@RequestParam String dataType) {
        try {
            List<Map<String, Object>> list = productionDataService.countProductionByProduct(dataType);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据数据类型统计各设备生产数据
     */
    @GetMapping("/count-by-equipment")
    public Result<List<Map<String, Object>>> countProductionByEquipment(@RequestParam String dataType) {
        try {
            List<Map<String, Object>> list = productionDataService.countProductionByEquipment(dataType);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据数据类型统计各生产线生产数据
     */
    @GetMapping("/count-by-production-line")
    public Result<List<Map<String, Object>>> countProductionByProductionLine(@RequestParam String dataType) {
        try {
            List<Map<String, Object>> list = productionDataService.countProductionByProductionLine(dataType);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取产量趋势
     */
    @GetMapping("/production-trend")
    public Result<List<Map<String, Object>>> getProductionTrend(@RequestParam String dataType, @RequestParam Integer limit) {
        try {
            List<Map<String, Object>> list = productionDataService.getProductionTrend(dataType, limit);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取合格率趋势
     */
    @GetMapping("/pass-rate-trend")
    public Result<List<Map<String, Object>>> getPassRateTrend(@RequestParam String dataType, @RequestParam Integer limit) {
        try {
            List<Map<String, Object>> list = productionDataService.getPassRateTrend(dataType, limit);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取生产效率趋势
     */
    @GetMapping("/efficiency-trend")
    public Result<List<Map<String, Object>>> getProductionEfficiencyTrend(@RequestParam String dataType, @RequestParam Integer limit) {
        try {
            List<Map<String, Object>> list = productionDataService.getProductionEfficiencyTrend(dataType, limit);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取产品产量趋势
     */
    @GetMapping("/product-yield-trend")
    public Result<List<Map<String, Object>>> getProductYieldTrend(@RequestParam Integer productId, @RequestParam String dataType, @RequestParam Integer limit) {
        try {
            List<Map<String, Object>> list = productionDataService.getProductYieldTrend(productId, dataType, limit);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取设备产量趋势
     */
    @GetMapping("/equipment-production-trend")
    public Result<List<Map<String, Object>>> getEquipmentProductionTrend(@RequestParam Integer equipmentId, @RequestParam String dataType, @RequestParam Integer limit) {
        try {
            List<Map<String, Object>> list = productionDataService.getEquipmentProductionTrend(equipmentId, dataType, limit);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取今日生产统计
     */
    @GetMapping("/today-statistics")
    public Result<Map<String, Object>> getTodayProductionStatistics() {
        try {
            Map<String, Object> result = productionDataService.getTodayProductionStatistics();
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取本周生产统计
     */
    @GetMapping("/week-statistics")
    public Result<Map<String, Object>> getWeekProductionStatistics() {
        try {
            Map<String, Object> result = productionDataService.getWeekProductionStatistics();
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取本月生产统计
     */
    @GetMapping("/month-statistics")
    public Result<Map<String, Object>> getMonthProductionStatistics() {
        try {
            Map<String, Object> result = productionDataService.getMonthProductionStatistics();
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取年度生产统计
     */
    @GetMapping("/year-statistics")
    public Result<Map<String, Object>> getYearProductionStatistics() {
        try {
            Map<String, Object> result = productionDataService.getYearProductionStatistics();
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 导出生产数据
     */
    @PostMapping("/export")
    public Result<List<Map<String, Object>>> exportProductionData(@RequestBody Map<String, Object> params) {
        try {
            List<Map<String, Object>> list = productionDataService.exportProductionData(params);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
