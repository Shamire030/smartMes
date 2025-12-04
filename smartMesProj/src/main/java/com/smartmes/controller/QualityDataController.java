package com.smartmes.controller;

import com.smartmes.model.QualityData;
import com.smartmes.service.QualityDataService;
import com.smartmes.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 质量数据统计分析Controller
 */
@RestController
@RequestMapping("/api/quality-data")
public class QualityDataController {
    
    @Autowired
    private QualityDataService qualityDataService;
    
    /**
     * 根据ID查询质量数据
     */
    @GetMapping("/{id}")
    public Result<QualityData> selectById(@PathVariable Integer id) {
        try {
            QualityData data = qualityDataService.selectById(id);
            return Result.success(data);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据数据类型和周期查询质量数据
     */
    @GetMapping("/by-type-period")
    public Result<QualityData> selectByDataTypeAndPeriod(@RequestParam String dataType, @RequestParam String period) {
        try {
            QualityData data = qualityDataService.selectByDataTypeAndPeriod(dataType, period);
            return Result.success(data);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据产品ID查询质量数据
     */
    @GetMapping("/by-product/{productId}")
    public Result<List<QualityData>> selectByProductId(@PathVariable Integer productId) {
        try {
            List<QualityData> list = qualityDataService.selectByProductId(productId);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据质检类型查询质量数据
     */
    @GetMapping("/by-inspection-type/{inspectionType}")
    public Result<List<QualityData>> selectByInspectionType(@PathVariable String inspectionType) {
        try {
            List<QualityData> list = qualityDataService.selectByInspectionType(inspectionType);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据设备ID查询质量数据
     */
    @GetMapping("/by-equipment/{equipmentId}")
    public Result<List<QualityData>> selectByEquipmentId(@PathVariable Integer equipmentId) {
        try {
            List<QualityData> list = qualityDataService.selectByEquipmentId(equipmentId);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据工位ID查询质量数据
     */
    @GetMapping("/by-workstation/{workStationId}")
    public Result<List<QualityData>> selectByWorkStationId(@PathVariable Integer workStationId) {
        try {
            List<QualityData> list = qualityDataService.selectByWorkStationId(workStationId);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据操作人ID查询质量数据
     */
    @GetMapping("/by-operator/{operatorId}")
    public Result<List<QualityData>> selectByOperatorId(@PathVariable Integer operatorId) {
        try {
            List<QualityData> list = qualityDataService.selectByOperatorId(operatorId);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据时间范围查询质量数据
     */
    @GetMapping("/by-time-range")
    public Result<List<QualityData>> selectByTimeRange(@RequestParam String startTime, @RequestParam String endTime) {
        try {
            List<QualityData> list = qualityDataService.selectByTimeRange(startTime, endTime);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据产品ID和时间范围查询质量数据
     */
    @GetMapping("/by-product-time-range")
    public Result<List<QualityData>> selectByProductIdAndTimeRange(@RequestParam Integer productId, @RequestParam String startTime, @RequestParam String endTime) {
        try {
            List<QualityData> list = qualityDataService.selectByProductIdAndTimeRange(productId, startTime, endTime);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据数据类型查询质量数据列表
     */
    @GetMapping("/by-type/{dataType}")
    public Result<List<QualityData>> selectByDataType(@PathVariable String dataType) {
        try {
            List<QualityData> list = qualityDataService.selectByDataType(dataType);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 分页查询质量数据
     */
    @GetMapping("/page")
    public Result<Map<String, Object>> selectPage(@RequestParam Integer page, @RequestParam Integer limit) {
        try {
            Map<String, Object> result = qualityDataService.selectPage(page, limit);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 查询所有质量数据
     */
    @GetMapping("/all")
    public Result<List<QualityData>> selectAll() {
        try {
            List<QualityData> list = qualityDataService.selectAll();
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据条件查询质量数据
     */
    @PostMapping("/by-params")
    public Result<List<QualityData>> selectByParams(@RequestBody Map<String, Object> params) {
        try {
            List<QualityData> list = qualityDataService.selectByParams(params);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 插入质量数据
     */
    @PostMapping
    public Result<Integer> insert(@RequestBody QualityData qualityData) {
        try {
            Integer result = qualityDataService.insert(qualityData);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 更新质量数据
     */
    @PutMapping
    public Result<Integer> update(@RequestBody QualityData qualityData) {
        try {
            Integer result = qualityDataService.update(qualityData);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据ID删除质量数据
     */
    @DeleteMapping("/{id}")
    public Result<Integer> deleteById(@PathVariable Integer id) {
        try {
            Integer result = qualityDataService.deleteById(id);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 批量插入质量数据
     */
    @PostMapping("/batch-insert")
    public Result<Integer> batchInsert(@RequestBody List<QualityData> list) {
        try {
            Integer result = qualityDataService.batchInsert(list);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 批量删除质量数据
     */
    @PostMapping("/batch-delete")
    public Result<Integer> batchDelete(@RequestBody List<Integer> ids) {
        try {
            Integer result = qualityDataService.batchDelete(ids);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 生成质检编号
     */
    @GetMapping("/generate-inspection-no")
    public Result<String> generateInspectionNo() {
        try {
            String inspectionNo = qualityDataService.generateInspectionNo();
            return Result.success(inspectionNo);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据数据类型和产品统计质量数据
     */
    @GetMapping("/count-by-type-product")
    public Result<Map<String, Object>> countQualityByDataTypeAndProduct(@RequestParam String dataType, @RequestParam Integer productId) {
        try {
            Map<String, Object> result = qualityDataService.countQualityByDataTypeAndProduct(dataType, productId);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据数据类型统计各产品质量数据
     */
    @GetMapping("/count-by-product")
    public Result<List<Map<String, Object>>> countQualityByProduct(@RequestParam String dataType) {
        try {
            List<Map<String, Object>> list = qualityDataService.countQualityByProduct(dataType);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据数据类型统计各质检类型质量数据
     */
    @GetMapping("/count-by-inspection-type")
    public Result<List<Map<String, Object>>> countQualityByInspectionType(@RequestParam String dataType) {
        try {
            List<Map<String, Object>> list = qualityDataService.countQualityByInspectionType(dataType);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据数据类型统计各设备质量数据
     */
    @GetMapping("/count-by-equipment")
    public Result<List<Map<String, Object>>> countQualityByEquipment(@RequestParam String dataType) {
        try {
            List<Map<String, Object>> list = qualityDataService.countQualityByEquipment(dataType);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据数据类型统计各工位质量数据
     */
    @GetMapping("/count-by-workstation")
    public Result<List<Map<String, Object>>> countQualityByWorkStation(@RequestParam String dataType) {
        try {
            List<Map<String, Object>> list = qualityDataService.countQualityByWorkStation(dataType);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取产品质量趋势分析
     */
    @GetMapping("/product-quality-analysis")
    public Result<Map<String, Object>> getProductQualityAnalysis(@RequestParam Integer productId, @RequestParam String dataType) {
        try {
            Map<String, Object> result = qualityDataService.getProductQualityAnalysis(productId, dataType);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取设备质量趋势分析
     */
    @GetMapping("/equipment-quality-analysis")
    public Result<Map<String, Object>> getEquipmentQualityAnalysis(@RequestParam Integer equipmentId, @RequestParam String dataType) {
        try {
            Map<String, Object> result = qualityDataService.getEquipmentQualityAnalysis(equipmentId, dataType);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取质检人员工作统计
     */
    @GetMapping("/inspector-work-statistics")
    public Result<Map<String, Object>> getInspectorWorkStatistics(@RequestParam Integer inspectorId, @RequestParam String dataType) {
        try {
            Map<String, Object> result = qualityDataService.getInspectorWorkStatistics(inspectorId, dataType);
            return Result.success(result);
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
            List<Map<String, Object>> list = qualityDataService.getPassRateTrend(dataType, limit);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取缺陷类型分布
     */
    @GetMapping("/defect-type-distribution")
    public Result<List<Map<String, Object>>> getDefectTypeDistribution(@RequestParam String dataType) {
        try {
            List<Map<String, Object>> list = qualityDataService.getDefectTypeDistribution(dataType);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取主要缺陷原因分布
     */
    @GetMapping("/main-defect-reason-distribution")
    public Result<List<Map<String, Object>>> getMainDefectReasonDistribution(@RequestParam String dataType, @RequestParam Integer limit) {
        try {
            List<Map<String, Object>> list = qualityDataService.getMainDefectReasonDistribution(dataType, limit);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取产品质量趋势
     */
    @GetMapping("/quality-trend-by-product")
    public Result<List<Map<String, Object>>> getQualityTrendByProduct(@RequestParam Integer productId, @RequestParam String dataType, @RequestParam Integer limit) {
        try {
            List<Map<String, Object>> list = qualityDataService.getQualityTrendByProduct(productId, dataType, limit);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取今日质量统计
     */
    @GetMapping("/today-statistics")
    public Result<Map<String, Object>> getTodayQualityStatistics() {
        try {
            Map<String, Object> result = qualityDataService.getTodayQualityStatistics();
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取本周质量统计
     */
    @GetMapping("/week-statistics")
    public Result<Map<String, Object>> getWeekQualityStatistics() {
        try {
            Map<String, Object> result = qualityDataService.getWeekQualityStatistics();
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取本月质量统计
     */
    @GetMapping("/month-statistics")
    public Result<Map<String, Object>> getMonthQualityStatistics() {
        try {
            Map<String, Object> result = qualityDataService.getMonthQualityStatistics();
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取年度质量统计
     */
    @GetMapping("/year-statistics")
    public Result<Map<String, Object>> getYearQualityStatistics() {
        try {
            Map<String, Object> result = qualityDataService.getYearQualityStatistics();
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 导出质量数据
     */
    @PostMapping("/export")
    public Result<List<Map<String, Object>>> exportQualityData(@RequestBody Map<String, Object> params) {
        try {
            List<Map<String, Object>> list = qualityDataService.exportQualityData(params);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
